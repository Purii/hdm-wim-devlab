# hdm-wim-devlab

*Hinweis: Bei diesem Projekt handelt es sich um ein Forschungsprojekt der Hochschule der Medien, Stuttgart.*

* [Schnellzugriffe](#schnellzugriffe)
* [Spielregeln](#spielregeln)
* [Google PubSub](#google-pubsub)
    * [Grundlegende Kommunikation](#grundlegende-kommunikation)
    * [Aktuell existierende Topics](#aktuell-existierende-topics)
    
    
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

* Zugriff auf die PubSub, also die Funktionen Senden & Empfangen kann über diesen [GitHub Issue](https://github.com/Purii/hdm-wim-devlab/issues/4) beantragt werden (Google Account benötigt).
* Die verfügbaren Topics (Kommunikationskanäle) werden über die [`sharedLib`](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/Constants.java#L45) bereitgestellt. Näheres zur Verwendung findet Ihr in der [Dokumentation](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md). Werden zusätzliche Topics benötigt, können diese über einen neuen [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) angefragt werden.
* Als Teil der `sharedLib` werden Euch Klassen zur Verfügung gestellt, um Events empfangen und senden zu können. Falls darüber hinaus weitere Informationen benötigt werden, kann die [ausführliche offizielle Dokumentation](https://cloud.google.com/pubsub/docs/reference/libraries) von Google PubSub genutzt werden. Die Dokumentation der SDK findet sich [hier](http://googlecloudplatform.github.io/google-cloud-java/0.18.0/apidocs/index.html) (Package: com.google.cloud.pubsub.spi.v1)

### Grundlegende Kommunikation (WIP)
![PubSub Workflow](https://github.com/Purii/hdm-wim-devlab/blob/master/assets/26975555-9a009aa6-4d20-11e7-98c3-f6268862762d.jpg)

* **Gruppe:** jeweiliges Team
* **Message:** von allen zu verwenden aus der SharedLib, siehe [Message Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/classes/Message.java)
* **AppEngine:** PaaS. Publisher und Subscriber werden automatisch erstellt (wird durch Event-Gruppe zur Verfügung gestellt), Publisher kreieren eine PubSubMessage und versenden diese über PubSub im angegebenen Topic (Achtung: Topics sind Konstanten, einzusehen in dieser [Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/Constants.java))
* **PubSub:** verteilt PubSubMessages durch die Topics
* **Publish:** Eine Message wird in das eingetragene Topic veröffentlicht, siehe [Message Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/classes/Message.java)
* **Subscribe:** Mehrere PubSubMessages werden aus dem eingetragenen Topic als Stream übertragen, siehe [PubSubMessage Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/classes/PubSubMessage.java)

***Bitte die Vorgaben für die Klassen aus der SharedLib einhalten!***

***Topics können in den Issues beantragt werden!***
