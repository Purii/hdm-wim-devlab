# hdm-wim-devlab

*Hinweis: Bei diesem Projekt handelt es sich um ein Forschungsprojekt der Hochschule der Medien, Stuttgart.*


## Spielregeln

* Jede Gruppe hat ein eigenes Verzeichnis.
* Jede Gruppe stellt einen oder mehrere Microservices bereit. Jede Gruppe ist damit selbst für die eigene Systemumgebung verantwortlich.
* Entwickelt wird eine Event Driven Architecture. Die Kommunikation findet über Messages statt.
* Diese Events werden mittels [Google PubSub](https://cloud.google.com/pubsub/docs/overview) übermittelt.

### Google PubSub

* Zugriff auf die Funktionen Senden & Empfangen kann über folgenden [GitHub Issue](https://github.com/Purii/hdm-wim-devlab/issues/4) beantragt werden (Google Account benötigt).
* Topics können nicht selbst angelegt werden, sondern müssen ebenfalls über einen [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) beantragt werden.
* Für PubSub wird eine [ausführliche Dokumentation](https://cloud.google.com/pubsub/docs/reference/libraries) bereitgestellt. Die Dokumentation der SDK findet sich [hier](http://googlecloudplatform.github.io/google-cloud-java/0.18.0/apidocs/index.html) (Package: com.google.cloud.pubsub.spi.v1)

#### Topics

Topics, die genutzt werden sollten:

* FeedbackGui
* Insights (User klickt mehrfach auf denselben Dokumentvorschlag; Tokens können zu folgendem Dokument führen)
* Offers (Vorschläge)
* SessionInsights (User loggt sich ein/aus; User ist passiv)
* RichTokens (SR > CEP)
* Tokens (ST > SR)
* Training (CEP > ML) ?

#### Felder einer PubSub Message

Über PubSub werden Messages versendet. Folgende Felder dienen dabei als [Grundlage](https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage):

| Feld  | Datentyp | Beschreibung |
| :------------ | :---------------: | ------------ |
| `data` | `string (bytes format)` | Frei definierbar durch Gruppe. Beispiele: Dokumentenvorschläge, Tokens,.. |
| `attributes` | `map (key: string, value: string)` | Beispiele: `eventSource`, `eventType` |
| `messageId` | `string` | Wird durch PubSub Server hinzugefügt |
| `publishTime` | `string (Timestamp format)` | Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |

##### `attributes`
Relevant ist für uns neben dem Feld `data` das Feld `attributes`.
Die einzelnen Attribute werden anhand von Keys identifiziert.
Dazu sind die vordefinierten Konstanten der Datei [`sharedLib/Constants.java`](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/Constants.java#L14) zu verwenden.
Diese können nach Bedarf erweitert werden.

##### Event Type

Event Type spezifiziert die grundlegenden Eigenschaften einer Message. 

| `EventType`  | Beschreibung | Topic |
| :------------ | --------------- | --------------- |
| StayAlive | Heartbeat von GUI Clients | SessionInsights |
| LinkOffer | Vorschlag zur Anzeige von Google Calender, Drive,.. | Offers |
| Offer | Dokumentvorschläge | Offers |
| Training? | Feedback an ML | Training |
| Token | Einfache Tokens | Tokens |
| RichToken | Angereicherte Tokens | RichTokens |

##### Event Source

Event Source beschreibt die Herkunft der Message.

| `EventSource` | Beschreibung |
| :------------ | --------------- |
| SPEECH_TOKENIZATION | Speech Tokenization |
| EVENT | Complex Event Processing |
| MACHINE_LEARNING | Machine Learning |
| USER_INTERFACE | User Interface |
| SEMANTIC_REPRESENTATION | Semantic Representation |


Causality?

`EventID`= `String`? (MessageID)

