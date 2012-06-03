package com.sparsity.dex.groovy;

class Graph {
    com.sparsity.dex.gdb.DexConfig config
    com.sparsity.dex.gdb.Dex dex
    com.sparsity.dex.gdb.Database db
    com.sparsity.dex.gdb.Session sess
    com.sparsity.dex.gdb.Graph graph

    def collections = []

    def close() {
        collections.each {
            it.objs.close()
        }

        graph.dumpData(db.getAlias() + ".data")
        graph.dumpStorage(db.getAlias() + ".storage")

        sess.close()
        db.close()
        dex.close()
        graph = null
        sess = null
        db = null
        dex = null
        config = null
    }

    def String toString() {
        new String("DexGDB['${db.getAlias()}'@'${db.getPath()}']")
    }

    def Object get(long id) {
        int type = graph.getObjectType(id)
        if (type == com.sparsity.dex.gdb.Type.InvalidType)
            throw new IllegalArgumentException("Invalid OID $id")
        com.sparsity.dex.gdb.Type tdata = graph.getType(type)
        if (tdata.objectType == com.sparsity.dex.gdb.ObjectType.Node)
            new Node(this, id)
        else
            new Edge(this, id)
    }

    def methodMissing(String name, args) {
        if (name.startsWith("newNode")) {
            String nodetype = name.replaceFirst("newNode", "");
            int t = graph.findType(nodetype)
            if (t == com.sparsity.dex.gdb.Type.InvalidType) t = graph.newNodeType(nodetype)
            def oid = graph.newNode(t)
            if (args.size() == 1) {
                com.sparsity.dex.gdb.Value v = new com.sparsity.dex.gdb.Value()
                args[0].each { attrname, value ->
                    int attr = graph.findAttribute(t, attrname)
                    if (attr == com.sparsity.dex.gdb.Attribute.InvalidAttribute)
                        attr = graph.newAttribute(t, attrname,
                                com.sparsity.dex.gdb.DataType.String,
                                com.sparsity.dex.gdb.AttributeKind.Indexed)
                    graph.setAttribute(oid, attr, v.setString(value))
                }
            }
            return new Node(this, oid)
        } else if (name.startsWith("newEdge")) {
            String edgetype = name.replaceFirst("newEdge", "");
            int t = graph.findType(edgetype)
            if (t == com.sparsity.dex.gdb.Type.InvalidType) t = graph.newEdgeType(edgetype, true, true)
            def oid = com.sparsity.dex.gdb.Objects.InvalidOID;
            // I don't understand the order of the args :-S
            if (args.size() == 3) {
                Node source = args[1];
                Node target = args[2];
                oid = graph.newEdge(t, source.oid, target.oid)
                com.sparsity.dex.gdb.Value v = new com.sparsity.dex.gdb.Value()
                args[0].each { attrname, value ->
                    int attr = graph.findAttribute(t, attrname)
                    if (attr == com.sparsity.dex.gdb.Attribute.InvalidAttribute)
                        attr = graph.newAttribute(t, attrname,
                                com.sparsity.dex.gdb.DataType.String,
                                com.sparsity.dex.gdb.AttributeKind.Indexed)
                    graph.setAttribute(oid, attr, v.setString(value))
                }
            } else {
                Node source = args[0]
                Node target = args[1]
                oid = graph.newEdge(t, source.oid, target.oid)
            }
            return new Edge(this, oid)
        } else if (name.startsWith("select")) {
            String type = name.replaceFirst("select", "");
            int t = graph.findType(type)
            if (t == com.sparsity.dex.gdb.Type.InvalidType)
                throw new IllegalArgumentException("Undefined type $type")

            com.sparsity.dex.gdb.Type tdata = graph.getType(t)
            boolean nodes = tdata.getObjectType() == com.sparsity.dex.gdb.ObjectType.Node

            if (args.size() == 0) {
                if (nodes)
                    collections << new Objects<Node>(this, graph.select(t), Node.class)
                else
                    collections << new Objects<Edge>(this, graph.select(t), Edge.class)

                collections[collections.size()-1]
            } else if (args.size() == 1) {
                def aux = sess.newObjects()
                def v = new com.sparsity.dex.gdb.Value()
                boolean first = true
                args[0].each { attrname, value ->
                    int attr = graph.findAttribute(t, attrname)
                    if (attr == com.sparsity.dex.gdb.Attribute.InvalidAttribute) {
                        attr = graph.findAttribute(com.sparsity.dex.gdb.Type.GlobalType, attrname)
                    }
                    if (attr != com.sparsity.dex.gdb.Attribute.InvalidAttribute) {
                        def current = graph.select(attr, com.sparsity.dex.gdb.Condition.Equal, v.setString(value.toString()))
                        if (first) aux.copy(current)
                        else aux.intersection(current)
                        current.close()
                    } else {
                        aux.close()
                        aux = sess.newObjects()
                    }
                    first = false
                }

                if (nodes)
                    collections << new Objects<Node>(this, aux, Node.class)
                else
                    collections << new Objects<Edge>(this, aux, Edge.class)

                collections[collections.size()-1]
            }

        } else {
            println "method missing called! $name"
        }
    }
}