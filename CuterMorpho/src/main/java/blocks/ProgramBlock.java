package blocks;

import java.util.ArrayList;
import java.util.List;

public class ProgramBlock extends Block {
    private List<Block> globalScope;

    public ProgramBlock() {
        this.globalScope = new ArrayList<>();
    }

    public void addBlock(Block b) {
        this.globalScope.add(b);
    }

    public List<Block> getProgram() {
        return this.globalScope;
    }
}