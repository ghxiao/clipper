package org.semanticweb.clipper;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;


public class CompleteExamplesTest {


    /**
     * Example 2 in TR
     * <p/>
     * <p/>
     * T = {  A subclassOf (r and r3 and r4^-) some (B and A3) }
     * <p/>
     * q(X1) :- A1(X1), A2(X2), A3(X3), A4(X4), r1(X1, X4), r2(X1, X2), r3(X2, X3), r4(X3, X4).
     * <p/>
     * q can be rewritten to :
     * <p/>
     * q(X1) :- A1(X1), A(X3), A2(X3), A4(X3), r1(X1, X3), r2(X1, X3)
     */
    @Test
    public void testExample2() throws OWLOntologyCreationException, IOException {

        OWLOntologyManager owlOntologyManager = OWLManager.createOWLOntologyManager();

        IRI o1 = IRI.create("http://ghxiao.org/inst/o1");
        IRI o2 = IRI.create("http://ghxiao.org/inst/o2");
        IRI o3 = IRI.create("http://ghxiao.org/inst/o3");

        IRI a = IRI.create("http://ghxiao.org/onto/A");
        IRI a1 = IRI.create("http://ghxiao.org/onto/A1");
        IRI a2 = IRI.create("http://ghxiao.org/onto/A2");
        IRI a3 = IRI.create("http://ghxiao.org/onto/A3");
        IRI a4 = IRI.create("http://ghxiao.org/onto/A4");
        IRI b = IRI.create("http://ghxiao.org/onto/B");
        IRI r = IRI.create("http://ghxiao.org/onto/r");
        IRI r1 = IRI.create("http://ghxiao.org/onto/r1");
        IRI r2 = IRI.create("http://ghxiao.org/onto/r2");
        IRI r3 = IRI.create("http://ghxiao.org/onto/r3");
        IRI r4 = IRI.create("http://ghxiao.org/onto/r4");

        OWLOntology ontology = Ontology(owlOntologyManager,
                // TBox
                SubClassOf(Class(a), ObjectSomeValuesFrom(ObjectProperty(r), ObjectIntersectionOf(Class(b), Class(a3)))),
                SubObjectPropertyOf(ObjectProperty(r), ObjectProperty(r3)),
                SubObjectPropertyOf(ObjectProperty(r3), ObjectInverseOf(ObjectProperty(r4))),
                // ABox
                ClassAssertion(Class(a1), NamedIndividual(o1)),
                ClassAssertion(Class(a), NamedIndividual(o3)),
                ClassAssertion(Class(a2), NamedIndividual(o3)),
                ClassAssertion(Class(a4), NamedIndividual(o3)),
                ObjectPropertyAssertion(ObjectProperty(r1), NamedIndividual(o1), NamedIndividual(o3)),
                ObjectPropertyAssertion(ObjectProperty(r2), NamedIndividual(o1), NamedIndividual(o3))
        );

        String sparqlString = "PREFIX : <http://ghxiao.org/onto/> " +
                "SELECT ?X1 {" +
                "?X1 a :A1. ?X2 a :A2. ?X3 a :A3. ?X4 a :A4. " +
                "?X1 :r1 ?X4. ?X1 :r2 ?X2. ?X2 :r3 ?X3. ?X3 :r4 ?X4. " +
                "}";

        int expected = 1;

        runTest(ontology, sparqlString, expected);
    }

    /**
     * Example 3 in TR
     * <p/>
     * <p/>
     * <pre>
     * T = {
     *   A subClassOf (r and r1^- and r2^-) some B
     * }
     *
     *  q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
     * </pre>
     * <p/>
     * q can be rewritten to :
     * <p/>
     * q(X2) :- C(X2), A(X2)
     */
    @Test
    public void test_Example3() throws IOException, OWLOntologyCreationException {
        OWLOntologyManager owlOntologyManager = OWLManager.createOWLOntologyManager();

        IRI o1 = IRI.create("http://ghxiao.org/inst/o1");
        IRI o2 = IRI.create("http://ghxiao.org/inst/o2");
        IRI o3 = IRI.create("http://ghxiao.org/inst/o3");

        IRI a = IRI.create("http://ghxiao.org/onto/A");
        IRI a1 = IRI.create("http://ghxiao.org/onto/A1");
        IRI a2 = IRI.create("http://ghxiao.org/onto/A2");
        IRI a3 = IRI.create("http://ghxiao.org/onto/A3");
        IRI a4 = IRI.create("http://ghxiao.org/onto/A4");
        IRI b = IRI.create("http://ghxiao.org/onto/B");
        IRI c = IRI.create("http://ghxiao.org/onto/C");
        IRI r = IRI.create("http://ghxiao.org/onto/r");
        IRI r1 = IRI.create("http://ghxiao.org/onto/r1");
        IRI r2 = IRI.create("http://ghxiao.org/onto/r2");
        IRI r3 = IRI.create("http://ghxiao.org/onto/r3");
        IRI r4 = IRI.create("http://ghxiao.org/onto/r4");

        OWLOntology ontology = Ontology(owlOntologyManager,
                // TBox
                SubClassOf(Class(a), ObjectSomeValuesFrom(ObjectProperty(r1), ObjectIntersectionOf(Class(b)))),
                SubObjectPropertyOf(ObjectProperty(r1), ObjectInverseOf(ObjectProperty(r1))),
                //SubObjectPropertyOf(ObjectProperty(r1), ObjectInverseOf(ObjectProperty(r2))),
                SubObjectPropertyOf(ObjectProperty(r1), ObjectProperty(r2)),
                // ABox
                ClassAssertion(Class(a), NamedIndividual(o2)),
                ClassAssertion(Class(c), NamedIndividual(o2))
        );

        String sparqlString = "PREFIX : <http://ghxiao.org/onto/> " +
                "SELECT ?X1 {" +
                "?X1 a :C. ?X2 a :B. " +
                "?X1 :r1 ?X2. ?X3 :r1 ?X2. ?X2 :r2 ?X4. " +
                "}";

        int expected = 1;

        runTest(ontology, sparqlString, expected);
    }

    /**
     * Example 4 in TR
     * <p/>
     * <p/>
     * <pre>
     * T = {
     *   A subClassOf (r and r1^- and r2^-) some B,
     *   trans(r1)
     * }
     *
     *  q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
     * </pre>
     * <p/>
     * q can be rewritten to :
     * <p/>
     * q(X1) :- C(X2), r1(X1, X2), A(X2)
     */
    @Test
    public void test_Example4() throws IOException, OWLOntologyCreationException {
        OWLOntologyManager owlOntologyManager = OWLManager.createOWLOntologyManager();

        IRI o1 = IRI.create("http://ghxiao.org/inst/o1");
        IRI o2 = IRI.create("http://ghxiao.org/inst/o2");
        IRI o3 = IRI.create("http://ghxiao.org/inst/o3");

        IRI a = IRI.create("http://ghxiao.org/onto/A");
        IRI a1 = IRI.create("http://ghxiao.org/onto/A1");
        IRI a2 = IRI.create("http://ghxiao.org/onto/A2");
        IRI a3 = IRI.create("http://ghxiao.org/onto/A3");
        IRI a4 = IRI.create("http://ghxiao.org/onto/A4");
        IRI b = IRI.create("http://ghxiao.org/onto/B");
        IRI c = IRI.create("http://ghxiao.org/onto/C");
        IRI r = IRI.create("http://ghxiao.org/onto/r");
        IRI r1 = IRI.create("http://ghxiao.org/onto/r1");
        IRI r2 = IRI.create("http://ghxiao.org/onto/r2");
        IRI r3 = IRI.create("http://ghxiao.org/onto/r3");
        IRI r4 = IRI.create("http://ghxiao.org/onto/r4");

        OWLOntology ontology = Ontology(owlOntologyManager,
                // TBox
                SubClassOf(Class(a), ObjectSomeValuesFrom(ObjectProperty(r1), ObjectIntersectionOf(Class(b)))),
                SubObjectPropertyOf(ObjectProperty(r1), ObjectInverseOf(ObjectProperty(r1))),
                SubObjectPropertyOf(ObjectProperty(r1), ObjectInverseOf(ObjectProperty(r2))),
                TransitiveObjectProperty(ObjectProperty(r1)),
                // ABox
                ClassAssertion(Class(c), NamedIndividual(o1)),
                ClassAssertion(Class(a), NamedIndividual(o2)),
                ObjectPropertyAssertion(ObjectProperty(r1), NamedIndividual(o1), NamedIndividual(o2))
        );

        String sparqlString = "PREFIX : <http://ghxiao.org/onto/> " +
                "SELECT ?X1 {" +
                "?X1 a :C. ?X2 a :B. " +
                "?X1 :r1 ?X2. ?X3 :r1 ?X2. ?X2 :r2 ?X4. " +
                "}";

        int expected = 1;

        runTest(ontology, sparqlString, expected);
    }

    /**
     * Example 5 in TR
     * <p/>
     * <p/>
     * Assume T = {r ⊑ r−, trans(r), A ⊑ ∃r.B, B ⊑ ∃r.C, C ⊑ D}.
     * <p/>
     * B ⊑ ∃(r ⊓ r−).(C ⊓ D) ∈ Ξ(T).
     * <p/>
     * Let ρ : q(x) ← A(x), r(x, y), C(y), D(z), r(y, z).
     * <p/>
     * Then ρ can be rewritten to :
     * <p/>
     * ρ1 : q(x) ← A(x), r(x, y), B(y).
     * <p/>
     * ρ2 : q(x) ← A(x)
     * <p/>
     */
    @Test
    public void test_Example5() throws IOException, OWLOntologyCreationException {

        OWLOntologyManager owlOntologyManager = OWLManager.createOWLOntologyManager();

        IRI o1 = IRI.create("http://ghxiao.org/inst/o1");
        IRI o2 = IRI.create("http://ghxiao.org/inst/o2");
        IRI o3 = IRI.create("http://ghxiao.org/inst/o3");

        IRI a = IRI.create("http://ghxiao.org/onto/A");
        IRI a1 = IRI.create("http://ghxiao.org/onto/A1");
        IRI a2 = IRI.create("http://ghxiao.org/onto/A2");
        IRI a3 = IRI.create("http://ghxiao.org/onto/A3");
        IRI a4 = IRI.create("http://ghxiao.org/onto/A4");
        IRI b = IRI.create("http://ghxiao.org/onto/B");
        IRI c = IRI.create("http://ghxiao.org/onto/C");
        IRI d = IRI.create("http://ghxiao.org/onto/D");
        IRI r = IRI.create("http://ghxiao.org/onto/r");
        IRI r1 = IRI.create("http://ghxiao.org/onto/r1");
        IRI r2 = IRI.create("http://ghxiao.org/onto/r2");
        IRI r3 = IRI.create("http://ghxiao.org/onto/r3");
        IRI r4 = IRI.create("http://ghxiao.org/onto/r4");

        OWLOntology ontology = Ontology(owlOntologyManager,
                // TBox
                SubObjectPropertyOf(ObjectProperty(r1), ObjectInverseOf(ObjectProperty(r1))),
                TransitiveObjectProperty(ObjectProperty(r)),
                SubClassOf(Class(a), ObjectSomeValuesFrom(ObjectProperty(r), Class(b))),
                SubClassOf(Class(b), ObjectSomeValuesFrom(ObjectProperty(r), Class(c))),
                SubClassOf(Class(c), Class(d)),
                // ABox
                ClassAssertion(Class(a), NamedIndividual(o1))
        );

        String sparqlString = "PREFIX : <http://ghxiao.org/onto/> " +
                "SELECT ?x {" +
                "?x a :A. ?y a :C. ?z a :D. " +
                "?x :r ?y. ?y :r ?z " +
                "}";

        int expected = 1;

        runTest(ontology, sparqlString, expected);

    }

    private void runTest(OWLOntology ontology, String sparqlString, int expected) {
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();

        qaHornSHIQ.addOntology(ontology);

        List<List<String>> results = qaHornSHIQ.execQuery(sparqlString);

        System.out.println(results);

        assertEquals(expected, results.size());
    }

}
