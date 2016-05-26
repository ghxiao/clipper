package org.semanticweb.clipper.hornshiq.queryrewriting;

import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Set;
import java.util.function.Function;

public interface SelfLoopComponentCluster extends Function<CQGraph, Set<Set<Variable>>> {
}
