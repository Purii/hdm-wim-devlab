# Ontologie

In diesem Ordner befindet sich die Ontologie. 
Die Ontologie repräesetiert Dokumente aus GoogleDrive 
und Objekte in Zusammenhang mit Dokumenten stehen. 
Wie zum Beispiel: Person. Ontologie bestehen oftmals aus einer 
A-Box und einer T-Box. Diese enthalten die Beschreibungslogik 
häufig in form von Tripple-Notation  (Subjekt, Objekt, Relation). 
OntologieNotoationen sind: Turle, RDF, etc.

<b>T-Box-Statements</b>
```
T-Box-Anweisungen beschreiben ein System in Bezug 
auf kontrolliertes Vokabular, dass zum Beispiel eine Reihe von 
Klassen und Eigenschaften besteht.
```
<b>A-Box-Statements</b>
```
In der ABox sind TBox-konforme Aussagen über dieses Vokabular.

```
Weitere Informationen zur Beschreibungslogik [`hier`](https://de.wikipedia.org/wiki/Beschreibungslogik) 


<b>Ontologie Modelling</b>

[`Protegé`](http://protege.stanford.edu/)ist ein Ontologie-Modellierungstool, 
mit dem Ontologien entwickelt und bearbeitet werden können.
In der Ontologie ist die A-Box,sowie die T-Box enthalten.
In Protegé können Sie diese Ontologie einsehen. Wie Sie in Protege eine 
Ontologie in Protegé importieren erfahren Sie [`hier`](https://protegewiki.stanford.edu/wiki/Importing_Ontologies_in_P41)
Diese Ontologie enthält 8 Wurzelknoten: Dokuemnt, Person, Unternehmen, 
Abteilung, Geoinformationen. Insgesamt fasst diese Ontologie YXZ Triples. 


#Fuseki Server 

Der Apache [`Fuseki Server`](https://jena.apache.org/documentation/fuseki2/)ist Teil des Apache Jena Frameworks. 
Der Server ist ein RDF-Storage Server, durch den 
Ontologien verwaltet werden können. Der Fuseki Server bietet durch 
GET und POST Statements wege Ontologie auszulesen und 
durch insert, update und delete sparql Querys zu manipulieren. 
Der Fuseki Server ist in unserem Beispiel auf einer Google Compute 
Engine Instanz gehostet.  <br> <br>

<b> Fuseki Standard Endpoints </b> <br><br>

Durch die Standart E-Points kann auf die importierte Ontologie zugegriffen werden. 
Wie Sie den Fuseki Server installieren und ausführen erfahren Sie hier.

```
http://*host*/dataset/query -- the SPARQL query endpoint.
http://*host*/dataset/update -- the SPARQL Update language endpoint.
http://*host*/dataset/data -- the SPARQL Graph Store Protocol endpoint.
http://*host*/dataset/upload -- the file upload endpoint.
```



<br>


 
<b> Fuseki Installation </b> <br><br>

Vorraussetzungen für die Installation des Fuseki Servers ist Java 8 JDK.
Der Fusksekiserver bietet eine GUI verschiedene Datastores in denen
die Ontologie abgelegt ist zu verwalten. Der Fusekiserver bei Ausführung under 
localhost:3030 in Ihrem Webrowser erreichbar. Lokal betrieben, wird die 
GUI geladen, auf einem Liveserver muss die Shiro.ini angepasst und eine 
Konfiurationsdatei hinterlegt werden. 

Download Fuseki (Linux):<br>
https://jena.apache.org/download/#jena-fuseki
```
# Download the latest jena-fuseki-*-distribution -curl/wget
# tar zxfv
# chmod +x fuseki-server s-* 

```
<br>

Run Fuseki:<br>https://jena.apache.org/documentation/fuseki2/fuseki-run.html

 



 
 