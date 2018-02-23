/* The following code was generated by JFlex 1.6.1 */

/**
	JFlex lexgreiningardæmi byggt á lesgreini fyrir NanoMorpho
	Höfundur: Sindri Pétur Ingimundarson, janúar 2017
	          Stella Rut Guðmundsdóttir

	Þennan lesgreini má þýða og keyra með skipununum
		java -jar JFlex-1.6.1.jar nanomlexer.jflex
		javac NanoMLexer.java
		java NanoMLexer inntaksskrá > úttaksskrá
	Einnig má nota forritið 'make', ef viðeigandi 'makefile'
	er til staðar:
		make test

    Breytingar elsif lykilord sleppt (else if naegir)
    func baett fyrir framan funcdecl
 */

import java.io.*;
import java.util.Vector;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 * from the specification file <tt>nanomlexer.jflex</tt>
 */
public class NanoMLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\26\1\2\1\42\1\26\1\1\22\0\1\26\1\23\1\10"+
    "\2\0\2\23\1\17\2\22\1\23\1\7\1\22\1\4\1\5\1\25"+
    "\4\20\4\21\2\3\1\23\1\22\1\23\1\24\2\23\1\0\4\27"+
    "\1\6\25\27\1\0\1\11\1\0\1\23\1\27\1\0\1\41\1\12"+
    "\1\37\1\27\1\31\1\15\1\27\1\35\1\30\2\27\1\32\1\27"+
    "\1\14\3\27\1\16\1\33\1\13\1\36\1\40\1\34\3\27\1\22"+
    "\1\23\1\22\1\23\6\0\1\42\44\0\1\27\12\0\1\27\4\0"+
    "\1\27\5\0\27\27\1\0\37\27\1\0\u01ca\27\4\0\14\27\16\0"+
    "\5\27\7\0\1\27\1\0\1\27\201\0\5\27\1\0\2\27\2\0"+
    "\4\27\1\0\1\27\6\0\1\27\1\0\3\27\1\0\1\27\1\0"+
    "\24\27\1\0\123\27\1\0\213\27\10\0\246\27\1\0\46\27\2\0"+
    "\1\27\7\0\47\27\110\0\33\27\5\0\3\27\55\0\53\27\43\0"+
    "\2\27\1\0\143\27\1\0\1\27\17\0\2\27\7\0\2\27\12\0"+
    "\3\27\2\0\1\27\20\0\1\27\1\0\36\27\35\0\131\27\13\0"+
    "\1\27\30\0\41\27\11\0\2\27\4\0\1\27\5\0\26\27\4\0"+
    "\1\27\11\0\1\27\3\0\1\27\27\0\31\27\107\0\23\27\121\0"+
    "\66\27\3\0\1\27\22\0\1\27\7\0\12\27\17\0\20\27\4\0"+
    "\10\27\2\0\2\27\2\0\26\27\1\0\7\27\1\0\1\27\3\0"+
    "\4\27\3\0\1\27\20\0\1\27\15\0\2\27\1\0\3\27\16\0"+
    "\2\27\23\0\6\27\4\0\2\27\2\0\26\27\1\0\7\27\1\0"+
    "\2\27\1\0\2\27\1\0\2\27\37\0\4\27\1\0\1\27\23\0"+
    "\3\27\20\0\11\27\1\0\3\27\1\0\26\27\1\0\7\27\1\0"+
    "\2\27\1\0\5\27\3\0\1\27\22\0\1\27\17\0\2\27\43\0"+
    "\10\27\2\0\2\27\2\0\26\27\1\0\7\27\1\0\2\27\1\0"+
    "\5\27\3\0\1\27\36\0\2\27\1\0\3\27\17\0\1\27\21\0"+
    "\1\27\1\0\6\27\3\0\3\27\1\0\4\27\3\0\2\27\1\0"+
    "\1\27\1\0\2\27\3\0\2\27\3\0\3\27\3\0\14\27\26\0"+
    "\1\27\64\0\10\27\1\0\3\27\1\0\27\27\1\0\20\27\3\0"+
    "\1\27\32\0\2\27\6\0\2\27\43\0\10\27\1\0\3\27\1\0"+
    "\27\27\1\0\12\27\1\0\5\27\3\0\1\27\40\0\1\27\1\0"+
    "\2\27\17\0\2\27\22\0\10\27\1\0\3\27\1\0\51\27\2\0"+
    "\1\27\20\0\1\27\21\0\2\27\30\0\6\27\5\0\22\27\3\0"+
    "\30\27\1\0\11\27\1\0\1\27\2\0\7\27\72\0\60\27\1\0"+
    "\2\27\14\0\7\27\72\0\2\27\1\0\1\27\2\0\2\27\1\0"+
    "\1\27\2\0\1\27\6\0\4\27\1\0\7\27\1\0\3\27\1\0"+
    "\1\27\1\0\1\27\2\0\2\27\1\0\4\27\1\0\2\27\11\0"+
    "\1\27\2\0\5\27\1\0\1\27\25\0\4\27\40\0\1\27\77\0"+
    "\10\27\1\0\44\27\33\0\5\27\163\0\53\27\24\0\1\27\20\0"+
    "\6\27\4\0\4\27\3\0\1\27\3\0\2\27\7\0\3\27\4\0"+
    "\15\27\14\0\1\27\21\0\46\27\1\0\1\27\5\0\1\27\2\0"+
    "\53\27\1\0\u014d\27\1\0\4\27\2\0\7\27\1\0\1\27\1\0"+
    "\4\27\2\0\51\27\1\0\4\27\2\0\41\27\1\0\4\27\2\0"+
    "\7\27\1\0\1\27\1\0\4\27\2\0\17\27\1\0\71\27\1\0"+
    "\4\27\2\0\103\27\45\0\20\27\20\0\125\27\14\0\u026c\27\2\0"+
    "\21\27\1\0\32\27\5\0\113\27\6\0\10\27\7\0\15\27\1\0"+
    "\4\27\16\0\22\27\16\0\22\27\16\0\15\27\1\0\3\27\17\0"+
    "\64\27\43\0\1\27\4\0\1\27\103\0\130\27\10\0\51\27\1\0"+
    "\1\27\5\0\106\27\12\0\37\27\61\0\36\27\2\0\5\27\13\0"+
    "\54\27\25\0\7\27\70\0\27\27\11\0\65\27\122\0\1\27\135\0"+
    "\57\27\21\0\7\27\67\0\36\27\15\0\2\27\12\0\54\27\32\0"+
    "\44\27\51\0\3\27\12\0\44\27\153\0\4\27\1\0\4\27\3\0"+
    "\2\27\11\0\300\27\100\0\u0116\27\2\0\6\27\2\0\46\27\2\0"+
    "\6\27\2\0\10\27\1\0\1\27\1\0\1\27\1\0\1\27\1\0"+
    "\37\27\2\0\65\27\1\0\7\27\1\0\1\27\3\0\3\27\1\0"+
    "\7\27\3\0\4\27\2\0\6\27\4\0\15\27\5\0\3\27\1\0"+
    "\7\27\53\0\1\42\1\42\107\0\1\27\15\0\1\27\20\0\15\27"+
    "\145\0\1\27\4\0\1\27\2\0\12\27\1\0\1\27\3\0\5\27"+
    "\6\0\1\27\1\0\1\27\1\0\1\27\1\0\4\27\1\0\13\27"+
    "\2\0\4\27\5\0\5\27\4\0\1\27\64\0\2\27\u0a7b\0\57\27"+
    "\1\0\57\27\1\0\205\27\6\0\4\27\3\0\2\27\14\0\46\27"+
    "\1\0\1\27\5\0\1\27\2\0\70\27\7\0\1\27\20\0\27\27"+
    "\11\0\7\27\1\0\7\27\1\0\7\27\1\0\7\27\1\0\7\27"+
    "\1\0\7\27\1\0\7\27\1\0\7\27\120\0\1\27\u01d5\0\2\27"+
    "\52\0\5\27\5\0\2\27\4\0\126\27\6\0\3\27\1\0\132\27"+
    "\1\0\4\27\5\0\51\27\3\0\136\27\21\0\33\27\65\0\20\27"+
    "\u0200\0\u19b6\27\112\0\u51cd\27\63\0\u048d\27\103\0\56\27\2\0\u010d\27"+
    "\3\0\20\27\12\0\2\27\24\0\57\27\20\0\37\27\2\0\106\27"+
    "\61\0\11\27\2\0\147\27\2\0\4\27\1\0\36\27\2\0\2\27"+
    "\105\0\13\27\1\0\3\27\1\0\4\27\1\0\27\27\35\0\64\27"+
    "\16\0\62\27\76\0\6\27\3\0\1\27\16\0\34\27\12\0\27\27"+
    "\31\0\35\27\7\0\57\27\34\0\1\27\20\0\5\27\1\0\12\27"+
    "\12\0\5\27\1\0\51\27\27\0\3\27\1\0\10\27\24\0\27\27"+
    "\3\0\1\27\3\0\62\27\1\0\1\27\3\0\2\27\2\0\5\27"+
    "\2\0\1\27\1\0\1\27\30\0\3\27\2\0\13\27\7\0\3\27"+
    "\14\0\6\27\2\0\6\27\2\0\6\27\11\0\7\27\1\0\7\27"+
    "\1\0\53\27\1\0\4\27\4\0\2\27\132\0\43\27\35\0\u2ba4\27"+
    "\14\0\27\27\4\0\61\27\u2104\0\u016e\27\2\0\152\27\46\0\7\27"+
    "\14\0\5\27\5\0\1\27\1\0\12\27\1\0\15\27\1\0\5\27"+
    "\1\0\1\27\1\0\2\27\1\0\2\27\1\0\154\27\41\0\u016b\27"+
    "\22\0\100\27\2\0\66\27\50\0\14\27\164\0\5\27\1\0\207\27"+
    "\44\0\32\27\6\0\32\27\13\0\131\27\3\0\6\27\2\0\6\27"+
    "\2\0\6\27\2\0\3\27\43\0\14\27\1\0\32\27\1\0\23\27"+
    "\1\0\2\27\1\0\17\27\2\0\16\27\42\0\173\27\u0185\0\35\27"+
    "\3\0\61\27\57\0\40\27\20\0\21\27\1\0\10\27\6\0\46\27"+
    "\12\0\36\27\2\0\44\27\4\0\10\27\60\0\236\27\142\0\50\27"+
    "\10\0\64\27\234\0\u0137\27\11\0\26\27\12\0\10\27\230\0\6\27"+
    "\2\0\1\27\1\0\54\27\1\0\2\27\3\0\1\27\2\0\27\27"+
    "\12\0\27\27\11\0\37\27\141\0\26\27\12\0\32\27\106\0\70\27"+
    "\6\0\2\27\100\0\1\27\17\0\4\27\1\0\3\27\1\0\33\27"+
    "\54\0\35\27\3\0\35\27\43\0\10\27\1\0\34\27\33\0\66\27"+
    "\12\0\26\27\12\0\23\27\15\0\22\27\156\0\111\27\u03ba\0\65\27"+
    "\113\0\55\27\40\0\31\27\32\0\44\27\51\0\43\27\3\0\1\27"+
    "\14\0\60\27\16\0\4\27\25\0\1\27\45\0\22\27\1\0\31\27"+
    "\204\0\57\27\46\0\10\27\2\0\2\27\2\0\26\27\1\0\7\27"+
    "\1\0\2\27\1\0\5\27\3\0\1\27\37\0\5\27\u011e\0\60\27"+
    "\24\0\2\27\1\0\1\27\270\0\57\27\121\0\60\27\24\0\1\27"+
    "\73\0\53\27\u01f5\0\100\27\37\0\1\27\u01c0\0\71\27\u0507\0\u0399\27"+
    "\u0c67\0\u042f\27\u33d1\0\u0239\27\7\0\37\27\161\0\36\27\22\0\60\27"+
    "\20\0\4\27\37\0\25\27\5\0\23\27\u0370\0\105\27\13\0\1\27"+
    "\102\0\15\27\u4060\0\2\27\u0bfe\0\153\27\5\0\15\27\3\0\11\27"+
    "\7\0\12\27\u1766\0\125\27\1\0\107\27\1\0\2\27\2\0\1\27"+
    "\2\0\2\27\2\0\4\27\1\0\14\27\1\0\1\27\1\0\7\27"+
    "\1\0\101\27\1\0\4\27\2\0\10\27\1\0\7\27\1\0\34\27"+
    "\1\0\4\27\1\0\5\27\1\0\1\27\3\0\7\27\1\0\u0154\27"+
    "\2\0\31\27\1\0\31\27\1\0\37\27\1\0\31\27\1\0\37\27"+
    "\1\0\31\27\1\0\37\27\1\0\31\27\1\0\37\27\1\0\31\27"+
    "\1\0\10\27\u1034\0\305\27\u053b\0\4\27\1\0\33\27\1\0\2\27"+
    "\1\0\1\27\2\0\1\27\1\0\12\27\1\0\4\27\1\0\1\27"+
    "\1\0\1\27\6\0\1\27\4\0\1\27\1\0\1\27\1\0\1\27"+
    "\1\0\3\27\1\0\2\27\1\0\1\27\2\0\1\27\1\0\1\27"+
    "\1\0\1\27\1\0\1\27\1\0\1\27\1\0\2\27\1\0\1\27"+
    "\2\0\4\27\1\0\7\27\1\0\4\27\1\0\4\27\1\0\1\27"+
    "\1\0\12\27\1\0\21\27\5\0\3\27\1\0\5\27\1\0\21\27"+
    "\u1144\0\ua6d7\27\51\0\u1035\27\13\0\336\27\u3fe2\0\u021e\27\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\u05f0\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\1\3\1\4\1\5\1\4\1\1"+
    "\4\5\1\1\2\6\1\4\4\5\2\0\1\3\1\0"+
    "\5\5\2\0\1\4\1\7\3\5\1\3\5\5\2\0"+
    "\1\2\2\5\1\10\1\0\1\3\1\11\1\5\1\12"+
    "\1\5\1\3\1\0\1\5\1\13\1\14";

  private static int [] zzUnpackAction() {
    int [] result = new int[60];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\43\0\106\0\43\0\151\0\214\0\257\0\322"+
    "\0\365\0\u0118\0\u013b\0\u015e\0\u0181\0\u01a4\0\43\0\322"+
    "\0\u01c7\0\u01ea\0\u020d\0\u0230\0\u0253\0\u0276\0\365\0\43"+
    "\0\u0299\0\u02bc\0\u02df\0\u0302\0\u0325\0\u0348\0\u036b\0\u038e"+
    "\0\u03b1\0\257\0\u03d4\0\u03f7\0\u041a\0\u043d\0\u0460\0\u0483"+
    "\0\u04a6\0\u04c9\0\u04ec\0\u050f\0\u0532\0\u0555\0\u0578\0\u059b"+
    "\0\257\0\u05be\0\257\0\257\0\u05e1\0\257\0\u0604\0\u0627"+
    "\0\u0627\0\u064a\0\257\0\257";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[60];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\2\1\7\1\10"+
    "\1\11\1\2\1\7\1\12\1\13\1\14\1\15\1\16"+
    "\2\5\1\17\1\10\1\20\1\21\1\4\1\7\1\22"+
    "\1\23\2\7\1\24\3\7\1\25\1\7\46\0\1\4"+
    "\43\0\1\5\1\0\1\26\12\0\2\5\24\0\1\5"+
    "\1\10\2\0\1\10\10\0\2\5\1\0\3\10\20\0"+
    "\1\7\2\0\1\7\3\0\5\7\1\0\2\7\5\0"+
    "\13\7\5\0\1\10\2\0\1\10\13\0\3\10\15\0"+
    "\10\27\1\30\1\31\31\27\3\0\1\7\2\0\1\7"+
    "\3\0\4\7\1\32\1\0\2\7\5\0\13\7\4\0"+
    "\1\7\2\0\1\7\3\0\5\7\1\0\2\7\5\0"+
    "\7\7\1\33\3\7\4\0\1\7\2\0\1\7\3\0"+
    "\5\7\1\0\2\7\5\0\7\7\1\34\2\7\1\35"+
    "\4\0\1\7\2\0\1\7\3\0\5\7\1\0\2\7"+
    "\5\0\2\7\1\36\10\7\1\0\11\37\1\40\5\37"+
    "\1\0\23\37\4\0\1\10\2\0\1\10\13\0\2\10"+
    "\1\41\20\0\1\7\2\0\1\7\3\0\3\7\1\42"+
    "\1\7\1\0\2\7\5\0\13\7\4\0\1\7\2\0"+
    "\1\7\3\0\5\7\1\0\2\7\5\0\3\7\1\43"+
    "\7\7\4\0\1\7\2\0\1\7\3\0\5\7\1\0"+
    "\2\7\5\0\6\7\1\44\4\7\4\0\1\7\2\0"+
    "\1\7\3\0\5\7\1\0\2\7\5\0\12\7\1\45"+
    "\4\0\1\46\14\0\2\46\31\0\12\27\24\0\1\7"+
    "\2\0\1\7\3\0\5\7\1\0\2\7\5\0\7\7"+
    "\1\47\3\7\4\0\1\7\2\0\1\7\3\0\5\7"+
    "\1\0\2\7\5\0\3\7\1\50\7\7\4\0\1\7"+
    "\2\0\1\7\3\0\2\7\1\51\2\7\1\0\2\7"+
    "\5\0\13\7\4\0\1\7\2\0\1\7\3\0\5\7"+
    "\1\0\2\7\5\0\3\7\1\52\7\7\4\0\1\7"+
    "\2\0\1\7\3\0\1\7\1\53\3\7\1\0\2\7"+
    "\5\0\13\7\20\0\1\30\33\0\10\37\1\54\1\55"+
    "\21\0\1\56\1\3\1\4\1\56\1\41\2\56\1\41"+
    "\13\56\3\41\15\56\3\0\1\7\2\0\1\7\3\0"+
    "\5\7\1\0\2\7\5\0\4\7\1\57\6\7\4\0"+
    "\1\7\2\0\1\7\3\0\5\7\1\0\2\7\5\0"+
    "\1\7\1\60\11\7\4\0\1\7\2\0\1\7\3\0"+
    "\4\7\1\61\1\0\2\7\5\0\13\7\4\0\1\46"+
    "\2\0\1\62\11\0\2\46\7\0\1\62\14\0\1\7"+
    "\2\0\1\7\3\0\5\7\1\0\2\7\5\0\2\7"+
    "\1\63\10\7\4\0\1\7\2\0\1\7\3\0\5\7"+
    "\1\0\2\7\5\0\3\7\1\63\7\7\4\0\1\7"+
    "\2\0\1\7\3\0\5\7\1\0\2\7\5\0\10\7"+
    "\1\64\2\7\4\0\1\7\2\0\1\7\3\0\5\7"+
    "\1\0\2\7\5\0\4\7\1\47\6\7\4\0\1\7"+
    "\2\0\1\7\3\0\5\7\1\0\2\7\5\0\7\7"+
    "\1\65\3\7\20\0\1\30\2\55\40\0\1\30\2\37"+
    "\21\0\1\56\1\3\1\4\40\56\3\0\1\7\2\0"+
    "\1\7\3\0\5\7\1\0\2\7\5\0\2\7\1\66"+
    "\10\7\4\0\1\7\2\0\1\7\3\0\5\7\1\0"+
    "\2\7\5\0\3\7\1\67\7\7\4\0\1\70\1\71"+
    "\2\0\1\71\10\0\2\70\24\0\1\7\2\0\1\7"+
    "\3\0\4\7\1\72\1\0\2\7\5\0\13\7\4\0"+
    "\1\7\2\0\1\7\3\0\5\7\1\0\2\7\5\0"+
    "\2\7\1\73\10\7\4\0\1\70\14\0\2\70\24\0"+
    "\1\7\2\0\1\7\3\0\2\7\1\74\2\7\1\0"+
    "\2\7\5\0\13\7\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1645];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\12\1\1\11\6\1\2\0"+
    "\1\11\1\0\5\1\2\0\13\1\2\0\4\1\1\0"+
    "\6\1\1\0\3\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[60];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */

// Skilgreiningar á tókum (tokens):
final static int ERROR = -1;

// keywords
final static int IF = 1001;
final static int ELSE = 1002;
final static int WHILE = 1003;
final static int RETURN = 1004;
final static int VAR = 1005;
final static int FUNC = 1006;

// ---
final static int NAME = 1020;
final static int LITERAL = 1030;
final static int OPNAME = 1040;

// Breyta sem mun innihalda les (lexeme):
public static String lexeme;
public static String lexemeFirst;
public static int token;
public static int tokenFirst;
// linecount
public static int lineFirst;
public static int line;

public static NanoMLexer lexer;

public static int nextToken() {
	return tokenFirst;
}

public static String nextLexeme() {
	return lexemeFirst;
}

public static void advance() throws Exception {
	lexemeFirst = lexeme;
	tokenFirst = token;
	lineFirst = line;
	token = lexer.yylex();
	line = lexer.yyline;
}

public static void over(int t) throws Exception {
	if (tokenFirst != t) {
		throw new Exception("Expected " + ((char) t) + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	advance();
}

public static void over(int t, String key_w) throws Exception {
	if (tokenFirst != t) {
		throw new Exception("Expected " + key_w + " but found " + lexemeFirst +
			" on line: " + lineFirst);
	}
	advance();
}

public static int peek() {
	return token;
}

public static void init() throws Exception {
	advance();
	advance();
}

// Þetta keyrir lexgreininn:
public static void main( String[] args ) throws Exception
{
	lexer = new NanoMLexer(new FileReader(args[0]));
	init();
	Object[] intermediate = program_t();
}

public static Object[] program_t() throws Exception {
	Vector<Object> funcs = new Vector<Object>();
	while(token != 0) {
		function_t();
	}
	over(0); //EOF
	return funcs.toArray();
}

public static void function_t() throws Exception {
	
	over(FUNC, "func");
	over(NAME, "A name");
	over('(');
	if (nextToken() != ')') {
		over(NAME, "A name");
		while (nextToken() == ',') {
			over(',');
			over(NAME, "A name");
		}
	}
	over(')');
	over('{');
	while (nextToken() == VAR) {
		vardecl_t();
	}

	expr_t();
	over(';');
	while(nextToken() != '}') {
		expr_t();
		over(';');
	}
	over('}');
}

public static void vardecl_t() throws Exception {
	over(VAR, "var");
	over(NAME, "A name");
	while(nextToken() != ';') {
		over(',');
		over(NAME, "A name");
	}
	over(';');
}

public static void expr_t() throws Exception {
	switch (nextToken()) {
		case RETURN:
			over(RETURN, "return");
			expr_t();
			break;
		case NAME:
			if (peek() == '=') {
				over(NAME, "A name");
				over('=');
				expr_t();
			} else {
				binopexpr_t();
			}
			break;
		default:
			binopexpr_t();
			break;
	}
}

public static void binopexpr_t() throws Exception {
	smallexpr_t();
	while(nextToken() == OPNAME) {
		over(OPNAME, "An operator");
		smallexpr_t();
	}
}

public static void smallexpr_t() throws Exception {
	switch (nextToken()) {
		case '(':
			over('(');
			expr_t();
			over(')');
			break;
		case LITERAL:
			over(LITERAL, "A literal");
			break;
		case OPNAME:
			over(OPNAME, "An operator");
			smallexpr_t();
			break;
		case IF:
			ifexpr_t();
			break;
		case WHILE:
			over(WHILE, "while");
			over('(');
			expr_t();
			over(')');
			body_t();
			break;
		default:
			over(NAME, "A name");
			if (nextToken() == '(') {
				over('(');
				expr_t();
				while(nextToken() != ')') {
					over(',');
					expr_t();
				}
				over(')');
			}
			break;
	}
}

public static void ifexpr_t() throws Exception {
	over(IF, "if");
	over('(');
	expr_t();
	over(')');
	body_t();
	
	if(nextToken() == ELSE) {
		over(ELSE, "if");
		if (nextToken() == '{') {
			body_t();
		} else {
			ifexpr_t();
		}
	}
}

public static void body_t() throws Exception {
	over('{');
	expr_t();
	over(';');
	while(nextToken() != '}') {
		expr_t();
		over(';');
	}
	over('}');
	
}



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public NanoMLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 2306) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return 0; }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { lexeme = yytext(); return ERROR;
            }
          case 13: break;
          case 2: 
            { /* ignore */
            }
          case 14: break;
          case 3: 
            { lexeme = yytext(); return LITERAL;
            }
          case 15: break;
          case 4: 
            { lexeme = yytext(); return OPNAME;
            }
          case 16: break;
          case 5: 
            { lexeme = yytext(); return NAME;
            }
          case 17: break;
          case 6: 
            { lexeme = yytext(); return yycharat(0);
            }
          case 18: break;
          case 7: 
            { lexeme = yytext(); return IF;
            }
          case 19: break;
          case 8: 
            { lexeme = yytext(); return VAR;
            }
          case 20: break;
          case 9: 
            { lexeme = yytext(); return FUNC;
            }
          case 21: break;
          case 10: 
            { lexeme = yytext(); return ELSE;
            }
          case 22: break;
          case 11: 
            { lexeme = yytext(); return WHILE;
            }
          case 23: break;
          case 12: 
            { lexeme = yytext(); return RETURN;
            }
          case 24: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
