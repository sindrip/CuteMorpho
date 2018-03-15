package ast;

public interface Node {

    void Accept(Visitor visitor);
}