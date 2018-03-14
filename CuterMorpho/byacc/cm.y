%{
    import java.io.*;
    import java.util.*;
    import blocks.*;
%}


%token FUNC RETURN IF VAR ELSE WHILE
%token <sval> NAME LITERAL OPNAME1 OPNAME2 OPNAME3
%token <sval> OR AND NOT EQUALS
// %type <ival> 
%type <obj> start
%type <obj> opt_idlist
%type <obj> idlist
%type <obj> body
%type <obj> funcdecl
%type <obj> bodyexprs
%type <obj> decl_vars
%type <obj> expr
%type <obj> orexpr
%type <obj> andexpr
%type <obj> notexpr
%type <obj> binopexpr
%type <obj> smallexpr
%type <obj> args
%type <obj> arglist
%type <obj> ifexpr
%type <obj> elseexpr

%left OPNAME1
%left OPNAME2
%left OPNAME3
%%

start: 
    bodyexprs { this.program = (Vector<Object>)$1; }
    ;

opt_idlist: 
    /* empty */ { $$=new ArrayList<String>(); }
    | idlist { $$=$1; }
    ;

idlist: 
    idlist ',' NAME { ((List<String>)($$)).add($3); }
    | NAME { $$=new ArrayList<String>(); ((List<String>)($$)).add($1); }
    ;

body:
    '{' bodyexprs '}' { $$=new Object[]{"BODY", ((Vector<Object>)($2)).toArray()}; }
    ;

bodyexprs: 
    bodyexprs expr ';' { ((Vector<Object>)($$)).add($2); }
    | bodyexprs funcdecl ';' { ((Vector<Object>)($$)).add($2); }
    | bodyexprs VAR decl_vars ';' { ((Vector<Object>)($$)).add($3);}
    | /* empty */ { $$=new Vector<Object>();}
    ;

funcdecl: 
    FUNC NAME '(' opt_idlist ')' body { $$=new Object[]{"FUNC", $2, $4, $6}; }
    ;

decl_vars: 
    NAME EQUALS expr { $$=new Object[]{"VAR", $1, $3}; }
    ;

expr:
    RETURN expr { $$=new Object[]{"RETURN", $2}; }
    | NAME EQUALS expr { $$=new Object[]{"STORE", $1, $3}; }
    | orexpr { $$=$1; }
    ;

orexpr:
    orexpr OR andexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    | andexpr { $$=$1; }
    ;

andexpr: 
    andexpr AND notexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    | notexpr { $$=$1; }
    ;

notexpr: 
    NOT notexpr { $$=new Object[]{"CALL", $1, $2}; }
    | binopexpr { $$=$1; }
    ;

binopexpr: 
    smallexpr OPNAME1 binopexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    | smallexpr OPNAME2 binopexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    | smallexpr OPNAME3 binopexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    | smallexpr { $$=$1; }
    ;

smallexpr: 
    OPNAME1 smallexpr { $$=new Object[]{"CALL", $1, $2}; }
    | OPNAME2 smallexpr { $$=new Object[]{"CALL", $1, $2}; }
    | OPNAME3 smallexpr { $$=new Object[]{"CALL", $1, $2}; }     
    | '(' expr ')' { $$=$2; }
    | NAME { $$=new Object[]{"FETCH", $1}; }
    | NAME '(' args ')' { $$=new Object[]{"CALL", $1, $3 }; }
    | WHILE '(' expr ')' body { $$=new Object[]{"WHILE", $3, $5}; }
    | ifexpr { $$=$1; }
    | LITERAL { $$=new Object[]{"LITERAL", $1}; }
    | body { $$=$1; }
    // | FUNC '(' opt_idlist ')' body
    ;

args: /* empty */ { $$=new Object[]{"ARGS", new Object[]{} }; }
    | arglist { $$=new Object[]{"ARGS", ((Vector<Object>)($1)).toArray()}; }
    ;

arglist: 
    arglist ',' expr { ((Vector<Object>)($$)).add($3); }
    | expr { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

ifexpr: 
    IF '(' expr ')' body  { $$=new Object[]{"IF1", $3, $5}; }
    | IF '(' expr ')' body elseexpr { $$=new Object[]{"IF2", $3, $5, $6}; }
    ;

elseexpr: 
    ELSE body { $$=$2; }
    | ELSE ifexpr { $$=$2; }
    ;

%%


    private CuteMorphoLexer lexer;
    private Vector<Object> program;

    int last_token_read;

    public Vector<Object> getProgram() {
        return this.program;
    }

    private int yylex() {
        int yyl_return = -1;
        try {
            yylval = null;
            last_token_read = yyl_return = lexer.yylex();
            if (yylval == null) 
                yylval = new ParserVal(Parser.yyname[yyl_return]);
        } catch (IOException e) {
            System.err.println("IO error: " + e);
        }
        return yyl_return;
    }

    public void yyerror(String error) {
        System.out.println("Error: " + error);
        System.out.println("Token: " + Parser.yyname[last_token_read]);
        System.out.println("Line: " + lexer.getLine()+1);
        System.out.println("Column: " + lexer.getColumn());
        System.exit(1);
    }

    public Parser(Reader r) {
        lexer = new CuteMorphoLexer(r, this);
    }