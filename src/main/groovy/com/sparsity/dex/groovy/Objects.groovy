package com.sparsity.dex.groovy;

class Objects<T extends Object> implements Iterable<T> {
    Graph sg
    com.sparsity.dex.gdb.Objects objs
    Class<T> clazz
    
    Iterator<T> iterator() {
        com.sparsity.dex.gdb.ObjectsIterator itt = objs.iterator()
        int i = 0
        int max = objs.size()
        return [
            hasNext: {  i < max },
            next: {
                def oid = itt.next()
                def ret = (clazz.isAssignableFrom(Node.class) ? 
                    new Node(sg, oid) : new Edge(sg, oid))
                
                if (++i >= max) {
                    itt.close()
                }
                ret
            } ] as Iterator<T>
    }
}



