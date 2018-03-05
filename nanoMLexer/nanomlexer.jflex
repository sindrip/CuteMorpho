/**
	JFlex lexgreiningardæmi byggt á lesgreini fyrir NanoMorpho
	Höfundur: Sindri Pétur Ingimundarson, janúar 2017
	          Stella Rut Guðmundsdóttir

	Þennan lesgreini má þýða og keyra með skipununum
		java -jar JFlex-1.6.1.jar nanomlexer.jflex
		javac NanoMLexer.java
		java NanoMLexer inntaksskrá > úttaksskrá
	Einnig má nota forritið 'make', ef viðeigandi 'makefile'
	er til staðar:
		make test

    Breytingar elsif lykilord sleppt (else if naegir)
    func baett fyrir framan funcdecl
 */

import java.io.*;
import java.util.Vector;
import java.util.HashMap;

%%

%public
%class NanoMLexer
%unicode
%byaccj
%line

%{

// Skilgreiningar á tókum (tokens):
final static int ERROR = -1;

// keywords
final static int IF = 1001;
final static int ELSE = 1002;
final static int WHILE = 1003;
final static int RETURN = 1004;
final static int VAR = 1005;
final static int FUNC = 1006;

// ---
final static int NAME = 1020;
final static int LITERAL = 1030;
final static int OPNAME = 1040;

// Breyta sem mun innihalda les (lexeme):
public static String lexeme;
public static String lexemeFirst;
public static int token;
public static int tokenFirst;
// linecount
public static int lineFirst;
public static int line;

public static NanoMLexer lexer;

public static int nextToken() {
	return tokenFirst;
}

public static String nextLexeme() {
	return lexemeFirst;
}

public static void advance() throws Exception {
	lexemeFirst = lexeme;
	tokenFirst = token;
	lineFirst = line;
	token = lexer.yylex();
	line = lexer.yyline;
}

public static String over(int t) throws Exception {
	if (tokenFirst != t) {
		throw new Exception("Expected " + ((char) t) + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	String ret = lexemeFirst;
	advance();
	return ret;
}

public static String over(int t, String key_w) throws Exception {
	if (tokenFirst != t) {
		throw new Exception("Expected " + key_w + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	String ret = lexemeFirst;
	advance();
	return ret;
}

public static int peek() {
	return token;
}

public static void init() throws Exception {
	advance();
	advance();
}

// Þetta keyrir lexgreininn:
public static void main( String[] args ) throws Exception
{
	lexer = new NanoMLexer(new FileReader(args[0]));
	init();
	Object[] intermediate = program_t();
}

public static int varPos(String name) throws Exception {
	int ret;
	try {
		ret = vars.get(name);
	} catch (Exception e) {
		throw new Exception("Variable \'" + name + "\' not declared at line: " + lineFirst);
	}
	return ret;
}

public static void putMap(String name, HashMap<String, Integer> dict) throws Exception {
	if (vars.get(name) != null || args.get(name) != null) {
		throw new Exception("\'" + name + "\'" + " declared multipe times at line: " + lineFirst);
	}
	dict.put(name, dict.size());
}

public static Object[] program_t() throws Exception {
	Vector<Object> funcs = new Vector<Object>();
	while(token != 0) {
		funcs.add(function_t());
	}
	over(0); //EOF
	System.out.println( ((Object[]) ((Object[]) ((Object[]) funcs.get(0))[3])[0])[0] );	
	return funcs.toArray();
}

static HashMap<String, Integer> args;
static HashMap<String, Integer> vars;
public static Object[] function_t() throws Exception {
	args = new HashMap<>();
	vars = new HashMap<>();

	Vector<Object> func = new Vector<>();
	over(FUNC, "func");
	// Add name of function to object array
	func.add(over(NAME, "A name"));
	over('(');
	if (nextToken() != ')') {
		putMap(over(NAME, "A name"), args);
		while (nextToken() == ',') {
			over(',');
			putMap(over(NAME, "A name"), args);			
		}
	}
	over(')');
	over('{');
	while (nextToken() == VAR) {
		vardecl_t();
	}
	func.add(args.size());
	func.add(vars.size());

	Vector<Object> expr = new Vector<>();
	
	expr.add(expr_t());
	over(';');

	while(nextToken() != '}') {
		expr.add(expr_t());
		over(';');
	}
	over('}');

	func.add(expr.toArray());
	return func.toArray();
}

public static void vardecl_t() throws Exception {
	over(VAR, "var");
	putMap(over(NAME, "A name"), vars);
	while(nextToken() != ';') {
		over(',');
		putMap(over(NAME, "A name"), vars);
	}
	over(';');
}

public static Object[] expr_t() throws Exception {
	Object[] res;	
	switch (nextToken()) {
		case RETURN:
			over(RETURN, "return");
			res = new Object[]{"RETURN", expr_t()};
			return res;
		case NAME:
			if (peek() == '=') {
				int varp = varPos(over(NAME, "A name"));
				over('=');								
				res = new Object[]{"STORE", varp, expr_t()};
				return res;
			} else {
				return binopexpr_t();
			}
		default:
			return binopexpr_t();
	}
}

public static Object[] binopexpr_t() throws Exception {
	Vector<Object> res = new Vector<>();
	res.add(smallexpr_t());
	while(nextToken() == OPNAME) {
		String operator;
		operator = over(OPNAME, "An operator");
		res.add(smallexpr_t());
		Object[] opcall = new Object[]{"CALL", operator, res.toArray()};
		res = new Vector<>();
		res.add(opcall);
	}
	return res.toArray();
}

public static Object[] smallexpr_t() throws Exception {

	Object[] res;
	switch (nextToken()) {
		case '(':
			over('(');
			res = expr_t();
			over(')');
			break;
		case LITERAL:
			res = new Object[]{"LITERAL", over(LITERAL, "A literal")};
			break;
		case OPNAME:
			String operator = over(OPNAME, "An operator");
			res = new Object[]{"CALL", operator, smallexpr_t()};
			break;
		case IF:
			res = ifexpr_t();
			break;
		case WHILE:
			Object cond, body;
			over(WHILE, "while");
			over('(');
			cond = expr_t();
			over(')');
			body = body_t();
			res = new Object[]{"WHILE", cond, body};
			break;
		default:
			String name = over(NAME, "A name");
			if (nextToken() == '(') {
				Vector<Object> lArgs = new Vector<>();
				over('(');
				lArgs.add(expr_t());
				while(nextToken() != ')') {
					over(',');
					lArgs.add(expr_t());
				}
				over(')');
				res = new Object[]{"CALL", name, lArgs.toArray()};
			} else {
				res = new Object[]{"FETCH", vars.get(name)};	
			}
			break;
	}
	return res;
}

public static Object[] ifexpr_t() throws Exception {
	Object cond, thenexpr, elseexpr;
	elseexpr = null;

	over(IF, "if");
	over('(');
	cond = expr_t();
	over(')');
	thenexpr = body_t();
	
	if(nextToken() == ELSE) {
		over(ELSE, "else");
		if (nextToken() == '{') {
			elseexpr = body_t();
		} else {
			elseexpr = ifexpr_t();
		}
	}

	return new Object[]{"IF", cond, thenexpr, elseexpr};
}

public static Object[] body_t() throws Exception {
	Vector<Object> res = new Vector<>();
	over('{');
	res.add(expr_t());
	over(';');
	while(nextToken() != '}') {
		res.add(expr_t());
		over(';');
	}
	over('}');
	return res.toArray();
}

%}

/* Regular definitions */

_LINETERMINATOR = \r|\n|\r\n
_INPUTCHARACTER = [^\r\n]

_DIGIT 	= [0-9]
_FLOAT 	= -? {_DIGIT}+\.{_DIGIT}+([eE][+-]?{_DIGIT}+)?
_INT 	= -? {_DIGIT}+
_STRING = \"([^\"\\]|\\b|\\t|\\n|\\f|\\r|\\\"|\\\'|\\\\|\\[0-3]?[0-7]?[0-7])*\"
_CHAR 	= \'([^\'\\]|\\b|\\t|\\n|\\f|\\r|\\\"|\\\'|\\\\|\\[0-3]?[0-7]?[0-7])\'

_DELIM 	= [(){},;=]

_OPNAME = [\:&|><=+\-*/%!?~\^]+

_COMMENT = "//" {_INPUTCHARACTER}* {_LINETERMINATOR}?

_WHITESPACE = {_LINETERMINATOR} | [ \t\f]

// _NAME = [:jletter:][:jletterdigit:]*
_NAME 	= ([:letter:]|_)([:letter:]|{_DIGIT}|_)*

%%

/* Lesgreiningarreglur */

<YYINITIAL> {
	/* keywords */
	"if" 		{ lexeme = yytext(); return IF; }
	"else" 		{ lexeme = yytext(); return ELSE; }
	"while" 	{ lexeme = yytext(); return WHILE; }
	"return" 	{ lexeme = yytext(); return RETURN; }
	"func" 		{ lexeme = yytext(); return FUNC; }
	"var" 		{ lexeme = yytext(); return VAR; }

	/* literals */
	{_STRING} 
	| {_FLOAT} 
	| {_CHAR} 
	| {_INT} 
	| "null" 
	| "true" 
	| "false" { lexeme = yytext(); return LITERAL; }

	/* delimiters/seperators */
	{_DELIM} { lexeme = yytext(); return yycharat(0); }

	/* operators */
	{_OPNAME} { lexeme = yytext(); return OPNAME; }

	/* comments */
	{_COMMENT} { /* ignore */ }

	/* whitespace */
	{_WHITESPACE} { /* ignore */ }

	/* identifiers/name */
	{_NAME} { lexeme = yytext(); return NAME; }
}

. { lexeme = yytext(); return ERROR; }