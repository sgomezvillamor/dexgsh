dexgsh
======

Dex groovy shell

Example

```
Welcome to the Dex groovy-based shell
-------------------------------------
dexgsh $ gdb = Dex.create("GDB.dex", "GDB")
>DexGDB['GDB'@'GDB.dex']
dexgsh $ v1 = gdb.newNodePeople(name:"sergio", age:"32")
>People[1024]{age=32, name=sergio}
dexgsh $ v1.oid
>1024
dexgsh $ v1.type
>People
dexgsh $ v1.typeId
>2
dexgsh $ v1.name
>sergio
dexgsh $ v1.age
>32
dexgsh $ v1.eyes
Unnexisting attribute eyes
Stack trace? [yN] 
dexgsh $ v1.attrs
>age=32
>name=sergio
dexgsh $ v1.eyes = "brown"
>brown
dexgsh $ v1.attrs
>age=32
>eyes=brown
>name=sergio
dexgsh $ 
dexgsh $ 
dexgsh $ 
dexgsh $ v2 = gdb.newNodePeople(name:"joel", eyes:"blue")
>People[1025]{eyes=blue, name=joel}
dexgsh $ e1 = gdb.newEdgeParent(v1, v2, since:"2012")
>Parent[2048|1024->1025]{since=2012}
dexgsh $ gdb.selectPeople()
>People[1024]{age=32, eyes=brown, name=sergio}
>People[1025]{eyes=blue, name=joel}
dexgsh $ gdb.selectPeople().grep{it.name.startsWith("s")}
>People[1024]{age=32, eyes=brown, name=sergio}
dexgsh $ 
dexgsh $ 
dexgsh $ 
dexgsh $ gdb.newNodeX(a:"1", b:"2", c:"3")
>X[1024]{a=1, b=2, c=3}
dexgsh $ gdb.newNodeX(a:"1", b:"2")
>X[1025]{a=1, b=2}
dexgsh $ gdb.newNodeX(a:"1")
>X[1026]{a=1}
dexgsh $ gdb.selectX(a:"1")
>X[1024]{a=1, b=2, c=3}
>X[1025]{a=1, b=2}
>X[1026]{a=1}
dexgsh $ gdb.selectX(a:"1", b:"2")
>X[1024]{a=1, b=2, c=3}
>X[1025]{a=1, b=2}
dexgsh $ gdb.selectX(a:"1", b:"2", c:"3")
>X[1024]{a=1, b=2, c=3}
dexgsh $ gdb.selectX(a:"1", b:"2", c:"3", d:"4")
dexgsh $ gdb.selectX(d:"4")
dexgsh $ gdb.selectX()
>X[1024]{a=1, b=2, c=3}
>X[1025]{a=1, b=2}
>X[1026]{a=1}
dexgsh $ 
dexgsh $ 
dexgsh $ gdb.selectX(b:"2").collect{it.a}
>1
>1
dexgsh $ gdb.selectX(b:"2").collect{it.oid}
>1024
>1025
dexgsh $ 
dexgsh $ 
dexgsh $ gdb.close()
```
