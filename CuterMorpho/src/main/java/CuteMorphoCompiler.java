import java.io.FileReader;
import java.io.IOException;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class CuteMorphoCompiler {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws IOException {
        Parser yyparser = new Parser(new FileReader("test.s"));
        yyparser.yyparse();        
    }
}
