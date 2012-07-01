package org.semanticweb.clipper.hornshiq.profile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.profiles.*;

/**
 * Author:Matthew Horridge<br> The University of Manchester<br> Information Management Group<br> Date: 18-Jul-2009
 * Modified by Trung Kien Tran<br>TU Wien <br> Jan-2011 
 */
public class NFHornSHIQProfile implements OWLProfile {
	OWLOntologyManager manager;
	OWLOntology ont;
	OWLObjectPropertyManager objectPropertyManager;
    private Set<IRI> allowedDatatypes = new HashSet<IRI>();

    public NFHornSHIQProfile() {
  /*    allowedDatatypes.add(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_REAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_RATIONAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DECIMAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NORMALIZED_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_TOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NCNAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NMTOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_HEX_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BASE_64_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_ANY_URI.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME_STAMP.getIRI());
        */
    }

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    public String getName() {
        return "DL Horn-SHIQ Normal Form.";
    }

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
    	manager = ontology.getOWLOntologyManager();
    	objectPropertyManager = new OWLObjectPropertyManager(manager, ontology);
    	ont=ontology;
    	OWL2DLProfile  profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        violations.addAll(report.getViolations());

        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        NFHornSHIQObjectVisitor visitor = new NFHornSHIQObjectVisitor(walker, manager);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }
    
    /**
	 * @author  admin
	 */
    private class NFHornSHIQObjectVisitor extends OWLOntologyWalkerVisitor<Object> {

        /**
		 * @uml.property  name="profileViolations"
		 */
        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();
     

        
        private NFHornSHIQObjectVisitor(OWLOntologyWalker walker,OWLOntologyManager manager) {
            super(walker);
     
        }

        /**
		 * @return
		 * @uml.property  name="profileViolations"
		 */
        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        @Override
        /* Check the use of Datatype
         * */
		public Object visit(OWLDatatype node) {
            if(!allowedDatatypes.contains(node.getIRI())) {
                profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }
        /*check the use of anonymous individual
        */
        @Override
		public Object visit(OWLAnonymousIndividual individual) {
            profileViolations.add(new UseOfAnonymousIndividual(getCurrentOntology(), getCurrentAxiom(), individual));
            return null;
        }
        
        
        /*check the use of Nominal 
        */ 
        @Override
		public Object visit(OWLObjectOneOf desc) {
             profileViolations.add(new UseOfObjectOneOf(getCurrentOntology(), getCurrentAxiom(), desc));
             return null;
        }
        /*check the use of Nominal */ 
        @Override
		public Object visit(OWLObjectUnionOf desc) {
            profileViolations.add(new UseOfObjectUnionOf(getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }
       /* check the use of Self relation
        * */
        @Override
        public Boolean visit(OWLObjectHasSelf desc) {
        	   profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),getCurrentAxiom(), desc));
               return null;
        }
        /* check the use of HasKey
         * */
        @Override
		public Object visit(OWLHasKeyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }
        
        @Override
        /* Check the use of Equivalent Constructor 
         * */
		public Object visit(OWLEquivalentClassesAxiom axiom) {
          	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }
        /* Check the use of ExactCardinality Constructor OWLObjectExactCardinality 
         * */
        public Boolean visit(OWLObjectExactCardinality desc) {
     	   profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),getCurrentAxiom(), desc));
            return null;
        }
        /* Check the use of hasValue restriction (OWLObjectHasValue) 
         * */
        public Boolean visit(OWLObjectHasValue desc) {
      	   profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),getCurrentAxiom(), desc));
             return null;
         }
        
        /* Check the use of Propery Chain 
         * */
        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
     	}
        /* Check the use of Disjoint property 
         * */
        @Override
        public Object visit(OWLDisjointObjectPropertiesAxiom  axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
        }
        
        /* Check the use of Reflexive property 
         * */
        @Override
        public Object visit(OWLReflexiveObjectPropertyAxiom  axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
        }
        
        /* Check the use of IrrReflexive property 
         * */
        @Override
        public Object visit(OWLIrreflexiveObjectPropertyAxiom  axiom) {
        profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        return null;
        }
        
        /*Check the allowed GCIs in Normal form of  Horn-SHIQ 
         * */ 
        @Override
		public Object visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression subClass=axiom.getSubClass();
            OWLClassExpression superClass=axiom.getSuperClass();
            //if subClass is a ConceptName
            if (!isNFHornSHIQSuperClassExpression(superClass))
            profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getSubClass()));
                        		
            if (!isNFHornSHIQSubClassExpression(subClass))
            profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, axiom.getSubClass()));
            return null;
          }
        
        /* Check the use of RIA with non simple roles.
         * 
         * */
  /*      public Object visit(OWLSubObjectPropertyOfAxiom  axiom) {
            OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
            OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
            if (!superProperty.isOWLTopObjectProperty())   
            if (objectPropertyManager.isNonSimple(superProperty) ||objectPropertyManager.isNonSimple(subProperty))
            profileViolations.add(new UseOfNonSimplePropertyInSubPropertyAxiom(getCurrentOntology(), axiom));
            return null;
        }*/
        @Override
		public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLDataPropertyDomainAxiom axiom) {
        	profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLClassAssertionAxiom axiom) {
            if(axiom.getClassExpression().isAnonymous()) {
                profileViolations.add(new UseOfNonAtomicClassExpression(getCurrentOntology(), axiom, axiom.getClassExpression()));
            }
            return null;
        }

        @Override
		public Object visit(OWLSameIndividualAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        @Override
		public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }
   
    
        @Override
		public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
            return null;
        }

        @Override
		public Object visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDataOneOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
		public Object visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }
    }

    
    private static class NFHornSHIQSubClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return true;
        }
        //Check subclasses  of the form 
        //A AND B
        // Where A, B are concept names.
        public Boolean visit(OWLObjectIntersectionOf desc) {
        	boolean ok=true;
        	List<OWLClassExpression> operands= desc.getOperandsAsList();
        	for(int i=0; i< operands.size();i++){	
        		if (!operands.get(i).getClassExpressionType().getName().equals("Class")) ok=false;
        	}
    		return ok;	
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
        	return false;
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return false;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }


        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return false;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    }


    /**
	 * @uml.property  name="subClassExpressionChecker"
	 * @uml.associationEnd  
	 */
    private NFHornSHIQSubClassExpressionChecker subClassExpressionChecker = new NFHornSHIQSubClassExpressionChecker();

    protected boolean isNFHornSHIQSubClassExpression(OWLClassExpression ce) {
            return ce.accept(subClassExpressionChecker);
    }
    
    
    
    /**
	 * @uml.property  name="superClassExpressionChecker"
	 * @uml.associationEnd  
	 */
    
    //==============Kien Adding ====================
  
    private NFHornSHIQSuperClassExpressionChecker superClassExpressionChecker =new NFHornSHIQSuperClassExpressionChecker();   
   
    public boolean isNFHornSHIQSuperClassExpression(OWLClassExpression ce){
    	return ce.accept(superClassExpressionChecker);
    }
      //=======================
    private class NFHornSHIQSuperClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean>{
    	public Boolean visit(OWLClass desc) {
           return true;
        }
   // 	public Boolean visit(OWLClass desc) {
   //         return true;
  //      }
        public Boolean visit(OWLObjectIntersectionOf desc) {
            return false;
        }
 
        public Boolean visit(OWLObjectComplementOf desc) {
        	return false;
        }
        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            // Check super class of the form
            // Exist R . B
            // Where R is role (not necessary a simple role),and B is a concept name
        	ClassExpressionType ct= desc.getFiller().getClassExpressionType();
        	boolean isConceptName =ct.getName().equals("Class");
        	return isConceptName;
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            // Check super class of the form
            // forAll R . A
            // Where R is role (not necessary a simple role),and A is a concept name
        	ClassExpressionType ct= desc.getFiller().getClassExpressionType();
        	boolean isConceptName =ct.getName().equals("Class");
        	return isConceptName;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
        	 return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            // check supper class of the form 
            //               <=1S.B ; 
            // where S is a simple role and B is concept name
            //
        	ClassExpressionType ct= desc.getFiller().getClassExpressionType();
        	boolean isConceptName =ct.getName().equals("Class");
            int n= desc.getCardinality();
            //boolean isS=desc.getProperty().is
            //boolean isSimpleRole=!(objectPropertyManager.isNonSimple(desc.getProperty()));
            boolean isSimpleRole=true;
            for (OWLSubObjectPropertyOfAxiom subRoleAxiom : ont.getObjectSubPropertyAxiomsForSuperProperty(desc.getProperty()))
            {
            	if (subRoleAxiom.getSubProperty().isTransitive(ont)) isSimpleRole=false;
            }
            return (n==1) && isConceptName && isSimpleRole;
                       
        }

        public Boolean visit(OWLObjectUnionOf desc) {
        	return false;
        }
        public Boolean visit(OWLObjectHasValue desc) {
        	return false;
        }
        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return false;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    	 
    }
      //=========================
}
