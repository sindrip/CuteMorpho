package blocks;

import java.util.List;

public class FuncBlock extends Block {
    private String name;
    private List<String> args;
    private int vars;

    public FuncBlock(String name, List<String> args, int vars) {
        this.name = name;
        this.args = args;
        this.vars = vars;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\n" + "Argcount:" + String.valueOf(args) + 
                "\n" + "Varcount:" + String.valueOf(vars);
    }
}