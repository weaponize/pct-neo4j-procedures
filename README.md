# PCT Neo4j Stored Procesdures

This is a repo for some stored procedure stuff for the Neo4j graph database. These plugins are 
mainly build to look at graphs that represent trees. All nodes have a relationship of type Child
relating a parent to it's many children. There are other relationships as well, so any copying of 
this code will need modification for your graph relationships. There are multitudes of node labels
in my data, so some of those might get hardcoded here somewhere.

The idea here is that you have a template for matching nodes or paths based on some criteria.
The first commit here is pct.FilterNodeByLabel() -- this is essentially a specialized form of
the `WHERE` clause.

## Calling from Cypher queries

```
// Calling FilterNodeByLabel
// This will match all nodes according to the first label (type) 
// of the 101st node in the database
match (f) with f skip 100 limit 1
match (n) with f,n limit 100000
call pct.FilterNodeByLabel(n,labels(f)[0]) yield result
return result
```


## Based on Neo4j Examples

The skeleton for this project came from Neo4j. This repo is a place to store some of the things I'm doing with graphs. They may or may not be applicable to your shit.

Forked from https://github.com/neo4j-examples/neo4j-procedure-template/blob/master/src/main/java/example/FullTextIndex.java[`src/main/java/example/FullTextIndex.java`] and the https://github.com/neo4j-examples/neo4j-procedure-template/blob/master/src/test/java/example/LegacyFullTextIndexTest.java[test code] including Test-Server-Setup.

## Building

This project uses maven, to build a jar-file with the procedure in this
project, simply package the project with maven:

    mvn clean package

This will produce a jar-file,`target/procedure-template-1.0.0-SNAPSHOT.jar`,
that can be deployed in the `plugin` directory of your Neo4j instance.

## License

Various. Anything here is free as in beer. I won't commit the secret sauce :)
