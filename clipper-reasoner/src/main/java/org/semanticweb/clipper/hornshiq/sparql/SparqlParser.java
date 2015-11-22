// $ANTLR 3.5.2 org/semanticweb/clipper/hornshiq/sparql/Sparql.g 2015-11-22 19:16:16

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SparqlParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANON", "DECIMAL", "DECIMAL_NEGATIVE", 
		"DECIMAL_POSITIVE", "DIGIT", "DOUBLE", "DOUBLE_NEGATIVE", "DOUBLE_POSITIVE", 
		"ECHAR", "EXPONENT", "INTEGER", "INTEGER_NEGATIVE", "INTEGER_POSITIVE", 
		"IRI_REF", "LANGTAG", "NIL", "PNAME_LN", "PNAME_NS", "PN_CHARS", "PN_CHARS_BASE", 
		"PN_CHARS_U", "PN_LOCAL", "PN_PREFIX", "STRING_LITERAL1", "STRING_LITERAL2", 
		"VAR1", "VAR2", "VARNAME", "WS", "'('", "')'", "'*'", "','", "'.'", "';'", 
		"'BASE'", "'PREFIX'", "'SELECT'", "'WHERE'", "'['", "']'", "'^^'", "'a'", 
		"'false'", "'true'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int ANON=4;
	public static final int DECIMAL=5;
	public static final int DECIMAL_NEGATIVE=6;
	public static final int DECIMAL_POSITIVE=7;
	public static final int DIGIT=8;
	public static final int DOUBLE=9;
	public static final int DOUBLE_NEGATIVE=10;
	public static final int DOUBLE_POSITIVE=11;
	public static final int ECHAR=12;
	public static final int EXPONENT=13;
	public static final int INTEGER=14;
	public static final int INTEGER_NEGATIVE=15;
	public static final int INTEGER_POSITIVE=16;
	public static final int IRI_REF=17;
	public static final int LANGTAG=18;
	public static final int NIL=19;
	public static final int PNAME_LN=20;
	public static final int PNAME_NS=21;
	public static final int PN_CHARS=22;
	public static final int PN_CHARS_BASE=23;
	public static final int PN_CHARS_U=24;
	public static final int PN_LOCAL=25;
	public static final int PN_PREFIX=26;
	public static final int STRING_LITERAL1=27;
	public static final int STRING_LITERAL2=28;
	public static final int VAR1=29;
	public static final int VAR2=30;
	public static final int VARNAME=31;
	public static final int WS=32;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public SparqlParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public SparqlParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return SparqlParser.tokenNames; }
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/hornshiq/sparql/Sparql.g"; }


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



	// $ANTLR start "query"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:105:1: query returns [CQ q] : prologue ( selectQuery ) EOF ;
	public final CQ query() throws RecognitionException {
		CQ q = null;


		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:106:5: ( prologue ( selectQuery ) EOF )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:106:7: prologue ( selectQuery ) EOF
			{
			pushFollow(FOLLOW_prologue_in_query76);
			prologue();
			state._fsp--;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:106:16: ( selectQuery )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:106:18: selectQuery
			{
			pushFollow(FOLLOW_selectQuery_in_query80);
			selectQuery();
			state._fsp--;

			}


			      cq.setHead(head);
			      cq.setBody(body);
			      q = cq; 
			    
			match(input,EOF,FOLLOW_EOF_in_query92); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
		return q;
	}
	// $ANTLR end "query"



	// $ANTLR start "prologue"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:114:1: prologue : ( baseDecl )? ( prefixDecl )* ;
	public final void prologue() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:5: ( ( baseDecl )? ( prefixDecl )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:7: ( baseDecl )? ( prefixDecl )*
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:7: ( baseDecl )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==39) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:7: baseDecl
					{
					pushFollow(FOLLOW_baseDecl_in_prologue110);
					baseDecl();
					state._fsp--;

					}
					break;

			}

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:17: ( prefixDecl )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==40) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:115:17: prefixDecl
					{
					pushFollow(FOLLOW_prefixDecl_in_prologue113);
					prefixDecl();
					state._fsp--;

					}
					break;

				default :
					break loop2;
				}
			}

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prologue"



	// $ANTLR start "baseDecl"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:118:1: baseDecl : 'BASE' IRI_REF ;
	public final void baseDecl() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:119:5: ( 'BASE' IRI_REF )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:119:7: 'BASE' IRI_REF
			{
			match(input,39,FOLLOW_39_in_baseDecl131); 
			match(input,IRI_REF,FOLLOW_IRI_REF_in_baseDecl133); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "baseDecl"



	// $ANTLR start "prefixDecl"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:122:1: prefixDecl : 'PREFIX' PNAME_NS IRI_REF ;
	public final void prefixDecl() throws RecognitionException {
		Token PNAME_NS1=null;
		Token IRI_REF2=null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:123:5: ( 'PREFIX' PNAME_NS IRI_REF )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:123:7: 'PREFIX' PNAME_NS IRI_REF
			{
			match(input,40,FOLLOW_40_in_prefixDecl150); 
			PNAME_NS1=(Token)match(input,PNAME_NS,FOLLOW_PNAME_NS_in_prefixDecl152); 
			IRI_REF2=(Token)match(input,IRI_REF,FOLLOW_IRI_REF_in_prefixDecl154); 

			      String key = (PNAME_NS1!=null?PNAME_NS1.getText():null);
			      key = key.substring(0, key.length() -1 );
			      String value = (IRI_REF2!=null?IRI_REF2.getText():null);
			      value = value.substring(1, value.length() - 1);
			      namespaces.put(key, value); 
			      //System.out.println((PNAME_NS1!=null?PNAME_NS1.getText():null) + "->" + (IRI_REF2!=null?IRI_REF2.getText():null));
			    
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prefixDecl"



	// $ANTLR start "selectQuery"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:133:1: selectQuery : 'SELECT' ( ( var )+ | '*' ) whereClause ;
	public final void selectQuery() throws RecognitionException {
		Variable var3 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:134:5: ( 'SELECT' ( ( var )+ | '*' ) whereClause )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:134:7: 'SELECT' ( ( var )+ | '*' ) whereClause
			{
			match(input,41,FOLLOW_41_in_selectQuery173); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:134:16: ( ( var )+ | '*' )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( ((LA4_0 >= VAR1 && LA4_0 <= VAR2)) ) {
				alt4=1;
			}
			else if ( (LA4_0==35) ) {
				alt4=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:135:7: ( var )+
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:135:7: ( var )+
					int cnt3=0;
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= VAR1 && LA3_0 <= VAR2)) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:135:8: var
							{
							pushFollow(FOLLOW_var_in_selectQuery185);
							var3=var();
							state._fsp--;

							 outputVars.add(var3); 
							}
							break;

						default :
							if ( cnt3 >= 1 ) break loop3;
							EarlyExitException eee = new EarlyExitException(3, input);
							throw eee;
						}
						cnt3++;
					}

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:136:10: '*'
					{
					match(input,35,FOLLOW_35_in_selectQuery199); 

					      //TODO
					    
					}
					break;

			}


			      NonDLPredicate ans = new NonDLPredicate("ans", outputVars.size());
			      head = new Atom(ans, outputVars);
			      //System.out.println("head: " + head);
			    
			pushFollow(FOLLOW_whereClause_in_selectQuery236);
			whereClause();
			state._fsp--;

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "selectQuery"



	// $ANTLR start "sourceSelector"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:183:1: sourceSelector : iriRef ;
	public final void sourceSelector() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:184:5: ( iriRef )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:184:7: iriRef
			{
			pushFollow(FOLLOW_iriRef_in_sourceSelector290);
			iriRef();
			state._fsp--;

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "sourceSelector"



	// $ANTLR start "whereClause"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:187:1: whereClause : ( 'WHERE' )? groupGraphPattern ;
	public final void whereClause() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:188:5: ( ( 'WHERE' )? groupGraphPattern )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:188:7: ( 'WHERE' )? groupGraphPattern
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:188:7: ( 'WHERE' )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==42) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:188:7: 'WHERE'
					{
					match(input,42,FOLLOW_42_in_whereClause307); 
					}
					break;

			}

			pushFollow(FOLLOW_groupGraphPattern_in_whereClause310);
			groupGraphPattern();
			state._fsp--;

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "whereClause"



	// $ANTLR start "groupGraphPattern"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:216:1: groupGraphPattern : '{' ( triplesBlock )* '}' ;
	public final void groupGraphPattern() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:217:5: ( '{' ( triplesBlock )* '}' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:217:7: '{' ( triplesBlock )* '}'
			{
			match(input,49,FOLLOW_49_in_groupGraphPattern352); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:217:11: ( triplesBlock )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= ANON && LA6_0 <= DECIMAL_POSITIVE)||(LA6_0 >= DOUBLE && LA6_0 <= DOUBLE_POSITIVE)||(LA6_0 >= INTEGER && LA6_0 <= IRI_REF)||(LA6_0 >= NIL && LA6_0 <= PNAME_NS)||(LA6_0 >= STRING_LITERAL1 && LA6_0 <= VAR2)||LA6_0==33||LA6_0==43||(LA6_0 >= 47 && LA6_0 <= 48)) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:217:11: triplesBlock
					{
					pushFollow(FOLLOW_triplesBlock_in_groupGraphPattern354);
					triplesBlock();
					state._fsp--;

					}
					break;

				default :
					break loop6;
				}
			}

			match(input,50,FOLLOW_50_in_groupGraphPattern357); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "groupGraphPattern"



	// $ANTLR start "triplesBlock"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:224:1: triplesBlock : triplesSameSubject '.' ;
	public final void triplesBlock() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:225:5: ( triplesSameSubject '.' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:225:7: triplesSameSubject '.'
			{
			pushFollow(FOLLOW_triplesSameSubject_in_triplesBlock382);
			triplesSameSubject();
			state._fsp--;

			match(input,37,FOLLOW_37_in_triplesBlock384); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesBlock"



	// $ANTLR start "triplesSameSubject"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:272:1: triplesSameSubject : ( varOrTerm propertyListNotEmpty | triplesNode propertyList );
	public final void triplesSameSubject() throws RecognitionException {
		Term varOrTerm4 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:273:5: ( varOrTerm propertyListNotEmpty | triplesNode propertyList )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( ((LA7_0 >= ANON && LA7_0 <= DECIMAL_POSITIVE)||(LA7_0 >= DOUBLE && LA7_0 <= DOUBLE_POSITIVE)||(LA7_0 >= INTEGER && LA7_0 <= IRI_REF)||(LA7_0 >= NIL && LA7_0 <= PNAME_NS)||(LA7_0 >= STRING_LITERAL1 && LA7_0 <= VAR2)||(LA7_0 >= 47 && LA7_0 <= 48)) ) {
				alt7=1;
			}
			else if ( (LA7_0==33||LA7_0==43) ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:273:7: varOrTerm propertyListNotEmpty
					{
					pushFollow(FOLLOW_varOrTerm_in_triplesSameSubject446);
					varOrTerm4=varOrTerm();
					state._fsp--;


					        currentSubject = varOrTerm4;
					      
					pushFollow(FOLLOW_propertyListNotEmpty_in_triplesSameSubject463);
					propertyListNotEmpty();
					state._fsp--;

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:277:30: triplesNode propertyList
					{
					pushFollow(FOLLOW_triplesNode_in_triplesSameSubject467);
					triplesNode();
					state._fsp--;

					pushFollow(FOLLOW_propertyList_in_triplesSameSubject469);
					propertyList();
					state._fsp--;

					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesSameSubject"



	// $ANTLR start "propertyListNotEmpty"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:280:1: propertyListNotEmpty : v1= verb o1= objectList ( ';' (v2= verb os2= objectList )? )* ;
	public final void propertyListNotEmpty() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:281:5: (v1= verb o1= objectList ( ';' (v2= verb os2= objectList )? )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:281:7: v1= verb o1= objectList ( ';' (v2= verb os2= objectList )? )*
			{
			pushFollow(FOLLOW_verb_in_propertyListNotEmpty490);
			verb();
			state._fsp--;

			pushFollow(FOLLOW_objectList_in_propertyListNotEmpty497);
			objectList();
			state._fsp--;

			 
			     
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:284:7: ( ';' (v2= verb os2= objectList )? )*
			loop9:
			while (true) {
				int alt9=2;
				int LA9_0 = input.LA(1);
				if ( (LA9_0==38) ) {
					alt9=1;
				}

				switch (alt9) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:284:9: ';' (v2= verb os2= objectList )?
					{
					match(input,38,FOLLOW_38_in_propertyListNotEmpty514); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:284:13: (v2= verb os2= objectList )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==IRI_REF||(LA8_0 >= PNAME_LN && LA8_0 <= PNAME_NS)||(LA8_0 >= VAR1 && LA8_0 <= VAR2)||LA8_0==46) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:284:15: v2= verb os2= objectList
							{
							pushFollow(FOLLOW_verb_in_propertyListNotEmpty520);
							verb();
							state._fsp--;

							pushFollow(FOLLOW_objectList_in_propertyListNotEmpty524);
							objectList();
							state._fsp--;


							      
							}
							break;

					}

					}
					break;

				default :
					break loop9;
				}
			}

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "propertyListNotEmpty"



	// $ANTLR start "propertyList"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:288:1: propertyList : ( propertyListNotEmpty )? ;
	public final void propertyList() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:289:5: ( ( propertyListNotEmpty )? )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:289:7: ( propertyListNotEmpty )?
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:289:7: ( propertyListNotEmpty )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==IRI_REF||(LA10_0 >= PNAME_LN && LA10_0 <= PNAME_NS)||(LA10_0 >= VAR1 && LA10_0 <= VAR2)||LA10_0==46) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:289:7: propertyListNotEmpty
					{
					pushFollow(FOLLOW_propertyListNotEmpty_in_propertyList548);
					propertyListNotEmpty();
					state._fsp--;

					}
					break;

			}

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "propertyList"



	// $ANTLR start "objectList"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:292:1: objectList : o1= object ( ',' o2= object )* ;
	public final void objectList() throws RecognitionException {
		ParserRuleReturnScope o1 =null;
		ParserRuleReturnScope o2 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:293:5: (o1= object ( ',' o2= object )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:295:5: o1= object ( ',' o2= object )*
			{
			pushFollow(FOLLOW_object_in_objectList580);
			o1=object();
			state._fsp--;


			      if(currentArity==1) {
				      int p = ClipperManager.getInstance().getOwlClassEncoder().getValueBySymbol(
				       OWLManager.getOWLDataFactory().getOWLClass(IRI.create(getFullName((o1!=null?input.toString(o1.start,o1.stop):null)))));
				      currentPredicate = new DLPredicate(p, 1);
				      currentBodyAtom = new Atom(currentPredicate, currentSubject);
				      body.add(currentBodyAtom);
			      } else if (currentArity==2) {
			        Term t = (o1!=null?((object_return)o1).term:null);
			        currentBodyAtom = new Atom(currentPredicate, currentSubject, t);
			        body.add(currentBodyAtom);          
			      }
			    
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:308:7: ( ',' o2= object )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==36) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:308:9: ',' o2= object
					{
					match(input,36,FOLLOW_36_in_objectList590); 
					pushFollow(FOLLOW_object_in_objectList596);
					o2=object();
					state._fsp--;


					      if(currentArity==1) {
					        int p = ClipperManager.getInstance().getOwlClassEncoder().getValueBySymbol(
					          OWLManager.getOWLDataFactory().getOWLClass(IRI.create(getFullName((o1!=null?input.toString(o1.start,o1.stop):null)))));
					        currentPredicate = new DLPredicate(p, 1);
					        currentBodyAtom = new Atom(currentPredicate, currentSubject);
					        body.add(currentBodyAtom);
					      } else if (currentArity==2) {
					        Term t = (o2!=null?((object_return)o2).term:null);
					        currentBodyAtom = new Atom(currentPredicate, currentSubject, t);
					        body.add(currentBodyAtom);          
					      }
					    
					}
					break;

				default :
					break loop11;
				}
			}

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "objectList"


	public static class object_return extends ParserRuleReturnScope {
		public Term term;
	};


	// $ANTLR start "object"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:326:1: object returns [Term term] : graphNode ;
	public final object_return object() throws RecognitionException {
		object_return retval = new object_return();
		retval.start = input.LT(1);

		Term graphNode5 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:327:5: ( graphNode )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:327:7: graphNode
			{
			pushFollow(FOLLOW_graphNode_in_object637);
			graphNode5=graphNode();
			state._fsp--;

			retval.term = graphNode5;
			}

			retval.stop = input.LT(-1);

		}
		 
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "object"



	// $ANTLR start "verb"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:330:1: verb : ( varOrIRIref | 'a' );
	public final void verb() throws RecognitionException {
		ParserRuleReturnScope varOrIRIref6 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:331:5: ( varOrIRIref | 'a' )
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==IRI_REF||(LA12_0 >= PNAME_LN && LA12_0 <= PNAME_NS)||(LA12_0 >= VAR1 && LA12_0 <= VAR2)) ) {
				alt12=1;
			}
			else if ( (LA12_0==46) ) {
				alt12=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 12, 0, input);
				throw nvae;
			}

			switch (alt12) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:332:5: varOrIRIref
					{
					pushFollow(FOLLOW_varOrIRIref_in_verb663);
					varOrIRIref6=varOrIRIref();
					state._fsp--;


						   this.currentArity = 2;  
						   int p = ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getValueBySymbol(
						     OWLManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(getFullName( (varOrIRIref6!=null?input.toString(varOrIRIref6.start,varOrIRIref6.stop):null)))));
							 currentPredicate = new DLPredicate(p, currentArity);   
					    
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:338:7: 'a'
					{
					match(input,46,FOLLOW_46_in_verb673); 

					      
					      this.currentArity = 1;
					    
					    
					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "verb"



	// $ANTLR start "triplesNode"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:345:1: triplesNode : ( collection | blankNodePropertyList );
	public final void triplesNode() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:346:5: ( collection | blankNodePropertyList )
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==33) ) {
				alt13=1;
			}
			else if ( (LA13_0==43) ) {
				alt13=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}

			switch (alt13) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:346:7: collection
					{
					pushFollow(FOLLOW_collection_in_triplesNode692);
					collection();
					state._fsp--;

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:347:7: blankNodePropertyList
					{
					pushFollow(FOLLOW_blankNodePropertyList_in_triplesNode700);
					blankNodePropertyList();
					state._fsp--;

					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "triplesNode"



	// $ANTLR start "blankNodePropertyList"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:350:1: blankNodePropertyList : '[' propertyListNotEmpty ']' ;
	public final void blankNodePropertyList() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:351:5: ( '[' propertyListNotEmpty ']' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:351:7: '[' propertyListNotEmpty ']'
			{
			match(input,43,FOLLOW_43_in_blankNodePropertyList717); 
			pushFollow(FOLLOW_propertyListNotEmpty_in_blankNodePropertyList719);
			propertyListNotEmpty();
			state._fsp--;

			match(input,44,FOLLOW_44_in_blankNodePropertyList721); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "blankNodePropertyList"



	// $ANTLR start "collection"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:354:1: collection : '(' ( graphNode )+ ')' ;
	public final void collection() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:355:5: ( '(' ( graphNode )+ ')' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:355:7: '(' ( graphNode )+ ')'
			{
			match(input,33,FOLLOW_33_in_collection738); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:355:11: ( graphNode )+
			int cnt14=0;
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( ((LA14_0 >= ANON && LA14_0 <= DECIMAL_POSITIVE)||(LA14_0 >= DOUBLE && LA14_0 <= DOUBLE_POSITIVE)||(LA14_0 >= INTEGER && LA14_0 <= IRI_REF)||(LA14_0 >= NIL && LA14_0 <= PNAME_NS)||(LA14_0 >= STRING_LITERAL1 && LA14_0 <= VAR2)||LA14_0==33||LA14_0==43||(LA14_0 >= 47 && LA14_0 <= 48)) ) {
					alt14=1;
				}

				switch (alt14) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:355:11: graphNode
					{
					pushFollow(FOLLOW_graphNode_in_collection740);
					graphNode();
					state._fsp--;

					}
					break;

				default :
					if ( cnt14 >= 1 ) break loop14;
					EarlyExitException eee = new EarlyExitException(14, input);
					throw eee;
				}
				cnt14++;
			}

			match(input,34,FOLLOW_34_in_collection743); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "collection"



	// $ANTLR start "graphNode"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:358:1: graphNode returns [Term term] : ( varOrTerm | triplesNode );
	public final Term graphNode() throws RecognitionException {
		Term term = null;


		Term varOrTerm7 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:359:5: ( varOrTerm | triplesNode )
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( ((LA15_0 >= ANON && LA15_0 <= DECIMAL_POSITIVE)||(LA15_0 >= DOUBLE && LA15_0 <= DOUBLE_POSITIVE)||(LA15_0 >= INTEGER && LA15_0 <= IRI_REF)||(LA15_0 >= NIL && LA15_0 <= PNAME_NS)||(LA15_0 >= STRING_LITERAL1 && LA15_0 <= VAR2)||(LA15_0 >= 47 && LA15_0 <= 48)) ) {
				alt15=1;
			}
			else if ( (LA15_0==33||LA15_0==43) ) {
				alt15=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}

			switch (alt15) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:359:7: varOrTerm
					{
					pushFollow(FOLLOW_varOrTerm_in_graphNode764);
					varOrTerm7=varOrTerm();
					state._fsp--;

					term =varOrTerm7;
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:359:44: triplesNode
					{
					pushFollow(FOLLOW_triplesNode_in_graphNode770);
					triplesNode();
					state._fsp--;

					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
		return term;
	}
	// $ANTLR end "graphNode"



	// $ANTLR start "varOrTerm"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:362:1: varOrTerm returns [Term term] : ( var | graphTerm );
	public final Term varOrTerm() throws RecognitionException {
		Term term = null;


		Variable var8 =null;
		Constant graphTerm9 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:363:5: ( var | graphTerm )
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( ((LA16_0 >= VAR1 && LA16_0 <= VAR2)) ) {
				alt16=1;
			}
			else if ( ((LA16_0 >= ANON && LA16_0 <= DECIMAL_POSITIVE)||(LA16_0 >= DOUBLE && LA16_0 <= DOUBLE_POSITIVE)||(LA16_0 >= INTEGER && LA16_0 <= IRI_REF)||(LA16_0 >= NIL && LA16_0 <= PNAME_NS)||(LA16_0 >= STRING_LITERAL1 && LA16_0 <= STRING_LITERAL2)||(LA16_0 >= 47 && LA16_0 <= 48)) ) {
				alt16=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}

			switch (alt16) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:363:7: var
					{
					pushFollow(FOLLOW_var_in_varOrTerm791);
					var8=var();
					state._fsp--;


					      //if (currentArity == 2) 
					        term = var8;
					      
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:367:7: graphTerm
					{
					pushFollow(FOLLOW_graphTerm_in_varOrTerm801);
					graphTerm9=graphTerm();
					state._fsp--;


					      //if (currentArity == 2)
					      term = graphTerm9;
					     
					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
		return term;
	}
	// $ANTLR end "varOrTerm"


	public static class varOrIRIref_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "varOrIRIref"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:373:1: varOrIRIref : ( var | iriRef );
	public final varOrIRIref_return varOrIRIref() throws RecognitionException {
		varOrIRIref_return retval = new varOrIRIref_return();
		retval.start = input.LT(1);

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:374:5: ( var | iriRef )
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( ((LA17_0 >= VAR1 && LA17_0 <= VAR2)) ) {
				alt17=1;
			}
			else if ( (LA17_0==IRI_REF||(LA17_0 >= PNAME_LN && LA17_0 <= PNAME_NS)) ) {
				alt17=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 17, 0, input);
				throw nvae;
			}

			switch (alt17) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:374:7: var
					{
					pushFollow(FOLLOW_var_in_varOrIRIref820);
					var();
					state._fsp--;

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:374:13: iriRef
					{
					pushFollow(FOLLOW_iriRef_in_varOrIRIref824);
					iriRef();
					state._fsp--;

					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		 
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "varOrIRIref"



	// $ANTLR start "var"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:377:1: var returns [Variable variable] : ( VAR1 | VAR2 );
	public final Variable var() throws RecognitionException {
		Variable variable = null;


		Token VAR110=null;
		Token VAR211=null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:378:5: ( VAR1 | VAR2 )
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==VAR1) ) {
				alt18=1;
			}
			else if ( (LA18_0==VAR2) ) {
				alt18=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}

			switch (alt18) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:378:7: VAR1
					{
					VAR110=(Token)match(input,VAR1,FOLLOW_VAR1_in_var845); 
					variable = new Variable(varEncoder.getValueBySymbol((VAR110!=null?VAR110.getText():null)));
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:379:7: VAR2
					{
					VAR211=(Token)match(input,VAR2,FOLLOW_VAR2_in_var855); 
					variable = new Variable(varEncoder.getValueBySymbol((VAR211!=null?VAR211.getText():null)));
					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
		return variable;
	}
	// $ANTLR end "var"



	// $ANTLR start "graphTerm"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:382:1: graphTerm returns [Constant constant] : ( iriRef | rdfLiteral | numericLiteral | booleanLiteral | blankNode | NIL );
	public final Constant graphTerm() throws RecognitionException {
		Constant constant = null;


		ParserRuleReturnScope iriRef12 =null;

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:383:5: ( iriRef | rdfLiteral | numericLiteral | booleanLiteral | blankNode | NIL )
			int alt19=6;
			switch ( input.LA(1) ) {
			case IRI_REF:
			case PNAME_LN:
			case PNAME_NS:
				{
				alt19=1;
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
				{
				alt19=2;
				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
				{
				alt19=3;
				}
				break;
			case 47:
			case 48:
				{
				alt19=4;
				}
				break;
			case ANON:
				{
				alt19=5;
				}
				break;
			case NIL:
				{
				alt19=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}
			switch (alt19) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:383:7: iriRef
					{
					pushFollow(FOLLOW_iriRef_in_graphTerm880);
					iriRef12=iriRef();
					state._fsp--;


					    String quotedIRIText = (iriRef12!=null?input.toString(iriRef12.start,iriRef12.stop):null);
					    String iri = quotedIRIText.substring(1, quotedIRIText.length()-1);
					      int c = ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getValueBySymbol(OWLManager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri)));
					      constant = new Constant(c);
					    
					     
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:390:7: rdfLiteral
					{
					pushFollow(FOLLOW_rdfLiteral_in_graphTerm890);
					rdfLiteral();
					state._fsp--;

					}
					break;
				case 3 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:391:7: numericLiteral
					{
					pushFollow(FOLLOW_numericLiteral_in_graphTerm898);
					numericLiteral();
					state._fsp--;

					}
					break;
				case 4 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:392:7: booleanLiteral
					{
					pushFollow(FOLLOW_booleanLiteral_in_graphTerm906);
					booleanLiteral();
					state._fsp--;

					}
					break;
				case 5 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:393:7: blankNode
					{
					pushFollow(FOLLOW_blankNode_in_graphTerm914);
					blankNode();
					state._fsp--;

					}
					break;
				case 6 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:394:7: NIL
					{
					match(input,NIL,FOLLOW_NIL_in_graphTerm922); 
					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
		return constant;
	}
	// $ANTLR end "graphTerm"



	// $ANTLR start "rdfLiteral"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:466:1: rdfLiteral : string ( LANGTAG | ( '^^' iriRef ) )? ;
	public final void rdfLiteral() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:5: ( string ( LANGTAG | ( '^^' iriRef ) )? )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:7: string ( LANGTAG | ( '^^' iriRef ) )?
			{
			pushFollow(FOLLOW_string_in_rdfLiteral1008);
			string();
			state._fsp--;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:14: ( LANGTAG | ( '^^' iriRef ) )?
			int alt20=3;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==LANGTAG) ) {
				alt20=1;
			}
			else if ( (LA20_0==45) ) {
				alt20=2;
			}
			switch (alt20) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:16: LANGTAG
					{
					match(input,LANGTAG,FOLLOW_LANGTAG_in_rdfLiteral1012); 
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:26: ( '^^' iriRef )
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:26: ( '^^' iriRef )
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:467:28: '^^' iriRef
					{
					match(input,45,FOLLOW_45_in_rdfLiteral1018); 
					pushFollow(FOLLOW_iriRef_in_rdfLiteral1020);
					iriRef();
					state._fsp--;

					}

					}
					break;

			}

			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "rdfLiteral"



	// $ANTLR start "numericLiteral"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:470:1: numericLiteral : ( numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative );
	public final void numericLiteral() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:471:5: ( numericLiteralUnsigned | numericLiteralPositive | numericLiteralNegative )
			int alt21=3;
			switch ( input.LA(1) ) {
			case DECIMAL:
			case DOUBLE:
			case INTEGER:
				{
				alt21=1;
				}
				break;
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_POSITIVE:
				{
				alt21=2;
				}
				break;
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case INTEGER_NEGATIVE:
				{
				alt21=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 21, 0, input);
				throw nvae;
			}
			switch (alt21) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:471:7: numericLiteralUnsigned
					{
					pushFollow(FOLLOW_numericLiteralUnsigned_in_numericLiteral1042);
					numericLiteralUnsigned();
					state._fsp--;

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:471:32: numericLiteralPositive
					{
					pushFollow(FOLLOW_numericLiteralPositive_in_numericLiteral1046);
					numericLiteralPositive();
					state._fsp--;

					}
					break;
				case 3 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:471:57: numericLiteralNegative
					{
					pushFollow(FOLLOW_numericLiteralNegative_in_numericLiteral1050);
					numericLiteralNegative();
					state._fsp--;

					}
					break;

			}
		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteral"



	// $ANTLR start "numericLiteralUnsigned"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:474:1: numericLiteralUnsigned : ( INTEGER | DECIMAL | DOUBLE );
	public final void numericLiteralUnsigned() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:475:5: ( INTEGER | DECIMAL | DOUBLE )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL||input.LA(1)==DOUBLE||input.LA(1)==INTEGER ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralUnsigned"



	// $ANTLR start "numericLiteralPositive"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:480:1: numericLiteralPositive : ( INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE );
	public final void numericLiteralPositive() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:481:5: ( INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL_POSITIVE||input.LA(1)==DOUBLE_POSITIVE||input.LA(1)==INTEGER_POSITIVE ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralPositive"



	// $ANTLR start "numericLiteralNegative"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:486:1: numericLiteralNegative : ( INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE );
	public final void numericLiteralNegative() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:487:5: ( INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( input.LA(1)==DECIMAL_NEGATIVE||input.LA(1)==DOUBLE_NEGATIVE||input.LA(1)==INTEGER_NEGATIVE ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "numericLiteralNegative"



	// $ANTLR start "booleanLiteral"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:492:1: booleanLiteral : ( 'true' | 'false' );
	public final void booleanLiteral() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:493:5: ( 'true' | 'false' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= 47 && input.LA(1) <= 48) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "booleanLiteral"



	// $ANTLR start "string"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:497:1: string : ( STRING_LITERAL1 | STRING_LITERAL2 );
	public final void string() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:498:5: ( STRING_LITERAL1 | STRING_LITERAL2 )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= STRING_LITERAL1 && input.LA(1) <= STRING_LITERAL2) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "string"


	public static class iriRef_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "iriRef"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:503:1: iriRef : ( IRI_REF | prefixedName );
	public final iriRef_return iriRef() throws RecognitionException {
		iriRef_return retval = new iriRef_return();
		retval.start = input.LT(1);

		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:504:5: ( IRI_REF | prefixedName )
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==IRI_REF) ) {
				alt22=1;
			}
			else if ( ((LA22_0 >= PNAME_LN && LA22_0 <= PNAME_NS)) ) {
				alt22=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}

			switch (alt22) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:504:7: IRI_REF
					{
					match(input,IRI_REF,FOLLOW_IRI_REF_in_iriRef1222); 
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:505:7: prefixedName
					{
					pushFollow(FOLLOW_prefixedName_in_iriRef1230);
					prefixedName();
					state._fsp--;


					//    String original = prefixedName.text;
					//    int i = original.indexOf(":");
					//    String prefix = original.substring(0,i);
					//    String full = namespaces.get(prefix) + original.substring(i+1);
					//    //prefixedName = (full);
					//    setText(full);
					//    //System.out.println(prefixedName.text + " -> " + full);
					    
					    
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		 
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "iriRef"



	// $ANTLR start "prefixedName"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:517:1: prefixedName : ( PNAME_LN | PNAME_NS );
	public final void prefixedName() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:518:5: ( PNAME_LN | PNAME_NS )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= PNAME_LN && input.LA(1) <= PNAME_NS) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prefixedName"



	// $ANTLR start "blankNode"
	// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:522:1: blankNode : ANON ;
	public final void blankNode() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:523:5: ( ANON )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:523:7: ANON
			{
			match(input,ANON,FOLLOW_ANON_in_blankNode1274); 
			}

		}
		 
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "blankNode"

	// Delegated rules



	public static final BitSet FOLLOW_prologue_in_query76 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_selectQuery_in_query80 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_query92 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_baseDecl_in_prologue110 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_prefixDecl_in_prologue113 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_39_in_baseDecl131 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_IRI_REF_in_baseDecl133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_prefixDecl150 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_PNAME_NS_in_prefixDecl152 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_IRI_REF_in_prefixDecl154 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_41_in_selectQuery173 = new BitSet(new long[]{0x0000000860000000L});
	public static final BitSet FOLLOW_var_in_selectQuery185 = new BitSet(new long[]{0x0002040060000000L});
	public static final BitSet FOLLOW_35_in_selectQuery199 = new BitSet(new long[]{0x0002040000000000L});
	public static final BitSet FOLLOW_whereClause_in_selectQuery236 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_sourceSelector290 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_42_in_whereClause307 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_groupGraphPattern_in_whereClause310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_groupGraphPattern352 = new BitSet(new long[]{0x00058802783BCEF0L});
	public static final BitSet FOLLOW_triplesBlock_in_groupGraphPattern354 = new BitSet(new long[]{0x00058802783BCEF0L});
	public static final BitSet FOLLOW_50_in_groupGraphPattern357 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesSameSubject_in_triplesBlock382 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_37_in_triplesBlock384 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrTerm_in_triplesSameSubject446 = new BitSet(new long[]{0x0000400060320000L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_triplesSameSubject463 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesNode_in_triplesSameSubject467 = new BitSet(new long[]{0x0000400060320000L});
	public static final BitSet FOLLOW_propertyList_in_triplesSameSubject469 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_verb_in_propertyListNotEmpty490 = new BitSet(new long[]{0x00018802783BCEF0L});
	public static final BitSet FOLLOW_objectList_in_propertyListNotEmpty497 = new BitSet(new long[]{0x0000004000000002L});
	public static final BitSet FOLLOW_38_in_propertyListNotEmpty514 = new BitSet(new long[]{0x0000404060320002L});
	public static final BitSet FOLLOW_verb_in_propertyListNotEmpty520 = new BitSet(new long[]{0x00018802783BCEF0L});
	public static final BitSet FOLLOW_objectList_in_propertyListNotEmpty524 = new BitSet(new long[]{0x0000004000000002L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_propertyList548 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_object_in_objectList580 = new BitSet(new long[]{0x0000001000000002L});
	public static final BitSet FOLLOW_36_in_objectList590 = new BitSet(new long[]{0x00018802783BCEF0L});
	public static final BitSet FOLLOW_object_in_objectList596 = new BitSet(new long[]{0x0000001000000002L});
	public static final BitSet FOLLOW_graphNode_in_object637 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrIRIref_in_verb663 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_verb673 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_collection_in_triplesNode692 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blankNodePropertyList_in_triplesNode700 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_43_in_blankNodePropertyList717 = new BitSet(new long[]{0x0000400060320000L});
	public static final BitSet FOLLOW_propertyListNotEmpty_in_blankNodePropertyList719 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_blankNodePropertyList721 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_33_in_collection738 = new BitSet(new long[]{0x00018802783BCEF0L});
	public static final BitSet FOLLOW_graphNode_in_collection740 = new BitSet(new long[]{0x00018806783BCEF0L});
	public static final BitSet FOLLOW_34_in_collection743 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varOrTerm_in_graphNode764 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triplesNode_in_graphNode770 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_varOrTerm791 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_graphTerm_in_varOrTerm801 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_var_in_varOrIRIref820 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_varOrIRIref824 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR1_in_var845 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR2_in_var855 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_iriRef_in_graphTerm880 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rdfLiteral_in_graphTerm890 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteral_in_graphTerm898 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_booleanLiteral_in_graphTerm906 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blankNode_in_graphTerm914 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NIL_in_graphTerm922 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_string_in_rdfLiteral1008 = new BitSet(new long[]{0x0000200000040002L});
	public static final BitSet FOLLOW_LANGTAG_in_rdfLiteral1012 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_45_in_rdfLiteral1018 = new BitSet(new long[]{0x0000000000320000L});
	public static final BitSet FOLLOW_iriRef_in_rdfLiteral1020 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralUnsigned_in_numericLiteral1042 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralPositive_in_numericLiteral1046 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteralNegative_in_numericLiteral1050 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IRI_REF_in_iriRef1222 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_prefixedName_in_iriRef1230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ANON_in_blankNode1274 = new BitSet(new long[]{0x0000000000000002L});
}
