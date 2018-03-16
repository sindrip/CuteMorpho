package ast;

public class VarNode extends Node {

    String val;

    public VarNode(StringContainerNode sn) {
        this.val = sn.val();
    }
}