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
%column

%{ 
	// Skilgreiningar á tókum (tokens):
	final static int ERROR = -1;

	private CuteMorphoParser yyparser;

	public int getLine() {
		return this.yyline;
	}

	public int getColumn() {
		return this.yycolumn;
	}

	public CuteMorphoLexer(java.io.Reader r, CuteMorphoParser yyparser) {
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

_DELIM 	= [(){},;]

_OPNAME = [\:&|><=+\-*/%!?~\^]+

_COMMENT = "//" {_INPUTCHARACTER}* {_LINETERMINATOR}?

_WHITESPACE = {_LINETERMINATOR} | [ \t\f]

_NAME 	= ([:letter:]|_)([:letter:]|{_DIGIT}|_)*

%%

/* Lesgreiningarreglur */

<YYINITIAL> {
	/* keywords */
	"if" 		{ return Parser.IF; }
	"else" 		{ return Parser.ELSE; }
	"while" 	{ return Parser.WHILE; }
	"return" 	{ return Parser.RETURN; }
	"func" 		{ return Parser.FUNC; }
	"var" 		{ return Parser.VAR; }

	/* literals */
	{_STRING} 
	| {_FLOAT} 
	| {_CHAR} 
	| {_INT}
	| "null" 
	| "true" 
	| "false" { yyparser.yylvals = yytext(); return Parser.LITERAL;}

	/* delimiters/seperators */
	{_DELIM} { yyparser.yylvals = yytext(); return (int) yycharat(0); }

	/* operators */
    "=" { return Parser.EQUALS; }

    "+"
    | "-" { return Parser.OPNAME2; }

    "*"
    | "/" { return Parser.OPNAME3; }
    
    "||" { return Parser.OR; }
    "&&" { return Parser.AND; }
    "!"  { return Parser.NOT; }
    
	{_OPNAME} { yyparser.yylvals = yytext(); return Parser.OPNAME1; }

	/* comments */
	{_COMMENT} { /* ignore */ }

	/* whitespace */
	{_WHITESPACE} { /* ignore */ }

	/* identifiers/name */
	{_NAME} { yyparser.yylvals = yytext(); return Parser.NAME; }
}

. { return ERROR; }