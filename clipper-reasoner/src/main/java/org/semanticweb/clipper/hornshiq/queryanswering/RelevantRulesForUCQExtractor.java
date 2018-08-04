package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

import java.util.List;

public class RelevantRulesForUCQExtractor {

    List<CQ> relatedRules(List<CQ> query, List<CQ> rules) {

        // normally is "ans"
        Predicate queryPredicate = query.iterator().next().getHead().getPredicate();



        return null;
    }

}
