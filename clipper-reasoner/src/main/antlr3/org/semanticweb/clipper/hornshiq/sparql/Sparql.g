/*
 * Copyright 2007 the original author or authors.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the author or authors nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Simone Tripodi   (simone)
 * @author Michele Mostarda (michele)
 * @version $Id: Sparql.g 5 2007-10-30 17:20:36Z simone $
 */

grammar Sparql;

options {
    k=1;
    language = Java;
}

@header {
package org.semanticweb.clipper.hornshiq.sparql;
 
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import com.google.common.collect.Lists;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Constant;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
}

@lexer::header {
package org.semanticweb.clipper.hornshiq.sparql;
}

@members {
  Map<String, String> namespaces = new HashMap<String, String>();
  SymbolEncoder<String> varEncoder = new SymbolEncoder<String>(String.class);
  CQ cq = new CQ();
  List<Term> outputVars = new ArrayList<Term>();
  Atom head;
  List<Atom> body = new ArrayList<>();
  
  Atom currentBodyAtom;
  Predicate currentPredicate;
  Term currentSubject;
  int currentArity;
  
  boolean outputAll = false;
  
  public SparqlParser(String sparqlFile) throws IOException {
    super(new CommonTokenStream(new SparqlLexer(new ANTLRFileStream(sparqlFile))));
  }
  
  private String getFullName(String prefixedName){
    String original = prefixedName.toString();
    int i = original.indexOf(":");
    String prefix = original.substring(0,i);
    String full = namespaces.get(prefix) + original.substring(i+1);
    return full;
  }
}

@rulecatch { }

// PARSER RULES


query returns [CQ q]
    : prologue ( selectQuery  ) {
      cq.setHead(head);
      cq.setBody(body);
      $q = cq; 
    } 
    EOF 
    ;

prologue
    : baseDecl? prefixDecl*
    ;

baseDecl
    : 'BASE' IRI_REF
    ;

prefixDecl
    : 'PREFIX' PNAME_NS IRI_REF {
      String key = $PNAME_NS.text;
      key = key.substring(0, key.length() -1 );
      String value = $IRI_REF.text;
      value = value.substring(1, value.length() - 1);
      namespaces.put(key, value); 
      //System.out.println($PNAME_NS.text + "->" + $IRI_REF.text);
    }
    ;

selectQuery
    : 'SELECT' ( 
      (var  { outputVars.add($var.variable); }
    )+ | '*'
    {
      //TODO
    }
    
     )
    {
      NonDLPredicate ans = new NonDLPredicate("ans", outputVars.size());
      head = new Atom(ans, outputVars);
      //System.out.println("head: " + head);
    }
    
      whereClause 
    ;
    
//selectQuery
//    : 'SELECT' ( 'DISTINCT' | 'REDUCED' )? ( var+ | '*' ) datasetClause* whereClause solutionModifier
//    ;    

//constructQuery
//    : 'CONSTRUCT' constructTemplate datasetClause* whereClause solutionModifier
//    ;

//describeQuery
//    : 'DESCRIBE' ( varOrIRIref+ | '*' ) datasetClause* whereClause? solutionModifier
//    ;

//askQuery
//    : 'ASK' datasetClause* whereClause
//    ;

//datasetClause
//    : 'FROM' ( defaultGraphClause | namedGraphClause )
//    ;

//datasetClause
//    : 'FROM' ( defaultGraphClause )
//    ;

//defaultGraphClause
//    : sourceSelector
//    ;
//
//namedGraphClause
//    : 'NAMED' sourceSelector
//    ;

sourceSelector
    : iriRef
    ;

whereClause
    : 'WHERE'? groupGraphPattern
    ;

//solutionModifier
//    : orderClause? limitOffsetClauses?
//    ;

//limitOffsetClauses
//    : ( limitClause offsetClause? | offsetClause limitClause? )
//    ;

//orderClause
//    : 'ORDER' 'BY' orderCondition+
//    ;
//
//orderCondition
//    : ( ( 'ASC' | 'DESC' ) brackettedExpression )
//    | ( constraint | var )
//    ;

//limitClause
//    : 'LIMIT' INTEGER
//    ;
//
//offsetClause
//    : 'OFFSET' INTEGER
//    ;

groupGraphPattern
    : '{' triplesBlock* '}'
    ;
    
//groupGraphPattern
//    : '{' triplesBlock? ( ( graphPatternNotTriples | filter ) '.'? triplesBlock? )* '}'
//    ;    

triplesBlock
    : triplesSameSubject '.' 
    ;

//triplesBlock
//    : triplesSameSubject ( '.' triplesBlock? )?
//    ;

//graphPatternNotTriples
//    : optionalGraphPattern | groupOrUnionGraphPattern | graphGraphPattern
//    ;

//optionalGraphPattern
//    : 'OPTIONAL' groupGraphPattern
//    ;

//graphGraphPattern
//    : 'GRAPH' varOrIRIref groupGraphPattern
//    ;

//groupOrUnionGraphPattern
//    : groupGraphPattern ( 'UNION' groupGraphPattern )*
//    ;

//filter
//    : 'FILTER' constraint
//    ;

//constraint
//    : brackettedExpression | builtInCall | functionCall
//    ;

//functionCall
//    : iriRef argList
//    ;

//argList
//    : ( NIL | '(' expression ( ',' expression )* ')' )
//    ;

//constructTemplate
//    : '{' constructTriples? '}'
//    ;
//
//constructTriples
//    : triplesSameSubject ( '.' constructTriples? )?
//    ;

triplesSameSubject
    : varOrTerm 
      {
        currentSubject = $varOrTerm.term;
      }
      propertyListNotEmpty | triplesNode propertyList
    ;

propertyListNotEmpty
    : v1 = verb  o1 = objectList
     { 
     }
      ( ';' ( v2=verb os2=objectList {
      })? )*
    ;

propertyList
    : propertyListNotEmpty?
    ;

objectList 
    : 
   
    o1 = object
    {
      if(currentArity==1) {
	      int p = ClipperManager.getInstance().getOwlClassEncoder().getValueBySymbol(
	       OWLManager.getOWLDataFactory().getOWLClass(IRI.create(getFullName($o1.text))));
	      currentPredicate = new DLPredicate(p, 1);
	      currentBodyAtom = new Atom(currentPredicate, currentSubject);
	      body.add(currentBodyAtom);
      } else if (currentArity==2) {
        Term t = $o1.term;
        currentBodyAtom = new Atom(currentPredicate, currentSubject, t);
        body.add(currentBodyAtom);          
      }
    } ( ',' o2 = object
    {
      if(currentArity==1) {
        int p = ClipperManager.getInstance().getOwlClassEncoder().getValueBySymbol(
          OWLManager.getOWLDataFactory().getOWLClass(IRI.create(getFullName($o1.text))));
        currentPredicate = new DLPredicate(p, 1);
        currentBodyAtom = new Atom(currentPredicate, currentSubject);
        body.add(currentBodyAtom);
      } else if (currentArity==2) {
        Term t = $o2.term;
        currentBodyAtom = new Atom(currentPredicate, currentSubject, t);
        body.add(currentBodyAtom);          
      }
    }
    
     )* 
    ;

object returns [Term term]
    : graphNode {$term = $graphNode.term;}
    ;

verb  
    : 
    varOrIRIref {
	   this.currentArity = 2;  
	   int p = ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getValueBySymbol(
	     OWLManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(getFullName( $varOrIRIref.text))));
		 currentPredicate = new DLPredicate(p, currentArity);   
    }
    | 'a' {
      
      this.currentArity = 1;
    
    }
    ;

triplesNode
    : collection
    | blankNodePropertyList
    ;

blankNodePropertyList
    : '[' propertyListNotEmpty ']'
    ;

collection
    : '(' graphNode+ ')'
    ;

graphNode returns [Term term]
    : varOrTerm {$term=$varOrTerm.term;} | triplesNode
    ;

varOrTerm returns [Term term]
    : var {
      //if (currentArity == 2) 
        $term = $var.variable;
      }
    | graphTerm {
      //if (currentArity == 2)
      $term = $graphTerm.constant;
     }
    ;

varOrIRIref
    : var | iriRef
    ;

var returns [Variable variable]
    : VAR1 {$variable = new Variable(varEncoder.getValueBySymbol($VAR1.text));}
    | VAR2 {$variable = new Variable(varEncoder.getValueBySymbol($VAR2.text));}
    ; 

graphTerm returns [Constant constant] 
    : iriRef {
    String quotedIRIText = $iriRef.text;
    String iri = quotedIRIText.substring(1, quotedIRIText.length()-1);
      int c = ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getValueBySymbol(OWLManager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri)));
      $constant = new Constant(c);
    
     }
    | rdfLiteral
    | numericLiteral
    | booleanLiteral
    | blankNode
    | NIL
    ;

//expression
//    : conditionalOrExpression
//    ;

//conditionalOrExpression
//    : conditionalAndExpression ( '||' conditionalAndExpression )*
//    ;
//
//conditionalAndExpression
//    : valueLogical ( '&&' valueLogical )*
//    ;

//valueLogical
//    : relationalExpression
//    ;

//relationalExpression
//    : numericExpression ( '=' numericExpression | '!=' numericExpression | '<' numericExpression | '>' numericExpression | '<=' numericExpression | '>=' numericExpression )?
//    ;

//numericExpression
//    : additiveExpression
//    ;

//additiveExpression
//    : multiplicativeExpression ( '+' multiplicativeExpression | '-' multiplicativeExpression | numericLiteralPositive | numericLiteralNegative )*
//    ;

//multiplicativeExpression
//    : unaryExpression ( '*' unaryExpression | '/' unaryExpression )*
//    ;

//unaryExpression
//    :  '!' primaryExpression
//    | '+' primaryExpression
//    | '-' primaryExpression
//    | primaryExpression
//    ;

//primaryExpression
//    : brackettedExpression | builtInCall | iriRefOrFunction | rdfLiteral | numericLiteral | booleanLiteral | var
//    ;

//brackettedExpression
//    : '(' expression ')'
//    ;

//builtInCall
//    : 'STR' '(' expression ')'
//    | 'LANG' '(' expression ')'
//    | 'LANGMATCHES' '(' expression ',' expression ')'
//    | 'DATATYPE' '(' expression ')'
//    | 'BOUND' '(' var ')'
//    | 'sameTerm' '(' expression ',' expression ')'
//    | 'isIRI' '(' expression ')'
//    | 'isURI' '(' expression ')'
//    | 'isBLANK' '(' expression ')'
//    | 'isLITERAL' '(' expression ')'
//    | regexExpression
//    ;

//regexExpression
//    : 'REGEX' '(' expression ',' expression ( ',' expression )? ')'
//    ;

//iriRefOrFunction
//    : iriRef argList?
//    ;

rdfLiteral
    : string ( LANGTAG | ( '^^' iriRef ) )?
    ;

numericLiteral
    : numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative
    ;

numericLiteralUnsigned
    : INTEGER
    | DECIMAL
    | DOUBLE
    ;

numericLiteralPositive
    : INTEGER_POSITIVE
    | DECIMAL_POSITIVE
    | DOUBLE_POSITIVE
    ;

numericLiteralNegative
    : INTEGER_NEGATIVE
    | DECIMAL_NEGATIVE
    | DOUBLE_NEGATIVE
    ;

booleanLiteral
    : 'true'
    | 'false'
    ;

string
    : STRING_LITERAL1
    | STRING_LITERAL2
    /* | STRING_LITERAL_LONG('0'..'9') | STRING_LITERAL_LONG('0'..'9')*/
    ;

iriRef
    : IRI_REF
    | prefixedName {
//    String original = prefixedName.text;
//    int i = original.indexOf(":");
//    String prefix = original.substring(0,i);
//    String full = namespaces.get(prefix) + original.substring(i+1);
//    //prefixedName = (full);
//    setText(full);
//    //System.out.println(prefixedName.text + " -> " + full);
    
    }
    ;

prefixedName
    : PNAME_LN
    | PNAME_NS
    ;

blankNode
    : ANON
    ;

//blankNode
//    : BLANK_NODE_LABEL
//    | ANON
//    ;

// LEXER RULES

//IRI_REF
//    : '<' ( options {greedy=false;} : ~('<' | '>' | '"' | '{' | '}' | '|' | '^' | '\\' | '`') | (PN_CHARS))* '>'
//    ;

IRI_REF
    : '<' ( options {greedy=false;} : ~('<' | '>' | '"' | '{' | '}' | '|' | '^' | '\\' | '`') *) '>'
    ;


PNAME_NS
    : p=PN_PREFIX? ':'
    ;

PNAME_LN
    : n=PNAME_NS l=PN_LOCAL 
    ;

//BLANK_NODE_LABEL
//    : '_:' PN_LOCAL
//    ;

VAR1  
    : '?' v=VARNAME { setText($v.text); }
    ;

VAR2  
    : '$' v=VARNAME { setText($v.text); }
    ;

LANGTAG
    : '@' PN_CHARS_BASE+ ('-' (PN_CHARS_BASE DIGIT)+)*
    ;

INTEGER
    : DIGIT+
    ;

DECIMAL
    : DIGIT+ '.' DIGIT*
    | '.' DIGIT+
    ;

DOUBLE
    : DIGIT+ '.' DIGIT* EXPONENT
    | '.' DIGIT+ EXPONENT
    | DIGIT+ EXPONENT
    ;

INTEGER_POSITIVE
    : '+' INTEGER
    ;

DECIMAL_POSITIVE
    : '+' DECIMAL
    ;

DOUBLE_POSITIVE
    : '+' DOUBLE
    ;

INTEGER_NEGATIVE
    : '-' INTEGER
    ;

DECIMAL_NEGATIVE
    : '-' DECIMAL
    ;

DOUBLE_NEGATIVE
    : '-' DOUBLE
    ;

EXPONENT
    : ('e'|'E') ('+'|'-')? DIGIT+
    ;

STRING_LITERAL1
    : '\'' ( options {greedy=false;} : ~('\u0027' | '\u005C' | '\u000A' | '\u000D') | ECHAR )* '\''
    ;

STRING_LITERAL2
    : '"'  ( options {greedy=false;} : ~('\u0022' | '\u005C' | '\u000A' | '\u000D') | ECHAR )* '"'
    ;

//STRING_LITERAL_LONG1
//    : '\'\'\'' ( options {greedy=false;} : ( '\'' | '\'\'' )? (~('\'' | '\\') | ECHAR ) )* '\'\'\''
//    ;
//
//STRING_LITERAL_LONG2
//    : '"""' ( options {greedy=false;} : ( '"' | '""' )? ( ~('\'' | '\\') | ECHAR ) )* '"""'
//    ;

ECHAR
    : '\\' ('t' | 'b' | 'n' | 'r' | 'f' | '"' | '\'')
    ;

NIL
    : '(' WS* ')'
    ;

ANON
    : '[' WS* ']'
    ;

PN_CHARS_U
    : PN_CHARS_BASE | '_'
    ;

VARNAME
    : ( PN_CHARS_U | DIGIT ) ( PN_CHARS_U | DIGIT | '\u00B7' | ('\u0300'..'\u036F') | ('\u203F'..'\u2040') )*
    ;

fragment
PN_CHARS
    : PN_CHARS_U
    | '-'
    | DIGIT
    /*| '\u00B7'
    | '\u0300'..'\u036F'
    | '\u203F'..'\u2040'*/
    ;

PN_PREFIX
    : PN_CHARS_BASE ((PN_CHARS|'.')* PN_CHARS)?
    ;

PN_LOCAL
    : ( PN_CHARS_U | DIGIT ) ((PN_CHARS|'.')* PN_CHARS)?
    ;

fragment
PN_CHARS_BASE
    : 'A'..'Z'
    | 'a'..'z'
    | '\u00C0'..'\u00D6'
    | '\u00D8'..'\u00F6'
    | '\u00F8'..'\u02FF'
    | '\u0370'..'\u037D'
    | '\u037F'..'\u1FFF'
    | '\u200C'..'\u200D'
    | '\u2070'..'\u218F'
    | '\u2C00'..'\u2FEF'
    | '\u3001'..'\uD7FF'
    | '\uF900'..'\uFDCF'
    | '\uFDF0'..'\uFFFD'
    ;

fragment
DIGIT
    : '0'..'9'
    ;

WS
    : (' '
    | '\t'
    | '\n'
    | '\r')+ { $channel=HIDDEN; }
    ;
