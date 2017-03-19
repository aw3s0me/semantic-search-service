# SolRDF
Run in cmd
```
cd to solr bin folder
./solr -p 8080 -s $SOLR_HOME -a "-Dsolr.data.dir=./work/data/solrdf"
```
$SOLR_HOME is path to /tmp/solr/solrdf-download/solrdf/solrdf-integration-tests/target/solrdf-integration-tests-1.1-dev/solrdf

# Fuseki (Apache Jena triple store installation)
https://www.youtube.com/watch?v=ISBkB9j4a00

## Note: need to create folder /Data in fuseki main folder

## Running
Run in cmd (Git bash fails if Windows 7):
```
java -Xmx1200M -jar fuseki-server.jar --update --loc=Data /data
```
