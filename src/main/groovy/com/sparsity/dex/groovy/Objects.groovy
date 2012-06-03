package com.sparsity.dex.groovy;

class Objects<T extends Object> implements Iterable<T> {

    // Read-only properties
    
    final Graph sg
    final com.sparsity.dex.gdb.Objects objs
    final Class<T> clazz
    
    public Objects(sg, objs, clazz) {
        this.sg = sg
        this.objs = objs
        this.clazz = clazz
    }
    
    def long getSize() {
        objs.sizeLong()
    }
    
    def boolean isEmpty() {
        objs.isEmpty()
    }
    
    // a << b      a.leftShift(b)
    
    def leftShift(T e) {
        objs.add(e.oid)
    }
    
    def leftShift(Objects<T> ee) {
        objs.union(ee.objs)
    }
    
    // a & b        a.and(b)
    
    def and(Objects<T> ee) {
        objs.intersection(ee.objs)
    }
    
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



