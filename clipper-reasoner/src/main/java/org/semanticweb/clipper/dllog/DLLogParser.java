// $ANTLR 3.5.2 org/semanticweb/clipper/dllog/DLLog.g 2015-11-22 17:59:55

package org.semanticweb.clipper.dllog;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class DLLogParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ATOM", "ATOM_LIST", "CONSTANT", 
		"DL_PREDICATE", "LOWER_LEADING_ID", "NON_DL_PREDICATE", "PREDICATE", "RULE", 
		"TERM_LIST", "UPPER_LEADING_ID", "VARIABLE", "WS", "'('", "')'", "','", 
		"'.'", "':-'", "'v'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public DLLogParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public DLLogParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return DLLogParser.tokenNames; }
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/dllog/DLLog.g"; }


	public static class dl_log_rules_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "dl_log_rules"
	// org/semanticweb/clipper/dllog/DLLog.g:29:1: dl_log_rules : ( dl_log_rule )* ;
	public final dl_log_rules_return dl_log_rules() throws RecognitionException {
		dl_log_rules_return retval = new dl_log_rules_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope dl_log_rule1 =null;


		try {
			// org/semanticweb/clipper/dllog/DLLog.g:29:13: ( ( dl_log_rule )* )
			// org/semanticweb/clipper/dllog/DLLog.g:30:3: ( dl_log_rule )*
			{
			root_0 = (Object)adaptor.nil();


			// org/semanticweb/clipper/dllog/DLLog.g:30:3: ( dl_log_rule )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==LOWER_LEADING_ID||LA1_0==UPPER_LEADING_ID) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:30:3: dl_log_rule
					{
					pushFollow(FOLLOW_dl_log_rule_in_dl_log_rules99);
					dl_log_rule1=dl_log_rule();
					state._fsp--;

					adaptor.addChild(root_0, dl_log_rule1.getTree());

					}
					break;

				default :
					break loop1;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "dl_log_rules"


	public static class dl_log_rule_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "dl_log_rule"
	// org/semanticweb/clipper/dllog/DLLog.g:32:1: dl_log_rule : head ':-' body '.' -> ^( RULE head body ) ;
	public final dl_log_rule_return dl_log_rule() throws RecognitionException {
		dl_log_rule_return retval = new dl_log_rule_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal3=null;
		Token char_literal5=null;
		ParserRuleReturnScope head2 =null;
		ParserRuleReturnScope body4 =null;

		Object string_literal3_tree=null;
		Object char_literal5_tree=null;
		RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
		RewriteRuleSubtreeStream stream_body=new RewriteRuleSubtreeStream(adaptor,"rule body");

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:32:12: ( head ':-' body '.' -> ^( RULE head body ) )
			// org/semanticweb/clipper/dllog/DLLog.g:33:3: head ':-' body '.'
			{
			pushFollow(FOLLOW_head_in_dl_log_rule112);
			head2=head();
			state._fsp--;

			stream_head.add(head2.getTree());
			string_literal3=(Token)match(input,20,FOLLOW_20_in_dl_log_rule114);  
			stream_20.add(string_literal3);

			pushFollow(FOLLOW_body_in_dl_log_rule116);
			body4=body();
			state._fsp--;

			stream_body.add(body4.getTree());
			char_literal5=(Token)match(input,19,FOLLOW_19_in_dl_log_rule118);  
			stream_19.add(char_literal5);

			// AST REWRITE
			// elements: head, body
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 33:22: -> ^( RULE head body )
			{
				// org/semanticweb/clipper/dllog/DLLog.g:33:25: ^( RULE head body )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULE, "RULE"), root_1);
				adaptor.addChild(root_1, stream_head.nextTree());
				adaptor.addChild(root_1, stream_body.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "dl_log_rule"


	public static class head_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "head"
	// org/semanticweb/clipper/dllog/DLLog.g:35:1: head : atom ( 'v' atom )* -> ^( ATOM_LIST ( atom )* ) ;
	public final head_return head() throws RecognitionException {
		head_return retval = new head_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal7=null;
		ParserRuleReturnScope atom6 =null;
		ParserRuleReturnScope atom8 =null;

		Object char_literal7_tree=null;
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:35:5: ( atom ( 'v' atom )* -> ^( ATOM_LIST ( atom )* ) )
			// org/semanticweb/clipper/dllog/DLLog.g:36:3: atom ( 'v' atom )*
			{
			pushFollow(FOLLOW_atom_in_head141);
			atom6=atom();
			state._fsp--;

			stream_atom.add(atom6.getTree());
			// org/semanticweb/clipper/dllog/DLLog.g:36:8: ( 'v' atom )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==21) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:36:9: 'v' atom
					{
					char_literal7=(Token)match(input,21,FOLLOW_21_in_head144);  
					stream_21.add(char_literal7);

					pushFollow(FOLLOW_atom_in_head146);
					atom8=atom();
					state._fsp--;

					stream_atom.add(atom8.getTree());
					}
					break;

				default :
					break loop2;
				}
			}

			// AST REWRITE
			// elements: atom
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 36:20: -> ^( ATOM_LIST ( atom )* )
			{
				// org/semanticweb/clipper/dllog/DLLog.g:36:23: ^( ATOM_LIST ( atom )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ATOM_LIST, "ATOM_LIST"), root_1);
				// org/semanticweb/clipper/dllog/DLLog.g:36:35: ( atom )*
				while ( stream_atom.hasNext() ) {
					adaptor.addChild(root_1, stream_atom.nextTree());
				}
				stream_atom.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "head"


	public static class body_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "body"
	// org/semanticweb/clipper/dllog/DLLog.g:38:1: body : atom ( ',' atom )* -> ^( ATOM_LIST ( atom )* ) ;
	public final body_return body() throws RecognitionException {
		body_return retval = new body_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal10=null;
		ParserRuleReturnScope atom9 =null;
		ParserRuleReturnScope atom11 =null;

		Object char_literal10_tree=null;
		RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
		RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:38:5: ( atom ( ',' atom )* -> ^( ATOM_LIST ( atom )* ) )
			// org/semanticweb/clipper/dllog/DLLog.g:39:3: atom ( ',' atom )*
			{
			pushFollow(FOLLOW_atom_in_body168);
			atom9=atom();
			state._fsp--;

			stream_atom.add(atom9.getTree());
			// org/semanticweb/clipper/dllog/DLLog.g:39:8: ( ',' atom )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==18) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:39:9: ',' atom
					{
					char_literal10=(Token)match(input,18,FOLLOW_18_in_body171);  
					stream_18.add(char_literal10);

					pushFollow(FOLLOW_atom_in_body173);
					atom11=atom();
					state._fsp--;

					stream_atom.add(atom11.getTree());
					}
					break;

				default :
					break loop3;
				}
			}

			// AST REWRITE
			// elements: atom
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 39:20: -> ^( ATOM_LIST ( atom )* )
			{
				// org/semanticweb/clipper/dllog/DLLog.g:39:23: ^( ATOM_LIST ( atom )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ATOM_LIST, "ATOM_LIST"), root_1);
				// org/semanticweb/clipper/dllog/DLLog.g:39:35: ( atom )*
				while ( stream_atom.hasNext() ) {
					adaptor.addChild(root_1, stream_atom.nextTree());
				}
				stream_atom.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "body"


	public static class atom_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "atom"
	// org/semanticweb/clipper/dllog/DLLog.g:41:1: atom : predicate '(' term ( ',' term )* ')' -> ^( predicate ( term )* ) ;
	public final atom_return atom() throws RecognitionException {
		atom_return retval = new atom_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal13=null;
		Token char_literal15=null;
		Token char_literal17=null;
		ParserRuleReturnScope predicate12 =null;
		ParserRuleReturnScope term14 =null;
		ParserRuleReturnScope term16 =null;

		Object char_literal13_tree=null;
		Object char_literal15_tree=null;
		Object char_literal17_tree=null;
		RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
		RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
		RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
		RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
		RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:41:5: ( predicate '(' term ( ',' term )* ')' -> ^( predicate ( term )* ) )
			// org/semanticweb/clipper/dllog/DLLog.g:42:3: predicate '(' term ( ',' term )* ')'
			{
			pushFollow(FOLLOW_predicate_in_atom195);
			predicate12=predicate();
			state._fsp--;

			stream_predicate.add(predicate12.getTree());
			char_literal13=(Token)match(input,16,FOLLOW_16_in_atom197);  
			stream_16.add(char_literal13);

			pushFollow(FOLLOW_term_in_atom199);
			term14=term();
			state._fsp--;

			stream_term.add(term14.getTree());
			// org/semanticweb/clipper/dllog/DLLog.g:42:22: ( ',' term )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==18) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:42:23: ',' term
					{
					char_literal15=(Token)match(input,18,FOLLOW_18_in_atom202);  
					stream_18.add(char_literal15);

					pushFollow(FOLLOW_term_in_atom204);
					term16=term();
					state._fsp--;

					stream_term.add(term16.getTree());
					}
					break;

				default :
					break loop4;
				}
			}

			char_literal17=(Token)match(input,17,FOLLOW_17_in_atom208);  
			stream_17.add(char_literal17);

			// AST REWRITE
			// elements: term, predicate
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 42:38: -> ^( predicate ( term )* )
			{
				// org/semanticweb/clipper/dllog/DLLog.g:42:41: ^( predicate ( term )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_predicate.nextNode(), root_1);
				// org/semanticweb/clipper/dllog/DLLog.g:42:53: ( term )*
				while ( stream_term.hasNext() ) {
					adaptor.addChild(root_1, stream_term.nextTree());
				}
				stream_term.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atom"


	public static class predicate_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "predicate"
	// org/semanticweb/clipper/dllog/DLLog.g:56:1: predicate : ( UPPER_LEADING_ID | LOWER_LEADING_ID );
	public final predicate_return predicate() throws RecognitionException {
		predicate_return retval = new predicate_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set18=null;

		Object set18_tree=null;

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:56:10: ( UPPER_LEADING_ID | LOWER_LEADING_ID )
			// org/semanticweb/clipper/dllog/DLLog.g:
			{
			root_0 = (Object)adaptor.nil();


			set18=input.LT(1);
			if ( input.LA(1)==LOWER_LEADING_ID||input.LA(1)==UPPER_LEADING_ID ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set18));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "predicate"


	public static class term_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "term"
	// org/semanticweb/clipper/dllog/DLLog.g:62:1: term : ( variable -> ^( VARIABLE variable ) | constant -> ^( CONSTANT constant ) );
	public final term_return term() throws RecognitionException {
		term_return retval = new term_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope variable19 =null;
		ParserRuleReturnScope constant20 =null;

		RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
		RewriteRuleSubtreeStream stream_variable=new RewriteRuleSubtreeStream(adaptor,"rule variable");

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:62:5: ( variable -> ^( VARIABLE variable ) | constant -> ^( CONSTANT constant ) )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==UPPER_LEADING_ID) ) {
				alt5=1;
			}
			else if ( (LA5_0==LOWER_LEADING_ID) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// org/semanticweb/clipper/dllog/DLLog.g:63:3: variable
					{
					pushFollow(FOLLOW_variable_in_term247);
					variable19=variable();
					state._fsp--;

					stream_variable.add(variable19.getTree());
					// AST REWRITE
					// elements: variable
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 63:12: -> ^( VARIABLE variable )
					{
						// org/semanticweb/clipper/dllog/DLLog.g:63:15: ^( VARIABLE variable )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);
						adaptor.addChild(root_1, stream_variable.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// org/semanticweb/clipper/dllog/DLLog.g:64:5: constant
					{
					pushFollow(FOLLOW_constant_in_term262);
					constant20=constant();
					state._fsp--;

					stream_constant.add(constant20.getTree());
					// AST REWRITE
					// elements: constant
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 64:14: -> ^( CONSTANT constant )
					{
						// org/semanticweb/clipper/dllog/DLLog.g:64:17: ^( CONSTANT constant )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTANT, "CONSTANT"), root_1);
						adaptor.addChild(root_1, stream_constant.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "term"


	public static class variable_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "variable"
	// org/semanticweb/clipper/dllog/DLLog.g:66:1: variable : UPPER_LEADING_ID ;
	public final variable_return variable() throws RecognitionException {
		variable_return retval = new variable_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token UPPER_LEADING_ID21=null;

		Object UPPER_LEADING_ID21_tree=null;

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:66:9: ( UPPER_LEADING_ID )
			// org/semanticweb/clipper/dllog/DLLog.g:67:3: UPPER_LEADING_ID
			{
			root_0 = (Object)adaptor.nil();


			UPPER_LEADING_ID21=(Token)match(input,UPPER_LEADING_ID,FOLLOW_UPPER_LEADING_ID_in_variable279); 
			UPPER_LEADING_ID21_tree = (Object)adaptor.create(UPPER_LEADING_ID21);
			adaptor.addChild(root_0, UPPER_LEADING_ID21_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "variable"


	public static class constant_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "constant"
	// org/semanticweb/clipper/dllog/DLLog.g:69:1: constant : LOWER_LEADING_ID ;
	public final constant_return constant() throws RecognitionException {
		constant_return retval = new constant_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LOWER_LEADING_ID22=null;

		Object LOWER_LEADING_ID22_tree=null;

		try {
			// org/semanticweb/clipper/dllog/DLLog.g:69:9: ( LOWER_LEADING_ID )
			// org/semanticweb/clipper/dllog/DLLog.g:70:3: LOWER_LEADING_ID
			{
			root_0 = (Object)adaptor.nil();


			LOWER_LEADING_ID22=(Token)match(input,LOWER_LEADING_ID,FOLLOW_LOWER_LEADING_ID_in_constant290); 
			LOWER_LEADING_ID22_tree = (Object)adaptor.create(LOWER_LEADING_ID22);
			adaptor.addChild(root_0, LOWER_LEADING_ID22_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "constant"

	// Delegated rules



	public static final BitSet FOLLOW_dl_log_rule_in_dl_log_rules99 = new BitSet(new long[]{0x0000000000002102L});
	public static final BitSet FOLLOW_head_in_dl_log_rule112 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_dl_log_rule114 = new BitSet(new long[]{0x0000000000002100L});
	public static final BitSet FOLLOW_body_in_dl_log_rule116 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_dl_log_rule118 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atom_in_head141 = new BitSet(new long[]{0x0000000000200002L});
	public static final BitSet FOLLOW_21_in_head144 = new BitSet(new long[]{0x0000000000002100L});
	public static final BitSet FOLLOW_atom_in_head146 = new BitSet(new long[]{0x0000000000200002L});
	public static final BitSet FOLLOW_atom_in_body168 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_18_in_body171 = new BitSet(new long[]{0x0000000000002100L});
	public static final BitSet FOLLOW_atom_in_body173 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_predicate_in_atom195 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_atom197 = new BitSet(new long[]{0x0000000000002100L});
	public static final BitSet FOLLOW_term_in_atom199 = new BitSet(new long[]{0x0000000000060000L});
	public static final BitSet FOLLOW_18_in_atom202 = new BitSet(new long[]{0x0000000000002100L});
	public static final BitSet FOLLOW_term_in_atom204 = new BitSet(new long[]{0x0000000000060000L});
	public static final BitSet FOLLOW_17_in_atom208 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_term247 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constant_in_term262 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_UPPER_LEADING_ID_in_variable279 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LOWER_LEADING_ID_in_constant290 = new BitSet(new long[]{0x0000000000000002L});
}
