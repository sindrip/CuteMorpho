package ast;

public class StringContainerNode extends Node {

    private String val;

    public StringContainerNode(String s) {
        this.val = s;
    }

    public String val() {
        return this.val;
    }
}