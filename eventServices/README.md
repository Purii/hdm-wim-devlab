# Event Gruppe

Als Framework zur Realisierung von `CEP` nutzt die Gruppe [`Apache Flink`](https://protegewiki.stanford.edu/wiki/Importing_Ontologies_in_P41). Für `Flink` wird ein [Docker Image](https://flink.apache.org/news/2017/05/16/official-docker-image.html) angeboten, welches von der Community bereitgestellt und von der `Apache Flink PMC` beworben wird.

```
# Ausgabe der Logs
docker run -t -p 8081:8081 flink local

# Detached mode
docker run -d -p 8081:8081 flink local
```

## Run

damit `RunFlink` ausgeführt werden kann, muss vorher das die jar `sharedLib` mit `mvn clean install` gebaut werden. Der command muss in dem Verzeichnis ausgeführt werden. Danach im Verzeichnis `eventProcessing` `mvn clean package` ausführen und dann `RunFlink`ausführen.
