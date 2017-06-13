# Events

Dieses Dokument dient der verbindlichen Definition der Events, welche durch unser Netzwerk geschickt werden.

* [FeedbackEvent](#feedbackevent)
* [InsightEvent](#insightevent)
* [LearnEvent](#learnevent)
* [OfferEvent](#offerevent)
* [StayAliveEvent](#stayaliveevent)
* [TokenEvent](#tokenevent)

*Um die Konstanten nutzen zu können, muss die entsprechende Datei wie folgt implementiert werden*
```
import  de.hdm.wim.sharedLib.Constants;
```


## DocumentRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_RELEVANT } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentHighlyRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument mehrfach angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## FeedbackEvent
*erstellt durch GUI; ein Nutzer hat ein Dokument angeklickt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |


## InsightEvent

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.INSIGHT } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## LearnEvent
*erstellt durch ML; Kontext zwischen Personen oder Dokumenten zu Projekten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.MACHINE_LEARNING, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.LEARN } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId, projectId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## OfferEvent
*erstellt durch SR; Dokumentenvorschläge zur Anzeige*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.OFFER } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId, relevance } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.OFFERS` |


## SessionEvent
*erstellt durch CEP und GUI; der User führt eine Sitzungs-Aktion durch (Login, Logout, passiver Logout)*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT(USER_INTERFACE), constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSIONINSIGHT } ``` |
| `data` | `string (bytes format)` | ``` { userId, action } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSION` |


## SessionInsightEvent
*erstellt durch CEP; Schlussfolgerungen auf Grundlage der SessionEvents (Session beendet)*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSIONINSIGHT } ``` |
| `data` | `string (bytes format)` | ``` { userId, action } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## StayAliveEvent
*erstellt durch GUI; Ping-Event zur Aktivitätsprüfung*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.STAYALIVE } ``` |
| `data` | `string (bytes format)` | ``` { userId, isActive } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## TokenEvent
*erstellt durch ST; beinhaltet den Token*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SPEECH_TOKENIZATION, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.TOKEN } ``` |
| `data` | `string (bytes format)` | ``` { userId, token } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.TOKEN` |


## 
