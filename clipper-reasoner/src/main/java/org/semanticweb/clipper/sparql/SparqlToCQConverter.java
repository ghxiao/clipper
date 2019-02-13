package org.semanticweb.clipper.sparql;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.semanticweb.clipper.hornshiq.rule.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class SparqlToCQConverter {

    private final Set<String> classesInSignature;
    private final Set<String> objectPropertiesInSignature;
    private final Set<String> dataPropertiesInSignature;

    public SparqlToCQConverter(OWLOntology ontology) {
        classesInSignature = ontology.getClassesInSignature(true).stream()
                .map(owlClass -> owlClass.getIRI().toString()).collect(toSet());
        objectPropertiesInSignature = ontology.getObjectPropertiesInSignature(true).stream()
                .map(owlClass -> owlClass.getIRI().toString()).collect(toSet());
        dataPropertiesInSignature = ontology.getDataPropertiesInSignature(true).stream()
                .map(owlClass -> owlClass.getIRI().toString()).collect(toSet());
    }

    /**
     * @param query
     */
    public CQ compileQuery(Query query) {

        List<Atom> body = new ArrayList<>();
        List<Term> ansVars = new ArrayList<>();
        List<Var> projectVars = query.getProjectVars();
        for (Var var : projectVars) {
            ansVars.add(compileTerm(var));
        }

        NonDLPredicate ans = new NonDLPredicate("ans", ansVars.size());

        // NormalPredicate ans = CacheManager.getInstance().getPredicate("ans",
        // ansVars.size());

        Atom head = new Atom(ans, ansVars);

        Element queryPattern = query.getQueryPattern();

        if (queryPattern instanceof ElementGroup) {
            ElementGroup group = (ElementGroup) queryPattern;
            List<Element> elements = group.getElements();
            for (Element ele : elements) {
                // System.out.println(ele.getClass());
                if (ele instanceof ElementPathBlock) {
                    ElementPathBlock block = (ElementPathBlock) ele;
                    Iterator<TriplePath> patternElts = block.patternElts();
                    while (patternElts.hasNext()) {
                        TriplePath triplePath = patternElts.next();
                        Triple triple = triplePath.asTriple();
                        final Atom lit = complileTriple(triple);
                        // System.out.println(triple + " ==> " + lit);
                        body.add(lit);
                    }
                    return new CQ(head, body);
                } else {
                    throw new UnsupportedOperationException();
                }

            }

        } else {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    private Atom complileTriple(Triple triple) {

        if (triple.getPredicate().getURI()
                .equals(OWLRDFVocabulary.RDF_TYPE.toString())) {
            return new Atom(compileUnaryPredicate(triple.getObject()), //
                    compileTerm(triple.getSubject()));
        } else {
            return new Atom(compileBinaryPredicate(triple.getPredicate()), //
                    compileTerm(triple.getSubject()), //
                    compileTerm(triple.getObject()));
        }

    }

    private DLPredicate compileUnaryPredicate(Node predicate) {

        if (predicate.isURI()) {
            String uri = predicate.getURI();

            return new DLPredicate(OWLManager.getOWLDataFactory()
                    .getOWLClass(IRI.create(uri)));
        } else {
            throw new IllegalArgumentException();
        }
    }


    private DLPredicate compileBinaryPredicate(Node predicate) {

        if (predicate.isURI()) {
            String uri = predicate.getURI();
            if (objectPropertiesInSignature.contains(uri)) {
                return new DLPredicate(OWLManager.getOWLDataFactory()
                        .getOWLObjectProperty(IRI.create(uri)));
            } else if(dataPropertiesInSignature.contains(uri)){
                return new DLPredicate(OWLManager.getOWLDataFactory()
                        .getOWLDataProperty(IRI.create(uri)));
            } else {
                // ?? by default, we use object properties
                return new DLPredicate(OWLManager.getOWLDataFactory()
                        .getOWLObjectProperty(IRI.create(uri)));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Term compileTerm(Node node) {
        if (node.isURI()) {
            String uri = node.getURI();
            return new Constant(quote(uri));
        } else if (node.isLiteral()) {
            String uri = node.getLiteralDatatypeURI();
            if (uri != null)
                return new Constant(quote(uri));
            else
                return new Constant(node.toString());
        } else if (node.isVariable()) {
            String name = node.getName();

            // in Datalog/DLV, variable names are capitalized
            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);

            return new Variable(capitalizedName);
        } else {
            throw new IllegalArgumentException(node.toString());
        }
    }

    private String quote(String uri) {
        final String quotedIRI = "<" + uri + ">";
        return quotedIRI;
    }

}
