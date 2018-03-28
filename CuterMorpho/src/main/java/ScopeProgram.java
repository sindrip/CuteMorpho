import java.security.cert.CertPathChecker;
import java.util.Vector;
import java.util.stream.Stream;

public class ScopeProgram {

    private ScopeStack env;
    private Vector<Object> program;

    public ScopeProgram(Vector<Object> program) {
        this.program = program;
        this.env = new ScopeStack();
    }

    // UTIL START
    public Object[] otoa(Object objToObjArray) {
        return (Object[]) objToObjArray;
    }
    // UTIL END

    private void createGlobalScope() {
        env.newScope();
        this.program.stream()
            .forEach(f -> {
                String n = String.valueOf(otoa(f)[0]);
                if (n.equals("VARDECL")) {
                    try {
                        otoa(f)[1] = env.addVar(String.valueOf(otoa(f)[1]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
    }

    private void createScopes(Object[] o) {
        switch((String)o[0]) {
            case "VARDECL":
                try {
                    o[1] = env.addVar(String.valueOf(o[1]));
                    createScopes(otoa(o[2]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);       
                }
            break;
            case "ASSIGN":
                try {
                    o[1] = env.getVar(String.valueOf(o[1]));
                    createScopes(otoa(o[2]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);    
                }
            break;
            case "RETURN":
                createScopes(otoa(o[1]));
            break;
            case "LITERAL":
                // Do nothing
            break;
            case "FETCH":
                try {
                    o[1] = env.getVar(String.valueOf(o[1]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);  
                }
            break;
            case "CALL":
                Stream.of(otoa(o[2]))
                .forEach(v -> {
                    try {
                        createScopes(otoa(v));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);                            
                    }
                });
            break;
            case "BODY":
                env.newScope();
                Stream.of(otoa(o[1]))
                    .forEach(v -> {
                        try {
                            createScopes(otoa(v));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(-1);                            
                        }
                    });
                env.exitScope();
            break;
            case "UNSCOPEDBODY":
                o[0] = "BODY";
                Stream.of(otoa(o[1]))
                    .forEach(v -> {
                        try {
                            createScopes(otoa(v));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(-1);                            
                        }
                    });
            break;
            case "MAKECLOSURE":
                int upperVars = env.getVarCounter();
                env.newScope();
                Stream.of(otoa(o[1]))
                    .forEach(a -> {
                        try {
                            env.addVar((String)a);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(-1);
                        }
                    });
                createScopes(otoa(o[2]));
                o[3] = upperVars; 
                env.exitScope();
            break;
            case "UNKNOWN_CALL":
                try {
                    int pos = env.getVar(String.valueOf(o[1]));
                    o[0] = "CALLCLOSURE";
                    o[1] = pos;
                } catch (Exception e) {
                    o[0] = "CALL";
                }
                Stream.of(otoa(o[2]))
                    .forEach(v -> {
                        try {
                            createScopes(otoa(v));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(-1);                            
                        }
                });
            break;
            case "IF1":
                createScopes(otoa(o[1]));
                createScopes(otoa(o[2]));                
            break;
            case "IF2":
                createScopes(otoa(o[1]));
                createScopes(otoa(o[2]));   
                createScopes(otoa(o[3]));
            break;
            case "WHILE":
                createScopes(otoa(o[1]));
                createScopes(otoa(o[2]));                
            break;
            case "AND":
                createScopes(otoa(o[1]));
                createScopes(otoa(o[2]));                
            break;
            case "OR":
                createScopes(otoa(o[1]));
                createScopes(otoa(o[2]));  
            break;
            case "NOT":
                createScopes(otoa(o[1]));
            break;
            default:
                System.out.println("Don't know what this is: " + o[0]);
            break;
        }
    }
    
    /***
     * Convert names in the AST to integer ID's
    ***/
    public void scope() {
        env.newScope();
        this.program.stream()
        .forEach(f -> {
            try {
                createScopes(otoa(f));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }           
        });   
        env.exitScope(); 
    }
}