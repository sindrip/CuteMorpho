import blocks.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

import javax.management.InstanceAlreadyExistsException;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class CuteMorphoCompiler {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws Exception {
        Parser yyparser = new Parser(new FileReader("test.s"));
        yyparser.yyparse();
        Vector<Object> parserOutput = yyparser.getProgram();
        
        prog(parserOutput);
    }

    public static void emit(String s) {
        System.out.println(s);
    }

    public static void indentEmit(String s, int indent) {
        int len = indent + s.length();
        emit(String.format("%"+len+"s", s));
    }
    
    public static Object[] otoa(Object objToObjArray) {
        return (Object[]) objToObjArray;
    }

    public static void prog(Vector<Object> prog) {
        emit("PROGRAM STARTS HERE");
        prog.stream()
            .forEach(f -> printer(otoa(f), 1));
    }

    public static void printer(Object[] o, final int indent) {
        switch((String)o[0]) {
            case "FUNC":
                final StringBuilder sB = new StringBuilder();
                ((List<String>)(o[2])).stream()
                    .forEach(a -> sB.append(a + ","));
                indentEmit("FUNC " + o[1] + "(" + sB.toString() + ")", indent);
                Stream.of(otoa(o[3]))
                    .forEach(e -> printer(otoa(e), indent+1));
                break; 
            case "STORE":
                indentEmit("STORE", indent);
                printer(otoa(o[2]), indent+1);
                break;
            case "FETCH":
                indentEmit("FETCH: " + o[1], indent);                
                break;
            case "LITERAL":
                indentEmit("LITERAL: " + o[1], indent);    
                break;
            case "VAR":
                indentEmit("VAR: " + o[1], indent);
                printer(otoa(o[2]), indent+1);
                break;
            case "RETURN":
                indentEmit("RETURN", indent);
                printer(otoa(o[1]), indent+1);
                break;
            case "CALL":
                //binop
                indentEmit("CALL -> " + o[1], indent);                
                if (o.length==4) {
                    printer(otoa(o[2]), indent+1);
                    printer(otoa(o[3]), indent+1);
                }
                break;
            case "IF":
                indentEmit("IF", indent);
                printer(otoa(o[1]), indent+1);
                indentEmit("THEN", indent);
                Stream.of(otoa(o[2]))
                    .forEach(e -> printer(otoa(e), indent+1));
                break;
            default: 
                indentEmit("No idea what this is", indent);
                break;
        }
    }


}
