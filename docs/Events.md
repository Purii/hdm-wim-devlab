# Events

Dieses Dokument dient der verbindlichen Definition der Events, welche durch unser Netzwerk geschickt werden.

* [FeedbackEvent](#feedbackevent)
* [InsightEvent](#insightevent)
* [OfferEvent](#offerevent)
* [StayAliveEvent](#stayaliveevent)
* [TokenEvent](#tokenevent)

** Um die Konstanten nutzen zu können, muss die entsprechende Datei wie folgt implementiert werden **
```
import  de.hdm.wim.sharedLib.Constants
...
```

## FeedbackEvent

| Feld | Datentyp | Wert |
| :---- | :---- | :---- |
| `attributes` | `map (key: string, value: string)` | ``` { constants.AttributeKey.EVENT_SOURCE: Constants.PubSub.EventSource.USER_INTERFACE, constants.AttributeKey.EVENT_TYPE: Constants.PubSub.EventType.FEEDBACK } ``` |
| `data` | `string (bytes format)` | ``` { userId, documentId } ``` |
| `messageId` | `string` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp in RFC3339)` | *wird von PubSub gesetzt* |
| `pubSubTopic` | `string` | `Constants.PubSub.Topic.FEEDBACK_GUI` |