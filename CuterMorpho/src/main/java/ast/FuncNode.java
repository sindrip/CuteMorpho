package ast;

public class FuncNode extends Node {

    public String name;

    public FuncNode(StringContainerNode name) {
        this.name = name.val();
    }
}