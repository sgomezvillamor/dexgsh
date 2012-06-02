package com.sparsity.dex.groovy;

class MetaDex {

    public static void ini() {
        com.sparsity.dex.gdb.Dex.metaClass.static.create = { gdb, alias ->
            def sg = new Graph()
            sg.config = new com.sparsity.dex.gdb.DexConfig()
            sg.dex = new com.sparsity.dex.gdb.Dex(sg.config)
            sg.db = sg.dex.create(gdb, alias)
            sg.sess = sg.db.newSession()
            sg.graph = sg.sess.getGraph()
            sg
        }

        com.sparsity.dex.gdb.Dex.metaClass.static.use = { gdb ->
            def sg = new Graph()
            sg.config = new com.sparsity.dex.gdb.DexConfig()
            sg.dex = new com.sparsity.dex.gdb.Dex(sg.config)
            sg.db = sg.dex.open(gdb, false)
            sg.sess = sg.db.newSession()
            sg.graph = sg.sess.getGraph()
            sg
        }
    }
}