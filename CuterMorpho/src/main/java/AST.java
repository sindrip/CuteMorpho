import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.*;

public class AST {

    private Vector<Object> program;
    private Vector<HashMap<String,Integer>> scopes;
    private HashMap<String, Integer> currScope;
    private boolean newFuncScope;
    private int varCounter;

    public AST(Vector<Object> program) {
        this.program = program;
        this.scopes = new Vector<>();
        this.newFuncScope = false;
        this.varCounter = 0;
    }

    public void analysis() {
        enterScope();
        createGlobalScope();

        // Top level functions
        program.stream()
        .forEach(f -> {
            String n = String.valueOf(otoa(f)[0]);
            if (n.equals("FUNC")) {
                createVarScope(otoa(f));
            }
        });

        exitScope();
    }

    private void createGlobalScope() {
        program.stream()
            .forEach(f -> {
                String n = String.valueOf(otoa(f)[0]);
                if (n.equals("VAR")) {
                    otoa(f)[1] = addVar(String.valueOf(otoa(f)[1]));
                }
            });
    }

    private void enterScope() {
        
        if (newFuncScope) {
            newFuncScope = false;
        } else {
            emit("==ENTERING SCOPE== " + (scopes.size()+1));        
            scopes.add(new HashMap<>());
            currScope = scopes.lastElement();
        }
    }

    private void exitScope() {
        emit("==EXITTING SCOPE== " + scopes.size());
        
        int size = currScope.size();
        varCounter -= size;

        scopes.remove(scopes.size()-1);
        if (scopes.size() != 0) {
            currScope = scopes.lastElement();
        } else {
            currScope = null;
        }
    }

    private boolean varNameCurrentScopeFree(String name) {
        // endurskrifa i getelement....
        List<String> l = currScope.entrySet().stream()
            .filter(entry -> entry.getKey().equals(name))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        return l.size() == 0; 

    }

    private int addVar(String name) {
        if (varNameCurrentScopeFree(name)) {
            int ret = varCounter;
            currScope.put(name, varCounter++);
            emit(name + (varCounter-1));
            return ret;
        } else {
            emit("NAME TAKEN ERROR :(");
            return -1;
        }
    }

    private int getVar(String name) {
        Integer pos;
        for(int i = scopes.size()-1; i >= 0; i--) {
            pos = scopes.get(i).get(name);
            if (pos != null) {
                return pos;
            }
        }

        return -1;
    }

    private void emit(String s) {
        System.out.println(s);
    }

    public void globalSize() {
        emit(String.valueOf(program.size()));
    }

    private Object[] otoa(Object objToObjArray) {
        return (Object[]) objToObjArray;
    }

    public void createVarScope(Object[] o) {
        switch(String.valueOf(o[0])) {
            case "FUNC":
                enterScope();
                this.newFuncScope = true;
                emit("FUNCERONI");
                createVarScope(otoa(o[3]));
                break; 
            case "BODY":
                enterScope();
                Stream.of(otoa(o[1]))
                    .forEach(e -> createVarScope(otoa(e)));
                exitScope();
                break;
            case "VAR":
                o[1] = addVar(String.valueOf(o[1]));
                createVarScope(otoa(o[2]));
                break;
            case "FETCH":
                emit("varpos" + getVar(String.valueOf(o[1])));
                o[1] = getVar(String.valueOf(o[1]));
                break;
            case "STORE":
                o[1] = getVar(String.valueOf(o[1]));
                createVarScope(otoa(o[2]));
                break;
            case "LITERAL":
                break;
            case "RETURN":
                createVarScope(otoa(o[1]));
                break;
            case "CALL":
                if (o.length == 3) {
                    createVarScope(otoa(o[2]));
                } else {
                    createVarScope(otoa(o[2]));
                    createVarScope(otoa(o[3]));                    
                }
                break;
            case "ARGS":
                Stream.of(otoa(o[1]))
                    .forEach(e -> createVarScope(otoa(e)));
                break;
            case "IF1":
                createVarScope(otoa(o[1]));
                createVarScope(otoa(o[2]));                
                break;
            case "IF2":
                createVarScope(otoa(o[1]));
                createVarScope(otoa(o[2]));  
                createVarScope(otoa(o[3]));                  
                break;
            default:
                emit("WHAT IS THIS");
                emit("   " + o.length);
                break;
        }
    }

}