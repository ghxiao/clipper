package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.profiles.*;

import java.net.URI;
import java.io.*;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOWLObjectRendererImpl;

/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009 Modified by Kien Date: Jan 2011
 */
public class NormalizationHornSHIQProfilesTest {
	public static void main(String[] args) {

		try {
			DefaultPrefixManager pm = new DefaultPrefixManager(
					"http://protege.cim3.net/file/pub/ontologies/tambis/tambis-full.owl#");
			// SimpleShortFormProvider sfp = new SimpleShortFormProvider();
			// SimpleRenderer ren = new SimpleRenderer();
			// ManchesterOWLSyntaxOWLObjectRendererImpl ren = new
			// ManchesterOWLSyntaxOWLObjectRendererImpl();
			// ren.setShortFormProvider(sfp);
			// ToStringRenderer.getInstance().setRenderer(ren);

			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			 OWLOntology ont =
			 man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
			File file = new File("TestData/univ-bench.owl");

			// Now load the local copy
			//OWLOntology ont = man.loadOntologyFromOntologyDocument(file);
			
			System.out.println(ont);

			// OWLOntology ont =
			// man.loadOntologyFromOntologyDocument(IRI.create("http://owl.cs.manchester.ac.uk/repository/download?ontology=file:/Users/seanb/Desktop/Cercedilla2005/hands-on/people.owl&format=RDF/XML"));
			// OWLOntology ont =
			// man.loadOntologyFromOntologyDocument(IRI.create("http://owl.cs.manchester.ac.uk/repository/download?ontology=http://www.co-ode.org/ontologies/pizza/pizza.owl"));

			System.out.println("Loaaaaaaaaaaaaaaaaaaaaded ontology");
			// check(ont, new OWL2Profile());
			// check(ont, new OWL2DLProfile());
			// check(ont, new OWL2ELProfile());
			check(ont, new HornSHIQProfile());
			// check(ont, new OWL2RLProfile());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

	private static void check(OWLOntology ont, OWLProfile profile) {
		System.out.println("Checking ontology is in " + profile.getName());
		OWLProfileReport report = profile.checkOntology(ont);
		try {
			FileWriter outFile = new FileWriter("TestData/HornSHIQPrifile.txt");
			PrintWriter out = new PrintWriter(outFile);

			// Also could be written as follows on one line
			// Printwriter out = new PrintWriter(new FileWriter(args[0]));

			// Write text to file
			out.println("CHECKING PROFILE: " + profile.getName());
			out.println(report);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(report);
		System.out
				.println("--------------------------------------------------------------------------");
	}
}
