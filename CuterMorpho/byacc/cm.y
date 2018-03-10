%{
    import java.io.*;
    import java.util.*;
%}

%token FUNC RETURN OPNAME LITERAL IF VAR
%token <sval> NAME
%type <ival> args, vars
%%
start: program
    ;

program: program function
    | function
    ;

function: FUNC NAME '(' args ')' '{' vars expr ';' '}' { System.out.println($2); System.out.println($4); System.out.println($7); }
    ;

args: /* empty */       { $$=0; }
    | NAME              { $$=$$+1; }
    | args ',' NAME     { $$=$1+1; }
    ;

// need to add multivar!
vars: /* empty */       { $$=0; }
    | vars VAR NAME ';' { $$=$1+1; }
    ;

expr: RETURN expr
    | NAME '=' expr
    | binopexpr
    ;

binopexpr: smallexpr OPNAME smallexpr
    | smallexpr
    ;

smallexpr: '(' expr ')'
    | LITERAL
    | OPNAME smallexpr
    | ifexpr
    | NAME
    | NAME '(' expr ')'
    ;

ifexpr: IF '(' expr ')' body

body: '{' expr ';' '}'
%%


    private CuteMorphoLexer lexer;

    int last_token_read;

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