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

public static void over(int t) throws Exception {
	System.out.println(""+tokenFirst+": \'"+lexemeFirst+"\'");	
	if (tokenFirst != t) {
		throw new Exception("Expected " + ((char) t) + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	advance();
}

public static void over(int t, String key_w) throws Exception {
	System.out.println(""+tokenFirst+": \'"+lexemeFirst+"\'");	
	if (tokenFirst != t) {
		throw new Exception("Expected " + key_w + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	advance();
}

public static int peak() {
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

	program_t();
}

public static void program_t() throws Exception {
	int f_counter = 0;
	while(token != 0) {
		function_t();
		f_counter++;
	}
	if (f_counter == 0) {
		throw new Exception("You must have at least one function");
	}
	over(0); //EOF
}

public static void function_t() throws Exception {
	over(FUNC, "func");
	over(NAME, "A name");
	over('(');
	while (nextToken() != ')') {
		over(NAME, "A name");
		if (nextToken() == ',') {
			over(',');
		}
	}
	over(')');
	over('{');
	while (nextToken() == VAR) {
		vardecl_t();
	}

	expr_t();
	over(';');
	while(nextToken() != '}') {
		expr_t();
		over(';');
	}
	over('}');
}

public static void vardecl_t() throws Exception {
	over(VAR, "var");
	over(NAME, "A name");
	while(nextToken() != ';') {
		over(',');
		over(NAME, "A name");
	}
	over(';');
}

public static void expr_t() throws Exception {
	switch (nextToken()) {
		case RETURN:
			over(RETURN, "return");
			expr_t();
			break;
		case NAME:
			if (peak() == '=') {
				over(NAME, "A name");
				over('=');
				expr_t();
			} else {
				binopexpr_t();
			}
			break;
		default:
			binopexpr_t();
			break;
	}
}

public static void binopexpr_t() throws Exception {
	smallexpr_t();
	while(nextToken() == OPNAME) {
		over(OPNAME, "An operator");
		smallexpr_t();
	}
}

public static void smallexpr_t() throws Exception {
	switch (nextToken()) {
		case '(':
			over('(');
			expr_t();
			over(')');
			break;
		case LITERAL:
			over(LITERAL, "A literal");
			break;
		case OPNAME:
			over(OPNAME, "An operator");
			smallexpr_t();
			break;
		case IF:
			ifexpr_t();
			break;
		case WHILE:
			over(WHILE, "while");
			over('(');
			expr_t();
			over(')');
			body_t();
			break;
		default:
			over(NAME, "A name");
			if (nextToken() == '(') {
				over('(');
				expr_t();
				while(nextToken() != ')') {
					over(',');
					expr_t();
				}
				over(')');
			}
			break;
	}
}

public static void ifexpr_t() throws Exception {
	over(IF, "if");
	over('(');
	expr_t();
	over(')');
	body_t();
	
	if(nextToken() == ELSE) {
		over(ELSE, "if");
		if (nextToken() == '{') {
			body_t();
		} else {
			ifexpr_t();
		}
	}
}

public static void body_t() throws Exception {
	over('{');
	expr_t();
	over(';');
	while(nextToken() != '}') {
		expr_t();
		over(';');
	}
	over('}');
	
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