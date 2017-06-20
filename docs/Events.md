# Events

Dieses Dokument dient der verbindlichen Definition der Events, welche durch unser Netzwerk geschickt werden.
Werden weitere Events benötigt oder genügen die definierten Attribute nicht, ist dafür ein [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) zu erstellen.

* [DocumentContextEvent](#documentcontextevent)
* [DocumentInformationEvent](#documentinformationevent)
* [DocumentRelevantEvent](#documentrelevantevent)
* [DocumentHighlyRelevantEvent](#documenthighlyrelevantevent)
* [FeedbackEvent](#feedbackevent)
* [LearnEvent](#learnevent)
* [OfferEvent](#offerevent)
* [SessionEndEvent](sessionendevent)
* [SessionStartEvent](#sessionstartevent)
* [StayAliveEvent](#stayaliveevent)
* [TokenEvent](#tokenevent)
* [UserContextEvent](#usercontextevent)
* [UserInactiveEvent](#userinactiveevent)
* [UserInformationEvent](#userinformationevent)
* [UserLoginEvent](#userloginevent)
* [UserLogoutEvent](#userlogoutevent)
* [UserJoinedSessionEvent](#userjoinedsessionevent)
* [UserLeftSessionEvent](#userleftsessionevent)
* [UserStartEvent](#userstartevent)

*Um die Konstanten nutzen zu können, muss die entsprechende Datei wie folgt implementiert werden*
```
import  de.hdm.wim.sharedLib.Constants;
```

**WICHTIG:**

Da `key` und `value` der `attributes` jeweils vom Datentyp `String` sind, müssen andere Datentypen erst zu einem `String` konvertiert werden.

Dies kann mit Hilfe von `JSON` gemacht werden, dazu bitte die [gson lib](https://github.com/google/gson/blob/master/UserGuide.md) von google benutzen.

Beispiel:
```
import com.google.gson.Gson;


Gson gson = new Gson();
String[] names = {"Chris", "Bene", "Jimmy", "Patscho"};

// Serialization
String jsonString = gson.toJson(strings);  ==> ["Chris", "Bene", "Jimmy", "Patscho"]

// Deserialization
String names = gson.fromJson(jsonString, String.class); 
```

## DepartmentInformationEvent
*erstellt durch CEP; Detailinformationen über die Abteilung*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: String</code></li><li><code>constants.AttributeKey.TOKEN_ID: String</code></li><li><code>constants.AttributeKey.DEPARTMENT_ID: String</code></li><li><code>constants.AttributeKey.DEPARTMENT_NAME: String</code></li><li><code>constants.AttributeKey.DEPARTMENT_SHORT: String</code></li><li><code>constants.AttributeKey.DEPARTMENT_HAS_PROJECTS: Array(String)</code></li><li><code>constants.AttributeKey.DEPARTMENT_HAS_WORKER: Array(String)</code></li><li><code>constants.AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY: String</code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_CONTEXT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Document Context } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INFORMATION` |


## DocumentContextEvent
*erstellt durch CEP; die Dokumentinformationen lassen auf einen Projekt-Kontext schließen*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>documentIds: Array(String)</code></li><li><code>documentNames: Array(String)</code></li><li><code>projectId: String</code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_CONTEXT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Document Context } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_RELEVANT</code></li></ul> |
| `data` | `string (bytes format)` | ``` Document is relevant ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## DocumentInformationEvent
*erstellt durch CEP; Detailinformationen zu den gefundenen Dokumenten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.TOKEN_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_NAME: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_URL: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ADDED: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_VERSION: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_TYPE: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_WRITTEN_BY: Array\<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_FAVORED_BY: Array\<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_KEYWORDS: Array\<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_FOLDER: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_INFO</code></li></ul> |
| `data` | `string (bytes format)` | ``` Document Information ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INFORMATION` |


## DocumentHighlyRelevantEvent
*erstellt durch CEP; ein Nutzer hat ein bestimmtes Dokument mehrfach angeklickt; die SR kann die Verbindung höher gewichten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Document is highly relevant } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## FeedbackEvent
*erstellt durch GUI; ein Dokument wurde dem User angezeigt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Offer is shown } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |


## InformationToAllDocumentsEvent
*erstellt durch SR; initialer Vorschlag aller geeigneten Dokumente*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.DOCUMENT: Object(constants.AttributeKey.SESSION_ID: \<String\>, constants.AttributeKey.DOCUMENT_ID: \<String\>, constants.AttributeKey.DOCUMENT_NAME: \<String\>, constants.AttributeKey.DOCUMENT_PRIO: Integer, constants.AttributeKey.DOCUMENT_TYPE: \<String\>, constants.AttributeKey.DOCUMENT_URL: \<String\>, constants.AttributeKey.DOCUMENT_FOLDER: \<String\>)</li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.INFO_TOALL_DOCUMENTS</code></li></ul> |
| `data` | `string (bytes format)` | ``` Information to all ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INFORMATION` |


## LearnEvent
*erstellt durch ML; Kontext zwischen Personen oder Dokumenten zu Projekten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>projectId: \<String\></code><li><code>userclick: \<Boolean\></code></li></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.MACHINE_LEARNING</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.LEARN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Learning } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## OfferEvent
*erstellt durch SR; Vorschlag (Update) aller geeigneten Dokumente*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.DOCUMENT: Object(constants.AttributeKey.SESSION_ID: \<String\>, constants.AttributeKey.DOCUMENT_ID: \<String\>, constants.AttributeKey.DOCUMENT_NAME: \<String\>, constants.AttributeKey.DOCUMENT_PRIO: Integer, constants.AttributeKey.DOCUMENT_TYPE: \<String\>, constants.AttributeKey.DOCUMENT_URL: \<String\>, constants.AttributeKey.DOCUMENT_FOLDER: \<String\>)</li><li><code>constants.AttributeKey.FAVORITE: Object(constants.AttributeKey.SESSION_ID: \<String\>, constants.AttributeKey.DOCUMENT_ID: \<String\>, constants.AttributeKey.DOCUMENT_NAME: \<String\>, constants.AttributeKey.DOCUMENT_PRIO: Integer, constants.AttributeKey.DOCUMENT_TYPE: \<String\>, constants.AttributeKey.DOCUMENT_URL: \<String\>, constants.AttributeKey.DOCUMENT_FOLDER: \<String\>)</li><li><code>constants.AttributeKey.DOCUMENT_OFFER: Object(constants.AttributeKey.SESSION_ID: \<String\>, constants.AttributeKey.DOCUMENT_ID: \<String\>, constants.AttributeKey.DOCUMENT_NAME: \<String\>, constants.AttributeKey.DOCUMENT_PRIO: Integer, constants.AttributeKey.DOCUMENT_TYPE: \<String\>, constants.AttributeKey.DOCUMENT_URL: \<String\>, constants.AttributeKey.DOCUMENT_FOLDER: \<String\>)</li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.OFFER</code></li></ul> |
| `data` | `string (bytes format)` | ``` Document Offer ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.OFFERS` |


## ProjectInformationEvent
*erstellt durch SR; Detailinformationen zu Projekten*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.TOKEN_ID: \<String\></code></li><li><code>constants.AttributeKey.PROJECT_ID: \<String\></code></li><li><code>constants.AttributeKey.PROJECT_NAME: \<String\></code></li><li><code>constants.AttributeKey.PROJECT_BELONGS_TO_COMPANY: \<String\></code></li><li><code>constants.AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT: \<String\></code></li><li><code>constants.AttributeKey.PROJECT_HAS_MEMBERS: Array\<String\></code></li><li><code>constants.AttributeKey.PROJECT_HAS_DOCUMENTS: Array\<String\></code></li><li><code>constants.Attribut<li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.PROJECT_INFO</code></li></ul> |
| `data` | `string (bytes format)` | ``` Project Information ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INFORMATION` |


## SessionEndEvent
*erstellt durch CEP; es ist kein User mehr in der Session vorhanden*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_END</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Session finished } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## SessionStartEvent
*erstellt durch GUI; ein User betritt eine Session*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.SESSION_START</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Session started } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## StayAliveEvent
*erstellt durch GUI; Ping-Event zur Aktivitätsprüfung*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.STAYALIVE</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User is active } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## SuccessfulFeedbackEvent
*erstellt durch GUI; ein Nutzer hat ein Dokument angeklickt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.DOCUMENT_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User clicked on an offer } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |


## TokenEvent
*erstellt durch ST; beinhaltet den Token*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>sessionId: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SPEECH_TOKENIZATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.TOKEN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { Token } ```|
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `contexts` | `ArrayList<string>` | ``` { Context1, Context2, ... } ```  |
| `tokens` | `ArrayList<string>` | ``` { Token1, Token2, ... } ``` |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.TOKEN` | 


## UserInactiveEvent
*erstellt durch CEP; ein User ist inaktiv (passiver Logout)*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INACTIVE</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User is inactive } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserContextEvent
*erstellt durch CEP; die Nutzerinformationen lassen auf einen Projekt-Kontext schließen*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>userIds: Array\<String\></code></li><li><code>userNames: Array(String)</code></li><li><code>projectId: String</code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_CONTEXT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User Context } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INSIGHTS` |


## UserInformationEvent
*erstellt durch SR; Detailinformationen über den User, wird bspw. beim Login ausgelöst*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.TOKEN_ID: \<String\></code></li><li><code>constants.AttributeKey.PERSON: Object(constants.AttributeKey.USER_ID: \<String\>, constants.AttributeKey.PRENAME: \<String\>, constants.AttributeKey.SURNAME: \<String\>, constants.AttributeKey.EMAIL: \<String\>, constants.AttributeKey.PROJECT_NAME: \<String\>, constants.AttributeKey.PROJECT_ROLE: \<String\>, constants.AttributeKey.DEPARTMENT_SHORT: \<String\>, constants.AttributeKey.DOCUMENT_AUTHOR: \<String\>, constants.AttributeKey.DOCUMENT_CALL: \<String\>, constants.AttributeKey.DOCUMENT_FAVORIT: \<String\>)</code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_INFO</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User information } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.INFORMATION` |


## UserLoginEvent
*erstellt durch GUI; ein User hat sich eingeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGIN</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User logged in } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserLogoutEvent
*erstellt durch GUI; ein User hat sich ausgeloggt*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.EVENT(USER_INTERFACE)</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LOGOUT</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User logged out } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |


## UserJoinedSessionEvent
*erstellt durch GUI; ein User hat sich an einer Session angemeldet*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_JOINED_SESSION</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User joined Session } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |

## UserLeftSessionEvent
*erstellt durch GUI; ein User hat sich von einer Session abgemeldet*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_LEFT_SESSION</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User left Session } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |

## UserStartEvent
*erstellt durch GUI; ein User hat sich von einer Session abgemeldet*

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | <ul><li><code>constants.AttributeKey.USER_ID: \<String\></code></li><li><code>constants.AttributeKey.SESSION_ID: \<String\></code></li><li><code>constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE</code></li><li><code>constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.USER_START</code></li></ul> |
| `data` | `string (bytes format)` | ``` { User starts Recommendation } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.SESSIONINSIGHTS` |

