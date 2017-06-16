# Sematische Representation von Dokumenen 



#Apache Jena 

Die Realisierung der Gruppe: `Semantische Represenation von Dokumennte` wird mit [`Apache Jena`](https://jena.apache.org/). Die `Jena-Services` sind über das REST-Endpunkte  für `POST` und `GET` Anfragen für externe Komponenten erreichbar. Diese Komponente bietet Inferenzmechanismen, mit denen aus gegeben Keywörtern, Dokumentenvorschläge schlussfolgern kann. Realisiert wurde die REST-Enpunkte auf der Google App Eninge und mit dem Java Framework Jersey. Im Folgenden sind alle REST-Endpunkte definiert: 

```
# Ausgabe der Logs
docker run -t -p 8081:8081 flink local

# Detached mode
docker run -d -p 8081:8081 flink local
```

#Endpoints SemRep
```
# Ausgabe der Logs
docker run -t -p 8081:8081 flink local

# Detached mode
docker run -d -p 8081:8081 flink local
```

#Ontologie
Die Daten werden in  Ontologie oder als Wissenbasis bezeichnet gespeichert. Diese besteht aus einer A-Box und einer T-Box. Die A-Box enthält Axiome, wobei die T-Box als Schablone gilt. 

#Apache Fuseki
Für die Persistente Datenspeicherung der Ontologie wird der [`Apache Jena Fuseki Server`](https://jena.apache.org/documentation/fuseki2/) verwendet. Dieser bietet viele Funktionen im Ontologien in Notationen wie RDF/XML oder Turtle an. Unter anderem die persistent Speicherung sowie die Manipulation dieser durch [`SPARQL-Queries`](https://jena.apache.org/). Der `Fuseki-Server` ist gehostet der Google Compute Engine. 

```
# run fuseki as service 
./fuseki start 

# Detached mode
docker run -d -p 8081:8081 flink local
```

#Google App Script 
Google App Script wird verwendet, um in `Google-Drive` mittels eines `Google App Script Addins` manuell eingegebene oder statisch hinterlegte Metadaten des Google Drive Dokuments abzufragen und an den `REST-S`

 

## Run

damit `RunFlink` ausgeführt werden kann, muss vorher das die jar `sharedLib` mit `mvn clean install` gebaut werden. Der command muss in dem Verzeichnis ausgeführt werden. Danach im Verzeichnis `eventProcessing` `mvn clean package` ausführen und dann `RunFlink`ausführen.
