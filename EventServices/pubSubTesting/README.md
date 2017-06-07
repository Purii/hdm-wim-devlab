# pubSubTesting

Webapp um Messages zu publishen oder via push zu empfangen.

## Run
- im Verzeichnis pubSubTesting `mvn clean package` ausführen.
- im selben Verzeichnis `mvn jetty:run` um die app lokal zu deployen. 
- Um die app auf der `appengine` zu deployen, im Verzeichnis der `app.yaml` mit der `gcloud` Konsole den Befehl `mvn appengine:deploy` ausführen.
