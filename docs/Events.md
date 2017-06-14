# Events

Dieses Dokument dient der verbindlichen Definition der Events, welche durch unser Netzwerk geschickt werden.

* [ContextEvent](#contextevent)
* [DocumentRelevantEvent](#documentrelevantevent)
* [DocumentHighlyRelevantEvent](#documenthighlyrelevant)
* [FeedbackEvent](#feedbackevent)
* [LearnEvent](#learnevent)
* [OfferEvent](#offerevent)
* [SessionEndEvent](sessionendevent)
* [SessionStartEvent](#sessionstartevent)
* [StayAliveEvent](#stayaliveevent)
* [TokenEvent](#tokenevent)
* [UserInformationEvent](#userinformationevent)
* [UserLoginEvent](#userloginevent)
* [UserLogoutEvent](#userlogoutevent)

*Um die Konstanten nutzen zu können, muss die entsprechende Datei wie folgt implementiert werden*
```
import  de.hdm.wim.sharedLib.Constants;
```

## ContextEvent
*erstellt durch CEP; die Nutzerinformationen und gesprochenen Token lassen auf einen Projekt-Kontext schließen*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { projectId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.CONTEXT } ``` |
| `data` | `string (bytes format)` | ``` { Context } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` {userId: String, documentId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_RELEVANT } ``` |
| `data` | `string (bytes format)` | ``` Document is relevant ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentHighlyRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument mehrfach angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, documentId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT } ``` |
| `data` | `string (bytes format)` | ``` { Document is highly relevant } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## FeedbackEvent
*erstellt durch GUI; ein Nutzer hat ein Dokument angeklickt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, documentId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK } ``` |
| `data` | `string (bytes format)` | ``` { User clicked on an offer } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |


## LearnEvent
*erstellt durch ML; Kontext zwischen Personen oder Dokumenten zu Projekten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, documentId: String, projectId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.MACHINE_LEARNING, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.LEARN } ``` |
| `data` | `string (bytes format)` | ``` { Learning } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## OfferEvent
*erstellt durch SR; Dokumentenvorschläge zur Anzeige*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, documentId: String, relevance: Integer, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.OFFER } ``` |
| `data` | `string (bytes format)` | ``` { Offer } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.OFFERS` |


## SessionEndEvent
*erstellt durch CEP; es ist kein User mehr in der Session vorhanden*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_END } ``` |
| `data` | `string (bytes format)` | ``` { Session finished } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## SessionStartEvent
*erstellt durch GUI; ein User betritt eine Session*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_START } ``` |
| `data` | `string (bytes format)` | ``` { Session started } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## StayAliveEvent
*erstellt durch GUI; Ping-Event zur Aktivitätsprüfung*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.STAYALIVE } ``` |
| `data` | `string (bytes format)` | ``` { User is active } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## TokenEvent
*erstellt durch ST; beinhaltet den Token*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, token: Object, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SPEECH_TOKENIZATION, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.TOKEN } ``` |
| `data` | `string (bytes format)` | ``` { Token } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.TOKEN` | 


## UserInactiveEvent
*erstellt durch CEP; ein User ist inaktiv (passiver Logout)*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INACTIVE } ``` |
| `data` | `string (bytes format)` | ``` { User is inactive } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserInformationEvent
*erstellt durch SR; Detailinformationen über den User, wird bspw. beim Login ausgelöst*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, ..., constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INFORMATION } ``` |
| `data` | `string (bytes format)` | ``` { User information } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserLoginEvent
*erstellt durch GUI; ein User hat sich eingeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGIN } ``` |
| `data` | `string (bytes format)` | ``` { User logged in } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserLogoutEvent
*erstellt durch GUI; ein User hat sich ausgeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { userId: String, constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT(USER_INTERFACE), constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGOUT } ``` |
| `data` | `string (bytes format)` | ``` { User logged out } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |
