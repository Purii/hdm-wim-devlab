# Sematische Representation von Dokumenen 
Ziel dieses Projektes ist es mittels einer Ontolofie Google Drive Dokumente zu representieren.
Dies wurde mittels einer Ontologie realisiert. Mit APche Jena wurden Methoden 
geschrieben mit dieser über den Apache Fuseki Server zu kommunizieren. Weiter 
kommuniziert das Google Drive Addin mit der Ontologie um neue Dokumente 
einzufügen und zu manipulieren.  


#Apache Jena 

Die Realisierung der Gruppe: `Semantische Represenation von Dokumennte` 
wird mit [`Apache Jena`](https://jena.apache.org/). Die `Jena-Services` sind über das REST-Endpunkte  für `POST` und `GET` Anfragen für externe Komponenten erreichbar. Diese Komponente bietet Inferenzmechanismen, mit denen aus gegeben Keywörtern, Dokumentenvorschläge schlussfolgern kann. Realisiert wurde die REST-Enpunkte auf der Google App Eninge und mit dem Java Framework Jersey. 

#Ontologie

Für die persistente Speicherung wurde der [`Fuseki Server`](https://jena.apache.org/documentation/fuseki2/)verwendet.
Dieser wird bei uns auf der Compute Engine unter der IP: [`35.187.45.171`] betrieben.  


#Google Drive Addin mit Google App Script 
Mit dem Google Addin können GoogleDrive Dokuemnte Metadaten hinterlegt werden. 
Diese kann der Nutzer über eine grafische Oberfläche dem Dokument hinzufügen. 
Wie Sie das Google Drive Addin hinzufügen und benutzen können erfahren Sie [`hier`](https://github.com/Purii/hdm-wim-devlab/tree/master/SemRep/Google-AppScipt)
 

