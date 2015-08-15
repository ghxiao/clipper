package org.semanticweb.clipper.hornshiq.queryrewriting;

import com.google.common.collect.ImmutableSet;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Set;


public class NaiveSelfLoopComponentCluster implements SelfLoopComponentCluster {

    @Override
    public Set<Set<Variable>> transform(CQGraph cqGraph) {
        return ImmutableSet.of(cqGraph.getNonAnswerVariables());
    }
}
