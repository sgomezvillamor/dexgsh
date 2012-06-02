package com.sparsity.dex.groovy;

class Edge extends Object {

    // Read-only properties

    final long tail
    
    final long head
    
    public Edge(Graph sg, long oid) {
        super(sg, oid)
        com.sparsity.dex.gdb.EdgeData edata = sg.graph.getEdgeData(oid)
        tail = edata.tail
        head = edata.head
    }

    def String toString() {
        StringBuffer sb = new StringBuffer()
        sb << type
        sb << "[" << oid << "|" << tail << "->" << head << "]"
        sb << attrs
        sb.toString()
    }
}
