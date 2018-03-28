import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.*;

public class ScopeStack {

    private Vector<HashMap<String,Integer>> scopes;
    private HashMap<String, Integer> currentScope;
    private int varCounter;

    public ScopeStack() {
        this.scopes = new Vector<>();
        this.currentScope = new HashMap<>();
        this.varCounter = 0;
    }

    public int getVarCounter() {
        return this.varCounter;
    }

    public void newScope() {
        scopes.add(new HashMap<>());
        currentScope = scopes.lastElement();
    }

    public void exitScope(){
        int size = currentScope.size();
        varCounter -= size;

        scopes.remove(scopes.size()-1);

        if (scopes.size() == 0) {
            currentScope = null;
        } else {
            currentScope = scopes.lastElement();
        }
    }

    private boolean varFreeInScope(String name) {
        Integer v;
        v = currentScope.get(name);

        if (v == null) {
            return true;
        }
        return false;
    }

    public int addVar(String name) throws Exception {
        if (varFreeInScope(name)) {
            int ret = varCounter;
            currentScope.put(name, varCounter++);
            return ret;
        } 

        throw new Exception(name + " already declared in scope");
    }

    public int getVar(String name) throws Exception {
        Integer pos;
        for(int i = scopes.size()-1; i >= 0; i--) {
            pos = scopes.get(i).get(name);
            if (pos != null) {
                return pos;
            }
        }

        throw new Exception(name + " not declared in scope");
    }
}