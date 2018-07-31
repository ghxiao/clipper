package org.semanticweb.clipper.util;

import org.junit.Test;
import uk.ac.ox.cs.JRDFox.JRDFoxException;
import uk.ac.ox.cs.JRDFox.store.DataStore;

import java.io.File;

public class RDFoxLoaderTest {

    @Test
    public void test(){
        String aboxFile = "src/test/resources/r2rml/bsbm/bsbm.ttl.abox.ttl";
        DataStore store = null;
        try {
            store = new DataStore(DataStore.StoreType.ParallelSimpleNN);
            System.out.println("Setting the number of threads...");
            store.setNumberOfThreads(2);

            System.out.println("Importing RDF data...");
            store.importFiles(new File[]{new File(aboxFile)});
            System.out.println("Number of tuples after import: " + store.getTriplesCount());


        } catch (JRDFoxException e) {
            throw new RuntimeException(e);
        } finally {
            // When no longer needed, the data store should be disposed so that all related resources are released.
            store.dispose();
        }
    }
}
