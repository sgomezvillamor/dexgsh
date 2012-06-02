package com.sparsity.dex.groovy;

class Node extends Object {
    public Node(Graph sg, long oid) {
        super(sg, oid)
    }
    
    def String toString() {
        StringBuffer sb = new StringBuffer()
        sb << type
        sb << "[" << oid << "]"
        sb << attrs
        sb.toString()
    }
}
