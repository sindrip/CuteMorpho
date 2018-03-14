%{
    import java.io.*;
    import java.util.*;
%}

%token FUNC RETURN IF VAR ELSE WHILE 
%token <sval> NAME LITERAL OPNAME1 OPNAME2 OPNAME3 OR AND NOT EQUALS
// %type <ival> 
%type <obj> program multidecl decl funcdecl vardecl body expr opt_funcargs funcargs
%type <obj> orexpr andexpr notexpr opexpr smallexpr ifexpr elseexpr
%type <obj> operator

%left OPNAME1 // other
%left OPNAME2 // + -
%left OPNAME3 // * /

%%

start: program { this.program = (Vector<Object>)$1; }
    ;

program: program decl { ((Vector<Object>)($1)).add($2); $$=$1; }
    | decl { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

funcdecl: FUNC NAME '(' opt_funcargs ')' body 
    { $$=new Object[]{
            "FUNC",
            $2,
            $4,
            $6
        }; 
    }
    ;

opt_funcargs: /* empty */ { $$=new ArrayList<String>(); }
    | funcargs { $$=$1; }
    ;

funcargs: funcargs ',' NAME { ((List<String>)($$)).add($3); } 
    | NAME { $$=new ArrayList<String>(); ((List<String>)($$)).add($1); }
    ;

// No multi declaration implemented (i.e. var x, y = 2;)
vardecl: VAR NAME EQUALS expr { $$=new Object[]{"VAR", $2, $4 }; }
    ;

expr: RETURN expr { $$=new Object[]{"RETURN", $2 }; }
    | NAME EQUALS expr { $$=new Object[]{"STORE", $1, $3 }; }
    | orexpr { $$=$1; }
    ;

orexpr: andexpr { $$=$1; }
    | orexpr OR andexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    ;

andexpr: notexpr { $$=$1; }
    | andexpr AND notexpr { $$=new Object[]{"CALL", $2, $1, $3}; }
    ;

notexpr: opexpr { $$=$1; }
    | NOT notexpr { $$=new Object[]{"CALL", $1, $2}; }
    ;

opexpr: smallexpr { $$=$1; }
    | opexpr OPNAME1 smallexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // other
    | opexpr OPNAME2 smallexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // other
    | opexpr OPNAME3 smallexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // other    
    ;

operator: OPNAME1 { $$=$1; }
    | OPNAME2 { $$=$1; }
    | OPNAME3 { $$=$1; }
    ;

smallexpr: NAME { $$=new Object[]{"FETCH", $1}; }
    | NAME '(' ')' { $$=new Object[]{"CALL"}; }
    | operator smallexpr { $$=new Object[]{"CALL", $1, $2}; }
    | LITERAL { $$=new Object[]{"LITERAL", $1}; }
    | '(' expr ')' { $$=$2; }
    | ifexpr { $$=$1; }
    | WHILE '(' expr ')' body { $$=new Object[]{"WHILE", $3, $5}; }
    // | FUN '(' opt_funcargs ')' body { $$=new Object[]{"MAKECLOSURE", $3, $5}; }
    ;

decl: funcdecl ';' { $$=$1; }
    | vardecl ';' { $$=$1; }
    | expr ';' { $$=$1; }
    ;

multidecl: multidecl decl { ((Vector<Object>)($$)).add($2); }
    | decl { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

ifexpr: IF '(' expr ')' body { $$=new Object[]{"IF1", $3, $5}; }
    | IF '(' expr ')' body elseexpr { $$=new Object[]{"IF2", $3, $5, $6}; }
    ;

elseexpr: ELSE body { $$=$2; }
    | ELSE ifexpr { $$=$2; }
    ;

body: '{' multidecl '}' { $$=new Object[]{"BODY", ((Vector<Object>)($2)).toArray()}; }
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