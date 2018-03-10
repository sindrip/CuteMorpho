%{
    import java.io.*;
    import java.util.*;
    import blocks.*;
%}


%token FUNC RETURN IF VAR
%token <sval> NAME LITERAL OPNAME1 OPNAME2 OPNAME3
// %type <ival> 
%type <obj> program function start vars expr args
%type <obj> _binopexpr smallexpr exprargs ifexpr body multiexpr _operator
%left OPNAME1
%left OPNAME2
%%
start: program { this.program = (Vector<Object>)$1; }
    ;

program: program function { ((Vector<Object>)($1)).add($2); $$=$1; }
    | function { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

function: FUNC NAME '(' args ')' '{' vars multiexpr '}' {
        $$ = new Object[]{"FUNC", $2, (List<String>)$4, (List<String>)$7, ((Vector<Object>)($8)).toArray() };
    }
    ;

// need to add multivar!
vars: /* empty */       { $$=new ArrayList<String>(); }
    | vars VAR NAME ';' { ((List<String>)($$)).add($3); }
    ;

multiexpr: /* empty */  { $$=new Vector<Object>(); }
    | multiexpr expr ';'   { ((Vector<Object>)($$)).add($2); }
    ;

exprargs: /* empty */ { $$=new Vector<Object>(); }
    | expr { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1);  }
    | exprargs ',' expr { ((Vector<Object>)($$)).add($3); }
    ;

expr: RETURN expr { $$=new Object[]{"RETURN", $2 }; }
    | NAME '=' expr { $$=new Object[]{"STORE", $1, $3}; } 
    | _binopexpr
    ;
    
_binopexpr: _binopexpr OPNAME1 _binopexpr {$$=new Object[]{"CALL", $2, $1, $3}; }
    | _binopexpr OPNAME2 _binopexpr {$$=new Object[]{"CALL", $2, $1, $3}; }
    | smallexpr
    ;

_operator: OPNAME1  { $$=$1; }
    | OPNAME2       { $$=$1; } 
    | OPNAME3       { $$=$1; }
    ;
    
smallexpr: '(' expr ')' { $$=$2; }
    | LITERAL { $$=new Object[]{"LITERAL", $1}; }
    | _operator smallexpr { $$=new Object[]{"CALL", $1, $2}; }
    | ifexpr { $$=$1; }
    | NAME { $$=new Object[]{"FETCH", $1}; }
    | NAME '(' exprargs ')' { $$=new Object[]{"CALL", $1}; }
    ;

args: /* empty */       { $$=new ArrayList<String>(); }
    | NAME              { $$=new ArrayList<String>(); ((List<String>)$$).add($1); }
    | args ',' NAME     { ((List<String>)$$).add($3); }
    ;

ifexpr: IF '(' expr ')' body { $$=new Object[]{"IF", $3, $5}; }
    ;

body: '{' exprargs '}' { $$=new Object[]{ "BODY", ((Vector<Object>)($2)).toArray() }; }
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
        System.exit(1);
    }

    public Parser(Reader r) {
        lexer = new CuteMorphoLexer(r, this);
    }