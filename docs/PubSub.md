# PubSub

--> Andere an den Start bringen. Arbeit mit SharedLib demonstrieren

**Vezeichnis**

* [Grundlagen](#grundlagen)
* [ShareLib-Klassen](#sharelib-klassen)
* [Events als Messages in PubSub veröffentlichen](#eventsalsmessagesinpubsubveroeffentlichen)

## Grundlagen

- Ein `publisher` erstellt eine `topic`.
- Ein `subscriber` erstellt eine `subscription` auf diese `topic`.
- Der `publisher` sendet eine `message` an diese `topic`.
- Der `subscriber` empfängt die `message` via `push` oder `pull`, je nach Konfiguration.
- Der `subscriber` bestätigt den Empfang der `message` und diese wird aus der `queue` gelöscht.

## ShareLib-Klassen

Über die `SharedLib` werden Klassen zur Verfügung gestellt, um die Integration mit PubSub zu vereinfachen.

* [Event-Klasse](#event-klasse)
* [PublishHelper](#publishhelper)
* [SuscriptionHelper](#subscriptionhelper)
* [Events als Messages in PubSub veröffentlichen](#eventsalsmessagesinpubsubveröffentlichen)


### Event-Klasse

In der `SharedLib` wird die Klasse `Event` zur Verfügung gestellt. Ein Objekt dieser Klasse kann mithilfe des PublishHelper veröffentlicht oder mithilfe des SubscriptionHelpers empfangen werden. Die Attribute der Klasse bauen auf denen der [Message-Klasse von Google PubSub (https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage) auf. 

| Feld  | Datentyp | Methoden | Beschreibung |
| :------ | :------ | :------ | :------ |
| `data` | `string (bytes format)` | `String getData()` & `setData(String data)` | Frei definierbar durch Gruppe. Beispiele: Dokumentenvorschläge, Tokens,..|
| `attributes` | `map (key: string, value: string)` | `String getAttribute(String attribute)` & `setAttribute(String key, String value)` | -**Event Source** von welcher Gruppe wird das Event gesendet. <br /> -**Event Type** spezifiziert die grundlegenden Eigenschaften einer Message. <br /> -**zusätzliche Attribute** kennzeichnend die Message mit weiteren Attributen.<br />|
| `messageId` | `string` | `getData() : string | String getMessageId()` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp format)` | String `getPublishTime()` | *wird von PubSub gesetzt*. Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |

[Hier findet ihr alle vereinbarten Events](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md).

### PublishHelper 

* Mit Hilfe des `PublishHelper`, werden Events als Messages an PubSub übermittelt. 
* [Zur PublishHelper-Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/pubsub/helper/PublishHelper.java)

### SubscriptionHelper

* Der `SubscriptionHelper` kann eine `subscription` auf folgende [Topics](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Topics.md) erstellen.
* Der`SubscriptionHelper` kann via `push` oder `pull`(je nach Konfiguration) `message` von PubSub empfangen. 
* [Zur SubscriptionHelper-Klasse](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/pubsub/helper/SubscriptionHelper.java)


Pro Gruppe ein MessageReceiver --> Dazu bieten wir Vorlage an

SubscribeHelper ohne AppEngine + Anbieten über SharedLibrary

Warten auf Bene mit Prefix + Receiver

## Events als Messages in PubSub veröffentlichen

**Ablauf**

- (1) Bevor eine Message veröffentlicht werden kann, muss ein Subscriber erstellt werden. (-> Welche Topics interessieren uns!)
Um Topics in PubSub zu abonnieren, kann der `SubscriptionHeler` genutzt werden.

```java
		// init a SubscriptionHelper to use for prod environment for the given project
		SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);

		/**
		 * this will create a subscription with id: "subscription-pull-topic-1-test1"
		 * if the subscription already exists, we will use it
		 */
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test2");
        
  ```
- (2) Bevor eine Message veröffentlicht werden kann, muss ein Event erstellt werden. (-> Welches Ereignis wollen wir veröffentlichen!) 

```java
        Event insightEvent = new Event();
		insightEvent.setData("insightEvent");
		insightEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);}});

```
- (3) Um Events als Messages in PubSub zu veröffentlichen, kann der `PublishHelper` genutzt werden.

```java
		PublishHelper ph = new PublishHelper(false);
        
        ph.Publish(feedbackEvent, 	Topic.TOPIC_1);
		ph.Publish(insightEvent, 	Topic.TOPIC_1);
		ph.Publish(offerEvent, 		Topic.TOPIC_1);
		ph.Publish(richTokenEvent, 	Topic.TOPIC_1);
		ph.Publish(stayaliveEvent, 	Topic.TOPIC_1);
		ph.Publish(tokenEvent, 		Topic.TOPIC_1);
```
