package org.semanticweb.clipper.hornshiq.queryanswering;

import org.apache.commons.collections15.Transformer;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Set;

public interface SelfLoopComponentCluster extends Transformer<CQGraph, Set<Set<Variable>>> {
}
