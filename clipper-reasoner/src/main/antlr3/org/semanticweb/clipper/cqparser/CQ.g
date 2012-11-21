grammar CQ;

options {
  language = Java;
  output = AST;
}

tokens {
  VARIABLE; 
  CONSTANT;
  ATOM;
  PREDICATE;
  RULE;
  DL_PREDICATE;
  NON_DL_PREDICATE;
  ATOM_LIST;
  TERM_LIST;
  RULE_LIST;
}

@header{
package org.semanticweb.clipper.cqparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
}

@members{

      Set<OWLOntology> ontologies;
      
      public CQParser(String string, Set<OWLOntology> ontologies)
        throws FileNotFoundException, IOException {
        this(new CommonTokenStream(new CQLexer(new ANTLRStringStream(string))));
        this.ontologies = ontologies;
      }
  
		  public CQParser(InputStream istream, Set<OWLOntology> ontologies) throws FileNotFoundException, IOException {
		    this(new CommonTokenStream(new CQLexer(new ANTLRInputStream(istream))));
		    this.ontologies = ontologies;
		  }
  
  
		  public CQParser(File file, Set<OWLOntology> ontologies) throws FileNotFoundException, IOException {
		    this(new CommonTokenStream(new CQLexer(new ANTLRInputStream(new FileInputStream(file)))));
		    this.ontologies = ontologies;
		  }

      public CQ parse() {
        cq_return r = null;
        try {
          r = cq();
        } catch (RecognitionException e) {
          e.printStackTrace();
        }
        CommonTree t = (CommonTree) r.getTree();

        //System.out.println(t.toStringTree());
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
        // AST nodes have payloads that point into token stream
        nodes.setTokenStream(input);

        ShortFormProvider sfp = new SimpleShortFormProvider();
        BidirectionalShortFormProvider bsfp = new BidirectionalShortFormProviderAdapter(ontologies, sfp);

        CQTreeWalker walker = new CQTreeWalker(bsfp);

        CQ cq = walker.walkRuleNode(t);
        return cq;
      }
      
      public CQ parseCQ() {
        return parse();
      }
      
      public List<CQ> parseUCQ() {
        ucq_return r = null;
        try {
          r = ucq();
        } catch (RecognitionException e) {
          e.printStackTrace();
        }
        CommonTree t = (CommonTree) r.getTree();

        //System.out.println(t.toStringTree());
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
        // AST nodes have payloads that point into token stream
        nodes.setTokenStream(input);

        ShortFormProvider sfp = new SimpleShortFormProvider();
        BidirectionalShortFormProvider bsfp = new BidirectionalShortFormProviderAdapter(ontologies, sfp);

        CQTreeWalker walker = new CQTreeWalker(bsfp);

        List<CQ> ucq = walker.walkUCQNode(t);
        return ucq;
      }
}

@lexer::header{
package org.semanticweb.clipper.cqparser;
}
  
ucq: 
  cq* -> ^(RULE_LIST cq*);
  
cq:
  head (':-' | '<-') body '.'?  -> ^(RULE head body);
  //head (':-' | '<-') body COMMENT_LINE? -> ^(RULE head body);
  
  /*head (':-' | '<-') body '.' -> ^(RULE head body);*/
   
head: 
  atom ('v' atom)* -> ^(ATOM_LIST atom*);
  
body:
  atom (',' atom)* -> ^(ATOM_LIST atom*);
  
atom:
  predicate '(' term (',' term)* ')' -> ^(predicate term*);

/*
  predicate:
  dl_predicate -> ^(DL_PREDICATE dl_predicate) 
  | non_dl_predicate -> ^(NON_DL_PREDICATE non_dl_predicate);  

dl_predicate:
  '<'! (UPPER_LEADING_ID | LOWER_LEADING_ID) '>'! ;
  
non_dl_predicate:
  LOWER_LEADING_ID ;
*/

predicate:
  UPPER_LEADING_ID | LOWER_LEADING_ID;

//terms:
//  term (',' term)* -> ^(TERM_LIST term+);
  
term:
  variable -> ^(VARIABLE variable) 
  | constant -> ^(CONSTANT constant);

// TODO: FIXIT X1 can be parsed as variable
variable:
  ('?' | 'X') ! INT;
  
constant:
  LOWER_LEADING_ID;

UPPER_LEADING_ID  :   ('A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
LOWER_LEADING_ID  :   ('a'..'z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
WS  : (' '|'\n'|'\r')+ {$channel=HIDDEN;} ;
INT : ('0'..'9')+; 

