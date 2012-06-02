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
dexgsh $ v2 = gdb.newNodePeople(name:"joel", eyes:"blue")
>People[1025]{eyes=blue, name=joel}
dexgsh $ e1 = gdb.newEdgeParent(v1, v2, since:"2012")
>Parent[2048|1024->1025]{since=2012}
dexgsh $ gdb.selectPeople()
>People[1024]{age=32, eyes=brown, name=sergio}
>People[1025]{eyes=blue, name=joel}
dexgsh $ gdb.selectPeople().grep{it.name.startsWith("s")}
>People[1024]{age=32, eyes=brown, name=sergio}
dexgsh $ gdb.close()
```
