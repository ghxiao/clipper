// $ANTLR 3.5.2 org/semanticweb/clipper/hornshiq/sparql/Sparql.g 2015-11-22 19:16:16

package org.semanticweb.clipper.hornshiq.sparql;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SparqlLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public SparqlLexer() {} 
	public SparqlLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public SparqlLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/hornshiq/sparql/Sparql.g"; }

	// $ANTLR start "T__33"
	public final void mT__33() throws RecognitionException {
		try {
			int _type = T__33;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:11:7: ( '(' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:11:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__33"

	// $ANTLR start "T__34"
	public final void mT__34() throws RecognitionException {
		try {
			int _type = T__34;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:12:7: ( ')' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:12:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__34"

	// $ANTLR start "T__35"
	public final void mT__35() throws RecognitionException {
		try {
			int _type = T__35;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:13:7: ( '*' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:13:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__35"

	// $ANTLR start "T__36"
	public final void mT__36() throws RecognitionException {
		try {
			int _type = T__36;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:14:7: ( ',' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:14:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__36"

	// $ANTLR start "T__37"
	public final void mT__37() throws RecognitionException {
		try {
			int _type = T__37;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:15:7: ( '.' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:15:9: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__37"

	// $ANTLR start "T__38"
	public final void mT__38() throws RecognitionException {
		try {
			int _type = T__38;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:16:7: ( ';' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:16:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__38"

	// $ANTLR start "T__39"
	public final void mT__39() throws RecognitionException {
		try {
			int _type = T__39;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:17:7: ( 'BASE' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:17:9: 'BASE'
			{
			match("BASE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__39"

	// $ANTLR start "T__40"
	public final void mT__40() throws RecognitionException {
		try {
			int _type = T__40;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:18:7: ( 'PREFIX' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:18:9: 'PREFIX'
			{
			match("PREFIX"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__40"

	// $ANTLR start "T__41"
	public final void mT__41() throws RecognitionException {
		try {
			int _type = T__41;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:19:7: ( 'SELECT' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:19:9: 'SELECT'
			{
			match("SELECT"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__41"

	// $ANTLR start "T__42"
	public final void mT__42() throws RecognitionException {
		try {
			int _type = T__42;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:20:7: ( 'WHERE' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:20:9: 'WHERE'
			{
			match("WHERE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__42"

	// $ANTLR start "T__43"
	public final void mT__43() throws RecognitionException {
		try {
			int _type = T__43;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:21:7: ( '[' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:21:9: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__43"

	// $ANTLR start "T__44"
	public final void mT__44() throws RecognitionException {
		try {
			int _type = T__44;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:22:7: ( ']' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:22:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__44"

	// $ANTLR start "T__45"
	public final void mT__45() throws RecognitionException {
		try {
			int _type = T__45;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:23:7: ( '^^' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:23:9: '^^'
			{
			match("^^"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__45"

	// $ANTLR start "T__46"
	public final void mT__46() throws RecognitionException {
		try {
			int _type = T__46;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:24:7: ( 'a' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:24:9: 'a'
			{
			match('a'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__46"

	// $ANTLR start "T__47"
	public final void mT__47() throws RecognitionException {
		try {
			int _type = T__47;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:25:7: ( 'false' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:25:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__47"

	// $ANTLR start "T__48"
	public final void mT__48() throws RecognitionException {
		try {
			int _type = T__48;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:26:7: ( 'true' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:26:9: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__48"

	// $ANTLR start "T__49"
	public final void mT__49() throws RecognitionException {
		try {
			int _type = T__49;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:27:7: ( '{' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:27:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__49"

	// $ANTLR start "T__50"
	public final void mT__50() throws RecognitionException {
		try {
			int _type = T__50;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:28:7: ( '}' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:28:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__50"

	// $ANTLR start "IRI_REF"
	public final void mIRI_REF() throws RecognitionException {
		try {
			int _type = IRI_REF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:538:5: ( '<' ( options {greedy=false; } : (~ ( '<' | '>' | '\"' | '{' | '}' | '|' | '^' | '\\\\' | '`' ) )* ) '>' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:538:7: '<' ( options {greedy=false; } : (~ ( '<' | '>' | '\"' | '{' | '}' | '|' | '^' | '\\\\' | '`' ) )* ) '>'
			{
			match('<'); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:538:11: ( options {greedy=false; } : (~ ( '<' | '>' | '\"' | '{' | '}' | '|' | '^' | '\\\\' | '`' ) )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:538:39: (~ ( '<' | '>' | '\"' | '{' | '}' | '|' | '^' | '\\\\' | '`' ) )*
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:538:39: (~ ( '<' | '>' | '\"' | '{' | '}' | '|' | '^' | '\\\\' | '`' ) )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '\u0000' && LA1_0 <= '!')||(LA1_0 >= '#' && LA1_0 <= ';')||LA1_0=='='||(LA1_0 >= '?' && LA1_0 <= '[')||LA1_0==']'||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||(LA1_0 >= '~' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= ';')||input.LA(1)=='='||(input.LA(1) >= '?' && input.LA(1) <= '[')||input.LA(1)==']'||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '~' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop1;
				}
			}

			}

			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IRI_REF"

	// $ANTLR start "PNAME_NS"
	public final void mPNAME_NS() throws RecognitionException {
		try {
			int _type = PNAME_NS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			CommonToken p=null;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:543:5: ( (p= PN_PREFIX )? ':' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:543:7: (p= PN_PREFIX )? ':'
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:543:8: (p= PN_PREFIX )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( ((LA2_0 >= 'A' && LA2_0 <= 'Z')||(LA2_0 >= 'a' && LA2_0 <= 'z')||(LA2_0 >= '\u00C0' && LA2_0 <= '\u00D6')||(LA2_0 >= '\u00D8' && LA2_0 <= '\u00F6')||(LA2_0 >= '\u00F8' && LA2_0 <= '\u02FF')||(LA2_0 >= '\u0370' && LA2_0 <= '\u037D')||(LA2_0 >= '\u037F' && LA2_0 <= '\u1FFF')||(LA2_0 >= '\u200C' && LA2_0 <= '\u200D')||(LA2_0 >= '\u2070' && LA2_0 <= '\u218F')||(LA2_0 >= '\u2C00' && LA2_0 <= '\u2FEF')||(LA2_0 >= '\u3001' && LA2_0 <= '\uD7FF')||(LA2_0 >= '\uF900' && LA2_0 <= '\uFDCF')||(LA2_0 >= '\uFDF0' && LA2_0 <= '\uFFFD')) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:543:8: p= PN_PREFIX
					{
					int pStart251 = getCharIndex();
					int pStartLine251 = getLine();
					int pStartCharPos251 = getCharPositionInLine();
					mPN_PREFIX(); 
					p = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, pStart251, getCharIndex()-1);
					p.setLine(pStartLine251);
					p.setCharPositionInLine(pStartCharPos251);

					}
					break;

			}

			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PNAME_NS"

	// $ANTLR start "PNAME_LN"
	public final void mPNAME_LN() throws RecognitionException {
		try {
			int _type = PNAME_LN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			CommonToken n=null;
			CommonToken l=null;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:547:5: (n= PNAME_NS l= PN_LOCAL )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:547:7: n= PNAME_NS l= PN_LOCAL
			{
			int nStart272 = getCharIndex();
			int nStartLine272 = getLine();
			int nStartCharPos272 = getCharPositionInLine();
			mPNAME_NS(); 
			n = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nStart272, getCharIndex()-1);
			n.setLine(nStartLine272);
			n.setCharPositionInLine(nStartCharPos272);

			int lStart276 = getCharIndex();
			int lStartLine276 = getLine();
			int lStartCharPos276 = getCharPositionInLine();
			mPN_LOCAL(); 
			l = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, lStart276, getCharIndex()-1);
			l.setLine(lStartLine276);
			l.setCharPositionInLine(lStartCharPos276);

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PNAME_LN"

	// $ANTLR start "VAR1"
	public final void mVAR1() throws RecognitionException {
		try {
			int _type = VAR1;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			CommonToken v=null;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:555:5: ( '?' v= VARNAME )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:555:7: '?' v= VARNAME
			{
			match('?'); 
			int vStart299 = getCharIndex();
			int vStartLine299 = getLine();
			int vStartCharPos299 = getCharPositionInLine();
			mVARNAME(); 
			v = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, vStart299, getCharIndex()-1);
			v.setLine(vStartLine299);
			v.setCharPositionInLine(vStartCharPos299);

			 setText((v!=null?v.getText():null)); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VAR1"

	// $ANTLR start "VAR2"
	public final void mVAR2() throws RecognitionException {
		try {
			int _type = VAR2;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			CommonToken v=null;

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:559:5: ( '$' v= VARNAME )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:559:7: '$' v= VARNAME
			{
			match('$'); 
			int vStart323 = getCharIndex();
			int vStartLine323 = getLine();
			int vStartCharPos323 = getCharPositionInLine();
			mVARNAME(); 
			v = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, vStart323, getCharIndex()-1);
			v.setLine(vStartLine323);
			v.setCharPositionInLine(vStartCharPos323);

			 setText((v!=null?v.getText():null)); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VAR2"

	// $ANTLR start "LANGTAG"
	public final void mLANGTAG() throws RecognitionException {
		try {
			int _type = LANGTAG;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:5: ( '@' ( PN_CHARS_BASE )+ ( '-' ( PN_CHARS_BASE DIGIT )+ )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:7: '@' ( PN_CHARS_BASE )+ ( '-' ( PN_CHARS_BASE DIGIT )+ )*
			{
			match('@'); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:11: ( PN_CHARS_BASE )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')||(LA3_0 >= '\u00C0' && LA3_0 <= '\u00D6')||(LA3_0 >= '\u00D8' && LA3_0 <= '\u00F6')||(LA3_0 >= '\u00F8' && LA3_0 <= '\u02FF')||(LA3_0 >= '\u0370' && LA3_0 <= '\u037D')||(LA3_0 >= '\u037F' && LA3_0 <= '\u1FFF')||(LA3_0 >= '\u200C' && LA3_0 <= '\u200D')||(LA3_0 >= '\u2070' && LA3_0 <= '\u218F')||(LA3_0 >= '\u2C00' && LA3_0 <= '\u2FEF')||(LA3_0 >= '\u3001' && LA3_0 <= '\uD7FF')||(LA3_0 >= '\uF900' && LA3_0 <= '\uFDCF')||(LA3_0 >= '\uFDF0' && LA3_0 <= '\uFFFD')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:26: ( '-' ( PN_CHARS_BASE DIGIT )+ )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0=='-') ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:27: '-' ( PN_CHARS_BASE DIGIT )+
					{
					match('-'); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:31: ( PN_CHARS_BASE DIGIT )+
					int cnt4=0;
					loop4:
					while (true) {
						int alt4=2;
						int LA4_0 = input.LA(1);
						if ( ((LA4_0 >= 'A' && LA4_0 <= 'Z')||(LA4_0 >= 'a' && LA4_0 <= 'z')||(LA4_0 >= '\u00C0' && LA4_0 <= '\u00D6')||(LA4_0 >= '\u00D8' && LA4_0 <= '\u00F6')||(LA4_0 >= '\u00F8' && LA4_0 <= '\u02FF')||(LA4_0 >= '\u0370' && LA4_0 <= '\u037D')||(LA4_0 >= '\u037F' && LA4_0 <= '\u1FFF')||(LA4_0 >= '\u200C' && LA4_0 <= '\u200D')||(LA4_0 >= '\u2070' && LA4_0 <= '\u218F')||(LA4_0 >= '\u2C00' && LA4_0 <= '\u2FEF')||(LA4_0 >= '\u3001' && LA4_0 <= '\uD7FF')||(LA4_0 >= '\uF900' && LA4_0 <= '\uFDCF')||(LA4_0 >= '\uFDF0' && LA4_0 <= '\uFFFD')) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:563:32: PN_CHARS_BASE DIGIT
							{
							mPN_CHARS_BASE(); 

							mDIGIT(); 

							}
							break;

						default :
							if ( cnt4 >= 1 ) break loop4;
							EarlyExitException eee = new EarlyExitException(4, input);
							throw eee;
						}
						cnt4++;
					}

					}
					break;

				default :
					break loop5;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LANGTAG"

	// $ANTLR start "INTEGER"
	public final void mINTEGER() throws RecognitionException {
		try {
			int _type = INTEGER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:567:5: ( ( DIGIT )+ )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:567:7: ( DIGIT )+
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:567:7: ( DIGIT )+
			int cnt6=0;
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt6 >= 1 ) break loop6;
					EarlyExitException eee = new EarlyExitException(6, input);
					throw eee;
				}
				cnt6++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER"

	// $ANTLR start "DECIMAL"
	public final void mDECIMAL() throws RecognitionException {
		try {
			int _type = DECIMAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:571:5: ( ( DIGIT )+ '.' ( DIGIT )* | '.' ( DIGIT )+ )
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
				alt10=1;
			}
			else if ( (LA10_0=='.') ) {
				alt10=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:571:7: ( DIGIT )+ '.' ( DIGIT )*
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:571:7: ( DIGIT )+
					int cnt7=0;
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt7 >= 1 ) break loop7;
							EarlyExitException eee = new EarlyExitException(7, input);
							throw eee;
						}
						cnt7++;
					}

					match('.'); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:571:18: ( DIGIT )*
					loop8:
					while (true) {
						int alt8=2;
						int LA8_0 = input.LA(1);
						if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
							alt8=1;
						}

						switch (alt8) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop8;
						}
					}

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:572:7: '.' ( DIGIT )+
					{
					match('.'); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:572:11: ( DIGIT )+
					int cnt9=0;
					loop9:
					while (true) {
						int alt9=2;
						int LA9_0 = input.LA(1);
						if ( ((LA9_0 >= '0' && LA9_0 <= '9')) ) {
							alt9=1;
						}

						switch (alt9) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt9 >= 1 ) break loop9;
							EarlyExitException eee = new EarlyExitException(9, input);
							throw eee;
						}
						cnt9++;
					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DECIMAL"

	// $ANTLR start "DOUBLE"
	public final void mDOUBLE() throws RecognitionException {
		try {
			int _type = DOUBLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:576:5: ( ( DIGIT )+ '.' ( DIGIT )* EXPONENT | '.' ( DIGIT )+ EXPONENT | ( DIGIT )+ EXPONENT )
			int alt15=3;
			alt15 = dfa15.predict(input);
			switch (alt15) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:576:7: ( DIGIT )+ '.' ( DIGIT )* EXPONENT
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:576:7: ( DIGIT )+
					int cnt11=0;
					loop11:
					while (true) {
						int alt11=2;
						int LA11_0 = input.LA(1);
						if ( ((LA11_0 >= '0' && LA11_0 <= '9')) ) {
							alt11=1;
						}

						switch (alt11) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt11 >= 1 ) break loop11;
							EarlyExitException eee = new EarlyExitException(11, input);
							throw eee;
						}
						cnt11++;
					}

					match('.'); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:576:18: ( DIGIT )*
					loop12:
					while (true) {
						int alt12=2;
						int LA12_0 = input.LA(1);
						if ( ((LA12_0 >= '0' && LA12_0 <= '9')) ) {
							alt12=1;
						}

						switch (alt12) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop12;
						}
					}

					mEXPONENT(); 

					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:577:7: '.' ( DIGIT )+ EXPONENT
					{
					match('.'); 
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:577:11: ( DIGIT )+
					int cnt13=0;
					loop13:
					while (true) {
						int alt13=2;
						int LA13_0 = input.LA(1);
						if ( ((LA13_0 >= '0' && LA13_0 <= '9')) ) {
							alt13=1;
						}

						switch (alt13) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt13 >= 1 ) break loop13;
							EarlyExitException eee = new EarlyExitException(13, input);
							throw eee;
						}
						cnt13++;
					}

					mEXPONENT(); 

					}
					break;
				case 3 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:578:7: ( DIGIT )+ EXPONENT
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:578:7: ( DIGIT )+
					int cnt14=0;
					loop14:
					while (true) {
						int alt14=2;
						int LA14_0 = input.LA(1);
						if ( ((LA14_0 >= '0' && LA14_0 <= '9')) ) {
							alt14=1;
						}

						switch (alt14) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt14 >= 1 ) break loop14;
							EarlyExitException eee = new EarlyExitException(14, input);
							throw eee;
						}
						cnt14++;
					}

					mEXPONENT(); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOUBLE"

	// $ANTLR start "INTEGER_POSITIVE"
	public final void mINTEGER_POSITIVE() throws RecognitionException {
		try {
			int _type = INTEGER_POSITIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:582:5: ( '+' INTEGER )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:582:7: '+' INTEGER
			{
			match('+'); 
			mINTEGER(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER_POSITIVE"

	// $ANTLR start "DECIMAL_POSITIVE"
	public final void mDECIMAL_POSITIVE() throws RecognitionException {
		try {
			int _type = DECIMAL_POSITIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:586:5: ( '+' DECIMAL )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:586:7: '+' DECIMAL
			{
			match('+'); 
			mDECIMAL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DECIMAL_POSITIVE"

	// $ANTLR start "DOUBLE_POSITIVE"
	public final void mDOUBLE_POSITIVE() throws RecognitionException {
		try {
			int _type = DOUBLE_POSITIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:590:5: ( '+' DOUBLE )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:590:7: '+' DOUBLE
			{
			match('+'); 
			mDOUBLE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOUBLE_POSITIVE"

	// $ANTLR start "INTEGER_NEGATIVE"
	public final void mINTEGER_NEGATIVE() throws RecognitionException {
		try {
			int _type = INTEGER_NEGATIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:594:5: ( '-' INTEGER )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:594:7: '-' INTEGER
			{
			match('-'); 
			mINTEGER(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER_NEGATIVE"

	// $ANTLR start "DECIMAL_NEGATIVE"
	public final void mDECIMAL_NEGATIVE() throws RecognitionException {
		try {
			int _type = DECIMAL_NEGATIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:598:5: ( '-' DECIMAL )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:598:7: '-' DECIMAL
			{
			match('-'); 
			mDECIMAL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DECIMAL_NEGATIVE"

	// $ANTLR start "DOUBLE_NEGATIVE"
	public final void mDOUBLE_NEGATIVE() throws RecognitionException {
		try {
			int _type = DOUBLE_NEGATIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:602:5: ( '-' DOUBLE )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:602:7: '-' DOUBLE
			{
			match('-'); 
			mDOUBLE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOUBLE_NEGATIVE"

	// $ANTLR start "EXPONENT"
	public final void mEXPONENT() throws RecognitionException {
		try {
			int _type = EXPONENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:606:5: ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+ )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:606:7: ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+
			{
			if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:606:17: ( '+' | '-' )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0=='+'||LA16_0=='-') ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:606:28: ( DIGIT )+
			int cnt17=0;
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( ((LA17_0 >= '0' && LA17_0 <= '9')) ) {
					alt17=1;
				}

				switch (alt17) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt17 >= 1 ) break loop17;
					EarlyExitException eee = new EarlyExitException(17, input);
					throw eee;
				}
				cnt17++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EXPONENT"

	// $ANTLR start "STRING_LITERAL1"
	public final void mSTRING_LITERAL1() throws RecognitionException {
		try {
			int _type = STRING_LITERAL1;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:610:5: ( '\\'' ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )* '\\'' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:610:7: '\\'' ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )* '\\''
			{
			match('\''); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:610:12: ( options {greedy=false; } :~ ( '\\u0027' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )*
			loop18:
			while (true) {
				int alt18=3;
				int LA18_0 = input.LA(1);
				if ( ((LA18_0 >= '\u0000' && LA18_0 <= '\t')||(LA18_0 >= '\u000B' && LA18_0 <= '\f')||(LA18_0 >= '\u000E' && LA18_0 <= '&')||(LA18_0 >= '(' && LA18_0 <= '[')||(LA18_0 >= ']' && LA18_0 <= '\uFFFF')) ) {
					alt18=1;
				}
				else if ( (LA18_0=='\\') ) {
					alt18=2;
				}
				else if ( (LA18_0=='\'') ) {
					alt18=3;
				}

				switch (alt18) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:610:40: ~ ( '\\u0027' | '\\u005C' | '\\u000A' | '\\u000D' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:610:87: ECHAR
					{
					mECHAR(); 

					}
					break;

				default :
					break loop18;
				}
			}

			match('\''); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING_LITERAL1"

	// $ANTLR start "STRING_LITERAL2"
	public final void mSTRING_LITERAL2() throws RecognitionException {
		try {
			int _type = STRING_LITERAL2;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:614:5: ( '\"' ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )* '\"' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:614:7: '\"' ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )* '\"'
			{
			match('\"'); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:614:12: ( options {greedy=false; } :~ ( '\\u0022' | '\\u005C' | '\\u000A' | '\\u000D' ) | ECHAR )*
			loop19:
			while (true) {
				int alt19=3;
				int LA19_0 = input.LA(1);
				if ( ((LA19_0 >= '\u0000' && LA19_0 <= '\t')||(LA19_0 >= '\u000B' && LA19_0 <= '\f')||(LA19_0 >= '\u000E' && LA19_0 <= '!')||(LA19_0 >= '#' && LA19_0 <= '[')||(LA19_0 >= ']' && LA19_0 <= '\uFFFF')) ) {
					alt19=1;
				}
				else if ( (LA19_0=='\\') ) {
					alt19=2;
				}
				else if ( (LA19_0=='\"') ) {
					alt19=3;
				}

				switch (alt19) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:614:40: ~ ( '\\u0022' | '\\u005C' | '\\u000A' | '\\u000D' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:614:87: ECHAR
					{
					mECHAR(); 

					}
					break;

				default :
					break loop19;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING_LITERAL2"

	// $ANTLR start "ECHAR"
	public final void mECHAR() throws RecognitionException {
		try {
			int _type = ECHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:626:5: ( '\\\\' ( 't' | 'b' | 'n' | 'r' | 'f' | '\"' | '\\'' ) )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:626:7: '\\\\' ( 't' | 'b' | 'n' | 'r' | 'f' | '\"' | '\\'' )
			{
			match('\\'); 
			if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ECHAR"

	// $ANTLR start "NIL"
	public final void mNIL() throws RecognitionException {
		try {
			int _type = NIL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:630:5: ( '(' ( WS )* ')' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:630:7: '(' ( WS )* ')'
			{
			match('('); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:630:11: ( WS )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( ((LA20_0 >= '\t' && LA20_0 <= '\n')||LA20_0=='\r'||LA20_0==' ') ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:630:11: WS
					{
					mWS(); 

					}
					break;

				default :
					break loop20;
				}
			}

			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NIL"

	// $ANTLR start "ANON"
	public final void mANON() throws RecognitionException {
		try {
			int _type = ANON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:634:5: ( '[' ( WS )* ']' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:634:7: '[' ( WS )* ']'
			{
			match('['); 
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:634:11: ( WS )*
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( ((LA21_0 >= '\t' && LA21_0 <= '\n')||LA21_0=='\r'||LA21_0==' ') ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:634:11: WS
					{
					mWS(); 

					}
					break;

				default :
					break loop21;
				}
			}

			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ANON"

	// $ANTLR start "PN_CHARS_U"
	public final void mPN_CHARS_U() throws RecognitionException {
		try {
			int _type = PN_CHARS_U;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:638:5: ( PN_CHARS_BASE | '_' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PN_CHARS_U"

	// $ANTLR start "VARNAME"
	public final void mVARNAME() throws RecognitionException {
		try {
			int _type = VARNAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:642:5: ( ( PN_CHARS_U | DIGIT ) ( PN_CHARS_U | DIGIT | '\\u00B7' | ( '\\u0300' .. '\\u036F' ) | ( '\\u203F' .. '\\u2040' ) )* )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:642:7: ( PN_CHARS_U | DIGIT ) ( PN_CHARS_U | DIGIT | '\\u00B7' | ( '\\u0300' .. '\\u036F' ) | ( '\\u203F' .. '\\u2040' ) )*
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:642:30: ( PN_CHARS_U | DIGIT | '\\u00B7' | ( '\\u0300' .. '\\u036F' ) | ( '\\u203F' .. '\\u2040' ) )*
			loop22:
			while (true) {
				int alt22=2;
				int LA22_0 = input.LA(1);
				if ( ((LA22_0 >= '0' && LA22_0 <= '9')||(LA22_0 >= 'A' && LA22_0 <= 'Z')||LA22_0=='_'||(LA22_0 >= 'a' && LA22_0 <= 'z')||LA22_0=='\u00B7'||(LA22_0 >= '\u00C0' && LA22_0 <= '\u00D6')||(LA22_0 >= '\u00D8' && LA22_0 <= '\u00F6')||(LA22_0 >= '\u00F8' && LA22_0 <= '\u037D')||(LA22_0 >= '\u037F' && LA22_0 <= '\u1FFF')||(LA22_0 >= '\u200C' && LA22_0 <= '\u200D')||(LA22_0 >= '\u203F' && LA22_0 <= '\u2040')||(LA22_0 >= '\u2070' && LA22_0 <= '\u218F')||(LA22_0 >= '\u2C00' && LA22_0 <= '\u2FEF')||(LA22_0 >= '\u3001' && LA22_0 <= '\uD7FF')||(LA22_0 >= '\uF900' && LA22_0 <= '\uFDCF')||(LA22_0 >= '\uFDF0' && LA22_0 <= '\uFFFD')) ) {
					alt22=1;
				}

				switch (alt22) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u00B7'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u203F' && input.LA(1) <= '\u2040')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop22;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VARNAME"

	// $ANTLR start "PN_CHARS"
	public final void mPN_CHARS() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:648:5: ( PN_CHARS_U | '-' | DIGIT )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PN_CHARS"

	// $ANTLR start "PN_PREFIX"
	public final void mPN_PREFIX() throws RecognitionException {
		try {
			int _type = PN_PREFIX;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:656:5: ( PN_CHARS_BASE ( ( PN_CHARS | '.' )* PN_CHARS )? )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:656:7: PN_CHARS_BASE ( ( PN_CHARS | '.' )* PN_CHARS )?
			{
			mPN_CHARS_BASE(); 

			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:656:21: ( ( PN_CHARS | '.' )* PN_CHARS )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( ((LA24_0 >= '-' && LA24_0 <= '.')||(LA24_0 >= '0' && LA24_0 <= '9')||(LA24_0 >= 'A' && LA24_0 <= 'Z')||LA24_0=='_'||(LA24_0 >= 'a' && LA24_0 <= 'z')||(LA24_0 >= '\u00C0' && LA24_0 <= '\u00D6')||(LA24_0 >= '\u00D8' && LA24_0 <= '\u00F6')||(LA24_0 >= '\u00F8' && LA24_0 <= '\u02FF')||(LA24_0 >= '\u0370' && LA24_0 <= '\u037D')||(LA24_0 >= '\u037F' && LA24_0 <= '\u1FFF')||(LA24_0 >= '\u200C' && LA24_0 <= '\u200D')||(LA24_0 >= '\u2070' && LA24_0 <= '\u218F')||(LA24_0 >= '\u2C00' && LA24_0 <= '\u2FEF')||(LA24_0 >= '\u3001' && LA24_0 <= '\uD7FF')||(LA24_0 >= '\uF900' && LA24_0 <= '\uFDCF')||(LA24_0 >= '\uFDF0' && LA24_0 <= '\uFFFD')) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:656:22: ( PN_CHARS | '.' )* PN_CHARS
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:656:22: ( PN_CHARS | '.' )*
					loop23:
					while (true) {
						int alt23=2;
						int LA23_0 = input.LA(1);
						if ( (LA23_0=='-'||(LA23_0 >= '0' && LA23_0 <= '9')||(LA23_0 >= 'A' && LA23_0 <= 'Z')||LA23_0=='_'||(LA23_0 >= 'a' && LA23_0 <= 'z')||(LA23_0 >= '\u00C0' && LA23_0 <= '\u00D6')||(LA23_0 >= '\u00D8' && LA23_0 <= '\u00F6')||(LA23_0 >= '\u00F8' && LA23_0 <= '\u02FF')||(LA23_0 >= '\u0370' && LA23_0 <= '\u037D')||(LA23_0 >= '\u037F' && LA23_0 <= '\u1FFF')||(LA23_0 >= '\u200C' && LA23_0 <= '\u200D')||(LA23_0 >= '\u2070' && LA23_0 <= '\u218F')||(LA23_0 >= '\u2C00' && LA23_0 <= '\u2FEF')||(LA23_0 >= '\u3001' && LA23_0 <= '\uD7FF')||(LA23_0 >= '\uF900' && LA23_0 <= '\uFDCF')||(LA23_0 >= '\uFDF0' && LA23_0 <= '\uFFFD')) ) {
							int LA23_1 = input.LA(2);
							if ( ((LA23_1 >= '-' && LA23_1 <= '.')||(LA23_1 >= '0' && LA23_1 <= '9')||(LA23_1 >= 'A' && LA23_1 <= 'Z')||LA23_1=='_'||(LA23_1 >= 'a' && LA23_1 <= 'z')||(LA23_1 >= '\u00C0' && LA23_1 <= '\u00D6')||(LA23_1 >= '\u00D8' && LA23_1 <= '\u00F6')||(LA23_1 >= '\u00F8' && LA23_1 <= '\u02FF')||(LA23_1 >= '\u0370' && LA23_1 <= '\u037D')||(LA23_1 >= '\u037F' && LA23_1 <= '\u1FFF')||(LA23_1 >= '\u200C' && LA23_1 <= '\u200D')||(LA23_1 >= '\u2070' && LA23_1 <= '\u218F')||(LA23_1 >= '\u2C00' && LA23_1 <= '\u2FEF')||(LA23_1 >= '\u3001' && LA23_1 <= '\uD7FF')||(LA23_1 >= '\uF900' && LA23_1 <= '\uFDCF')||(LA23_1 >= '\uFDF0' && LA23_1 <= '\uFFFD')) ) {
								alt23=1;
							}

						}
						else if ( (LA23_0=='.') ) {
							alt23=1;
						}

						switch (alt23) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop23;
						}
					}

					mPN_CHARS(); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PN_PREFIX"

	// $ANTLR start "PN_LOCAL"
	public final void mPN_LOCAL() throws RecognitionException {
		try {
			int _type = PN_LOCAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:660:5: ( ( PN_CHARS_U | DIGIT ) ( ( PN_CHARS | '.' )* PN_CHARS )? )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:660:7: ( PN_CHARS_U | DIGIT ) ( ( PN_CHARS | '.' )* PN_CHARS )?
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:660:30: ( ( PN_CHARS | '.' )* PN_CHARS )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( ((LA26_0 >= '-' && LA26_0 <= '.')||(LA26_0 >= '0' && LA26_0 <= '9')||(LA26_0 >= 'A' && LA26_0 <= 'Z')||LA26_0=='_'||(LA26_0 >= 'a' && LA26_0 <= 'z')||(LA26_0 >= '\u00C0' && LA26_0 <= '\u00D6')||(LA26_0 >= '\u00D8' && LA26_0 <= '\u00F6')||(LA26_0 >= '\u00F8' && LA26_0 <= '\u02FF')||(LA26_0 >= '\u0370' && LA26_0 <= '\u037D')||(LA26_0 >= '\u037F' && LA26_0 <= '\u1FFF')||(LA26_0 >= '\u200C' && LA26_0 <= '\u200D')||(LA26_0 >= '\u2070' && LA26_0 <= '\u218F')||(LA26_0 >= '\u2C00' && LA26_0 <= '\u2FEF')||(LA26_0 >= '\u3001' && LA26_0 <= '\uD7FF')||(LA26_0 >= '\uF900' && LA26_0 <= '\uFDCF')||(LA26_0 >= '\uFDF0' && LA26_0 <= '\uFFFD')) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:660:31: ( PN_CHARS | '.' )* PN_CHARS
					{
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:660:31: ( PN_CHARS | '.' )*
					loop25:
					while (true) {
						int alt25=2;
						int LA25_0 = input.LA(1);
						if ( (LA25_0=='-'||(LA25_0 >= '0' && LA25_0 <= '9')||(LA25_0 >= 'A' && LA25_0 <= 'Z')||LA25_0=='_'||(LA25_0 >= 'a' && LA25_0 <= 'z')||(LA25_0 >= '\u00C0' && LA25_0 <= '\u00D6')||(LA25_0 >= '\u00D8' && LA25_0 <= '\u00F6')||(LA25_0 >= '\u00F8' && LA25_0 <= '\u02FF')||(LA25_0 >= '\u0370' && LA25_0 <= '\u037D')||(LA25_0 >= '\u037F' && LA25_0 <= '\u1FFF')||(LA25_0 >= '\u200C' && LA25_0 <= '\u200D')||(LA25_0 >= '\u2070' && LA25_0 <= '\u218F')||(LA25_0 >= '\u2C00' && LA25_0 <= '\u2FEF')||(LA25_0 >= '\u3001' && LA25_0 <= '\uD7FF')||(LA25_0 >= '\uF900' && LA25_0 <= '\uFDCF')||(LA25_0 >= '\uFDF0' && LA25_0 <= '\uFFFD')) ) {
							int LA25_1 = input.LA(2);
							if ( ((LA25_1 >= '-' && LA25_1 <= '.')||(LA25_1 >= '0' && LA25_1 <= '9')||(LA25_1 >= 'A' && LA25_1 <= 'Z')||LA25_1=='_'||(LA25_1 >= 'a' && LA25_1 <= 'z')||(LA25_1 >= '\u00C0' && LA25_1 <= '\u00D6')||(LA25_1 >= '\u00D8' && LA25_1 <= '\u00F6')||(LA25_1 >= '\u00F8' && LA25_1 <= '\u02FF')||(LA25_1 >= '\u0370' && LA25_1 <= '\u037D')||(LA25_1 >= '\u037F' && LA25_1 <= '\u1FFF')||(LA25_1 >= '\u200C' && LA25_1 <= '\u200D')||(LA25_1 >= '\u2070' && LA25_1 <= '\u218F')||(LA25_1 >= '\u2C00' && LA25_1 <= '\u2FEF')||(LA25_1 >= '\u3001' && LA25_1 <= '\uD7FF')||(LA25_1 >= '\uF900' && LA25_1 <= '\uFDCF')||(LA25_1 >= '\uFDF0' && LA25_1 <= '\uFFFD')) ) {
								alt25=1;
							}

						}
						else if ( (LA25_0=='.') ) {
							alt25=1;
						}

						switch (alt25) {
						case 1 :
							// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
							{
							if ( (input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop25;
						}
					}

					mPN_CHARS(); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PN_LOCAL"

	// $ANTLR start "PN_CHARS_BASE"
	public final void mPN_CHARS_BASE() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:666:5: ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02FF' | '\\u0370' .. '\\u037D' | '\\u037F' .. '\\u1FFF' | '\\u200C' .. '\\u200D' | '\\u2070' .. '\\u218F' | '\\u2C00' .. '\\u2FEF' | '\\u3001' .. '\\uD7FF' | '\\uF900' .. '\\uFDCF' | '\\uFDF0' .. '\\uFFFD' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u02FF')||(input.LA(1) >= '\u0370' && input.LA(1) <= '\u037D')||(input.LA(1) >= '\u037F' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u200C' && input.LA(1) <= '\u200D')||(input.LA(1) >= '\u2070' && input.LA(1) <= '\u218F')||(input.LA(1) >= '\u2C00' && input.LA(1) <= '\u2FEF')||(input.LA(1) >= '\u3001' && input.LA(1) <= '\uD7FF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFDCF')||(input.LA(1) >= '\uFDF0' && input.LA(1) <= '\uFFFD') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PN_CHARS_BASE"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:683:5: ( '0' .. '9' )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:686:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:686:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
			// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:686:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
			int cnt27=0;
			loop27:
			while (true) {
				int alt27=2;
				int LA27_0 = input.LA(1);
				if ( ((LA27_0 >= '\t' && LA27_0 <= '\n')||LA27_0=='\r'||LA27_0==' ') ) {
					alt27=1;
				}

				switch (alt27) {
				case 1 :
					// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt27 >= 1 ) break loop27;
					EarlyExitException eee = new EarlyExitException(27, input);
					throw eee;
				}
				cnt27++;
			}

			 _channel=HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:8: ( T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | IRI_REF | PNAME_NS | PNAME_LN | VAR1 | VAR2 | LANGTAG | INTEGER | DECIMAL | DOUBLE | INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE | INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE | EXPONENT | STRING_LITERAL1 | STRING_LITERAL2 | ECHAR | NIL | ANON | PN_CHARS_U | VARNAME | PN_PREFIX | PN_LOCAL | WS )
		int alt28=44;
		alt28 = dfa28.predict(input);
		switch (alt28) {
			case 1 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:10: T__33
				{
				mT__33(); 

				}
				break;
			case 2 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:16: T__34
				{
				mT__34(); 

				}
				break;
			case 3 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:22: T__35
				{
				mT__35(); 

				}
				break;
			case 4 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:28: T__36
				{
				mT__36(); 

				}
				break;
			case 5 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:34: T__37
				{
				mT__37(); 

				}
				break;
			case 6 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:40: T__38
				{
				mT__38(); 

				}
				break;
			case 7 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:46: T__39
				{
				mT__39(); 

				}
				break;
			case 8 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:52: T__40
				{
				mT__40(); 

				}
				break;
			case 9 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:58: T__41
				{
				mT__41(); 

				}
				break;
			case 10 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:64: T__42
				{
				mT__42(); 

				}
				break;
			case 11 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:70: T__43
				{
				mT__43(); 

				}
				break;
			case 12 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:76: T__44
				{
				mT__44(); 

				}
				break;
			case 13 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:82: T__45
				{
				mT__45(); 

				}
				break;
			case 14 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:88: T__46
				{
				mT__46(); 

				}
				break;
			case 15 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:94: T__47
				{
				mT__47(); 

				}
				break;
			case 16 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:100: T__48
				{
				mT__48(); 

				}
				break;
			case 17 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:106: T__49
				{
				mT__49(); 

				}
				break;
			case 18 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:112: T__50
				{
				mT__50(); 

				}
				break;
			case 19 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:118: IRI_REF
				{
				mIRI_REF(); 

				}
				break;
			case 20 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:126: PNAME_NS
				{
				mPNAME_NS(); 

				}
				break;
			case 21 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:135: PNAME_LN
				{
				mPNAME_LN(); 

				}
				break;
			case 22 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:144: VAR1
				{
				mVAR1(); 

				}
				break;
			case 23 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:149: VAR2
				{
				mVAR2(); 

				}
				break;
			case 24 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:154: LANGTAG
				{
				mLANGTAG(); 

				}
				break;
			case 25 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:162: INTEGER
				{
				mINTEGER(); 

				}
				break;
			case 26 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:170: DECIMAL
				{
				mDECIMAL(); 

				}
				break;
			case 27 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:178: DOUBLE
				{
				mDOUBLE(); 

				}
				break;
			case 28 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:185: INTEGER_POSITIVE
				{
				mINTEGER_POSITIVE(); 

				}
				break;
			case 29 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:202: DECIMAL_POSITIVE
				{
				mDECIMAL_POSITIVE(); 

				}
				break;
			case 30 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:219: DOUBLE_POSITIVE
				{
				mDOUBLE_POSITIVE(); 

				}
				break;
			case 31 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:235: INTEGER_NEGATIVE
				{
				mINTEGER_NEGATIVE(); 

				}
				break;
			case 32 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:252: DECIMAL_NEGATIVE
				{
				mDECIMAL_NEGATIVE(); 

				}
				break;
			case 33 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:269: DOUBLE_NEGATIVE
				{
				mDOUBLE_NEGATIVE(); 

				}
				break;
			case 34 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:285: EXPONENT
				{
				mEXPONENT(); 

				}
				break;
			case 35 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:294: STRING_LITERAL1
				{
				mSTRING_LITERAL1(); 

				}
				break;
			case 36 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:310: STRING_LITERAL2
				{
				mSTRING_LITERAL2(); 

				}
				break;
			case 37 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:326: ECHAR
				{
				mECHAR(); 

				}
				break;
			case 38 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:332: NIL
				{
				mNIL(); 

				}
				break;
			case 39 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:336: ANON
				{
				mANON(); 

				}
				break;
			case 40 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:341: PN_CHARS_U
				{
				mPN_CHARS_U(); 

				}
				break;
			case 41 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:352: VARNAME
				{
				mVARNAME(); 

				}
				break;
			case 42 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:360: PN_PREFIX
				{
				mPN_PREFIX(); 

				}
				break;
			case 43 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:370: PN_LOCAL
				{
				mPN_LOCAL(); 

				}
				break;
			case 44 :
				// org/semanticweb/clipper/hornshiq/sparql/Sparql.g:1:379: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA15 dfa15 = new DFA15(this);
	protected DFA28 dfa28 = new DFA28(this);
	static final String DFA15_eotS =
		"\5\uffff";
	static final String DFA15_eofS =
		"\5\uffff";
	static final String DFA15_minS =
		"\2\56\3\uffff";
	static final String DFA15_maxS =
		"\1\71\1\145\3\uffff";
	static final String DFA15_acceptS =
		"\2\uffff\1\2\1\1\1\3";
	static final String DFA15_specialS =
		"\5\uffff}>";
	static final String[] DFA15_transitionS = {
			"\1\2\1\uffff\12\1",
			"\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
			"",
			"",
			""
	};

	static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
	static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
	static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
	static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
	static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
	static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
	static final short[][] DFA15_transition;

	static {
		int numStates = DFA15_transitionS.length;
		DFA15_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
		}
	}

	protected class DFA15 extends DFA {

		public DFA15(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 15;
			this.eot = DFA15_eot;
			this.eof = DFA15_eof;
			this.min = DFA15_min;
			this.max = DFA15_max;
			this.accept = DFA15_accept;
			this.special = DFA15_special;
			this.transition = DFA15_transition;
		}
		@Override
		public String getDescription() {
			return "575:1: DOUBLE : ( ( DIGIT )+ '.' ( DIGIT )* EXPONENT | '.' ( DIGIT )+ EXPONENT | ( DIGIT )+ EXPONENT );";
		}
	}

	static final String DFA28_eotS =
		"\1\uffff\1\42\3\uffff\1\44\1\uffff\4\47\1\57\2\uffff\1\61\2\47\3\uffff"+
		"\1\47\1\67\3\uffff\1\71\2\uffff\1\47\3\uffff\1\47\4\uffff\1\103\1\53\1"+
		"\uffff\1\53\1\uffff\1\106\1\uffff\3\53\3\uffff\2\53\1\106\1\66\4\uffff"+
		"\1\71\1\103\2\53\1\uffff\1\121\1\uffff\1\125\3\uffff\1\53\1\uffff\5\53"+
		"\1\66\1\103\2\76\1\104\1\uffff\1\143\1\uffff\1\143\1\uffff\1\145\1\uffff"+
		"\1\145\1\146\4\53\1\153\1\76\2\104\1\143\1\uffff\1\145\2\uffff\2\53\1"+
		"\156\1\157\1\uffff\1\160\1\161\4\uffff";
	static final String DFA28_eofS =
		"\162\uffff";
	static final String DFA28_minS =
		"\2\11\3\uffff\1\60\1\uffff\4\55\1\11\2\uffff\3\55\3\uffff\1\53\1\60\3"+
		"\uffff\1\55\2\56\1\55\3\uffff\1\55\4\uffff\1\60\1\55\1\uffff\3\55\1\uffff"+
		"\3\55\3\uffff\4\55\4\uffff\2\55\1\53\1\55\1\uffff\1\56\1\60\1\56\1\60"+
		"\2\uffff\1\55\1\uffff\7\55\1\53\1\60\1\55\1\uffff\1\60\1\uffff\1\60\1"+
		"\uffff\1\60\1\uffff\1\60\6\55\1\60\2\55\1\60\1\uffff\1\60\2\uffff\4\55"+
		"\1\uffff\2\55\4\uffff";
	static final String DFA28_maxS =
		"\1\ufffd\1\51\3\uffff\1\71\1\uffff\4\ufffd\1\135\2\uffff\3\ufffd\3\uffff"+
		"\2\ufffd\3\uffff\1\ufffd\2\71\1\ufffd\3\uffff\1\ufffd\4\uffff\1\145\1"+
		"\ufffd\1\uffff\3\ufffd\1\uffff\3\ufffd\3\uffff\4\ufffd\4\uffff\4\ufffd"+
		"\1\uffff\1\145\1\71\1\145\1\71\2\uffff\1\ufffd\1\uffff\7\ufffd\2\71\1"+
		"\ufffd\1\uffff\1\145\1\uffff\1\145\1\uffff\1\145\1\uffff\1\145\6\ufffd"+
		"\1\71\2\ufffd\1\145\1\uffff\1\145\2\uffff\4\ufffd\1\uffff\2\ufffd\4\uffff";
	static final String DFA28_acceptS =
		"\2\uffff\1\2\1\3\1\4\1\uffff\1\6\5\uffff\1\14\1\15\3\uffff\1\21\1\22\1"+
		"\23\2\uffff\1\26\1\27\1\30\4\uffff\1\43\1\44\1\45\1\uffff\1\54\1\1\1\46"+
		"\1\5\2\uffff\1\50\3\uffff\1\51\3\uffff\1\13\1\47\1\16\4\uffff\1\42\1\24"+
		"\1\25\1\31\4\uffff\1\53\4\uffff\1\32\1\33\1\uffff\1\52\12\uffff\1\34\1"+
		"\uffff\1\36\1\uffff\1\37\1\uffff\1\41\13\uffff\1\35\1\uffff\1\40\1\7\4"+
		"\uffff\1\20\2\uffff\1\12\1\17\1\10\1\11";
	static final String DFA28_specialS =
		"\162\uffff}>";
	static final String[] DFA28_transitionS = {
			"\2\41\2\uffff\1\41\22\uffff\1\41\1\uffff\1\36\1\uffff\1\27\2\uffff\1"+
			"\35\1\1\1\2\1\3\1\32\1\4\1\33\1\5\1\uffff\12\31\1\25\1\6\1\23\2\uffff"+
			"\1\26\1\30\1\34\1\7\2\34\1\24\12\34\1\10\2\34\1\11\3\34\1\12\3\34\1\13"+
			"\1\37\1\14\1\15\1\40\1\uffff\1\16\3\34\1\24\1\17\15\34\1\20\6\34\1\21"+
			"\1\uffff\1\22\102\uffff\27\34\1\uffff\37\34\1\uffff\u0208\34\160\uffff"+
			"\16\34\1\uffff\u1c81\34\14\uffff\2\34\142\uffff\u0120\34\u0a70\uffff"+
			"\u03f0\34\21\uffff\ua7ff\34\u2100\uffff\u04d0\34\40\uffff\u020e\34",
			"\2\43\2\uffff\1\43\22\uffff\1\43\10\uffff\1\43",
			"",
			"",
			"",
			"\12\45",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\1\46\31\50\4\uffff\1\50\1\uffff"+
			"\32\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160"+
			"\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120"+
			"\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\21\50\1\54\10\50\4\uffff\1\50"+
			"\1\uffff\32\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208"+
			"\50\160\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff"+
			"\u0120\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50"+
			"\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\55\25\50\4\uffff\1\50\1"+
			"\uffff\32\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208"+
			"\50\160\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff"+
			"\u0120\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50"+
			"\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\7\50\1\56\22\50\4\uffff\1\50\1"+
			"\uffff\32\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208"+
			"\50\160\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff"+
			"\u0120\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50"+
			"\40\uffff\u020e\50",
			"\2\60\2\uffff\1\60\22\uffff\1\60\74\uffff\1\60",
			"",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\1\62"+
			"\31\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160"+
			"\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120"+
			"\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\21"+
			"\50\1\63\10\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208"+
			"\50\160\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff"+
			"\u0120\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50"+
			"\40\uffff\u020e\50",
			"",
			"",
			"",
			"\1\66\1\uffff\1\64\1\51\1\uffff\12\65\1\25\6\uffff\32\50\4\uffff\1\50"+
			"\1\uffff\32\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208"+
			"\50\160\53\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff"+
			"\u0120\50\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50"+
			"\40\uffff\u020e\50",
			"\12\70\7\uffff\32\70\4\uffff\1\70\1\uffff\32\70\105\uffff\27\70\1\uffff"+
			"\37\70\1\uffff\u0208\70\160\uffff\16\70\1\uffff\u1c81\70\14\uffff\2\70"+
			"\142\uffff\u0120\70\u0a70\uffff\u03f0\70\21\uffff\ua7ff\70\u2100\uffff"+
			"\u04d0\70\40\uffff\u020e\70",
			"",
			"",
			"",
			"\1\76\1\73\1\uffff\12\72\7\uffff\4\75\1\74\25\75\4\uffff\1\75\1\uffff"+
			"\4\75\1\74\25\75\74\uffff\1\53\10\uffff\27\75\1\uffff\37\75\1\uffff\u0208"+
			"\75\160\53\16\75\1\uffff\u1c81\75\14\uffff\2\75\61\uffff\2\53\57\uffff"+
			"\u0120\75\u0a70\uffff\u03f0\75\21\uffff\ua7ff\75\u2100\uffff\u04d0\75"+
			"\40\uffff\u020e\75",
			"\1\100\1\uffff\12\77",
			"\1\102\1\uffff\12\101",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"",
			"",
			"",
			"\2\76\1\uffff\12\75\7\uffff\32\75\4\uffff\1\75\1\uffff\32\75\74\uffff"+
			"\1\53\10\uffff\27\75\1\uffff\37\75\1\uffff\u0208\75\160\53\16\75\1\uffff"+
			"\u1c81\75\14\uffff\2\75\61\uffff\2\53\57\uffff\u0120\75\u0a70\uffff\u03f0"+
			"\75\21\uffff\ua7ff\75\u2100\uffff\u04d0\75\40\uffff\u020e\75",
			"",
			"",
			"",
			"",
			"\12\45\13\uffff\1\104\37\uffff\1\104",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\22\50\1\105\7\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff\16\50\1"+
			"\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff\u03f0\50"+
			"\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52\105"+
			"\uffff\27\52\1\uffff\37\52\1\uffff\u0208\52\160\uffff\16\52\1\uffff\u1c81"+
			"\52\14\uffff\2\52\142\uffff\u0120\52\u0a70\uffff\u03f0\52\21\uffff\ua7ff"+
			"\52\u2100\uffff\u04d0\52\40\uffff\u020e\52",
			"\1\52\1\51\1\uffff\12\52\1\25\6\uffff\32\52\4\uffff\1\52\1\uffff\32"+
			"\52\105\uffff\27\52\1\uffff\37\52\1\uffff\u0208\52\160\uffff\16\52\1"+
			"\uffff\u1c81\52\14\uffff\2\52\142\uffff\u0120\52\u0a70\uffff\u03f0\52"+
			"\21\uffff\ua7ff\52\u2100\uffff\u04d0\52\40\uffff\u020e\52",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\107\25\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\13\50\1\110\16\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\111\25\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"",
			"",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\13"+
			"\50\1\112\16\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\24"+
			"\50\1\113\5\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\114\1\25\6\uffff\32\52\4\uffff\1\52\1\uffff\32"+
			"\52\105\uffff\27\52\1\uffff\37\52\1\uffff\u0208\52\160\uffff\16\52\1"+
			"\uffff\u1c81\52\14\uffff\2\52\142\uffff\u0120\52\u0a70\uffff\u03f0\52"+
			"\21\uffff\ua7ff\52\u2100\uffff\u04d0\52\40\uffff\u020e\52",
			"\1\52\1\51\1\uffff\12\65\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"",
			"",
			"",
			"",
			"\1\76\1\73\1\uffff\12\72\7\uffff\4\75\1\74\25\75\4\uffff\1\75\1\uffff"+
			"\4\75\1\74\25\75\74\uffff\1\53\10\uffff\27\75\1\uffff\37\75\1\uffff\u0208"+
			"\75\160\53\16\75\1\uffff\u1c81\75\14\uffff\2\75\61\uffff\2\53\57\uffff"+
			"\u0120\75\u0a70\uffff\u03f0\75\21\uffff\ua7ff\75\u2100\uffff\u04d0\75"+
			"\40\uffff\u020e\75",
			"\2\76\1\uffff\12\115\7\uffff\4\76\1\116\25\76\4\uffff\1\76\1\uffff\4"+
			"\76\1\116\25\76\105\uffff\27\76\1\uffff\37\76\1\uffff\u0208\76\160\uffff"+
			"\16\76\1\uffff\u1c81\76\14\uffff\2\76\142\uffff\u0120\76\u0a70\uffff"+
			"\u03f0\76\21\uffff\ua7ff\76\u2100\uffff\u04d0\76\40\uffff\u020e\76",
			"\1\104\1\uffff\1\117\1\76\1\uffff\12\120\7\uffff\32\75\4\uffff\1\75"+
			"\1\uffff\32\75\105\uffff\27\75\1\uffff\37\75\1\uffff\u0208\75\160\uffff"+
			"\16\75\1\uffff\u1c81\75\14\uffff\2\75\142\uffff\u0120\75\u0a70\uffff"+
			"\u03f0\75\21\uffff\ua7ff\75\u2100\uffff\u04d0\75\40\uffff\u020e\75",
			"\2\76\1\uffff\12\75\7\uffff\32\75\4\uffff\1\75\1\uffff\32\75\105\uffff"+
			"\27\75\1\uffff\37\75\1\uffff\u0208\75\160\uffff\16\75\1\uffff\u1c81\75"+
			"\14\uffff\2\75\142\uffff\u0120\75\u0a70\uffff\u03f0\75\21\uffff\ua7ff"+
			"\75\u2100\uffff\u04d0\75\40\uffff\u020e\75",
			"",
			"\1\122\1\uffff\12\77\13\uffff\1\123\37\uffff\1\123",
			"\12\124",
			"\1\126\1\uffff\12\101\13\uffff\1\127\37\uffff\1\127",
			"\12\130",
			"",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\131\25\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\5\50\1\132\24\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\133\25\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\21\50\1\134\10\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\22"+
			"\50\1\135\7\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
			"\1\136\25\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\114\1\25\6\uffff\32\52\4\uffff\1\52\1\uffff\32"+
			"\52\105\uffff\27\52\1\uffff\37\52\1\uffff\u0208\52\160\uffff\16\52\1"+
			"\uffff\u1c81\52\14\uffff\2\52\142\uffff\u0120\52\u0a70\uffff\u03f0\52"+
			"\21\uffff\ua7ff\52\u2100\uffff\u04d0\52\40\uffff\u020e\52",
			"\2\76\1\uffff\12\115\7\uffff\4\76\1\116\25\76\4\uffff\1\76\1\uffff\4"+
			"\76\1\116\25\76\105\uffff\27\76\1\uffff\37\76\1\uffff\u0208\76\160\uffff"+
			"\16\76\1\uffff\u1c81\76\14\uffff\2\76\142\uffff\u0120\76\u0a70\uffff"+
			"\u03f0\76\21\uffff\ua7ff\76\u2100\uffff\u04d0\76\40\uffff\u020e\76",
			"\1\104\1\uffff\1\137\2\uffff\12\140",
			"\12\141",
			"\2\76\1\uffff\12\120\7\uffff\32\75\4\uffff\1\75\1\uffff\32\75\74\uffff"+
			"\1\53\10\uffff\27\75\1\uffff\37\75\1\uffff\u0208\75\160\53\16\75\1\uffff"+
			"\u1c81\75\14\uffff\2\75\61\uffff\2\53\57\uffff\u0120\75\u0a70\uffff\u03f0"+
			"\75\21\uffff\ua7ff\75\u2100\uffff\u04d0\75\40\uffff\u020e\75",
			"",
			"\12\142\13\uffff\1\123\37\uffff\1\123",
			"",
			"\12\124\13\uffff\1\123\37\uffff\1\123",
			"",
			"\12\144\13\uffff\1\127\37\uffff\1\127",
			"",
			"\12\130\13\uffff\1\127\37\uffff\1\127",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\10\50\1\147\21\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\2\50\1\150\27\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\4\50\1\151\25\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\4\50"+
			"\1\152\25\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\12\140",
			"\2\76\1\uffff\12\140\7\uffff\32\76\4\uffff\1\76\1\uffff\32\76\105\uffff"+
			"\27\76\1\uffff\37\76\1\uffff\u0208\76\160\uffff\16\76\1\uffff\u1c81\76"+
			"\14\uffff\2\76\142\uffff\u0120\76\u0a70\uffff\u03f0\76\21\uffff\ua7ff"+
			"\76\u2100\uffff\u04d0\76\40\uffff\u020e\76",
			"\2\76\1\uffff\12\141\7\uffff\32\76\4\uffff\1\76\1\uffff\32\76\105\uffff"+
			"\27\76\1\uffff\37\76\1\uffff\u0208\76\160\uffff\16\76\1\uffff\u1c81\76"+
			"\14\uffff\2\76\142\uffff\u0120\76\u0a70\uffff\u03f0\76\21\uffff\ua7ff"+
			"\76\u2100\uffff\u04d0\76\40\uffff\u020e\76",
			"\12\142\13\uffff\1\123\37\uffff\1\123",
			"",
			"\12\144\13\uffff\1\127\37\uffff\1\127",
			"",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\27\50\1\154\2\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\23\50\1\155\6\50\4\uffff\1\50"+
			"\1\uffff\32\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\uffff"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\142\uffff\u0120\50\u0a70\uffff"+
			"\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"\1\52\1\51\1\uffff\12\50\1\25\6\uffff\32\50\4\uffff\1\50\1\uffff\32"+
			"\50\74\uffff\1\53\10\uffff\27\50\1\uffff\37\50\1\uffff\u0208\50\160\53"+
			"\16\50\1\uffff\u1c81\50\14\uffff\2\50\61\uffff\2\53\57\uffff\u0120\50"+
			"\u0a70\uffff\u03f0\50\21\uffff\ua7ff\50\u2100\uffff\u04d0\50\40\uffff"+
			"\u020e\50",
			"",
			"",
			"",
			""
	};

	static final short[] DFA28_eot = DFA.unpackEncodedString(DFA28_eotS);
	static final short[] DFA28_eof = DFA.unpackEncodedString(DFA28_eofS);
	static final char[] DFA28_min = DFA.unpackEncodedStringToUnsignedChars(DFA28_minS);
	static final char[] DFA28_max = DFA.unpackEncodedStringToUnsignedChars(DFA28_maxS);
	static final short[] DFA28_accept = DFA.unpackEncodedString(DFA28_acceptS);
	static final short[] DFA28_special = DFA.unpackEncodedString(DFA28_specialS);
	static final short[][] DFA28_transition;

	static {
		int numStates = DFA28_transitionS.length;
		DFA28_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA28_transition[i] = DFA.unpackEncodedString(DFA28_transitionS[i]);
		}
	}

	protected class DFA28 extends DFA {

		public DFA28(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 28;
			this.eot = DFA28_eot;
			this.eof = DFA28_eof;
			this.min = DFA28_min;
			this.max = DFA28_max;
			this.accept = DFA28_accept;
			this.special = DFA28_special;
			this.transition = DFA28_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | IRI_REF | PNAME_NS | PNAME_LN | VAR1 | VAR2 | LANGTAG | INTEGER | DECIMAL | DOUBLE | INTEGER_POSITIVE | DECIMAL_POSITIVE | DOUBLE_POSITIVE | INTEGER_NEGATIVE | DECIMAL_NEGATIVE | DOUBLE_NEGATIVE | EXPONENT | STRING_LITERAL1 | STRING_LITERAL2 | ECHAR | NIL | ANON | PN_CHARS_U | VARNAME | PN_PREFIX | PN_LOCAL | WS );";
		}
	}

}
