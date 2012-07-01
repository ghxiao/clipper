package org.semanticweb.clipper.hornshiq.profile;
//package normalization;

import java.io.File;
import java.util.*;

import org.coode.owlapi.owlxmlparser.OWLSubClassAxiomElementHandler;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;


public class HornALCHIQ {

	
	private Set<OWLClass> classesInSignature; //used when creating a new fresh concept name
	private Set<OWLObjectProperty> objectPropertiesInSignature = new HashSet<OWLObjectProperty>();
	private Set<OWLSubClassOfAxiom> enforceSubClassAxioms = new HashSet<OWLSubClassOfAxiom>();// used for enforce relations
	private Set<OWLSubClassOfAxiom> impSubClassAxioms = new HashSet<OWLSubClassOfAxiom>();// used for implication relations
	private Set<OWLSubClassOfAxiom> allValuesFromAxioms = new HashSet<OWLSubClassOfAxiom>();//store value restriction axiom
	private Set<OWLSubClassOfAxiom> removedAxioms = new HashSet<OWLSubClassOfAxiom>();
	private Set<OWLInverseObjectPropertiesAxiom> inverseAxiomforNewRole = new HashSet<OWLInverseObjectPropertiesAxiom>();
	private OWLOntology ont; 
	private OWLDataFactory factory;  
	private IRI ontologyIRI;
	private OWLClass noThing;
	private OWLClass thing;
	public void transformToHornALCHIQ(){
		try{
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		//	File file = new File("/home/students/kien/MasterProject/TestData/numberRestriction.owl");         //Use in Linux
			//File file = new File("/home/students/kien/MasterProject/TestData/AboxMaxCardinalityTest.owl");
		//	File file = new File("/home/students/kien/MasterProject/TestData/MTest.owl");
		//	File file = new File("/home/students/kien/MasterProject/TestData/NormALCSHIQ1.owl");
		//	File file = new File("/home/students/kien/MasterProject/TestData/CTest3.owl");
		//	File file = new File("C:\\tmp\\RoleAssertion.owl");//use in Windows 
		//	File file = new File("/home/students/kien/MasterProject/TestData/NormTest.owl");
		//	File file = new File("C:\\tmp\\TestAlgorithm.owl");//use in Windows
		//	File file = new File("C:\\tmp\\Person.owl");//use in Windows
		//	File file = new File("C:\\tmp\\AboxMaxCardinalityTest.owl");//use in Windows
		//	File file = new File("C:\\tmp\\ValueRestriction.owl");//use in Windows
		//	File file = new File("C:\\tmp\\Inconsistence.owl");//use in Windows
		//	File file = new File("C:\\tmp\\Inconsistence1.owl");//use in Windows
		//	File file = new File("C:\\tmp\\checkRuleE14.owl");//use in Windows
			File file = new File("C:\\tmp\\NormTest.owl");//use in Windows
			ont = man.loadOntologyFromOntologyDocument(file);
			factory = man.getOWLDataFactory();
			noThing = man.getOWLDataFactory().getOWLNothing();
			thing = man.getOWLDataFactory().getOWLThing();
	        OWLOntologyWalker walker = new OWLOntologyWalker(Collections.singleton(ont));	
	        HornACLHIQWalkerVisitor visitor= new  HornACLHIQWalkerVisitor(walker);
	 //       visitor.setObjectPropertyInSignature(ont.getObjectPropertiesInSignature());
	   //     visitor.setFactory(factory);
			IRI documentIRI = man.getOntologyDocumentIRI(ont);
			OWLOntologyID ontologyID = ont.getOntologyID();
			ontologyIRI = ontologyID.getOntologyIRI();
			classesInSignature =  ont.getClassesInSignature();
			objectPropertiesInSignature.addAll(ont.getObjectPropertiesInSignature());
		//	OWLObjectProperty hasF = factory.getOWLObjectProperty(IRI.create(ontologyIRI +"#hasFather"));
		//	System.out.println(hasF);
			
		//	objectPropertiesInSignature = ont.getObjectPropertiesInSignature();
		//	objectPropertiesInSignature.remove(hasF);
		//	objectPropertiesInSignature.add(hasF);
			System.out.println("Classes in the Signature:");
			System.out.println(classesInSignature);
			System.out.println("OWLProperty in the Signature:");
			System.out.println(objectPropertiesInSignature);
			OWLOntologyFormat format = man.getOntologyFormat(ont);
			//documentIRI.resolve(arg0)
			format.asPrefixOWLOntologyFormat().getPrefixIRI(documentIRI);
		//	documentIRI.
		//	System.out.println("FORMAT: " + format.asPrefixOWLOntologyFormat().getPrefixIRI(documentIRI));
					
		//	System.out.println("    from: " + documentIRI);
			
			walker.walkStructure(visitor);
	        
	     }
		catch (OWLOntologyCreationException e) {
	        System.out.println("There was a problem loading the ontology: " + e.getMessage());
	    }
		
	}
	private class HornACLHIQWalkerVisitor extends OWLOntologyWalkerVisitor<Object> {
		public HornACLHIQWalkerVisitor(OWLOntologyWalker walker) {
			super(walker);
			//newObjectPropertiesInSignature = new HashSet<OWLObjectProperty>();
			// TODO Auto-generated constructor stub
		}
		
		
	//	private Set<OWLSubClassOfAxiom> allValuesFromAxioms = new HashSet<OWLSubClassOfAxiom>();
	
	//	private Set<OWLObjectProperty> newObjectPropertiesInSignature;
	//	private OWLDataFactory factory1;
//		public void setObjectPropertyInSignature(Set<OWLObjectProperty> s){
//			newObjectPropertiesInSignature.addAll(s);
//		}
//		public void setFactory(OWLDataFactory f){
//			factory1 =f;
//		}Set<OWLSubClassOfAxiom> enforceSubClassAxioms = new HashSet<OWLSubClassOfAxiom>();
		/* Process axiom of the form:
		 * exist r.a  \sqsubseteq b
		 * */
		public Object visit(OWLObjectSomeValuesFrom desc)
		{
			
            System.out.println("OWLObjectSomeValuesFrom: " + desc);
			OWLSubClassOfAxiom ax =  (OWLSubClassOfAxiom) getCurrentAxiom();
            // If this axiom is needed to be rewritten
			if (desc.equals(ax.getSubClass())) {
            	OWLObjectProperty r= desc.getProperty().asOWLObjectProperty();
            	
            //	newObjectPropertiesInSignature.add(r);
            	Set<OWLClass> aSet= desc.getClassesInSignature();
            	Iterator<OWLClass> it= aSet.iterator();
            	OWLClass a = it.next();
            	OWLClass b = ax.getSuperClass().asOWLClass();
            	System.out.println(r + ""+a+""+b);
            	removedAxioms.add(ax);
            	Set<OWLInverseObjectPropertiesAxiom> inverAxioms = ont.getInverseObjectPropertyAxioms(r);
            	//If there exists an inverse role of r
            	if (inverAxioms.size() >0){
            		for (OWLInverseObjectPropertiesAxiom ex: inverAxioms){
            			OWLObjectProperty rBar;
            			if(!((OWLObjectProperty) ex.getSecondProperty()).equals(r)){
            				rBar= (OWLObjectProperty) ex.getSecondProperty();}
            			else rBar= (OWLObjectProperty) ex.getFirstProperty();
    				OWLClassExpression hasAllRbarB = factory.getOWLObjectAllValuesFrom(rBar, b);
    			
    			  	OWLSubClassOfAxiom newAxiom = factory.getOWLSubClassOfAxiom(a, hasAllRbarB);
    				System.out.println(newAxiom);
    				allValuesFromAxioms.add(newAxiom);
    				
            		}
            	}
            	//If there is no inverse role of r Then we create a new role and state that it's inverse of r
            	else {
            		Random random = new Random();
            		int randomInt= random.nextInt(3000000);
            	//	Set<OWLSubClassOfAxiom> enforceSubClassAxioms = new HashSet<OWLSubClassOfAxiom>();
            		OWLObjectProperty newRbar=null;
            		boolean ok=false;
            		while (!ok){
            		
            		
            		String newString= new String(r.getIRI().getFragment()+"_inv_"+randomInt);
            	    newRbar = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "#"+ newString));
            		System.out.println("new r Bar" + newRbar);
            		System.out.println(" properties: "+ objectPropertiesInSignature);
            		ok= objectPropertiesInSignature.add(newRbar);
            		
            		}
            		
            		//create axiom of the form: a \sqsubseteq VrBar.b
            		OWLClassExpression hasAllRbarB = factory.getOWLObjectAllValuesFrom(newRbar, b);
				   	OWLSubClassOfAxiom newAxiom = factory.getOWLSubClassOfAxiom(a, hasAllRbarB);
				  	System.out.println(newAxiom);
				    allValuesFromAxioms.add(newAxiom);
				    // add inverse axiom of r and newRBar to inverseAxiomforNewRole
				    inverseAxiomforNewRole.add(factory.getOWLInverseObjectPropertiesAxiom(r, newRbar));
            	} 
            	
            	System.out.println(desc);
            }
			
			
			return null;
		}
		
		//====================================Ontology1302086618874.owl
		/* Process axiom of the form:
		 * a  \sqsubseteq >=nR.c
		 * */
		public Object visit(OWLObjectMinCardinality minCardExpress)
		{
			System.out.println("OWLObjectSomeValuesFrom: " + minCardExpress);
			OWLSubClassOfAxiom ax =  (OWLSubClassOfAxiom) getCurrentAxiom();
			
        	Set<OWLClass> aSet= minCardExpress.getClassesInSignature();
			Iterator<OWLClass> it= aSet.iterator();
        	OWLClass c = it.next();
        	OWLObjectProperty r= minCardExpress.getProperty().asOWLObjectProperty(); 
        	int n= minCardExpress.getCardinality();
        	OWLClass a= ax.getSubClass().asOWLClass();
        	
        	System.out.println(a +"Subseteq"+">="+n+" " + r + c);
   //     	private Set<OWLSubClassOfAxiom> allValuesFromAxioms = new HashSet<OWLSubClassOfAxiom>();
			
        	//Create new n fresh Class Name. To make the name different , we add a random number at the end of the name.
        	Random random = new Random();
    		ArrayList<OWLClass> freshClassNameArray = new ArrayList<OWLClass>();
        	for (int i=1;i<=n;i++){
				
				OWLClass b = null;
				boolean createdNewClassName=false;
				
				while (!createdNewClassName){
					int randomInt= random.nextInt(3000000);
					b = factory.getOWLClass(IRI.create(ontologyIRI + "#"+c.getIRI().getFragment()+"_fresh_"+randomInt));
					if (classesInSignature.add(b)){
						createdNewClassName= true;
						freshClassNameArray.add(b);
					}
					//add axiom: A \sqsubseteq exist r.Bi
					OWLClassExpression hasSomeRb = factory.getOWLObjectSomeValuesFrom(r, b);
					OWLSubClassOfAxiom hasSomeRBAxiom = factory.getOWLSubClassOfAxiom(a, hasSomeRb);
					enforceSubClassAxioms.add(hasSomeRBAxiom);
					
					//add axiom:  Bi \sqsubseteq C
					OWLSubClassOfAxiom bSubClassOfCAxiom = factory.getOWLSubClassOfAxiom(b, c);
					impSubClassAxioms.add(bSubClassOfCAxiom);
				}
			}
      //  	private Set<OWLSubClassOfAxiom> allValuesFromAxioms = new HashSet<OWLSubClassOfAxiom>();
			System.out.println(enforceSubClassAxioms);
			System.out.println(impSubClassAxioms);
			System.out.println(freshClassNameArray);
			//add axiom : Bi \cap Bj \sqsubseteq Bottom
			int numberOfFreshName = freshClassNameArray.size();
//				newObjectPropertiesInSignature.addAll(s);
//				}
//				public void setFactory(OWLDataFactory f){
//					factory1 =f;
//				}Set<OWLSubClassOfAxiom> enforceSubClassAxioms = new HashSet<OWLSubClassOfAxiom>();
			for(int i=0;i<numberOfFreshName;i++)
				for(int j=i+1;j<numberOfFreshName;j++){
					OWLClassExpression biANDbj = factory.getOWLObjectIntersectionOf(freshClassNameArray.get(i),freshClassNameArray.get(j));
					OWLSubClassOfAxiom biANDbjSubClassOfNothing = factory.getOWLSubClassOfAxiom(biANDbj, noThing);
					impSubClassAxioms.add(biANDbjSubClassOfNothing);
					System.out.println(biANDbjSubClassOfNothing);
				}
			
			System.out.println(impSubClassAxioms.size());
			return null;
		}
		
		// 
		public Object visit(OWLObjectAllValuesFrom valueRestriction)
		{
			OWLObjectProperty r = valueRestriction.getProperty().asOWLObjectProperty();
			OWLSubClassOfAxiom ax= (OWLSubClassOfAxiom) getCurrentAxiom();
			//aiSet= Intersection(Ai).
			OWLClassExpression subClassOfValueRestriction= ax.getSubClass();
			Set<OWLClass> classInValueRestriction= valueRestriction.getClassesInSignature();
			Iterator<OWLClass> it= classInValueRestriction.iterator();
			OWLClass b= it.next();
			//get the set of all sub roles of r.
			Set<OWLSubObjectPropertyOfAxiom> subObjectPropertyAxiomSet = ont.getObjectSubPropertyAxiomsForSuperProperty(r);
			if (subObjectPropertyAxiomSet.size()==0) return null;
			for (OWLSubObjectPropertyOfAxiom roleAx: subObjectPropertyAxiomSet) {
				OWLObjectProperty subRole= roleAx.getSubProperty().asOWLObjectProperty();
				//if the sub role of r is transitive
				if (subRole.isTransitive(ont)){
					removedAxioms.add(ax);
					Random random= new Random();
					OWLClass bt = null;
					boolean createdNewClassName=false;
					//---------create new atomic concept: bt
					while (!createdNewClassName){
						int randomInt= random.nextInt(3000000);
						bt = factory.getOWLClass(IRI.create(ontologyIRI + "#"+b.getIRI().getFragment()+subRole.getIRI().getFragment()+"_fresh_"+randomInt));
						//check if this name exists
						if (classesInSignature.add(bt)){
							createdNewClassName= true;
						}
					}
					// --------end of creating new atomic concept
					
					OWLClassExpression hasAllSubRoleFromBT = factory.getOWLObjectAllValuesFrom(subRole, bt);
				  	OWLSubClassOfAxiom newAxiom1 = factory.getOWLSubClassOfAxiom(subClassOfValueRestriction, hasAllSubRoleFromBT);
				  	System.out.println(newAxiom1);
				    allValuesFromAxioms.add(newAxiom1);
				    
				    OWLSubClassOfAxiom newAxiom2 = factory.getOWLSubClassOfAxiom(bt, hasAllSubRoleFromBT);
				  	System.out.println(newAxiom2);
				    allValuesFromAxioms.add(newAxiom2);
				    
				    OWLSubClassOfAxiom newAxiom3 = factory.getOWLSubClassOfAxiom(bt,b );
				  	System.out.println(newAxiom3);
				    impSubClassAxioms.add(newAxiom3);
					
				}
			}
			
			
			return null;
		}
	}
	
	public static void main(String[] arg){
		HornALCHIQ ont= new HornALCHIQ();
		ont.transformToHornALCHIQ();
	}
}
