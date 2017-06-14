# Events

Dieses Dokument dient der verbindlichen Definition der Events, welche durch unser Netzwerk geschickt werden.
Werden weitere Events benötigt oder genügen die definierten Attribute nicht, ist dafür ein [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) zu erstellen.

* [ContextEvent](#contextevent)
* [DocumentRelevantEvent](#documentrelevantevent)
* [DocumentHighlyRelevantEvent](#documenthighlyrelevantevent)
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
* [UserJoinedSessionEvent](#userjoinedsessionevent)
* [UserLeftSessionEvent](#userleftsessionevent)

*Um die Konstanten nutzen zu können, muss die entsprechende Datei wie folgt implementiert werden*
```
import  de.hdm.wim.sharedLib.Constants;
```

## ContextEvent
*erstellt durch CEP; die Nutzerinformationen und gesprochenen Token lassen auf einen Projekt-Kontext schließen*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>projectId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.CONTEXT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Context } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>documentId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_RELEVANT</code></li></ul> |
| `data` | `string (bytes format)` | ``` Document is relevant ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentHighlyRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument mehrfach angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>documentId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Document is highly relevant } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## FeedbackEvent
*erstellt durch GUI; ein Nutzer hat ein Dokument angeklickt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>documentId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User clicked on an offer } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |


## LearnEvent
*erstellt durch ML; Kontext zwischen Personen oder Dokumenten zu Projekten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>documentId: \<String\></code></li><li><code>projectId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.MACHINE_LEARNING</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.LEARN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Learning } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## OfferEvent
*erstellt durch SR; Dokumentenvorschläge zur Anzeige*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>documentId: \<String\></code></li><li><code>relevance: \<Integer\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.OFFER</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Offer } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.OFFERS` |


## SessionEndEvent
*erstellt durch CEP; es ist kein User mehr in der Session vorhanden*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>sessionId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_END</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Session finished } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## SessionStartEvent
*erstellt durch GUI; ein User betritt eine Session*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>sessionId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_START</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Session started } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## StayAliveEvent
*erstellt durch GUI; Ping-Event zur Aktivitätsprüfung*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.STAYALIVE</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User is active } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## TokenEvent
*erstellt durch ST; beinhaltet den Token*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>token: \<Object\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SPEECH_TOKENIZATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.TOKEN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Token } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.TOKEN` | 


## UserInactiveEvent
*erstellt durch CEP; ein User ist inaktiv (passiver Logout)*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INACTIVE</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User is inactive } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserInformationEvent
*erstellt durch SR; Detailinformationen über den User, wird bspw. beim Login ausgelöst*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li>...</li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INFORMATION</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User information } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserLoginEvent
*erstellt durch GUI; ein User hat sich eingeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGIN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User logged in } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserLogoutEvent
*erstellt durch GUI; ein User hat sich ausgeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT(USER_INTERFACE)</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGOUT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User logged out } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserJoinedSessionEvent
*erstellt durch GUI; ein User hat sich an einer Session angemeldet*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>sessionId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_JOINED_SESSION</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User joined Session } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |

## UserLeftSessionEvent
*erstellt durch GUI; ein User hat sich von einer Session abgemeldet*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userId: \<String\></code></li><li><code>sessionId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LEFT_SESSION</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User left Session } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |

