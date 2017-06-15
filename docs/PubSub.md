# PubSub

--> Andere an den Start bringen. Arbeit mit SharedLib demonstrieren

Über die `SharedLib` werden Klassen zur Verfügung gestellt, um die Integration mit PubSub zu vereinfachen.

* [Event-Klasse](#event-klasse)
* [PublishHelper](#publishhelper)
* [SuscriptionHelper](#subscriptionhelper)
* [Message Veröffentlichen](#messageveröffentlichen)


## Event-Klasse
In der `SharedLib` wird die Klasse `Event` zur Verfügung gestellt. Ein Objekt dieser Klasse kann mithilfe des PublishHelper veröffentlicht oder mithilfe des SubscriptionHelpers empfangen werden. Die Attribute der Klasse bauen auf denen der [Message-Klasse von Google PubSub (https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage) auf. 

| Feld  | Datentyp | Methoden | Beschreibung |
| :------ | :------ | :------ | :------ |
| `data` | `string (bytes format)` | `String getData()` & `setData(String data)` | Frei definierbar durch Gruppe. Beispiele: Dokumentenvorschläge, Tokens,..|
| `attributes` | `map (key: string, value: string)` | `String getAttribute(String attribute)` & `setAttribute(String key, String value)` | -**Event Source** von welcher Gruppe wird das Event gesendet. <br /> -**Event Type** spezifiziert die grundlegenden Eigenschaften einer Message. <br /> -**zusätzliche Attribute** kennzeichnend die Message mit weiteren Attributen.<br />|
| `messageId` | `string` | `getData() : string | String getMessageId()` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp format)` | String `getPublishTime()` | *wird von PubSub gesetzt*. Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |

[Hier findet ihr alle vereinbarten Events](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md).

## PublishHelper 

Um Events als Messages in PubSub zu veröffentlichen, kann der `PublishHelper` genutzt werden.

## SubscriptionHelper
Pro Gruppe ein MessageReceiver --> Dazu bieten wir Vorlage an

SubscribeHelper ohne AppEngine + Anbieten über SharedLibrary

Warten auf Bene mit Prefix + Receiver

## Message Veröffentlichen 

**Grundlagen**
- Ein `publisher` erstellt eine `topic`.
- Ein `subscriber` erstellt eine `subscription` auf diese `topic`.
- Der `publisher` sendet eine `message` an diese `topic`.
- Der `subscriber` empfängt die `message` via `push` oder `pull`, je nach Konfiguration.
- Der `subscriber` bestätigt den Empfang der `message` und diese wird aus der `queue` gelöscht.


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



```java
# Import der Konstanten
import de.hdm.wim.sharedLib.Constants;

# Import der Klasse Event
import de.hdm.wim.sharedLib.events.Event;

# Import der Klasse PublishHelper
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;

# Import der Klasse SubscriptionHelper
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;


	public static void main(String[] args) throws Exception {
		int MAX_NUMBER_OF_MESSAGES = 1;

		// init a SubscriptionHelper to use for prod environment for the given project
		SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);

		/**
		 * this will create a subscription with id: "subscription-pull-topic-1-test1"
		 * if the subscription already exists, we will use it
		 */
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test2");

		PublishHelper ph = new PublishHelper(false);

		while (MAX_NUMBER_OF_MESSAGES <= 3) {
			Event event = Event.generate("blubb_" + MAX_NUMBER_OF_MESSAGES);

			// event, topic, useREST = true/false
			ph.Publish(event, Topic.TOPIC_1, false);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			MAX_NUMBER_OF_MESSAGES++;
		}
```





