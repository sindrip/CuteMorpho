package ast;

import java.util.*;

public class RootNode extends Node {

    public List<Node> nodes;

    public RootNode() {
        this.nodes = new ArrayList<>();
    }

    public RootNode(RootNode n1, Node n2) {
        this.nodes = n1.add(n2);
    }

    public List<Node> add(Node n) {
        this.nodes.add(n);
        return this.nodes;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }
}