# REST Jersey Projekt (Appengine Flexible)

In diesem Projekt wurde für die Präsentation
ein zweites Appengine Projekt aufgesetzt das Methodenaufrufe via REST zulässt, 
um die Machbarkeit zu beweisen. Für die REST-Integration wurde das Framework
Jersey benutzt. Dieses Projekt ist nicht mit JavaDoc kommentiert, da es großteils idetisch ist zum PubSub Projekt.

## Projekt URLs
Appengine-URL [Java Projekt]: clouddocs-internal.appspot.com
Compute-Engine-URL [Fuseki Server]: http://35.187.45.171:3030/

## Setup

Das Projekt ist ein Maven Projekt, daher benötigen muss Maven auf Ihrem
Betriebssystem installiert sein um das Projekt ausführen und folglich deployen
zu können. Alternativ können Sie Gradle nutzen.

* `gcloud init`
* `gcloud beta auth application-default login`

## Maven
### Running locally

    $ mvn clean jetty:run-exploded

### Deploying

    $ mvn appengine:deploy

## Gradle
### Running locally

    $ gradle jettyRun

If you do not have gradle installed, you can run using `./gradlew appengineRun`.

### Deploying

    $ gradle appengineDeploy

If you do not have gradle installed, you can deploy using `./gradlew appengineDeploy`.
