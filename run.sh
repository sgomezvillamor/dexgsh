mvn clean install

java -cp target/dexgsh-0.0.1-SNAPSHOT.jar:$HOME/.m2/repository/com/sparsity/dexjava/4.5.1/dexjava-4.5.1.jar:$HOME/.m2/repository/jline/jline/1.0/jline-1.0.jar:$HOME/.m2/repository/org/fusesource/jansi/jansi/1.5/jansi-1.5.jar:$HOME/.m2/repository/org/codehaus/groovy/groovy-all/1.8.5/groovy-all-1.8.5.jar org.sgomezvillamor.dexgsh.Shell
