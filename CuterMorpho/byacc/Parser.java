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






//#line 2 "cmtest.y"
    import java.io.*;
    import java.util.*;
//#line 20 "Parser.java"




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
public final static short OR_LOGICAL=272;
public final static short AND_LOGICAL=273;
public final static short NOT_LOGICAL=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    4,    8,    8,    8,    5,    7,    7,
    7,    9,    9,   10,   10,   11,   11,   12,   12,   12,
   12,   13,   13,   13,   13,    3,    3,    3,    2,    2,
   14,    6,
};
final static short yylen[] = {                            2,
    1,    2,    1,    6,    0,    1,    3,    4,    2,    3,
    1,    1,    3,    1,    3,    1,    2,    1,    3,    3,
    3,    1,    1,    3,    1,    2,    2,    2,    2,    1,
    5,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    3,    0,    0,    0,    0,    0,   14,    0,   18,   25,
    0,    9,    0,    0,    0,   22,   17,    0,    2,   26,
   27,   28,    0,    0,    0,    0,    0,    0,    0,    0,
   10,   24,    0,   15,    0,    0,   21,    6,    0,    0,
    8,    0,    0,    0,   31,    4,    7,    0,   30,   32,
   29,
};
final static short yydgoto[] = {                          9,
   10,   58,   11,   12,   13,   55,   14,   49,   15,   16,
   17,   18,   19,   20,
};
final static short yysindex[] = {                       -32,
 -254,  -24,  -26, -248, -252,    0,  -22,  -24,    0,  -32,
    0,  -33,  -30,  -28, -246, -242,    0, -230,    0,    0,
   -1,    0,  -24, -238,  -24,    0,    0,    1,    0,    0,
    0,    0,  -22,  -22,    4,    4,    4, -222,   13,  -24,
    0,    0, -242,    0, -255, -216,    0,    0,  -37,  -67,
    0,  -67, -206,  -32,    0,    0,    0,  -40,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -16,    0,    0,    0,    0,   58,
    0,    0,    0,    0,  -38,  -39,    0,   -7,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,    0,    0,
    0,    0,  -35,    0,   -9,  -11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -5,    0,    0,    7,   15,    0,    0,   27,
   -6,   10,    0,    0,
};
final static int YYTABLESIZE=268;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          8,
   27,   12,   11,   52,   29,   13,   53,    8,   21,    5,
   36,   37,    5,   23,   24,    8,   22,    8,   25,   12,
   11,   33,   28,   13,   22,   30,   34,   44,   31,   20,
   32,   19,   40,   16,   35,   36,   37,   39,   38,   41,
   48,   42,   22,    8,   45,   46,   47,   20,   59,   19,
   37,   16,   61,   50,   51,   54,   57,    1,   56,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    2,    3,    4,
    0,    0,    5,    6,    1,    2,    3,    4,   12,    7,
    5,    6,   13,    2,    3,    0,    3,    7,    5,    6,
   26,    6,    0,    0,    0,    7,    0,    7,   22,   22,
   22,   22,   22,   20,   20,   19,   20,   20,   19,   19,
   16,   16,    3,    0,    0,    0,   26,    6,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    7,   41,   41,   41,   10,   41,   44,   40,  263,   41,
  266,  267,   44,   40,  263,   40,    2,   40,  271,   59,
   59,  268,    8,   59,   41,   59,  269,   34,   59,   41,
   59,   41,  271,   41,  265,  266,  267,   23,   40,   25,
  263,   41,   59,   40,   35,   36,   37,   59,   54,   59,
  267,   59,   58,   41,   40,  123,  263,    0,   52,   33,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,   -1,  263,  264,  257,  258,  259,  260,  268,  270,
  263,  264,  268,  258,  259,   -1,  259,  270,  263,  264,
  263,  264,   -1,   -1,   -1,  270,   -1,  270,  265,  266,
  267,  268,  269,  265,  266,  265,  268,  269,  268,  269,
  268,  269,  259,   -1,   -1,   -1,  263,  264,
};
}
final static short YYFINAL=9;
final static short YYMAXTOKEN=274;
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
"OR_LOGICAL","AND_LOGICAL","NOT_LOGICAL",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : program decl",
"program : decl",
"funcdecl : FUNC NAME '(' funcargs ')' body",
"funcargs :",
"funcargs : NAME",
"funcargs : funcargs ',' NAME",
"vardecl : VAR NAME EQUALS expr",
"expr : RETURN expr",
"expr : NAME EQUALS expr",
"expr : orexpr",
"orexpr : andexpr",
"orexpr : orexpr OR andexpr",
"andexpr : notexpr",
"andexpr : andexpr AND notexpr",
"notexpr : opexpr",
"notexpr : NOT notexpr",
"opexpr : smallexpr",
"opexpr : opexpr OPNAME1 opexpr",
"opexpr : opexpr OPNAME2 opexpr",
"opexpr : opexpr OPNAME3 opexpr",
"smallexpr : NAME",
"smallexpr : LITERAL",
"smallexpr : '(' expr ')'",
"smallexpr : ifexpr",
"decl : funcdecl ';'",
"decl : vardecl ';'",
"decl : expr ';'",
"multidecl : multidecl decl",
"multidecl : decl",
"ifexpr : IF '(' expr ')' body",
"body : '{' multidecl '}'",
};

//#line 102 "cmtest.y"

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
//#line 314 "Parser.java"
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
//#line 23 "cmtest.y"
{ this.program = (Vector<Object>)val_peek(0).obj; }
break;
case 2:
//#line 26 "cmtest.y"
{ ((Vector<Object>)(val_peek(1).obj)).add(val_peek(0).obj); yyval.obj=val_peek(1).obj; }
break;
case 3:
//#line 27 "cmtest.y"
{ yyval.obj=new Vector<Object>(); ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 4:
//#line 31 "cmtest.y"
{ yyval.obj=new Object[]{
            "FUNC",
            val_peek(4).sval,
            val_peek(2).obj,
            val_peek(0).obj
        }; 
    }
break;
case 5:
//#line 40 "cmtest.y"
{ yyval.obj=new ArrayList<String>(); }
break;
case 6:
//#line 41 "cmtest.y"
{ yyval.obj=new ArrayList<String>(); ((List<String>)(yyval.obj)).add(val_peek(0).sval); }
break;
case 7:
//#line 42 "cmtest.y"
{ ((List<String>)yyval.obj).add(val_peek(0).sval); }
break;
case 8:
//#line 46 "cmtest.y"
{ yyval.obj=new Object[]{"VAR", val_peek(2).sval, val_peek(0).obj }; }
break;
case 9:
//#line 49 "cmtest.y"
{ yyval.obj=new Object[]{"RETURN", val_peek(0).obj }; }
break;
case 10:
//#line 50 "cmtest.y"
{ yyval.obj=new Object[]{"STORE", val_peek(2).sval, val_peek(0).obj }; }
break;
case 11:
//#line 51 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 12:
//#line 54 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 13:
//#line 55 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 14:
//#line 58 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 15:
//#line 59 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 16:
//#line 62 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 17:
//#line 63 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(0).obj}; }
break;
case 18:
//#line 66 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 19:
//#line 67 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 20:
//#line 68 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 21:
//#line 69 "cmtest.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 22:
//#line 77 "cmtest.y"
{ yyval.obj=new Object[]{"FETCH", val_peek(0).sval}; }
break;
case 23:
//#line 80 "cmtest.y"
{ yyval.obj=new Object[]{"LITERAL", val_peek(0).sval}; }
break;
case 24:
//#line 81 "cmtest.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 25:
//#line 82 "cmtest.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 26:
//#line 87 "cmtest.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 27:
//#line 88 "cmtest.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 28:
//#line 89 "cmtest.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 29:
//#line 92 "cmtest.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 30:
//#line 93 "cmtest.y"
{ yyval.obj=new Vector<Object>(); ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 31:
//#line 96 "cmtest.y"
{ yyval.obj=new Object[]{"IF", val_peek(2).obj, val_peek(0).obj}; }
break;
case 32:
//#line 98 "cmtest.y"
{ yyval.obj=((Vector<Object>)(val_peek(1).obj)).toArray(); }
break;
//#line 597 "Parser.java"
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
