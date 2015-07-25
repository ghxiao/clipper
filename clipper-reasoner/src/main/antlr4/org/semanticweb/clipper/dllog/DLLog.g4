grammar DLLog;

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
}
// END:header 

@header{
package org.semanticweb.clipper.dllog;
}

@lexer::header{
package org.semanticweb.clipper.dllog;
}

dl_log_rules:
  dl_log_rule *;
  
dl_log_rule:
  head ':-' body '.' -> ^(RULE head body);
   
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

variable:
  UPPER_LEADING_ID;
  
constant:
  LOWER_LEADING_ID;

UPPER_LEADING_ID  :   ('A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
LOWER_LEADING_ID  :   ('a'..'z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
WS  : (' '|'\n'|'\r')+ {$channel=HIDDEN;} ;