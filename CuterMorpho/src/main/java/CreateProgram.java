import java.util.Vector;
import java.util.stream.Stream;

public class CreateProgram {

    private int lab;
    private Vector<Object> program;

    public CreateProgram(Vector<Object> program) {
        this.program = program;
        lab = 0;
    }
    // UTIL START
    private void emit(String s) {
        System.out.println(s);
    }
    public Object[] otoa(Object objToObjArray) {
        return (Object[]) objToObjArray;
    }
    // UTIL END

    private void generateProgram() {
        emit("\"test.mexe\" = main in");
        emit("!{{");
        emit("#\"main[f0]\" =");
        emit("[");
        
        //Need null values on the stack for making a closure
        emit("(MakeVal null)");
        for(int i = 0; i < this.program.size()-1; i++) {
            emit("(Push)");
        }
        
        this.program.stream()
            .forEach(e -> {
                generateExpr(otoa(e));
            });

        emit("(Return)");
        emit("];");
        emit("}}*BASIS;");
    }

    private void generateExpr(Object[] e) {
        switch((String)e[0]) {
            case "VARDECL":
                generateExpr(otoa(e[2]));
                emit("(Store "+e[1]+")");
            break;
            case "ASSIGN":
                generateExpr(otoa(e[2]));
                emit("(Store "+e[1]+")");
            break;
            case "RETURN":
                generateExpr(otoa(e[1]));
                emit("(Return)");
            break;
            case "LITERAL":
                emit("(MakeVal "+e[1]+")");
            break;
            case "FETCH":
                emit("(Fetch "+e[1]+")");
            break;
            // fra snorra
            case "CALL":
                Object[] args = otoa(e[2]);
                if( args.length!=0 ) generateExpr((Object[])args[0]);
                for(int i=1; i<args.length; i++){
                    emit("(Push)");
                    generateExpr((Object[])args[i]);
                }
                emit("(Call #\""+e[1]+"[f"+args.length+"]\" "+args.length+")");
            break;
            case "CALLCLOSURE":
                emit("(Fetch "+e[1]+")");
                Object[] ar = otoa(e[2]);
                for(int i=0; i<ar.length; i++){
                    emit("(Push)");
                    generateExpr((Object[])ar[i]);
                }
                emit("(CallClosure "+ar.length+")");
            break;
            case "MAKECLOSURE":
                int labend = this.lab++;             
                emit("(MakeClosure 0 "+otoa(e[1]).length+" "+e[3] + " _L"+labend+")");
                generateExpr(otoa(e[2]));
                emit("_L"+labend+":");
            break;
            case "BODY":
                generateBody(otoa(e[1]));
            break;

            default:
                emit("      UNKNOWN: " + e[0]);
            break;
        }
    }

    private void generateBody(Object[] bod) {
        for( Object e: bod ) {
            generateExpr(otoa(e));
        }
    }

    public void printMexe() {
        generateProgram();
    }
}