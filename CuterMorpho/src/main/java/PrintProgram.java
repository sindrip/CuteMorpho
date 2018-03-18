import java.util.Vector;
import java.util.stream.Stream;

public class PrintProgram {

    private Vector<Object> program;

    public PrintProgram(Vector<Object> program) {
        this.program = program;
    }

    // UTIL START
    private void emit(String s) {
        System.out.println(s);
    }
    private void indentEmit(String s, int indent) {
        int len = indent + s.length();
        emit(String.format("%"+len+"s", s));
    }
    public Object[] otoa(Object objToObjArray) {
        return (Object[]) objToObjArray;
    }
    // UTIL END


    private void printer(Object[] o, final int indent) {
        switch((String)o[0]) {
            case "VARDECL":
                indentEmit("VARDECL: " + o[1], indent);
                printer(otoa(o[2]), indent+1);
            break;
            case "ASSIGN":
                indentEmit("ASSIGN: " + o[1], indent);
                printer(otoa(o[2]), indent+1);
            break;
            case "RETURN":
                indentEmit("RETURN", indent);
                printer(otoa(o[1]), indent+1);
            break;
            case "LITERAL":
                indentEmit("LITERAL: " + o[1], indent);
            break;
            case "FETCH":
                indentEmit("FETCH: " + o[1], indent);
            break;
            case "CALL":
                indentEmit("CALL: " + o[1], indent);
                Stream.of(otoa(o[2]))
                .forEach(v -> {
                    printer(otoa(v), indent+1);
                });
            break;
            case "CALLCLOSURE":
                indentEmit("CALLCLOSURE: " + o[1], indent);
                Stream.of(otoa(o[2]))
                .forEach(v -> {
                    printer(otoa(v), indent+1);
                });
            break;
            case "BODY":
                indentEmit("BODY", indent);
                Stream.of(otoa(o[1]))
                    .forEach(v -> {
                        printer(otoa(v), indent+1);
                    });
            break;
            case "UNSCOPEDBODY":
                indentEmit("UNSCOPEDBODY", indent);
                Stream.of(otoa(o[1]))
                    .forEach(v -> {
                        printer(otoa(v), indent+1);
                    });
            break;
            case "MAKECLOSURE":
                indentEmit("MAKECLOSURE", indent);
                Stream.of(otoa(o[1]))
                    .forEach(v -> {
                        indentEmit("arg: " + (String)v, indent);
                    });
                printer(otoa(o[2]), indent+1);
            break;
            default:
                emit("Don't know what this is: " + o[0]);
            break;
        }
    }

    public void print() {
        emit("PROGRAM STARTS HERE");
        this.program.stream()
        .forEach(f -> printer(otoa(f), 1));
    }
}