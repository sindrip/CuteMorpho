package ast;

import java.util.*;

public class RootNode extends Node {

    public List<Node> nodes;

    public RootNode() {
        this.nodes = new ArrayList<>();
    }

    public List<Node> add(Node n) {
        this.nodes.add(n);
        return this.nodes;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }
}