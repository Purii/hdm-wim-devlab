# PubSub

In diesem Dokument wird der Umgang mit der ShareLib demonstriert. Die ShareLib stellt die Kommunikation mit PubSub sicher und soll von allen Gruppen als gemeinsame Lösung verwendet werden. 

## Vezeichnis

* [ShareLib-Klassen](#sharelib-klassen)
* [Events als Messages in PubSub veröffentlichen](#ablauf)

## ShareLib-Klassen

Über die `SharedLib` werden Klassen zur Verfügung gestellt, um die Integration mit PubSub zu vereinfachen.

* [Event-Klasse](#event-klasse)
* [PublishHelper](#publishhelper)
* [SuscriptionHelper](#subscriptionhelper)

### Event-Klasse

In der `SharedLib` wird die Klasse `Event` zur Verfügung gestellt. Ein Objekt dieser Klasse kann mithilfe des PublishHelper veröffentlicht oder mithilfe des SubscriptionHelpers empfangen werden. Die Attribute der Klasse bauen auf denen der [Message-Klasse von Google PubSub (https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage) auf. 

| Feld  | Datentyp | Methoden | Beschreibung |
| :------ | :------ | :------ | :------ |
| `data` | `string (bytes format)` | `String getData()` & `setData(String data)` |`data` ist der Inhalt der Message und ist base64-encoded.|
| `attributes` | `map (key: string, value: string)` | `String getAttribute(String attribute)` & `setAttribute(String key, String value)` | -**Event Source** von welcher Gruppe wird das Event gesendet. <br /> -**Event Type** spezifiziert die grundlegenden Eigenschaften einer Message. <br /> -**zusätzliche Attribute** kennzeichnen die Message mit weiteren Attributen.<br /> -**Es können bis zu 100 `attributes` als Metainformation zur Message festgelegt werden**|
| `messageId` | `string` | `String getMessageId()` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp format)` | `String getPublishTime()` | *wird von PubSub gesetzt*. Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |

[Hier findet ihr alle Events, die gemeinsam vereinbart wurden.](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md).

### PublishHelper 

* Mit Hilfe des `PublishHelper`, werden Events als Messages an PubSub übermittelt. <br />
[Zur PublishHelper-Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/pubsub/helper/PublishHelper.java)

### SubscriptionHelper

* Der `SubscriptionHelper` kann eine `subscription` auf folgende [Topics](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Topics.md) erstellen.
* Der`SubscriptionHelper` kann via `push` oder `pull`(je nach Konfiguration) `message` von PubSub empfangen. <br />
[Zur SubscriptionHelper-Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/pubsub/helper/SubscriptionHelper.java)

Pro Gruppe ein MessageReceiver --> Dazu bieten wir Vorlage an

SubscribeHelper ohne AppEngine + Anbieten über SharedLibrary

Warten auf Bene mit Prefix + Receiver

## PubSub

**Grundlagen** 

(1) Ein `publisher` erstellt eine `topic`.<br />
(2) Ein `subscriber` erstellt eine `subscription` auf diese `topic`.<br />
(3) Der `publisher` sendet eine `message` an diese `topic`.<br />
(4) Der `subscriber` empfängt die `message` via `push` oder `pull`, je nach Konfiguration.<br />
(5) Der `subscriber` bestätigt den Empfang der `message` und diese wird aus der `queue` gelöscht.<br />

### Events an PubSub schicken (Publish)

**(1) Topics**
Die verfügbaren Topics (Kommunikationskanäle) werden über die [`SharedLib`](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/Constants.java#L45) bereitgestellt. Werden zusätzliche Topics benötigt, können diese über einen neuen [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) angefragt werden.

**(2) Bevor Event in PubSub veröffentlicht werden, muss sichergestellt werden, dass eine subscription auf die gewünschten Topics vorhanden ist.** 
```java
// init a SubscriptionHelper to use for prod environment for the given project (see Constants in SharedLib)
SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);
/**
 * this will create a subscription with id: "subscription-pull-topic-1-test1"
 * if the subscription already exists, we will use it
 */
sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");
  ```
**(4) Erstellen eines Events**
```java
Event insightEvent = new Event();
insightEvent.setData("insightEvent");
insightEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);}});
```
**(3) Um Events als Messages in PubSub zu veröffentlichen, wird der `PublishHelper` genutzt.**

```java
// init a PublishHelper to use for prod environment
PublishHelper ph = new PublishHelper(false);
ph.Publish(insightEvent, Topic.TOPIC_1);
```
**(4) Message von PubSub empfangen.**

Unterschiedlich. Bei Push gibt es einen Stream, bei Pull einen Batch von PubSub Messages

~~Mehrere PubSubMessages werden aus dem eingetragenen Topic als Stream übertragen, siehe [PubSubMessage Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/classes/PubSubMessage.java)

**(5) Empfang der Message bestätigen.** 

findet im receiver statt => `consumer.ack` oder `consumer.nack`
