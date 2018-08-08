package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bato on 7/27/17.
 */
public class TBoxReasonerTest {

    Collection<Set<Integer>> axiomActivatorSet;
    ClipperHornSHIQOntology tbox;
    @Before
    public void setUp() throws Exception {
/*      A->1      B->2        C->3
        D->4      E->5        F->6
        G->7      H->8        I->9*/

/*      r->10
        s->12
*/


        //create the clipper ontology here
        tbox = new ClipperHornSHIQOntology();

/*        //and(A,B)->C
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 3));

        //A-> forall r.C
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(1,11,3));

        //A-> exists r.C
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(1,11,3));

        //C-> <=1 r.B
        tbox.getAtomSubMinAxioms().add(new ClipperAtomSubMinAxiom(3,11,2,1));
*/


        //create the set of axiom activators here
        axiomActivatorSet = new HashSet<>();

    }

    @Test
    public void applyEnfToActivator() throws Exception {

        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);
        act1.add(2);
        axiomActivatorSet.add(act1);


        //act 1 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(2);
        act2.add(3);
        axiomActivatorSet.add(act2);


        //tbox

        /* and(A,B)-> (some)r.B
           --------------------
           and(A,B)-> newConcepts
           newConcept-> (some)r.B

           newConcept-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));

        //x- 11
        //X-> forall r.C
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));


        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);


        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(1,10,2);

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));

    }

    @Test
    public void newSaturate() throws Exception {

    }

    @Test
    public void applyDeltaTBoxToActivators() throws Exception {

    }


    @Test
    public void testFLYTBoxReasoner() throws Exception {
        String tmpDatalogFile = "src/test/resources/TestData/FlyAnatomy/fly_anatomy_rewriting.dl";
        String ontologyFile = "src/test/resources/TestData/FlyAnatomy/fly_anatomy.owl";

        testTBoxReasonerWithActivators(tmpDatalogFile, ontologyFile);

    }

    @Test
    public void testLUBMTBoxReasoner() throws Exception {
        String tmpDatalogFile = "src/test/resources/TestData/FlyAnatomy/full-lubm_rewriting.dl";
        String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

        testTBoxReasonerWithActivators(tmpDatalogFile, ontologyFile);

    }

    private void testTBoxReasonerWithActivators(String tmpDatalogFile, String ontologyFile) throws Exception {
        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ(true);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(1);

        qaHornSHIQ.setDatalogFileName(tmpDatalogFile);

        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);

        qaHornSHIQ.preprocessOntologies();

        qaHornSHIQ.saturateTBox();

        System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
                + "  millisecond");
    }

}