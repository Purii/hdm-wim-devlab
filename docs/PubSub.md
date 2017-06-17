# PubSub

In diesem Dokument wird der Umgang mit der ShareLib demonstriert. Die ShareLib stellt die Kommunikation mit PubSub sicher und soll von allen Gruppen als gemeinsame Lösung verwendet werden. 

## Verzeichnis

* [Google Cloud](#google-cloud)
* [ShareLib-Klassen](#sharelib-klassen)
* [Hands-on: Nutzung der SharedLib zur Integration von PubSub](#pubsub)
    * [Events senden](#events-an-pubsub-senden-publish)
    * [Events empfangen](#events-aus-pubsub-empfangen-subscribe)

## Google Cloud

Voraussetzung für die Nutzung von PubSub ist Google Cloud. 

(1) Installation 

Hinweise zur Installation und Downloads finden sich [hier](https://cloud.google.com/sdk/downloads). Bei der Installation bitte darauf achten, die für den Test benötigten Beta Commands mit zu installieren.

(2) Initiierung

Google Cloud SDK Shell öffnen. Der Befehl `gcloud init` initialisiert die Konfiguration. Den Befehlen in der Shell folgen. Falls noch kein Zugriff auf das PubSub-Projekt für den Google-Account beantrage wurde, bitte [hier](https://github.com/Purii/hdm-wim-devlab/issues/4) nachholen.

(3) Authentifizierung

Für die erste Authentifizierung muss der Default-Login definiert werden. Hierfür folgenden Befehl in die Shell eingeben
`gcloud auth application-default login`. Die API führt daraufhin die Authentifizierung im Browser durch. 

(3) Test

Den Befehl `gcloud beta pubsub topics list` in der Shell ausführen. Die Shell zeigt als Ergebnis die Liste der momentan vorhandenen Topics an.

## ShareLib-Klassen

Über die `SharedLib` werden Klassen zur Verfügung gestellt, um die Integration mit PubSub zu vereinfachen.

* [Event-Klasse](#event-klasse)
* [PublishHelper](#publishhelper)
* [SuscriptionHelper](#subscriptionhelper)

### Event-Klasse

In der `SharedLib` wird die Klasse `Event` zur Verfügung gestellt. Ein Objekt dieser Klasse kann mithilfe des PublishHelper veröffentlicht oder mithilfe des SubscriptionHelpers empfangen werden. Die Attribute der Klasse bauen auf denen der [Message-Klasse von Google PubSub](https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage) auf. 

| Feld  | Datentyp | Methoden | Beschreibung |
| :------ | :------ | :------ | :------ |
| `data` | `string (bytes format)` | `String getData()` & `void setData(String data)` | `data` ist der Inhalt der Message und ist base64-encoded.|
| `attributes` | `map (key: string, value: string)` | `String getAttribut(String attribute)` & `void setAttribut(String key, String value)` | <ul><li>**Event Source** von welcher Gruppe wird das Event gesendet.</li><li>**Event Type** spezifiziert die grundlegenden Eigenschaften einer Message.</li><li>**zusätzliche Attribute** kennzeichnen die Message mit weiteren Attributen.</li><li>**Es können bis zu 100 `attributes` als Metainformation zur Message festgelegt werden**</li></ul>|
| `messageId` | `string` | `String getMessageId()` | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp format)` | `String getPublishTime()` | *wird von PubSub gesetzt*. Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |

[Hier findet ihr alle Events, die gemeinsam vereinbart wurden](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md).

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

1. Ein `publisher` erstellt eine `topic`.<br />
2. Ein `subscriber` erstellt eine `subscription` auf diese `topic`.<br />
3. Der `publisher` sendet eine `message` an diese `topic`.<br />
4. Der `subscriber` empfängt die `message` via `push` oder `pull`, je nach Konfiguration.<br />
5. Der `subscriber` bestätigt den Empfang der `message` und diese wird aus der `queue` gelöscht.<br />

### Events an PubSub senden (Publish)

**(1) Topics**
Die verfügbaren Topics (Kommunikationskanäle) werden über die [`SharedLib`](https://github.com/Purii/hdm-wim-devlab/blob/master/SharedLib/src/main/java/de/hdm/wim/sharedLib/Constants.java#L45) bereitgestellt. Werden zusätzliche Topics benötigt, können diese über einen neuen [Issue](https://github.com/Purii/hdm-wim-devlab/issues/new) angefragt werden.

**(2) Bevor Events in PubSub veröffentlicht werden, muss sichergestellt werden, dass eine Subscription auf die gewünschten Topics vorhanden ist.**

```java
// init a SubscriptionHelper to use for prod environment for the given project (see Constants in SharedLib)
SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);
/**
 * this will create a subscription with id: "subscription-pull-topic-1-test1"
 * if the subscription already exists, we will use it
 */
sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");
```

**(3) Erstellen eines Events**
```java
Event insightEvent = new Event();
insightEvent.setData("insightEvent");
insightEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);}});
```
**(4) Um Events als Messages in PubSub zu veröffentlichen, wird der `PublishHelper` genutzt.**

```java
// init a PublishHelper to use for prod environment (false)
PublishHelper ph = new PublishHelper(false);
ph.Publish(insightEvent, Topic.TOPIC_1);
```

**(5) veröffentlichen von Events mit Hilfe der REST Schnittstelle.**

Endpointurl: `https://hdm-wim-devlab.appspot.com/publish`

| ParameterName  | ParameterValue |
| :------ | :------ |
| `topic` | `string` |
| `payload` | `string` |
| `attributes` | `string` |

Beispiel:
`https://hdm-wim-devlab.appspot.com/publishtopic=topic-1&payload=blubb_1&attributes=%7B%22EventType%22%3A%22insight%22%2C%22EventSource%22%3A%22user-interface%22%7D`
 
Value des `attributes` Parameters ist ein url codierter json string : `{"EventType":"insight","EventSource":"user-interface"}` => `%7B%22EventType%22%3A%22insight%22%2C%22EventSource%22%3A%22user-interface%22%7D`

**Hinweis:** Es findet keine Prüfung statt, ob die `topic` existiert. Diese bitte den `Constants` entnehmen.

### Events aus PubSub empfangen (Subscribe)
**(1) Message von PubSub empfangen.**

**Google Pub/Sub** unterstützt die ***Push und Pull-Methode***, um die veröffentlichten Messages von Pub/Sub zu den interessierten Subscription/Abonnement zu übermitteln.

***Pull-Subscription:*** Um neue Messages abzurufen, stellt der SubscriptionHelper eine Anfrage an den Pub/Sub-Server her. Der Pub/Sub-Server antwortet mit der Message. Sollten keine neuen Messages auf die abonnierten Topics veröffentlich wurden sein- bekommt der SubscriptionHelper eine Rückmeldung (Fehler) von Pub/Sub.

***Pull Beispiel***
```java
// In einem MessageReceiver werden die einzelnen Event bearbeitet, jede Gruppe braucht einen eignen Receiver
MessageReceiver receiver = new ExampleReceiver();

// Initialisierug des SubscriptionHelpers, dieser erwartet einen boolean ob sich der Enpoint local(true) oder auf der appengine befindet (false) und die Id des projects (zu finden in den Constants)
SubscriptionHelper sh = new SubscriptionHelper(false, Config.PROJECT_ID);

/**
 * Erstellt eine subscription mit folgender Id: "subscription-pull-topic-1-test1"
 * falls es bereits eine subscription mit dieser Id gibt, wird sie verwendet.
 * Parameter 1: SubscriptionType: PULL oder PUSH, wobei PUSH z.Z. noch nicht unterstützt wird
 * Parameter 2: Topic (siehe Constants)
 * Parameter 3: Suffix, welches ermöglicht mehrere Subscriptions auf eine Topic zu erstellen
 */
Subscription subscription = sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");

// Die folgende Methode erwartet die subscription und den receiver
sh.Subscribe(subscription, receiver);
```

***Push-Subscription(Stream):*** Der Pub/Sub-Server sendet, nach jeder Veröffentlichung einer neuen Message, diese als HTTP-Anfrage an einen interessierten Subscription/Abonnement (STREAM). Der SubscriptionHelper zeigt danach an, dass die Nachricht erfolgreich verarbeitet wurde und das Pub/Sub die Message aus dem Subscription/Abonnement löschen kann. Eine Nicht-Erfolgsreaktion zeigt an, dass die Nachricht erneut von Pub/Sub gesendet werden soll.

***Push Beispiel***
```java
```

**(2) Empfang der Message bestätigen.** 

Sobald eine Message an einen Subscription/Abonnent gesendet wird, muss der Abonnent entweder die Message bestätigen oder eine negative Rückmeldung an Pub/Sub geben. 

Es können zwei unterschiedliche Rückmeldungs-Varianten auftreten.

***positive acknowledgement(abbr.: ACK)*** Mit `consumer.ack` wird ein erfolgreicher Empfang einer Message ausgedrückt. 

***negative acknowledgement(abbr.: NACK)*** Mit `consumer.nack` wird Pub/Sub mitgeteilt, dass der Subscription/Abonnent die Message nicht verarbeiten konnte oder auch die aktuelle Message vom Abonnenten momentan nicht benötigt wird.

@bene, wird bei Pull benötigt? @patscho: jo, schau im receiver
findet im receiver statt => `consumer.ack` oder `consumer.nack`
