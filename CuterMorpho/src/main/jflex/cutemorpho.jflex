/**
	jFlex lesgreinir fyrir CuteMorpho
	Höfundur: Sindri Pétur Ingimundarson, janúar 2017
	          Stella Rut Guðmundsdóttir

	Mælt er með að þýðandinn er keyrður með viðeigandi
	jar distribution skrá.
 */

%%

%public
%class CuteMorphoLexer
%unicode
%byaccj
%line

%{
	// Skilgreiningar á tókum (tokens):
	final static int ERROR = -1;
	final static int ELSE = 1002;
	final static int WHILE = 1003;

	private Parser yyparser;

	public CuteMorphoLexer(java.io.Reader r, Parser yyparser) {
		this(r);
		this.yyparser = yyparser;
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

_NAME 	= ([:letter:]|_)([:letter:]|{_DIGIT}|_)*

%%

/* Lesgreiningarreglur */

<YYINITIAL> {
	/* keywords */
	"if" 		{ return Parser.IF; }
	"else" 		{ return ELSE; }
	"while" 	{ return WHILE; }
	"return" 	{ return Parser.RETURN; }
	"func" 		{ return Parser.FUNC; }
	"var" 		{ yyparser.yylval = new ParserVal(yytext()); return Parser.VAR; }

	/* literals */
	{_STRING} 
	| {_FLOAT} 
	| {_CHAR} 
	| {_INT} 
	| "null" 
	| "true" 
	| "false" { yyparser.yylval = new ParserVal(yytext()); return Parser.LITERAL;}

	/* delimiters/seperators */
	{_DELIM} { yyparser.yylval = new ParserVal(yytext()); return (int) yycharat(0); }

	/* operators */
    "+"
    | "-" { yyparser.yylval = new ParserVal(yytext()); return Parser.OPNAME1; }

    "*"
    | "/" { yyparser.yylval = new ParserVal(yytext()); return Parser.OPNAME2; }

	{_OPNAME} { yyparser.yylval = new ParserVal(yytext()); return Parser.OPNAME3; }

	/* comments */
	{_COMMENT} { /* ignore */ }

	/* whitespace */
	{_WHITESPACE} { /* ignore */ }

	/* identifiers/name */
	{_NAME} { yyparser.yylval = new ParserVal(yytext()); return Parser.NAME; }
}

. { return ERROR; }