package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.base.Strings;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import it.unical.mat.wrapper.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.semanticweb.clipper.QueryAnsweringSystem;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.clipper.hornshiq.queryrewriting.HornALCHIQQueryRewriter;
import org.semanticweb.clipper.hornshiq.queryrewriting.HornSHIQQueryRewriter;
import org.semanticweb.clipper.hornshiq.queryrewriting.QueryRewriter;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.sparql.SparqlToCQConverter;
import org.semanticweb.clipper.util.AnswerParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.*;
import java.util.*;

public class QAHornSHIQ implements QueryAnsweringSystem {

    private final boolean withActivatorOptimization;

    private String datalogFileName;

    private String datalogEngine = "dlv";

    private String queryRewriter = "";//"old";

    private List<String> answers;
    private List<List<String>> decodedAnswers;
    private CQ cq;

    protected ClipperReport clipperReport = new ClipperReport();

    private Collection<CQ> rewrittenQueries;

    // String dlvPath = "lib/dlv";
    private String dlvPath;

    private Collection<OWLOntology> ontologies;
    protected ClipperHornSHIQOntology clipperOntology;

    private CQFormatter cqFormatter;
    private NamingStrategy namingStrategy;
    protected OWLOntology combinedNormalizedOntology;
    protected Collection<Set<Resource>> activators;


    /*Initialization of QAHornSHIQ with default behaviour,
      with optimizations on (for now only TBoxSaturation optimization is implemented)
    */
    public QAHornSHIQ() {
        this(false);
    }


    /*
     * @param withActivatorOptimization turns on/off the optimization for TBoxSaturation
     * */
    public QAHornSHIQ(boolean withActivatorOptimization) {
        decodedAnswers = new ArrayList<>();
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);// default
        this.ontologies = new ArrayList<OWLOntology>();

        setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);// default
        cqFormatter = new CQFormatter(namingStrategy);

        //ClipperManager.getInstance().reset();
        //todo:add the case on top level calling of QAHornSHIQ class for this member to facilitate turning on and off the optimization of TBoxReasoner

        this.withActivatorOptimization = withActivatorOptimization;
    }

    public boolean activatorOptimizationEnabled() {
        return withActivatorOptimization;
    }

    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
        ClipperManager.getInstance().setNamingStrategy(namingStrategy);
    }

    /**
     * return Datalog program that contains: rewritten queries, completion
     * rules, and ABox assertions
     */
    public void generateDatalog() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Encoded Input query:" + cq);
        }

        try {
            preprocessOntologies();

            TBoxReasoner tb = saturateTBox();

            //reduceOntologyToDatalog(tb);

            rewriteQuery(tb);

            reduceRewrittenQueriesToDatalog(tb);

            reduceABoxToDatalog(tb, true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return Datalog program that contains: completion rules, and ABox
     * assertions
     */
    public void generateOntologyDatalog() {

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }

        reduceOntologyToDatalog(tb);

    }

    /**
     * @return Datalog program that contains: completion rules, and ABox
     * assertions
     */
    @SuppressWarnings("unused")
    public List<CQ> rewriteOntology() {

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ReductionToDatalog reduction = new ReductionToDatalog(clipperOntology, cqFormatter);
        // reduction.setNamingStrategy(this.namingStrategy);
        reduction.setCoreImps(tb.getImpContainer().getImps());
        reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());

        List<CQ> program = reduction.getCompletionRulesDatalogProgram();
        return program;

        //reduction.saveEncodedDataLogProgram(this.datalogFileName);
    }

    private void reduceRewrittenQueriesToDatalog(TBoxReasoner tb) throws IOException {
        reduceRewrittenQueriesToDatalog(tb, false);
    }

    /**
     * @param tb
     * @throws IOException
     */
    private void reduceRewrittenQueriesToDatalog(TBoxReasoner tb, boolean append) throws IOException {
        long starCountingRelatedRule = System.currentTimeMillis();

        Set<CQ> ucq = new HashSet<CQ>(rewrittenQueries);

        ReductionToDatalog reduction = new ReductionToDatalog(clipperOntology, cqFormatter);
        // reduction.setNamingStrategy(this.namingStrategy);
        reduction.setCoreImps(tb.getImpContainer().getImps());
        reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());

        List<CQ> program = reduction.getCompletionRulesDatalogProgram();

        FileWriter fstream = new FileWriter(this.datalogFileName, append);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("% rewritten queries:\n");
        for (CQ query : ucq)
            out.write(cqFormatter.formatQuery(query) + "\n");

        out.write("% relevant rules:\n");

        List<CQ> relevantRules = RelevantRulesForUCQExtractor.relevantRules(ucq, program);

        for (CQ rule : relevantRules) {
            out.write(cqFormatter.formatQuery(rule) + "\n");
        }

        out.close();
    }

    /**
     * @param tb
     */
    private void rewriteQuery(TBoxReasoner tb) {
        QueryRewriter qr = createQueryRewriter(clipperOntology, tb);

        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%rewritten queries:");
        }
        // /////////////////////////////////////////////////////////
        // Evaluate query rewriting time
        long rewritingBegin = System.currentTimeMillis();
        this.rewrittenQueries = qr.rewrite(cq);
        long rewritingEnd = System.currentTimeMillis();
        clipperReport.setQueryRewritingTime(rewritingEnd - rewritingBegin);
        // End of evaluating query rewriting time
        // /////////////////////////////////////////////////////////

        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println(rewrittenQueries);
        }
    }

    /**
     * @param tb
     */
    private void reduceOntologyToDatalog(TBoxReasoner tb) {
        ReductionToDatalog reduction = new ReductionToDatalog(clipperOntology, cqFormatter);
        // reduction.setNamingStrategy(this.namingStrategy);
        reduction.setCoreImps(tb.getImpContainer().getImps());
        reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());
        reduction.saveEncodedDataLogProgram(this.datalogFileName);
    }

    /**
     * @param tb
     */
    private void reduceTBoxToDatalog(TBoxReasoner tb) {
        ReductionToDatalog reduction = new ReductionToDatalog(clipperOntology, cqFormatter);
        // reduction.setNamingStrategy(this.namingStrategy);
        reduction.setCoreImps(tb.getImpContainer().getImps());
        reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());
        // reduction.saveEncodedDataLogProgram(this.dataLogName);
        reduction.getCompletionRulesDatalogProgram();

        // FIXME: output to this.datalogFileName
    }

    /**
     * @param tb
     */
    private void reduceABoxToDatalog(TBoxReasoner tb, boolean append) {
        ReductionToDatalog reduction = new ReductionToDatalog(clipperOntology, cqFormatter);
        // reduction.setNamingStrategy(this.namingStrategy);
        reduction.setCoreImps(tb.getImpContainer().getImps());
        reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());
        // reduction.saveEncodedDataLogProgram(this.dataLogName);
        //reduction.getABoxAssertionsDatalogProgram();

        List<CQ> facts = reduction.rulesFromABoxAssertions();


        FileWriter fstream;
        try {
            fstream = new FileWriter(this.datalogFileName, append);

            BufferedWriter out = new BufferedWriter(fstream);

            out.write("% facts:\n");

            for (CQ fact : facts) {
                out.write(cqFormatter.formatQuery(fact) + "\n");
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return
     */
    public TBoxReasoner saturateTBox() throws Exception {

        TBoxReasoner tb = new TBoxReasoner(clipperOntology);
        // ///////////////////////////////////////////////
        // Evaluate reasoning time
        long reasoningBegin = System.currentTimeMillis();
        tb.saturate();
        long reasoningEnd = System.currentTimeMillis();
        clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
        // end of evaluating reasoning time
        return tb;
    }

    /**
     * @param onto_bs
     * @param tb
     * @return
     */
    private QueryRewriter createQueryRewriter(ClipperHornSHIQOntology onto_bs, TBoxReasoner tb) {
        QueryRewriter qr;
        if (queryRewriter.equals("old")) {
            qr = new HornALCHIQQueryRewriter(tb.getEnfContainer(), tb.getInverseRoleAxioms(), tb.getAllValuesFromAxioms());
        } else {
            qr = new HornSHIQQueryRewriter(onto_bs, tb.getEnfContainer());
        }
        return qr;
    }

    /**
     * @param ontology
     * @return
     */
    private OWLOntology normalize(OWLOntology ontology) {

        HornSHIQProfile profile = new HornSHIQProfile();

        OWLProfileReport report = profile.checkOntology(ontology);
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println(report);
        }

        HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

        OWLOntology normalizedOnt = normalizer.normalize(ontology);

        HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
        OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

        HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
        OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
        return normalizedOnt3;
    }

    /**
     * @return Datalog program contains: rewritten queries, completion rules,
     * (no facts)
     */
    public void generateQueriesAndCompletionRulesDatalog() {

        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Encoded Input query:" + cq);
        }

        try {
            preprocessOntologies();

            TBoxReasoner tb = saturateTBox();

            reduceTBoxToDatalog(tb);

            rewriteQuery(tb);

            reduceRewrittenQueriesToDatalog(tb);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return Datalog program contains: rewritten queries, completion rules
     */
    public void generateTBoxRulesDatalog() {

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }

        reduceTBoxToDatalog(tb);

    }

    /**
     * @return Datalog program contains: rewritten queries, related rules
     */
    public void getQueriesAndRelatedRulesDataLog() {

        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Encoded Input query:" + cq);
        }

        try {
            preprocessOntologies();

            TBoxReasoner tb = saturateTBox();

            reduceTBoxToDatalog(tb);

            rewriteQuery(tb);

            reduceRewrittenQueriesToDatalog(tb);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return Datalog program contains only Abox assertions
     */
    public void generateABoxDatalog() {

        //this.headPredicate = cq.getHead().getPredicate().toString();
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Encoded Input query:" + cq);
        }

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }

        reduceOntologyToDatalog(tb);

        // rewriteQuery(tb);

        reduceABoxToDatalog(tb, true);
    }

    public List<List<String>> execQuery(String sparqlString) {
        Query query = QueryFactory.create(sparqlString);
        CQ cq = new SparqlToCQConverter(ontologies.iterator().next()).compileQuery(query);

        this.setQuery(cq);

        if (Strings.isNullOrEmpty(datalogFileName)) {
            this.setDatalogFileName("temp.dl");
        }

        return this.execQuery();
    }

    @Override
    public List<List<String>> execQuery() {

        generateDatalog();

        this.answers = new ArrayList<>();

        DLVInputProgram inputProgram = new DLVInputProgramImpl();
        String outPutNotification = "";

        /* I can add some file to the DLVInputProgram */

        inputProgram.addFile(this.datalogFileName);

        ensureDlvPath();

        DLVInvocation invocation = DLVWrapper.getInstance().createInvocation(dlvPath);
        /* I can specify a part of DLV program using simple strings */

        // Creates an instance of DLVInvocation

        // Creates an instance of DLVInputProgram
        if (ClipperManager.getInstance().getVerboseLevel() > 1)
            System.out.println("===========Answers for the query ========");

        try {
            invocation.setInputProgram(inputProgram);
            invocation.setNumberOfModels(1);
            List<String> filters = new ArrayList<String>();
            // filters.add(this.headPredicate);
            filters.add(cq.getHead().getPredicate().toString());
            invocation.setFilter(filters, true);
            ModelBufferedHandler modelBufferedHandler = new ModelBufferedHandler(invocation);

            this.answers = new ArrayList<>();

            /* In this moment I can start the DLV execution */
            FactHandler factHandler = (obsd, res) -> {
                String answerString = res.toString();
                if (ClipperManager.getInstance().getVerboseLevel() > 1)
                    System.out.println(answerString);
                answers.add(answerString);
            };

            invocation.subscribe(factHandler);
            // Roughly datalog program evalutaion
            long dlvBegin = System.currentTimeMillis();
            invocation.run();
            long dlvEng = System.currentTimeMillis();
            clipperReport.setDatalogRunTime(dlvEng - dlvBegin);
            // Roughly datalog program evalutaion
            if (!modelBufferedHandler.hasMoreModels()) {
                outPutNotification = "No model";
                if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
                    System.out.println("No model");
                }
            }
            invocation.waitUntilExecutionFinishes();
            List<DLVError> k = invocation.getErrors();
            if (k.size() > 0) {
                if (ClipperManager.getInstance().getVerboseLevel() >= 0) {
                    System.out.println(k);
                }
                outPutNotification = k.toString();
            }

        } catch (DLVInvocationException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long starOutputAnswer = System.currentTimeMillis();
        AnswerParser answerParser = new AnswerParser(namingStrategy);

        this.decodedAnswers.clear();
        answerParser.setAnswers(this.answers);
        answerParser.parse();
        this.decodedAnswers = answerParser.getDecodedAnswers();

        if (ClipperManager.getInstance().getVerboseLevel() > 1) {
            System.out.println("=============Decoded answers ==============");
        }

        BufferedWriter bufferedWriter = null;

        try {
            // Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(datalogFileName + "-answer.txt"));
            bufferedWriter.write(outPutNotification);
            // Start writing to the output stream
            for (List<String> ans : decodedAnswers) {
                bufferedWriter.write(ans.toString());
                bufferedWriter.newLine();
            }

            long endOutputAnswer = System.currentTimeMillis();
            this.clipperReport.setOutputAnswerTime(endOutputAnswer - starOutputAnswer);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return decodedAnswers;

    }

    /**
     * if dlvPath is not set, then we use ~/bin/dlv or /usr/bin/dlv
     */
    private void ensureDlvPath() {

        if (dlvPath != null) {
            if (new File(dlvPath).exists()) {
                return;
            }
            throw new IllegalStateException("DLV file " + dlvPath + " does not exist");
        } else {
            dlvPath = System.getenv("HOME") + "/bin/dlv";
            if (new File(dlvPath).exists()) {
                return;
            }
            dlvPath = "/usr/bin/dlv";
            if (new File(dlvPath).exists()) {
                return;
            }
            throw new IllegalStateException("dlv path is not set, and not on ~/bin/dlv or /usr/bin/dlv");
        }

    }

    @Override
    public void addOntology(OWLOntology ontology) {
        this.ontologies.add(ontology);
    }


    public void preprocessOntologies() {
        long startNormalizationTime = System.currentTimeMillis();
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        combinedNormalizedOntology = null;
        try {
            combinedNormalizedOntology = manager.createOntology();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        for (OWLOntology ontology : ontologies) {
            for (OWLOntology ontologyInImportsClosure : ontology.getImportsClosure()) {
                OWLOntology normalizedOntology = normalize(ontologyInImportsClosure);
                manager.addAxioms(combinedNormalizedOntology, normalizedOntology.getAxioms());
            }
        }

        initializeActivators();

        ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
        ClipperHornSHIQOntology onto_bs = converter.convert(combinedNormalizedOntology);

        this.clipperOntology = onto_bs;

        long endNormalizationTime = System.currentTimeMillis();
        this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizationTime);

    }


    protected void initializeActivators() {
        // do nothing
    }


    /**
     * @return exports saturated enforce relations
     */
    public OWLOntology exportSaturatedEnforceRelations(String iri) {

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EnforcedRelationExporter exporter = new EnforcedRelationExporter();
        return exporter.export(tb.getEnfContainer(), iri);
    }


    /**
     * Exports the Logical Axioms in normalized ontologies and all the enforce relations
     *
     * @return
     */
    public OWLOntology exportNormalizedAxiomsAndSaturatedEnforceRelations(String iri) {

        //this.headPredicate = cq.getHead().getPredicate().toString();
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Encoded Input query:" + cq);
        }

        preprocessOntologies();

        TBoxReasoner tb = null;
        try {
            tb = saturateTBox();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EnforcedRelationExporter exporter = new EnforcedRelationExporter();

        Set<OWLAxiom> owlAxioms = exporter.export(tb.getEnfContainer());

        Set<OWLLogicalAxiom> normalizedAxioms = combinedNormalizedOntology.getLogicalAxioms();

        Set<OWLDeclarationAxiom> declarationAxioms = combinedNormalizedOntology.getAxioms(AxiomType.DECLARATION);

        owlAxioms.addAll(normalizedAxioms);
        owlAxioms.addAll(declarationAxioms);

        OWLOntology ontology = null;
        try {
            ontology = OWLManager.createOWLOntologyManager().createOntology(owlAxioms, IRI.create(iri));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        return ontology;
    }


    public void clearOntologies() {
        this.ontologies = new ArrayList<>();
    }


    public List<String> getAnswers() {
        return this.answers;
    }

    public List<List<String>> getDecodedAnswers() {
        return this.decodedAnswers;
    }

    public ClipperReport getClipperReport() {
        return this.clipperReport;
    }

    public Collection<CQ> getRewrittenQueries() {
        return this.rewrittenQueries;
    }


    public Collection<OWLOntology> getOntologies() {
        return this.ontologies;
    }

    public void setDatalogFileName(String datalogFileName) {
        this.datalogFileName = datalogFileName;
    }

    public void setQueryRewriter(String queryRewriter) {
        this.queryRewriter = queryRewriter;
    }

    @Override
    public void setQuery(CQ cq) {
        this.cq = cq;
    }

    public void setDlvPath(String dlvPath) {
        this.dlvPath = dlvPath;
    }

    public void setOntologies(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
    }


}
