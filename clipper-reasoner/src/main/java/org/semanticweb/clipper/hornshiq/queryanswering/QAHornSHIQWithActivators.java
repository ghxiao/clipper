package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.aboxprofile.ABoxProfileLoader;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Set;

public class QAHornSHIQWithActivators extends QAHornSHIQ {
    public QAHornSHIQWithActivators(boolean withActivatorOptimization) {
        super(withActivatorOptimization);
    }

    @Override
    public TBoxReasoner saturateTBox() throws Exception {

        Collection<Set<Integer>> initialActivators = extractActivatorsFromProfiles(activators);

        TBoxReasoner tb;

        tb = new TBoxReasoner(clipperOntology, initialActivators);

        // ///////////////////////////////////////////////
        // Evaluate reasoning time
        long reasoningBegin = System.currentTimeMillis();
        tb.saturate();
        long reasoningEnd = System.currentTimeMillis();
        clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
        // end of evaluating reasoning time
        return tb;
    }

    @Override
    public TBoxReasoner saturateTBox(String message) throws Exception {

        Collection<Set<Integer>> initialActivators = extractActivatorsFromProfiles(activators);

        TBoxReasoner tb;

        tb = new TBoxReasoner(clipperOntology, initialActivators);

        // ///////////////////////////////////////////////
        // Evaluate reasoning time
        long reasoningBegin = System.currentTimeMillis();
        tb.saturate(this.clipperReport);
        long reasoningEnd = System.currentTimeMillis();
        clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
        // end of evaluating reasoning time
        return tb;
    }

    /**
     * @return
     */
    public TBoxReasoner saturateTBox(long timeout, String writeStatsToFile) throws Exception {

        Collection<Set<Integer>> initialActivators = extractActivatorsFromProfiles(activators);

        TBoxReasoner tb;

        tb = new TBoxReasoner(clipperOntology, initialActivators);

        long reasoningBegin = System.currentTimeMillis();

        tb.saturate(this.clipperReport);
        clipperReport.setReasoningTime(System.currentTimeMillis() - reasoningBegin);

        // end of evaluating reasoning time
        return tb;
    }

    /**
     * todo:lb
     * This method is used to extract activators from a collection of profiles supplied as a parameter
     */
    public Collection<Set<Integer>> extractActivatorsFromProfiles(Collection<Set<Resource>> prmAboxProfiles) throws Exception {
        return ABoxProfileLoader.getActivatorsFromActivatorObjects(prmAboxProfiles);
    }
}
