// $ANTLR 3.5.2 org/semanticweb/clipper/dllog/DLLog.g 2015-11-22 17:59:55

package org.semanticweb.clipper.dllog;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class DLLogLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int ATOM=4;
	public static final int ATOM_LIST=5;
	public static final int CONSTANT=6;
	public static final int DL_PREDICATE=7;
	public static final int LOWER_LEADING_ID=8;
	public static final int NON_DL_PREDICATE=9;
	public static final int PREDICATE=10;
	public static final int RULE=11;
	public static final int TERM_LIST=12;
	public static final int UPPER_LEADING_ID=13;
	public static final int VARIABLE=14;
	public static final int WS=15;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public DLLogLexer() {} 
	public DLLogLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public DLLogLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/dllog/DLLog.g"; }

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:11:7: ( '(' )
			// org/semanticweb/clipper/dllog/DLLog.g:11:9: '('
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
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:12:7: ( ')' )
			// org/semanticweb/clipper/dllog/DLLog.g:12:9: ')'
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
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:13:7: ( ',' )
			// org/semanticweb/clipper/dllog/DLLog.g:13:9: ','
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
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:14:7: ( '.' )
			// org/semanticweb/clipper/dllog/DLLog.g:14:9: '.'
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
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:15:7: ( ':-' )
			// org/semanticweb/clipper/dllog/DLLog.g:15:9: ':-'
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
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:16:7: ( 'v' )
			// org/semanticweb/clipper/dllog/DLLog.g:16:9: 'v'
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
	// $ANTLR end "T__21"

	// $ANTLR start "UPPER_LEADING_ID"
	public final void mUPPER_LEADING_ID() throws RecognitionException {
		try {
			int _type = UPPER_LEADING_ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/semanticweb/clipper/dllog/DLLog.g:72:19: ( ( 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// org/semanticweb/clipper/dllog/DLLog.g:72:23: ( 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/dllog/DLLog.g:72:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:
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
			// org/semanticweb/clipper/dllog/DLLog.g:73:19: ( ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// org/semanticweb/clipper/dllog/DLLog.g:73:23: ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// org/semanticweb/clipper/dllog/DLLog.g:73:34: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:
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
			// org/semanticweb/clipper/dllog/DLLog.g:74:5: ( ( ' ' | '\\n' | '\\r' )+ )
			// org/semanticweb/clipper/dllog/DLLog.g:74:7: ( ' ' | '\\n' | '\\r' )+
			{
			// org/semanticweb/clipper/dllog/DLLog.g:74:7: ( ' ' | '\\n' | '\\r' )+
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
					// org/semanticweb/clipper/dllog/DLLog.g:
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

	@Override
	public void mTokens() throws RecognitionException {
		// org/semanticweb/clipper/dllog/DLLog.g:1:8: ( T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | UPPER_LEADING_ID | LOWER_LEADING_ID | WS )
		int alt4=9;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt4=1;
			}
			break;
		case ')':
			{
			alt4=2;
			}
			break;
		case ',':
			{
			alt4=3;
			}
			break;
		case '.':
			{
			alt4=4;
			}
			break;
		case ':':
			{
			alt4=5;
			}
			break;
		case 'v':
			{
			int LA4_6 = input.LA(2);
			if ( ((LA4_6 >= '0' && LA4_6 <= '9')||(LA4_6 >= 'A' && LA4_6 <= 'Z')||LA4_6=='_'||(LA4_6 >= 'a' && LA4_6 <= 'z')) ) {
				alt4=8;
			}

			else {
				alt4=6;
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
		case 'X':
		case 'Y':
		case 'Z':
			{
			alt4=7;
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
			alt4=8;
			}
			break;
		case '\n':
		case '\r':
		case ' ':
			{
			alt4=9;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 4, 0, input);
			throw nvae;
		}
		switch (alt4) {
			case 1 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:10: T__16
				{
				mT__16(); 

				}
				break;
			case 2 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:16: T__17
				{
				mT__17(); 

				}
				break;
			case 3 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:22: T__18
				{
				mT__18(); 

				}
				break;
			case 4 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:28: T__19
				{
				mT__19(); 

				}
				break;
			case 5 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:34: T__20
				{
				mT__20(); 

				}
				break;
			case 6 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:40: T__21
				{
				mT__21(); 

				}
				break;
			case 7 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:46: UPPER_LEADING_ID
				{
				mUPPER_LEADING_ID(); 

				}
				break;
			case 8 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:63: LOWER_LEADING_ID
				{
				mLOWER_LEADING_ID(); 

				}
				break;
			case 9 :
				// org/semanticweb/clipper/dllog/DLLog.g:1:80: WS
				{
				mWS(); 

				}
				break;

		}
	}



}
