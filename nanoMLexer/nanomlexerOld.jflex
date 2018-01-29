/**
	JFlex lexgreiningard�mi byggt � lesgreini fyrir NanoMorpho
	Höfundur: Sindri Pétur Ingimundarson, janúar 2017

	Þennan lesgreini má þýða og keyra með skipununum
		java -jar JFlex-1.6.1.jar nanoMlexer.jflex
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

%{

// Setja koda upp a thaegilegri mata
// Skilgreiningar á tókum (tokens):
final static int ERROR = -1;

// flow
final static int IF = 1001;
final static int ELSE = 1002;
final static int WHILE = 1003;
final static int RETURN = 1004;


// declaration
final static int VAR = 1010;
final static int FUNC = 1011;

// ----
final static int NAME = 1020;
final static int LITERAL = 1021;

//
final static int OPNAME = 1030;

// Breyta sem mun innihalda les (lexeme):
public static String lexeme;

// Þetta keyrir lexgreininn:
public static void main( String[] args ) throws Exception
{
	NanoMLexer lexer = new NanoMLexer(new FileReader(args[0]));
	int token = lexer.yylex();
	while( token!=0 )
	{
		System.out.println(""+token+": \'"+lexeme+"\'");
		token = lexer.yylex();
	}
}

%}

  /* Reglulegar skilgreiningar */

  /* Regular definitions */

_DIGIT=[0-9]
_FLOAT={_DIGIT}+\.{_DIGIT}+([eE][+-]?{_DIGIT}+)?
_INT={_DIGIT}+
_STRING=\"([^\"\\]|\\b|\\t|\\n|\\f|\\r|\\\"|\\\'|\\\\|(\\[0-3][0-7][0-7])|\\[0-7][0-7]|\\[0-7])*\"
_CHAR=\'([^\'\\]|\\b|\\t|\\n|\\f|\\r|\\\"|\\\'|\\\\|(\\[0-3][0-7][0-7])|(\\[0-7][0-7])|(\\[0-7]))\'
_DELIM=[(){},;=]
_OPNAME=[&|><\^*+-/=]+
_NAME=([:letter:]|_)([:letter:]|{_DIGIT}|_)*

/* comments */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
_COMMENT="//" {InputCharacter}* {LineTerminator}?

%%

  /* Lesgreiningarreglur */

{_DELIM} {
	lexeme = yytext();
	return yycharat(0);
}

{_STRING} | {_FLOAT} | {_CHAR} | {_INT} | null | true | false {
	lexeme = yytext();
	return LITERAL;
}

{_OPNAME} {
    lexeme = yytext();
    return OPNAME;
}

"if" {
	lexeme = yytext();
	return IF;
}

"else" {
    lexeme = yytext();
    return ELSE;
}

"while" {
    lexeme = yytext();
    return WHILE;
}

"return" {
    lexeme = yytext();
    return RETURN;
}

"func" {
    lexeme = yytext();
    return FUNC;
}

"var" {
    lexeme = yytext();
    return VAR;
}

{_NAME} {
	lexeme = yytext();
	return NAME;
}

//breyta athugasemdum
{_COMMENT} {
}

[ \t\r\n\f] {
}

. {
	lexeme = yytext();
	return ERROR;
}
