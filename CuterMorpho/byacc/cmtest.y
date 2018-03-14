%{
    import java.io.*;
    import java.util.*;
%}

%token FUNC RETURN IF VAR ELSE WHILE 
%token <sval> NAME LITERAL OPNAME1 OPNAME2 OPNAME3 OR AND NOT EQUALS
// %type <ival> 
%type <obj> program multidecl decl funcdecl vardecl body expr funcargs
%type <obj> orexpr andexpr notexpr opexpr smallexpr ifexpr
// %type <obj> unaryoperator

%left OR_LOGICAL
%left AND_LOGICAL
%left NOT_LOGICAL
%left OPNAME1 // other
%left OPNAME2 // + -
%left OPNAME3 // * /


%%

start: program { this.program = (Vector<Object>)$1; }
    ;

program: program decl { ((Vector<Object>)($1)).add($2); $$=$1; }
    | decl { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

funcdecl: FUNC NAME '(' funcargs ')' body 
    { $$=new Object[]{
            "FUNC",
            $2,
            $4,
            $6
        }; 
    }
    ;

funcargs: /* empty */ { $$=new ArrayList<String>(); }
    | NAME { $$=new ArrayList<String>(); ((List<String>)($$)).add($1); }
    | funcargs ',' NAME { ((List<String>)$$).add($3); }
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
    | opexpr OPNAME1 opexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // other
    | opexpr OPNAME2 opexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // + -
    | opexpr OPNAME3 opexpr { $$=new Object[]{"CALL", $2, $1, $3}; } // * /
    ;

// unaryoperator: OPNAME1 { $$=$1; }
//     | OPNAME2 { $$=$1; }
//     | OPNAME3 { $$=$1; }
//     ;

smallexpr: NAME { $$=new Object[]{"FETCH", $1}; }
    // call here
    // | unaryoperator expr { $$=new Object[]{"CALL", $1, $2}; }
    | LITERAL { $$=new Object[]{"LITERAL", $1}; }
    | '(' expr ')' { $$=$2; }
    | ifexpr { $$=$1; }
    // whileexpr here
    // funcdef here
    ;

decl: funcdecl ';' { $$=$1; }
    | vardecl ';' { $$=$1; }
    | expr ';' { $$=$1; }
    ;

multidecl: multidecl decl { ((Vector<Object>)($$)).add($2); }
    | decl { $$=new Vector<Object>(); ((Vector<Object>)($$)).add($1); }
    ;

ifexpr: IF '(' expr ')' body { $$=new Object[]{"IF", $3, $5}; }

body: '{' multidecl '}' { $$=((Vector<Object>)($2)).toArray(); }
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