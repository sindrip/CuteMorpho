//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "cm.y"
    import java.io.*;
    import java.util.*;
    import blocks.*;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short FUNC=257;
public final static short RETURN=258;
public final static short IF=259;
public final static short VAR=260;
public final static short ELSE=261;
public final static short WHILE=262;
public final static short NAME=263;
public final static short LITERAL=264;
public final static short OPNAME1=265;
public final static short OPNAME2=266;
public final static short OPNAME3=267;
public final static short OR=268;
public final static short AND=269;
public final static short NOT=270;
public final static short EQUALS=271;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    5,    5,    5,    5,
    4,    6,    7,    7,    7,    8,    8,    9,    9,   10,
   10,   11,   11,   11,   11,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   13,   13,   14,   14,   15,
   15,   16,   16,
};
final static short yylen[] = {                            2,
    1,    0,    1,    3,    1,    3,    3,    3,    4,    0,
    6,    3,    2,    3,    1,    3,    1,    3,    1,    2,
    1,    3,    3,    3,    1,    2,    2,    2,    3,    1,
    4,    5,    1,    1,    1,    0,    1,    3,    1,    5,
    6,    2,    2,
};
final static short yydefred[] = {                        10,
    0,    0,    0,    0,    0,    0,    0,    0,   34,    0,
    0,    0,    0,   10,    0,   35,    0,    0,    0,    0,
   19,   21,    0,   33,    0,   13,    0,    0,    0,    0,
    0,    0,    0,   26,   27,   28,   20,    0,    0,    8,
    7,    0,    0,    0,    0,    0,    0,    0,    0,    9,
    0,   14,   39,    0,    0,    6,   29,    0,   18,   22,
   23,   24,    5,    0,    0,    0,   12,    0,   31,    0,
    0,    0,    0,   32,   38,   11,    4,    0,   41,   42,
   43,
};
final static short yydgoto[] = {                          1,
   64,   65,   16,   17,    2,   29,   18,   19,   20,   21,
   22,   23,   54,   55,   24,   79,
};
final static short yysindex[] = {                         0,
    0,  -24, -259,  -14,  -29, -249,  -22,  -40,    0,   19,
   19,   19,   -5,    0,  -14,    0,  -37,  -35, -247, -250,
    0,    0, -226,    0,  -15,    0,  -14, -241,  -28,  -14,
  -14,  -14,   -4,    0,    0,    0,    0,  -38,    3,    0,
    0,   -5,   -5,   19,   19,   19, -220,    5,  -14,    0,
    7,    0,    0,   20,   18,    0,    0, -250,    0,    0,
    0,    0,    0,   22,   24,  -59,    0,  -59,    0,  -14,
  -59, -197, -192,    0,    0,    0,    0, -122,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,   70,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -32,  -31,
    0,    0,    8,    0,    0,    0,    0,    0,    0,    0,
    0,   30,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   32,    0,    0,    0,
    0,    0,    0,    0,   33,    0,    0,  -21,    0,    0,
    0,    0,    0,    0,   34,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -63,    0,   62,    0,    2,    0,   35,   -6,
    9,   46,    0,    0,    4,    0,
};
final static int YYTABLESIZE=286;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   14,   15,   73,   25,   74,   26,   37,   76,   15,   17,
   27,   15,   17,   28,   80,   15,   39,   30,   43,   16,
   42,   40,   16,   41,   47,   15,   15,   17,   48,   49,
   50,   51,   52,   53,   15,   32,   59,   16,   44,   45,
   46,   30,   63,   57,   30,   66,   40,   68,   25,   40,
   67,   25,   60,   61,   62,   34,   35,   36,   15,   30,
   69,   70,   71,   14,   40,   77,   25,   72,   78,    1,
   36,   75,    2,   37,    3,   38,   58,    0,    0,    0,
    0,   81,    0,    0,   14,    0,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    0,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,
    0,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,    4,
    5,    6,    0,    7,    8,    9,   10,   11,   12,    0,
   31,   13,    3,    4,    5,    6,   17,    7,    8,    9,
   10,   11,   12,    4,    5,   13,   16,    7,    8,    9,
   10,   11,   12,    5,    0,   13,    7,   33,    9,   10,
   11,   12,    0,    0,   13,   30,   30,   30,   30,   30,
   40,   40,   40,   40,   40,   25,   25,    5,    0,    0,
    7,   33,    9,   10,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  123,   40,   66,  263,   68,    4,   13,   71,   41,   41,
   40,   44,   44,  263,   78,   40,   15,   40,  269,   41,
  268,   59,   44,   59,   40,   40,   59,   59,   27,  271,
   59,   30,   31,   32,   40,   40,   43,   59,  265,  266,
  267,   41,  263,   41,   44,   41,   41,   41,   41,   44,
   49,   44,   44,   45,   46,   10,   11,   12,   40,   59,
   41,   44,   41,  123,   59,  263,   59,   44,  261,    0,
   41,   70,   41,   41,   41,   14,   42,   -1,   -1,   -1,
   -1,   78,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  259,   -1,   -1,   -1,
   -1,  123,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,   -1,  262,  263,  264,  265,  266,  267,   -1,
  271,  270,  257,  258,  259,  260,  268,  262,  263,  264,
  265,  266,  267,  258,  259,  270,  268,  262,  263,  264,
  265,  266,  267,  259,   -1,  270,  262,  263,  264,  265,
  266,  267,   -1,   -1,  270,  265,  266,  267,  268,  269,
  265,  266,  267,  268,  269,  268,  269,  259,   -1,   -1,
  262,  263,  264,  265,  266,  267,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=271;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"FUNC","RETURN","IF","VAR","ELSE","WHILE",
"NAME","LITERAL","OPNAME1","OPNAME2","OPNAME3","OR","AND","NOT","EQUALS",
};
final static String yyrule[] = {
"$accept : start",
"start : bodyexprs",
"opt_idlist :",
"opt_idlist : idlist",
"idlist : idlist ',' NAME",
"idlist : NAME",
"body : '{' bodyexprs '}'",
"bodyexprs : bodyexprs expr ';'",
"bodyexprs : bodyexprs funcdecl ';'",
"bodyexprs : bodyexprs VAR decl_vars ';'",
"bodyexprs :",
"funcdecl : FUNC NAME '(' opt_idlist ')' body",
"decl_vars : NAME EQUALS expr",
"expr : RETURN expr",
"expr : NAME EQUALS expr",
"expr : orexpr",
"orexpr : orexpr OR andexpr",
"orexpr : andexpr",
"andexpr : andexpr AND notexpr",
"andexpr : notexpr",
"notexpr : NOT notexpr",
"notexpr : binopexpr",
"binopexpr : smallexpr OPNAME1 binopexpr",
"binopexpr : smallexpr OPNAME2 binopexpr",
"binopexpr : smallexpr OPNAME3 binopexpr",
"binopexpr : smallexpr",
"smallexpr : OPNAME1 smallexpr",
"smallexpr : OPNAME2 smallexpr",
"smallexpr : OPNAME3 smallexpr",
"smallexpr : '(' expr ')'",
"smallexpr : NAME",
"smallexpr : NAME '(' args ')'",
"smallexpr : WHILE '(' expr ')' body",
"smallexpr : ifexpr",
"smallexpr : LITERAL",
"smallexpr : body",
"args :",
"args : arglist",
"arglist : arglist ',' expr",
"arglist : expr",
"ifexpr : IF '(' expr ')' body",
"ifexpr : IF '(' expr ')' body elseexpr",
"elseexpr : ELSE body",
"elseexpr : ELSE ifexpr",
};

//#line 130 "cm.y"


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
//#line 337 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 36 "cm.y"
{ this.program = (Vector<Object>)val_peek(0).obj; }
break;
case 2:
//#line 40 "cm.y"
{ yyval.obj=new ArrayList<String>(); }
break;
case 3:
//#line 41 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 4:
//#line 45 "cm.y"
{ ((List<String>)(yyval.obj)).add(val_peek(0).sval); }
break;
case 5:
//#line 46 "cm.y"
{ yyval.obj=new ArrayList<String>(); ((List<String>)(yyval.obj)).add(val_peek(0).sval); }
break;
case 6:
//#line 50 "cm.y"
{ yyval.obj=new Object[]{"BODY", ((Vector<Object>)(val_peek(1).obj)).toArray()}; }
break;
case 7:
//#line 54 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(1).obj); }
break;
case 8:
//#line 55 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(1).obj); }
break;
case 9:
//#line 56 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(1).obj);}
break;
case 10:
//#line 57 "cm.y"
{ yyval.obj=new Vector<Object>();}
break;
case 11:
//#line 61 "cm.y"
{ yyval.obj=new Object[]{"FUNC", val_peek(4).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 12:
//#line 65 "cm.y"
{ yyval.obj=new Object[]{"VAR", val_peek(2).sval, val_peek(0).obj}; }
break;
case 13:
//#line 69 "cm.y"
{ yyval.obj=new Object[]{"RETURN", val_peek(0).obj}; }
break;
case 14:
//#line 70 "cm.y"
{ yyval.obj=new Object[]{"STORE", val_peek(2).sval, val_peek(0).obj}; }
break;
case 15:
//#line 71 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 16:
//#line 75 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 17:
//#line 76 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 18:
//#line 80 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 19:
//#line 81 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 20:
//#line 85 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(0).obj}; }
break;
case 21:
//#line 86 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 22:
//#line 90 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 23:
//#line 91 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 24:
//#line 92 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 25:
//#line 93 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 26:
//#line 97 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(0).obj}; }
break;
case 27:
//#line 98 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(0).obj}; }
break;
case 28:
//#line 99 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(0).obj}; }
break;
case 29:
//#line 100 "cm.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 30:
//#line 101 "cm.y"
{ yyval.obj=new Object[]{"FETCH", val_peek(0).sval}; }
break;
case 31:
//#line 102 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(3).sval, val_peek(1).obj }; }
break;
case 32:
//#line 103 "cm.y"
{ yyval.obj=new Object[]{"WHILE", val_peek(2).obj, val_peek(0).obj}; }
break;
case 33:
//#line 104 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 34:
//#line 105 "cm.y"
{ yyval.obj=new Object[]{"LITERAL", val_peek(0).sval}; }
break;
case 35:
//#line 106 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 36:
//#line 110 "cm.y"
{ yyval.obj=new Object[]{"ARGS", new Object[]{} }; }
break;
case 37:
//#line 111 "cm.y"
{ yyval.obj=new Object[]{"ARGS", ((Vector<Object>)(val_peek(0).obj)).toArray()}; }
break;
case 38:
//#line 115 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 39:
//#line 116 "cm.y"
{ yyval.obj=new Vector<Object>(); ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 40:
//#line 120 "cm.y"
{ yyval.obj=new Object[]{"IF1", val_peek(2).obj, val_peek(0).obj}; }
break;
case 41:
//#line 121 "cm.y"
{ yyval.obj=new Object[]{"IF2", val_peek(3).obj, val_peek(1).obj, val_peek(0).obj}; }
break;
case 42:
//#line 125 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 43:
//#line 126 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
//#line 658 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
