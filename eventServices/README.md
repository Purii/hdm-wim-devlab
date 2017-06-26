# Event Gruppe

Als Framework zur Realisierung von `CEP` nutzt die Gruppe [`Apache Flink`](https://flink.apache.org/).

## Demo und Debugging

Um die Demo nachzustellen, sind folgende Schritte notwendig:

Voraussetzungen:
* Java 1.8
* Maven ^3.3.0

Beschreibung:
* Der MessageServer erzeugt einen Eventstream und sendet zufällige Events an den lokalen Port `9999`.
* Der anschließend ausgeführte Job kann diesen Stream nun benutzen.

1. Zu Sharedlib navigieren: `cd sharedLib`
2. Sharelib bauen bzw. im lokalen Mavenrepository installieren: `mvn clean package`
3. MessageServer2 starten, um Messages lokal zu senden: `de.hdm.wim.sharedLib.testing.MessageServer2.main()`
4. Zurück zu Eventprocessing navigieren: `cd ../eventServices/eventProcessing`
5. Project bauen: `mvn clean package`
6. Zu testenden Job starten: `de.hdm.wim.eventServices.eventProcessing.UserContextJob.main()`
