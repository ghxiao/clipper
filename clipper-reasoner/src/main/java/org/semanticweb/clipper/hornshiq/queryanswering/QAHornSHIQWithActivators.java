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
        tb.saturate(false);
        long reasoningEnd = System.currentTimeMillis();
        clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
        // end of evaluating reasoning time
        return tb;
    }

    /**@param fromABox when true then Activators are extracted from ABox otherwise from Mappings
     * @return
     * @throws Exception
     */
    public TBoxReasoner saturateTBox(boolean fromABox) throws Exception {

        Collection<Set<Integer>> initialActivators;
        //if the extraction is done from ABox
        if(fromABox)
            initialActivators = activatorsFromABox;
        else
            initialActivators = activatorsFromMappings;

        TBoxReasoner tb;

        tb = new TBoxReasoner(clipperOntology, initialActivators);

        // ///////////////////////////////////////////////
        // Evaluate reasoning time
        long reasoningBegin = System.currentTimeMillis();
        //todo:implement properly the calling of appropriate methods
        // the parameter as it is now has no role inside the method, it's a meance to pick up the overloaded method which
        //implements the optimisation
        tb.saturate(fromABox);
        long reasoningEnd = System.currentTimeMillis();
        clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
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
