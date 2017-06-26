# hdm-wim-devlab

*Hinweis: Bei diesem Projekt handelt es sich um ein Forschungsprojekt der Hochschule der Medien, Stuttgart.*

* [Schnellzugriffe](#schnellzugriffe)
* [Spielregeln](#spielregeln)
* [Google PubSub](#google-pubsub)
    * [Grundlegende Kommunikation](#grundlegende-kommunikation)
    * [Aktuell existierende Topics](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Topics.md)
    
    
## Schnellzugriffe
        
* [Verfügbare Events](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md)
* [Verfügbare Topics](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Topics.md)
* [Erläuterungen zur Nutzung von PubSub](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/PubSub.md)

## Spielregeln

* Jede Gruppe hat ein eigenes Verzeichnis.
* Jede Gruppe stellt einen oder mehrere Microservices bereit. Jede Gruppe ist damit selbst für die eigene Systemumgebung verantwortlich.
* Entwickelt wird eine Event Driven Architecture. Die Kommunikation findet über Messages bzw. Events statt.
* Diese Events werden mittels [Google PubSub](https://cloud.google.com/pubsub/docs/overview) übermittelt.

## Google PubSub

Für die Kommunikation zwischen den Modulen wird Google PubSub genutzt.
Über PubSub werden `Messages` versendet. Wir sprechend jedoch häufiger von `Events`.
Betrachtet die `Messages` als eine Art Kapsel für ein `Event` bzw. Synonym zueinander.

* Zugriff auf die PubSub, also Funktionen für das Senden & Empfangen kann über diesen [GitHub Issue](https://github.com/Purii/hdm-wim-devlab/issues/4) beantragt werden (Google Account benötigt).
* Die verfügbaren Topics (Kommunikationskanäle) werden über die [`sharedLib`](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Topics.md) bereitgestellt. Näheres zur Verwendung findet Ihr in der [Dokumentation](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/PubSub.md#google-cloud#L36). Werden zusätzliche Topics benötigt, können diese über einen neuen [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) angefragt werden.
* Als Teil der `sharedLib` werden Euch Klassen zur Verfügung gestellt, um Events empfangen und senden (publish & pull) zu können. Falls darüber hinaus weitere Informationen benötigt werden, kann die [ausführliche offizielle Dokumentation](https://cloud.google.com/pubsub/docs/reference/libraries) von Google PubSub genutzt werden. Die Dokumentation der SDK findet sich [hier](http://googlecloudplatform.github.io/google-cloud-java/0.18.0/apidocs/index.html) (Package: com.google.cloud.pubsub.spi.v1). Idealerweise werden Events jedoch nicht über Pull periodisch abgefragt, sondern werden von einem PubSub-Server via Push an den Empfänger gesendet (Jede Gruppe hat dazu einen individuellen Workshop erhalten).

### Grundlegende Kommunikation
* Publish Workflow
![Publish Workflow](https://github.com/Purii/hdm-wim-devlab/blob/master/assets/Publish.PNG)

* Subscribe Workflow
![Subscribe Workflow](https://github.com/Purii/hdm-wim-devlab/blob/master/assets/Subscribe1.PNG)

* **AppEngine:** PaaS. Publisher und Subscriber werden automatisch erstellt (wird durch Event-Gruppe zur Verfügung gestellt), Publisher kreieren eine PubSubMessage und versenden diese über PubSub im angegebenen Topic.
* **PubSub:** verteilt PubSubMessages durch die Topics.
* **Publish:** Eine Message wird in das eingetragene Topic veröffentlicht.
* **Subscribe:** Mehrere PubSubMessages werden aus dem eingetragenen Topic als Stream übertragen.

***Bitte die Vorgaben für die Klassen aus der SharedLib einhalten!***

***Topics können in den Issues beantragt werden!***
