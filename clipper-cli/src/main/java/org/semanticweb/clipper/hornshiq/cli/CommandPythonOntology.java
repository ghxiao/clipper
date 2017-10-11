package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import gnu.trove.procedure.TIntProcedure;
import org.semanticweb.clipper.hornshiq.queryanswering.*;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Set;

@Parameters(commandNames = { "pex" }, separators = "=", commandDescription = "output the Existence Axioms in the sturated ontology")
public class CommandPythonOntology extends ReasoningCommandBase {

	private CQFormatter formatter;

	public CommandPythonOntology(JCommander jc) {
		super(jc);
	}

	@Override
	boolean validate() {
		return true;
	}

	@Override
	void exec() {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy should be set after create new QAHornSHIQ
		qaHornSHIQ.setNamingStrategy(NamingStrategy.FRAGMENT);

		Set<OWLOntology> ontologies = loadOntologies();

		qaHornSHIQ.setOntologies(ontologies);

		qaHornSHIQ.preprocessOntologies();

		TBoxReasoner saturation = null;
		try {
			saturation = qaHornSHIQ.saturateTBox();
		} catch (Exception e) {
			e.printStackTrace();
		}

		IndexedEnfContainer enfs = saturation.getEnfContainer();

		formatter = new CQFormatter(NamingStrategy.FRAGMENT);

		int axid = 0;

		System.out.println("[");
		for (EnforcedRelation enf : enfs) {
			axid++;
			System.out.println(enf2Pex(enf, axid) + " ,");
		}
		System.out.println("]");
	}

	private String enf2Pex(EnforcedRelation e, int axid) {
		final int thing = ClipperManager.getInstance().getThing();
		final StringBuilder sb = new StringBuilder();
		sb.append("ExistenceAxiom(");
		sb.append("[");
		e.getType1().forEach(new TIntProcedure() {
			@Override
			public boolean execute(int arg0) {
				if (arg0 != thing) {
					sb.append("'").append(formatter.getUnaryPredicate(arg0))
							.append("', ");
				}
				return true;
			}
		});
		sb.append("], [");
		e.getRoles().forEach(new TIntProcedure() {
			@Override
			public boolean execute(int r) {
				if (r % 2 == 0)
					sb.append("('").append(formatter.getBinaryPredicate(r))
							.append("', 'name'), ");
				else
					sb.append("('").append(formatter.getBinaryPredicate(r - 1))
							.append("', 'inv'), ");
				return true;
			}
		});
		sb.append("], [");
		e.getType2().forEach(new TIntProcedure() {
			@Override
			public boolean execute(int arg0) {
				if (arg0 != thing) {

					sb.append("'").append(formatter.getUnaryPredicate(arg0))
							.append("', ");
				}
				return true;
			}
		});
		sb.append("]");
		sb.append(", ").append(axid);
		sb.append(")");

		return sb.toString();
	}

    public CQFormatter getFormatter() {
        return this.formatter;
    }
}
