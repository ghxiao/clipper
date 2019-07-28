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

    //should return FALSE
    //axiom should not be activated by the null provided activator
    @Test
    public void applyEnfToActivator1() throws Exception {
        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));

        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(1,10,2);

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(new TIntHashSet());

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));

    }

    //should return FALSE
    //Although the axiom (enf) is activated by the provided activator
    //there exists an apporpriate activator for the child
    @Test
    public void applyEnfToActivator2() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);

        //act 1 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(1);act2.add(3);

        axiomActivatorSet.add(act2);
        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));


        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);


        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(1,10,2);

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));

    }

    //should return TRUE,
    //since the axiom(enf) is activated by the provided activator act1
    //activator for the child
    @Test
    public void applyEnfToActivator3() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);

        //act 1 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(2);act2.add(3);

        axiomActivatorSet.add(act2);
        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));


        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        TIntHashSet enfHead=new TIntHashSet();
        TIntHashSet enfBody=new TIntHashSet();


        //A-> (some) r.{B,C}
        enfBody.add(1);
        enfBody.add(0);
        enfHead.add(1);
        enfHead.add(3);
        enfHead.add(0);

        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(enfBody,10,enfHead);

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));

    }

    //should return FALSE,
    //since the axiom (enf) is not activated by the provided activator act1
    @Test
    public void applyEnfToActivator4() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);

        //act 2 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(2);act2.add(3);

        axiomActivatorSet.add(act2);
        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(4,10,4);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));
    }

    //should return TRUE,
    //since the axiom (enf) is activated by the provided activator act1
    //and there is no activator that subsumes the concepts of the enf successor
    @Test
    public void applyEnfToActivator5() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);

        //act 2 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(2);act2.add(3);

        axiomActivatorSet.add(act2);
        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));
        tbox.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(10,10,2));
        tbox.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(11,10,3));

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        //create an enforced relation to be applied to the activator
        EnforcedRelation tstEnf = new EnforcedRelation(1,10,4);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyEnfToActivator(tstAct,tstEnf));
    }

    //should return FALSE,
    //since the provided set of activator is null
    @Test
    public void applyImpToActivator1() throws Exception {

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(new TIntHashSet());

        TIntHashSet body= new TIntHashSet();

        body.add(1);
        body.add(2);
        int head=3;

        //create an enforced relation to be applied to the activator
        HornImplication tstImp = new HornImplication(body,head);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyImpToActivator(tstAct,tstImp));
    }

    //should return FALSE,
    //although axiom (imp) is activated by the provided act1
    // the head of imp is allready contained in act1, hence no update occurss to the activator
    @Test
    public void applyImpToActivator2() throws Exception {
        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));

        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);act1.add(3);

        axiomActivatorSet.add(act1);

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TIntHashSet body= new TIntHashSet();
        body.add(1);
        body.add(2);
        int head=2;

        //create an enforced relation to be applied to the activator
        HornImplication tstImp = new HornImplication(body,head);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyImpToActivator(tstAct,tstImp));
    }

    //should return FALSE,
    //since axiom (imp) is not activated by the provided act1
    @Test
    public void applyImpToActivator3() throws Exception {
        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));

        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(3);

        axiomActivatorSet.add(act1);

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TIntHashSet body= new TIntHashSet();
        body.add(1);
        body.add(2);
        int head=4;

        //create an enforced relation to be applied to the activator
        HornImplication tstImp = new HornImplication(body,head);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyImpToActivator(tstAct,tstImp));
    }

    //should return FALSE,
    //although the axiom (imp) is activated by the provided activator act1
    //the head of imp is allready contained in it
    @Test
    public void applyImpToActivator4() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);act1.add(3);

        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TIntHashSet body= new TIntHashSet();

        body.add(3);
        int head=1;

        //create an enforced relation to be applied to the activator
        HornImplication tstImp = new HornImplication(body,head);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyImpToActivator(tstAct,tstImp));
    }

    //should return TRUE,
    //since the axiom (enf) is activated by the provided activator act1
    //and the head of imp is not contained in act1, we update act1 to include it
    @Test
    public void applyImpToActivator5() throws Exception {
        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);act1.add(2);act1.add(3);

        axiomActivatorSet.add(act1);

        //tbox
        /*
           and(A,B)-> (some)r.B
           and(A,B)-> D
           D-> (some)r.B
           X-> (all)R.C
           A-1,     B-2,    C-3,    D-10,   X-11
           r-10
        */
        tbox.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(new TIntHashSet(new int[]{1,2}), 10));

        ClipperAxiomActivator tstAct = new ClipperAxiomActivator(act1);

        TIntHashSet body= new TIntHashSet();

        body.add(3);
        int head=4;

        //create an enforced relation to be applied to the activator
        HornImplication tstImp = new HornImplication(body,head);
        TBoxReasoner reasoner = new TBoxReasoner(tbox,axiomActivatorSet);

        System.out.println(reasoner.applyImpToActivator(tstAct,tstImp));
    }

    @Test
    public void testAxiomApplicable() throws Exception {

        Set<ClipperAxiomActivator> axiomActivators = new HashSet<>();

        //act 1 = {A,B}
        Set<Integer> act1 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act1.add(ClipperManager.getInstance().getThing());
        act1.add(1);
        act1.add(2);

        //act 2 = {B,C}
        Set<Integer> act2 = new HashSet<Integer>();//new TIntHashSet(new int[]{1,2});
        act2.add((ClipperManager.getInstance().getThing()));
        act2.add(2);
        act2.add(3);

        //=======================================================================
        TIntHashSet lhs = new TIntHashSet();

        //should fail, the set of activators is null but so is the body
        boolean b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs));

        System.out.println(b);
        //=======================================================================

        TIntHashSet lhs0 = new TIntHashSet();
        lhs0.add(1);
        //should fail, the set of activators is null
        b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs0));

        System.out.println(b);
        //=======================================================================

        axiomActivators.add(new ClipperAxiomActivator(act1));
        axiomActivators.add(new ClipperAxiomActivator(act2));

        //=======================================================================

        TIntHashSet lhs1 = new TIntHashSet();

        //should pass, checking if empty body is contained in the set of activators
        b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs1));

        System.out.println(b);
        //=======================================================================

        TIntHashSet lhs2 = new TIntHashSet();
        lhs2.add(1);

        //should pass, 1 is contained in act1
        b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs2));

        System.out.println(b);
        //=======================================================================

        TIntHashSet lhs3 = new TIntHashSet();
        lhs3.add(3);
        lhs3.add(1);

        //should fail, no activator contains both
        b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs3));

        System.out.println(b);
        //=======================================================================

        TIntHashSet lhs4 = new TIntHashSet();
        lhs4.add(3);

        //should pass, act2 contains the concept 3
        b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs4));

        System.out.println(b);
        //=======================================================================
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