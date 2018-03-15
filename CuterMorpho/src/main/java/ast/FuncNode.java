package ast;

public class FuncNode extends Node {

    public Node name;

    public FuncNode(Node sn) {
        this.name = sn;
    }
}