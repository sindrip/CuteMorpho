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
	"if" 		{ return yyparser.IF; }
	"else" 		{ return yyparser.ELSE; }
	"while" 	{ return yyparser.WHILE; }
	"return" 	{ return yyparser.RETURN; }
	"func" 		{ return yyparser.FUNC; }
	"var" 		{ return yyparser.VAR; }

	/* literals */
	{_STRING} 
	| {_FLOAT} 
	| {_CHAR} 
	| {_INT}
	| "null" 
	| "true" 
	| "false" { yyparser.yylval = yytext(); return yyparser.LITERAL;}

	/* delimiters/seperators */
	{_DELIM} { yyparser.yylval = yytext(); return (int) yycharat(0); }

	/* operators */
    "=" { return yyparser.EQUALS; }

    "+"
    | "-" { return yyparser.OPNAME2; }

    "*"
    | "/" { return yyparser.OPNAME3; }
    
    "||" { return yyparser.OR; }
    "&&" { return yyparser.AND; }
    "!"  { return yyparser.NOT; }
    
	{_OPNAME} { yyparser.yylval = yytext(); return yyparser.OPNAME1; }

	/* comments */
	{_COMMENT} { /* ignore */ }

	/* whitespace */
	{_WHITESPACE} { /* ignore */ }

	/* identifiers/name */
	{_NAME} { yyparser.yylval = yytext(); return yyparser.NAME; }
}

. { return ERROR; }