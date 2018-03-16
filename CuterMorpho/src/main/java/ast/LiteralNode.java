package ast;

public class LiteralNode extends Node {

    String val;

    public LiteralNode(StringContainerNode sn) {
        this.val = sn.val();
    };

    public String val() {
        return this.val;
    }
}