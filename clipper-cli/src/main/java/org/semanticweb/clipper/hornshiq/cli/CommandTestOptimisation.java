/**
 * Created by bato on 2/6/19.
 */

package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQWithActivatorsFromABox;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;
import java.util.Set;

@Parameters(commandNames = { "testOptimisation" }, separators = "=", commandDescription = "tests the TBox saturation with and without activator optimization and prints out statistics")
class CommandTestOptimisation extends ReasoningCommandBase {

    public CommandTestOptimisation(JCommander jc) {
        super(jc);
    }

    @Parameter(names = { "-optimised", "-x" }, description = "run with activator optimisation, where activators are obtained from the ABox")
    private boolean withActivatorOptimisation;

    // @Parameter(names = { "--remove-redundancy", "-r" }, description =
    // "remove redundancy rules w.r.t the query")
    // private boolean removingRedundancyRules;
    @Parameter(names = { "-output-datalog", "-d" }, description = "output datalog file (if not specified, the output will be stdout)")
    private String datalog = "output.dlv";

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    void exec() {
        System.setProperty("entityExpansionLimit", "512000");

        QAHornSHIQ qaHornSHIQ;
        if (withActivatorOptimisation) {
            qaHornSHIQ = new QAHornSHIQWithActivatorsFromABox();
        } else {
            qaHornSHIQ = new QAHornSHIQ();
            // TODO: only consider related rules
        }
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(4);

        Set<OWLOntology> ontologies = loadOntologies();
        //qaHornSHIQ.setOntologyName("test");
        qaHornSHIQ.setOntologies(ontologies);

        qaHornSHIQ.preprocessOntologies();

        try {
            //
            qaHornSHIQ.saturateTBox(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
                + "  millisecond");

    }


}