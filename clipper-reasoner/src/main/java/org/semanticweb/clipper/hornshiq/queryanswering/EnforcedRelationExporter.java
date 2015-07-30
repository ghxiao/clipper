package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.base.Joiner;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class EnforcedRelationExporter {

    private static final OWLOntologyManager OWL_ONTOLOGY_MANAGER = OWLManager.createOWLOntologyManager();

    private static final ClipperManager clipperManager = ClipperManager.getInstance();

    private static final SymbolEncoder<OWLClass> owlClassEncoder = clipperManager.getOwlClassEncoder();

    private static final SymbolEncoder<OWLPropertyExpression> owlPropertyExpressionEncoder
            = clipperManager.getOwlPropertyExpressionEncoder();

    private static final int THING = clipperManager.getThing();

    private static final int TOP_PROPERTY = clipperManager.getTopProperty();

    private static final OWLDataFactory OWL_DATA_FACTORY = OWL_ONTOLOGY_MANAGER.getOWLDataFactory();

    private static final String ROLE_CONJUNCTION_IRI_PREFIX = "http://www.example.org/role_conjunction/#";

    LinkedHashSet<OWLSubClassOfAxiom> subClassOfAxioms;

    LinkedHashSet<OWLSubObjectPropertyOfAxiom> subObjectPropertyOfAxioms;


    public Set<OWLAxiom> export(IndexedEnfContainer enfContainer) {
        computeAxioms(enfContainer);

        LinkedHashSet<OWLAxiom> axioms = new LinkedHashSet<>();

        axioms.addAll(subClassOfAxioms);

        axioms.addAll(subObjectPropertyOfAxioms);

        return axioms;

    }

    public OWLOntology export(IndexedEnfContainer enfContainer, String iri) {

        computeAxioms(enfContainer);

        OWLOntology ontology = null;

        try {
            ontology = OWL_ONTOLOGY_MANAGER.createOntology(IRI.create(iri));

            OWL_ONTOLOGY_MANAGER.addAxioms(ontology,subClassOfAxioms);
            OWL_ONTOLOGY_MANAGER.addAxioms(ontology, subObjectPropertyOfAxioms);

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        return ontology;
    }

    private void computeAxioms(IndexedEnfContainer enfContainer) {
        subClassOfAxioms = new LinkedHashSet<>();

        subObjectPropertyOfAxioms = new LinkedHashSet<>();

        for (EnforcedRelation enf : enfContainer) {

            OWLClassExpression leftClassExpression = getOWLClassExpression(enf.getType1());

            OWLObjectPropertyExpression owlPropertyExpression = getOWLPropertyExpression(enf.getRoles());

            OWLClassExpression rightClassExpression = getOWLClassExpression(enf.getType2());

            OWLSubClassOfAxiom owlSubClassOfAxiom = OWL_DATA_FACTORY.getOWLSubClassOfAxiom(leftClassExpression,
                    OWL_DATA_FACTORY.getOWLObjectSomeValuesFrom(owlPropertyExpression, rightClassExpression));

            subClassOfAxioms.add(owlSubClassOfAxiom);
        }
    }

    private OWLClassExpression getOWLClassExpression(TIntHashSet type1) {
        Set<OWLClass> leftClasses = new HashSet<>();

        TIntIterator type1Iter = type1.iterator();

        while (type1Iter.hasNext()) {
            int i = type1Iter.next();
            if (i != THING) {
                leftClasses.add(owlClassEncoder.getSymbolByValue(i));
            }
        }

        return getConjunctionOf(leftClasses);
    }

    private OWLClassExpression getConjunctionOf(Set<OWLClass> owlClasses) {
        OWLClassExpression ret;
        if (owlClasses.size() == 0) {
            ret = OWL_DATA_FACTORY.getOWLThing();
        } else if (owlClasses.size() == 1) {
            ret = owlClasses.iterator().next();
        } else { // owlClasses.size() > 1
            ret = OWL_DATA_FACTORY.getOWLObjectIntersectionOf(owlClasses);
        }

        return ret;
    }

    private OWLObjectPropertyExpression getOWLPropertyExpression(TIntHashSet roles) {

        List<Integer> sortedRoleList = new ArrayList<>();

        for (int i : roles.toArray()) {
            if (i != TOP_PROPERTY) {
                sortedRoleList.add(i);
            }
        }

        Collections.sort(sortedRoleList);

        OWLObjectPropertyExpression owlObjectProperty;

        if(sortedRoleList.size() == 0){
            owlObjectProperty = OWL_DATA_FACTORY.getOWLTopObjectProperty();
        } else if (sortedRoleList.size() == 1){
            owlObjectProperty = (OWLObjectPropertyExpression)owlPropertyExpressionEncoder.getSymbolByValue(sortedRoleList.get(0));
        } else {
            owlObjectProperty = OWL_DATA_FACTORY.getOWLObjectProperty(
                    IRI.create(ROLE_CONJUNCTION_IRI_PREFIX, Joiner.on("_").join(sortedRoleList)));

            for (Integer integer : sortedRoleList) {
                OWLPropertyExpression propertyExpression = owlPropertyExpressionEncoder.getSymbolByValue(integer);
                OWLObjectPropertyExpression objectPropertyExpression = (OWLObjectPropertyExpression) propertyExpression;
                OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom = OWL_DATA_FACTORY.getOWLSubObjectPropertyOfAxiom(owlObjectProperty, objectPropertyExpression);
                subObjectPropertyOfAxioms.add(owlSubObjectPropertyOfAxiom);
            }
        }

        return owlObjectProperty;
    }

}
