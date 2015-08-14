package org.semanticweb.clipper.hornshiq.queryanswering;


import org.semanticweb.clipper.hornshiq.queryrewriting.CQGraph;

public interface ICQGraphHomomorphismChecker {
    boolean isContainedIn(CQGraph g1, CQGraph g2);
}
