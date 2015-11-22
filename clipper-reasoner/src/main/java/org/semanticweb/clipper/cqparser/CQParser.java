// $ANTLR 3.5.2 org/semanticweb/clipper/cqparser/CQ.g 2015-11-22 17:59:56

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class CQParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ATOM", "ATOM_LIST", "CONSTANT", 
		"DL_PREDICATE", "INT", "LOWER_LEADING_ID", "NON_DL_PREDICATE", "PREDICATE", 
		"RULE", "RULE_LIST", "TERM_LIST", "UPPER_LEADING_ID", "VARIABLE", "WS", 
		"'('", "')'", "','", "'.'", "':-'", "'<-'", "'?'", "'X'", "'v'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public CQParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CQParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return CQParser.tokenNames; }
	@Override public String getGrammarFileName() { return "org/semanticweb/clipper/cqparser/CQ.g"; }



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


	public static class ucq_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "ucq"
	// org/semanticweb/clipper/cqparser/CQ.g:115:1: ucq : ( cq )* -> ^( RULE_LIST ( cq )* ) ;
	public final ucq_return ucq() throws RecognitionException {
		ucq_return retval = new ucq_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope cq1 =null;

		RewriteRuleSubtreeStream stream_cq=new RewriteRuleSubtreeStream(adaptor,"rule cq");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:115:4: ( ( cq )* -> ^( RULE_LIST ( cq )* ) )
			// org/semanticweb/clipper/cqparser/CQ.g:116:3: ( cq )*
			{
			// org/semanticweb/clipper/cqparser/CQ.g:116:3: ( cq )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==LOWER_LEADING_ID||LA1_0==UPPER_LEADING_ID) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:116:3: cq
					{
					pushFollow(FOLLOW_cq_in_ucq111);
					cq1=cq();
					state._fsp--;

					stream_cq.add(cq1.getTree());
					}
					break;

				default :
					break loop1;
				}
			}

			// AST REWRITE
			// elements: cq
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 116:7: -> ^( RULE_LIST ( cq )* )
			{
				// org/semanticweb/clipper/cqparser/CQ.g:116:10: ^( RULE_LIST ( cq )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULE_LIST, "RULE_LIST"), root_1);
				// org/semanticweb/clipper/cqparser/CQ.g:116:22: ( cq )*
				while ( stream_cq.hasNext() ) {
					adaptor.addChild(root_1, stream_cq.nextTree());
				}
				stream_cq.reset();

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
	// $ANTLR end "ucq"


	public static class cq_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "cq"
	// org/semanticweb/clipper/cqparser/CQ.g:118:1: cq : head ( ':-' | '<-' ) body ( '.' )? -> ^( RULE head body ) ;
	public final cq_return cq() throws RecognitionException {
		cq_return retval = new cq_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token string_literal3=null;
		Token string_literal4=null;
		Token char_literal6=null;
		ParserRuleReturnScope head2 =null;
		ParserRuleReturnScope body5 =null;

		Object string_literal3_tree=null;
		Object string_literal4_tree=null;
		Object char_literal6_tree=null;
		RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
		RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
		RewriteRuleSubtreeStream stream_body=new RewriteRuleSubtreeStream(adaptor,"rule body");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:118:3: ( head ( ':-' | '<-' ) body ( '.' )? -> ^( RULE head body ) )
			// org/semanticweb/clipper/cqparser/CQ.g:119:3: head ( ':-' | '<-' ) body ( '.' )?
			{
			pushFollow(FOLLOW_head_in_cq132);
			head2=head();
			state._fsp--;

			stream_head.add(head2.getTree());
			// org/semanticweb/clipper/cqparser/CQ.g:119:8: ( ':-' | '<-' )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==22) ) {
				alt2=1;
			}
			else if ( (LA2_0==23) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:119:9: ':-'
					{
					string_literal3=(Token)match(input,22,FOLLOW_22_in_cq135);  
					stream_22.add(string_literal3);

					}
					break;
				case 2 :
					// org/semanticweb/clipper/cqparser/CQ.g:119:16: '<-'
					{
					string_literal4=(Token)match(input,23,FOLLOW_23_in_cq139);  
					stream_23.add(string_literal4);

					}
					break;

			}

			pushFollow(FOLLOW_body_in_cq142);
			body5=body();
			state._fsp--;

			stream_body.add(body5.getTree());
			// org/semanticweb/clipper/cqparser/CQ.g:119:27: ( '.' )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==21) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:119:27: '.'
					{
					char_literal6=(Token)match(input,21,FOLLOW_21_in_cq144);  
					stream_21.add(char_literal6);

					}
					break;

			}

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
			// 119:33: -> ^( RULE head body )
			{
				// org/semanticweb/clipper/cqparser/CQ.g:119:36: ^( RULE head body )
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
	// $ANTLR end "cq"


	public static class head_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "head"
	// org/semanticweb/clipper/cqparser/CQ.g:124:1: head : atom ( 'v' atom )* -> ^( ATOM_LIST ( atom )* ) ;
	public final head_return head() throws RecognitionException {
		head_return retval = new head_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal8=null;
		ParserRuleReturnScope atom7 =null;
		ParserRuleReturnScope atom9 =null;

		Object char_literal8_tree=null;
		RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
		RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:124:5: ( atom ( 'v' atom )* -> ^( ATOM_LIST ( atom )* ) )
			// org/semanticweb/clipper/cqparser/CQ.g:125:3: atom ( 'v' atom )*
			{
			pushFollow(FOLLOW_atom_in_head179);
			atom7=atom();
			state._fsp--;

			stream_atom.add(atom7.getTree());
			// org/semanticweb/clipper/cqparser/CQ.g:125:8: ( 'v' atom )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==26) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:125:9: 'v' atom
					{
					char_literal8=(Token)match(input,26,FOLLOW_26_in_head182);  
					stream_26.add(char_literal8);

					pushFollow(FOLLOW_atom_in_head184);
					atom9=atom();
					state._fsp--;

					stream_atom.add(atom9.getTree());
					}
					break;

				default :
					break loop4;
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
			// 125:20: -> ^( ATOM_LIST ( atom )* )
			{
				// org/semanticweb/clipper/cqparser/CQ.g:125:23: ^( ATOM_LIST ( atom )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ATOM_LIST, "ATOM_LIST"), root_1);
				// org/semanticweb/clipper/cqparser/CQ.g:125:35: ( atom )*
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
	// org/semanticweb/clipper/cqparser/CQ.g:127:1: body : atom ( ',' atom )* -> ^( ATOM_LIST ( atom )* ) ;
	public final body_return body() throws RecognitionException {
		body_return retval = new body_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal11=null;
		ParserRuleReturnScope atom10 =null;
		ParserRuleReturnScope atom12 =null;

		Object char_literal11_tree=null;
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:127:5: ( atom ( ',' atom )* -> ^( ATOM_LIST ( atom )* ) )
			// org/semanticweb/clipper/cqparser/CQ.g:128:3: atom ( ',' atom )*
			{
			pushFollow(FOLLOW_atom_in_body206);
			atom10=atom();
			state._fsp--;

			stream_atom.add(atom10.getTree());
			// org/semanticweb/clipper/cqparser/CQ.g:128:8: ( ',' atom )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==20) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:128:9: ',' atom
					{
					char_literal11=(Token)match(input,20,FOLLOW_20_in_body209);  
					stream_20.add(char_literal11);

					pushFollow(FOLLOW_atom_in_body211);
					atom12=atom();
					state._fsp--;

					stream_atom.add(atom12.getTree());
					}
					break;

				default :
					break loop5;
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
			// 128:20: -> ^( ATOM_LIST ( atom )* )
			{
				// org/semanticweb/clipper/cqparser/CQ.g:128:23: ^( ATOM_LIST ( atom )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ATOM_LIST, "ATOM_LIST"), root_1);
				// org/semanticweb/clipper/cqparser/CQ.g:128:35: ( atom )*
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
	// org/semanticweb/clipper/cqparser/CQ.g:130:1: atom : predicate '(' term ( ',' term )* ')' -> ^( predicate ( term )* ) ;
	public final atom_return atom() throws RecognitionException {
		atom_return retval = new atom_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal14=null;
		Token char_literal16=null;
		Token char_literal18=null;
		ParserRuleReturnScope predicate13 =null;
		ParserRuleReturnScope term15 =null;
		ParserRuleReturnScope term17 =null;

		Object char_literal14_tree=null;
		Object char_literal16_tree=null;
		Object char_literal18_tree=null;
		RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
		RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
		RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:130:5: ( predicate '(' term ( ',' term )* ')' -> ^( predicate ( term )* ) )
			// org/semanticweb/clipper/cqparser/CQ.g:131:3: predicate '(' term ( ',' term )* ')'
			{
			pushFollow(FOLLOW_predicate_in_atom233);
			predicate13=predicate();
			state._fsp--;

			stream_predicate.add(predicate13.getTree());
			char_literal14=(Token)match(input,18,FOLLOW_18_in_atom235);  
			stream_18.add(char_literal14);

			pushFollow(FOLLOW_term_in_atom237);
			term15=term();
			state._fsp--;

			stream_term.add(term15.getTree());
			// org/semanticweb/clipper/cqparser/CQ.g:131:22: ( ',' term )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==20) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:131:23: ',' term
					{
					char_literal16=(Token)match(input,20,FOLLOW_20_in_atom240);  
					stream_20.add(char_literal16);

					pushFollow(FOLLOW_term_in_atom242);
					term17=term();
					state._fsp--;

					stream_term.add(term17.getTree());
					}
					break;

				default :
					break loop6;
				}
			}

			char_literal18=(Token)match(input,19,FOLLOW_19_in_atom246);  
			stream_19.add(char_literal18);

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
			// 131:38: -> ^( predicate ( term )* )
			{
				// org/semanticweb/clipper/cqparser/CQ.g:131:41: ^( predicate ( term )* )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_predicate.nextNode(), root_1);
				// org/semanticweb/clipper/cqparser/CQ.g:131:53: ( term )*
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
	// org/semanticweb/clipper/cqparser/CQ.g:145:1: predicate : ( UPPER_LEADING_ID | LOWER_LEADING_ID );
	public final predicate_return predicate() throws RecognitionException {
		predicate_return retval = new predicate_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set19=null;

		Object set19_tree=null;

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:145:10: ( UPPER_LEADING_ID | LOWER_LEADING_ID )
			// org/semanticweb/clipper/cqparser/CQ.g:
			{
			root_0 = (Object)adaptor.nil();


			set19=input.LT(1);
			if ( input.LA(1)==LOWER_LEADING_ID||input.LA(1)==UPPER_LEADING_ID ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set19));
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
	// org/semanticweb/clipper/cqparser/CQ.g:151:1: term : ( variable -> ^( VARIABLE variable ) | constant -> ^( CONSTANT constant ) );
	public final term_return term() throws RecognitionException {
		term_return retval = new term_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope variable20 =null;
		ParserRuleReturnScope constant21 =null;

		RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
		RewriteRuleSubtreeStream stream_variable=new RewriteRuleSubtreeStream(adaptor,"rule variable");

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:151:5: ( variable -> ^( VARIABLE variable ) | constant -> ^( CONSTANT constant ) )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( ((LA7_0 >= 24 && LA7_0 <= 25)) ) {
				alt7=1;
			}
			else if ( (LA7_0==LOWER_LEADING_ID) ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// org/semanticweb/clipper/cqparser/CQ.g:152:3: variable
					{
					pushFollow(FOLLOW_variable_in_term285);
					variable20=variable();
					state._fsp--;

					stream_variable.add(variable20.getTree());
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
					// 152:12: -> ^( VARIABLE variable )
					{
						// org/semanticweb/clipper/cqparser/CQ.g:152:15: ^( VARIABLE variable )
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
					// org/semanticweb/clipper/cqparser/CQ.g:153:5: constant
					{
					pushFollow(FOLLOW_constant_in_term300);
					constant21=constant();
					state._fsp--;

					stream_constant.add(constant21.getTree());
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
					// 153:14: -> ^( CONSTANT constant )
					{
						// org/semanticweb/clipper/cqparser/CQ.g:153:17: ^( CONSTANT constant )
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
	// org/semanticweb/clipper/cqparser/CQ.g:156:1: variable : ( '?' | 'X' ) ! INT ;
	public final variable_return variable() throws RecognitionException {
		variable_return retval = new variable_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set22=null;
		Token INT23=null;

		Object set22_tree=null;
		Object INT23_tree=null;

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:156:9: ( ( '?' | 'X' ) ! INT )
			// org/semanticweb/clipper/cqparser/CQ.g:157:3: ( '?' | 'X' ) ! INT
			{
			root_0 = (Object)adaptor.nil();


			set22=input.LT(1);
			if ( (input.LA(1) >= 24 && input.LA(1) <= 25) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			INT23=(Token)match(input,INT,FOLLOW_INT_in_variable328); 
			INT23_tree = (Object)adaptor.create(INT23);
			adaptor.addChild(root_0, INT23_tree);

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
	// org/semanticweb/clipper/cqparser/CQ.g:159:1: constant : LOWER_LEADING_ID ;
	public final constant_return constant() throws RecognitionException {
		constant_return retval = new constant_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LOWER_LEADING_ID24=null;

		Object LOWER_LEADING_ID24_tree=null;

		try {
			// org/semanticweb/clipper/cqparser/CQ.g:159:9: ( LOWER_LEADING_ID )
			// org/semanticweb/clipper/cqparser/CQ.g:160:3: LOWER_LEADING_ID
			{
			root_0 = (Object)adaptor.nil();


			LOWER_LEADING_ID24=(Token)match(input,LOWER_LEADING_ID,FOLLOW_LOWER_LEADING_ID_in_constant339); 
			LOWER_LEADING_ID24_tree = (Object)adaptor.create(LOWER_LEADING_ID24);
			adaptor.addChild(root_0, LOWER_LEADING_ID24_tree);

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



	public static final BitSet FOLLOW_cq_in_ucq111 = new BitSet(new long[]{0x0000000000008202L});
	public static final BitSet FOLLOW_head_in_cq132 = new BitSet(new long[]{0x0000000000C00000L});
	public static final BitSet FOLLOW_22_in_cq135 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_23_in_cq139 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_body_in_cq142 = new BitSet(new long[]{0x0000000000200002L});
	public static final BitSet FOLLOW_21_in_cq144 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atom_in_head179 = new BitSet(new long[]{0x0000000004000002L});
	public static final BitSet FOLLOW_26_in_head182 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_atom_in_head184 = new BitSet(new long[]{0x0000000004000002L});
	public static final BitSet FOLLOW_atom_in_body206 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_20_in_body209 = new BitSet(new long[]{0x0000000000008200L});
	public static final BitSet FOLLOW_atom_in_body211 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_predicate_in_atom233 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_atom235 = new BitSet(new long[]{0x0000000003000200L});
	public static final BitSet FOLLOW_term_in_atom237 = new BitSet(new long[]{0x0000000000180000L});
	public static final BitSet FOLLOW_20_in_atom240 = new BitSet(new long[]{0x0000000003000200L});
	public static final BitSet FOLLOW_term_in_atom242 = new BitSet(new long[]{0x0000000000180000L});
	public static final BitSet FOLLOW_19_in_atom246 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_term285 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constant_in_term300 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_variable318 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_INT_in_variable328 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LOWER_LEADING_ID_in_constant339 = new BitSet(new long[]{0x0000000000000002L});
}
