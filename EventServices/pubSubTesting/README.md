# pubSubTesting

Webapp um Messages zu publishen oder via push zu empfangen.

## Run
- SharedLib im lokalen Maven repository installieren, dazu im SharedLib Verzeichnis `mvn clean install` ausführen
- im Verzeichnis pubSubTesting `mvn clean package` ausführen.
- im selben Verzeichnis `mvn jetty:run` ausführen, um die app lokal zu deployen. 

Wenn die app lokal läuft, im Browser `localhost:8080` eingeben und man kommt auf die app.
Dort können zum testen messages an verschiedene topics abgeschickt werden.