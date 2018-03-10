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
public final static short NAME=261;
public final static short LITERAL=262;
public final static short OPNAME1=263;
public final static short OPNAME2=264;
public final static short OPNAME3=265;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,   11,   11,    8,    8,
    8,    4,    4,    4,    6,    6,    6,   12,   12,   12,
    7,    7,    7,    7,    7,    7,    5,    5,    5,    9,
   10,
};
final static short yylen[] = {                            2,
    1,    2,    1,    9,    0,    4,    0,    3,    0,    1,
    3,    2,    3,    1,    3,    3,    1,    1,    1,    1,
    3,    1,    2,    1,    1,    4,    0,    1,    3,    5,
    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,   28,    0,    0,
    0,    5,   29,    0,    0,    0,    0,    0,    0,    0,
   22,   18,   19,   20,    0,    4,    0,    0,   17,   24,
    0,    6,   12,    0,    0,    0,    0,    8,    0,    0,
    0,   23,    0,   10,    0,   13,   21,    0,   16,    0,
   26,    0,    0,   30,   11,    0,   31,
};
final static short yydgoto[] = {                          2,
    3,    4,   14,   44,    9,   28,   29,   45,   30,   54,
   16,   31,
};
final static short yysindex[] = {                      -245,
 -244,    0, -245,    0,  -21,    0, -233,    0,  -34,  -91,
 -225,    0,    0, -221, -220,  -40,  -19,  -24,    2,  -36,
    0,    0,    0,    0,  -24,    0,  -16, -229,    0,    0,
  -17,    0,    0,  -24,  -24,  -24,    3,    0,  -17,  -17,
    6,    0,    7,    0,  -30,    0,    0, -217,    0,  -73,
    0,  -24,  -24,    0,    0,  -43,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,   51,    0,    0,    0,  -23,    0,    0,    0,
    0,    0,    0,  -32,    0,    0,    0,    0,    0,  -14,
    0,    0,    0,    0,    0,    0,    0,  -39,    0,    0,
    0,    0,    0,    0,  -15,    0,    0,    0,    0,    0,
  -14,    0,    0,    0,    0,    0,    0,  -35,    0,    0,
    0,    0,  -41,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   49,    0,   -3,    0,   -2,   22,    1,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=250;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   52,   14,    9,   35,   14,   15,   10,    7,   15,   11,
   51,    1,   27,   52,   33,   25,    5,   27,    7,   14,
   27,   37,   25,   15,   36,    9,   25,    8,    9,   25,
   43,   12,   46,   39,   40,   13,   48,   49,   15,   32,
   17,   34,   38,   47,   25,   35,   40,   50,   55,   53,
    1,    6,   42,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   57,    0,    9,   26,   14,    0,    0,    0,   15,
    0,    0,    7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   25,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   18,   19,    0,
   20,   21,   22,   23,   24,    7,    7,   15,    7,    7,
    7,    7,    7,   18,   19,    0,   20,   21,   22,   23,
   24,   19,    0,   41,   21,   22,   23,   24,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   44,   41,   44,   40,   44,   41,   41,   40,   44,   44,
   41,  257,   16,   44,   18,   40,  261,   41,   40,   59,
   44,   25,   40,   59,   61,   41,   41,  261,   44,   44,
   34,  123,   36,  263,  264,  261,   39,   40,  260,   59,
  261,   40,   59,   41,   59,   40,  264,   41,   52,  123,
    0,    3,   31,   53,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  125,   -1,  125,  125,  125,   -1,   -1,   -1,  125,
   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  259,   -1,
  261,  262,  263,  264,  265,  258,  259,  263,  261,  262,
  263,  264,  265,  258,  259,   -1,  261,  262,  263,  264,
  265,  259,   -1,  261,  262,  263,  264,  265,  263,  264,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=265;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"';'",
null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"FUNC","RETURN","IF","VAR","NAME","LITERAL",
"OPNAME1","OPNAME2","OPNAME3",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : program function",
"program : function",
"function : FUNC NAME '(' args ')' '{' vars multiexpr '}'",
"vars :",
"vars : vars VAR NAME ';'",
"multiexpr :",
"multiexpr : multiexpr expr ';'",
"exprargs :",
"exprargs : expr",
"exprargs : exprargs ',' expr",
"expr : RETURN expr",
"expr : NAME '=' expr",
"expr : _binopexpr",
"_binopexpr : _binopexpr OPNAME1 _binopexpr",
"_binopexpr : _binopexpr OPNAME2 _binopexpr",
"_binopexpr : smallexpr",
"_operator : OPNAME1",
"_operator : OPNAME2",
"_operator : OPNAME3",
"smallexpr : '(' expr ')'",
"smallexpr : LITERAL",
"smallexpr : _operator smallexpr",
"smallexpr : ifexpr",
"smallexpr : NAME",
"smallexpr : NAME '(' exprargs ')'",
"args :",
"args : NAME",
"args : args ',' NAME",
"ifexpr : IF '(' expr ')' body",
"body : '{' exprargs '}'",
};

//#line 76 "cm.y"


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
//#line 298 "Parser.java"
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
//#line 16 "cm.y"
{ this.program = (Vector<Object>)val_peek(0).obj; }
break;
case 2:
//#line 19 "cm.y"
{ ((Vector<Object>)(val_peek(1).obj)).add(val_peek(0).obj); yyval.obj=val_peek(1).obj; }
break;
case 3:
//#line 20 "cm.y"
{ yyval.obj=new Vector<Object>(); ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 4:
//#line 23 "cm.y"
{
        yyval.obj = new Object[]{"FUNC", val_peek(7).sval, (List<String>)val_peek(5).obj, (List<String>)val_peek(2).obj, ((Vector<Object>)(val_peek(1).obj)).toArray() };
    }
break;
case 5:
//#line 29 "cm.y"
{ yyval.obj=new ArrayList<String>(); }
break;
case 6:
//#line 30 "cm.y"
{ ((List<String>)(yyval.obj)).add(val_peek(1).sval); }
break;
case 7:
//#line 33 "cm.y"
{ yyval.obj=new Vector<Object>(); }
break;
case 8:
//#line 34 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(1).obj); }
break;
case 9:
//#line 37 "cm.y"
{ yyval.obj=new Vector<Object>(); }
break;
case 10:
//#line 38 "cm.y"
{ yyval.obj=new Vector<Object>(); ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj);  }
break;
case 11:
//#line 39 "cm.y"
{ ((Vector<Object>)(yyval.obj)).add(val_peek(0).obj); }
break;
case 12:
//#line 42 "cm.y"
{ yyval.obj=new Object[]{"RETURN", val_peek(0).obj }; }
break;
case 13:
//#line 43 "cm.y"
{ yyval.obj=new Object[]{"STORE", val_peek(2).sval, val_peek(0).obj}; }
break;
case 15:
//#line 47 "cm.y"
{yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 16:
//#line 48 "cm.y"
{yyval.obj=new Object[]{"CALL", val_peek(1).sval, val_peek(2).obj, val_peek(0).obj}; }
break;
case 18:
//#line 52 "cm.y"
{ yyval.obj=val_peek(0).sval; }
break;
case 19:
//#line 53 "cm.y"
{ yyval.obj=val_peek(0).sval; }
break;
case 20:
//#line 54 "cm.y"
{ yyval.obj=val_peek(0).sval; }
break;
case 21:
//#line 57 "cm.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 22:
//#line 58 "cm.y"
{ yyval.obj=new Object[]{"LITERAL", val_peek(0).sval}; }
break;
case 23:
//#line 59 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(1).obj, val_peek(0).obj}; }
break;
case 24:
//#line 60 "cm.y"
{ yyval.obj=val_peek(0).obj; }
break;
case 25:
//#line 61 "cm.y"
{ yyval.obj=new Object[]{"FETCH", val_peek(0).sval}; }
break;
case 26:
//#line 62 "cm.y"
{ yyval.obj=new Object[]{"CALL", val_peek(3).sval}; }
break;
case 27:
//#line 65 "cm.y"
{ yyval.obj=new ArrayList<String>(); }
break;
case 28:
//#line 66 "cm.y"
{ yyval.obj=new ArrayList<String>(); ((List<String>)yyval.obj).add(val_peek(0).sval); }
break;
case 29:
//#line 67 "cm.y"
{ ((List<String>)yyval.obj).add(val_peek(0).sval); }
break;
case 30:
//#line 70 "cm.y"
{ yyval.obj=new Object[]{"IF", val_peek(2).obj, val_peek(0).obj}; }
break;
case 31:
//#line 73 "cm.y"
{ yyval.obj=new Object[]{ "BODY", ((Vector<Object>)(val_peek(1).obj)).toArray() }; }
break;
//#line 565 "Parser.java"
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
