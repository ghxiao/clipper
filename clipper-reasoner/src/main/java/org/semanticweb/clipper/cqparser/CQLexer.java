// $ANTLR 3.5.2 org/semanticweb/clipper/cqparser/CQ.g 2015-11-22 17:59:56

package org.semanticweb.clipper.cqparser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CQLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int ATOM=4;
	public static final int ATOM_LIST=5;
	public static final int CONSTANT=6;
	public static final int DL_PREDICATE=7;
	public static final int INT=8;
	public static final int LOWER_LEADING_ID=9;
	public static final int NON_DL_PREDICATE=10;
	public static final int PREDICATE=11;
	public static final int RULE=12;
	public static final int RULE_LIST=13;
	public static final int TERM_LIST=14;
	public static final int UPPER_LEADING_ID=15;
	public static final int VARIABLE=16;
	public static final int WS=17;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public CQLexer() {} 
	public CQLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public CQLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/cqparser/CQ.g"; }

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:11:7: ( '(' )
			// org/semanticweb/clipper/cqparser/CQ.g:11:9: '('
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
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:12:7: ( ')' )
			// org/semanticweb/clipper/cqparser/CQ.g:12:9: ')'
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
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:13:7: ( ',' )
			// org/semanticweb/clipper/cqparser/CQ.g:13:9: ','
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
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:14:7: ( '.' )
			// org/semanticweb/clipper/cqparser/CQ.g:14:9: '.'
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
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:15:7: ( ':-' )
			// org/semanticweb/clipper/cqparser/CQ.g:15:9: ':-'
			{
			match(":-"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:16:7: ( '<-' )
			// org/semanticweb/clipper/cqparser/CQ.g:16:9: '<-'
			{
			match("<-"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:17:7: ( '?' )
			// org/semanticweb/clipper/cqparser/CQ.g:17:9: '?'
			{
			match('?'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:18:7: ( 'X' )
			// org/semanticweb/clipper/cqparser/CQ.g:18:9: 'X'
			{
			match('X'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:19:7: ( 'v' )
			// org/semanticweb/clipper/cqparser/CQ.g:19:9: 'v'
			{
			match('v'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "UPPER_LEADING_ID"
	public final void mUPPER_LEADING_ID() throws RecognitionException {
		try {
			int _type = UPPER_LEADING_ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:162:19: ( ( 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// org/semanticweb/clipper/cqparser/CQ.g:162:23: ( 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/cqparser/CQ.g:162:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UPPER_LEADING_ID"

	// $ANTLR start "LOWER_LEADING_ID"
	public final void mLOWER_LEADING_ID() throws RecognitionException {
		try {
			int _type = LOWER_LEADING_ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:163:19: ( ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// org/semanticweb/clipper/cqparser/CQ.g:163:23: ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/cqparser/CQ.g:163:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
					break loop2;
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
	// $ANTLR end "LOWER_LEADING_ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:164:5: ( ( ' ' | '\\n' | '\\r' )+ )
			// org/semanticweb/clipper/cqparser/CQ.g:164:7: ( ' ' | '\\n' | '\\r' )+
			{
			// org/semanticweb/clipper/cqparser/CQ.g:164:7: ( ' ' | '\\n' | '\\r' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='\n'||LA3_0=='\r'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:
					{
					if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
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

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/cqparser/CQ.g:165:5: ( ( '0' .. '9' )+ )
			// org/semanticweb/clipper/cqparser/CQ.g:165:7: ( '0' .. '9' )+
			{
			// org/semanticweb/clipper/cqparser/CQ.g:165:7: ( '0' .. '9' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:
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
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	@Override
	public void mTokens() throws RecognitionException {
		// org/semanticweb/clipper/cqparser/CQ.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | UPPER_LEADING_ID | LOWER_LEADING_ID | WS | INT )
		int alt5=13;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt5=1;
			}
			break;
		case ')':
			{
			alt5=2;
			}
			break;
		case ',':
			{
			alt5=3;
			}
			break;
		case '.':
			{
			alt5=4;
			}
			break;
		case ':':
			{
			alt5=5;
			}
			break;
		case '<':
			{
			alt5=6;
			}
			break;
		case '?':
			{
			alt5=7;
			}
			break;
		case 'X':
			{
			int LA5_8 = input.LA(2);
			if ( ((LA5_8 >= '0' && LA5_8 <= '9')||(LA5_8 >= 'A' && LA5_8 <= 'Z')||LA5_8=='_'||(LA5_8 >= 'a' && LA5_8 <= 'z')) ) {
				alt5=10;
			}

			else {
				alt5=8;
			}

			}
			break;
		case 'v':
			{
			int LA5_9 = input.LA(2);
			if ( ((LA5_9 >= '0' && LA5_9 <= '9')||(LA5_9 >= 'A' && LA5_9 <= 'Z')||LA5_9=='_'||(LA5_9 >= 'a' && LA5_9 <= 'z')) ) {
				alt5=11;
			}

			else {
				alt5=9;
			}

			}
			break;
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'Y':
		case 'Z':
			{
			alt5=10;
			}
			break;
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt5=11;
			}
			break;
		case '\n':
		case '\r':
		case ' ':
			{
			alt5=12;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt5=13;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:10: T__18
				{
				mT__18(); 

				}
				break;
			case 2 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:16: T__19
				{
				mT__19(); 

				}
				break;
			case 3 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:22: T__20
				{
				mT__20(); 

				}
				break;
			case 4 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:28: T__21
				{
				mT__21(); 

				}
				break;
			case 5 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:34: T__22
				{
				mT__22(); 

				}
				break;
			case 6 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:40: T__23
				{
				mT__23(); 

				}
				break;
			case 7 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:46: T__24
				{
				mT__24(); 

				}
				break;
			case 8 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:52: T__25
				{
				mT__25(); 

				}
				break;
			case 9 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:58: T__26
				{
				mT__26(); 

				}
				break;
			case 10 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:64: UPPER_LEADING_ID
				{
				mUPPER_LEADING_ID(); 

				}
				break;
			case 11 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:81: LOWER_LEADING_ID
				{
				mLOWER_LEADING_ID(); 

				}
				break;
			case 12 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:98: WS
				{
				mWS(); 

				}
				break;
			case 13 :
				// org/semanticweb/clipper/cqparser/CQ.g:1:101: INT
				{
				mINT(); 

				}
				break;

		}
	}



}
