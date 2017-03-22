# Starting application
1. Install gradle:

https://gradle.org/install

2. Install solRDF

https://github.com/agazzarini/SolRDF

3. Run solr:
Run in cmd
```
cd to solr bin folder
./solr -p 8080 -s $SOLR_HOME -a "-Dsolr.data.dir=./work/data/solrdf"
```
$SOLR_HOME is path to /tmp/solr/solrdf-download/solrdf/solrdf-integration-tests/target/solrdf-integration-tests-1.1-dev/solrdf

3. Go to main project folder and run:
```
gradle bootRun
```



# Deprecated additional installation
## Fuseki (Apache Jena triple store installation)
https://www.youtube.com/watch?v=ISBkB9j4a00

### Note: need to create folder /Data in fuseki main folder

### Running
Run in cmd (Git bash fails if Windows 7):
```
java -Xmx1200M -jar fuseki-server.jar --update --loc=Data /data
```
