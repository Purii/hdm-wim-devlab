# PubSub

--> Andere an den Start bringen. Arbeit mit SharedLib demonstrieren

Über die `SharedLib` werden Klassen zur Verfügung gestellt, um die Integration mit PubSub zu vereinfachen.

* [Klasse Event](#klasseevent)
* [PublishHelper](#publishhelper)
* [SuscriptionHelper](#subscriptionhelper)


## Klasse Event
In der `SharedLib` wird die Klasse `Event` zur Verfügung gestellt.
Ein Objekt dieser Klasse kann mithilfe des PublishHelper veröffentlicht oder mithilfe des SubscriptionHelpers empfangen werden.
Die Attribute der Klasse bauen auf denen der [Message-Klasse von Google PubSub]((https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage):) auf:

| Feld  | Datentyp | Methoden | Beschreibung |
| :------ | :------ | :------ | :------ |
| `data` | `string (bytes format)` | `String getData() | setData(String data)` | ...Todo |
| `attributes` | `map (key: string, value: string)` | `String getAttribute(String attribute) | setAttribute(String key, String value)` | ...Todo |
| `messageId` | `string` | `getData() : string | String getMessageId()`  | *wird von PubSub gesetzt* |
| `publishTime` | `string (Timestamp format)` | String `getPublishTime() | *wird von PubSub gesetzt*. Timestamp im RFC3339 UTC "Zulu" Format (Genauigkeit in Nanosekunden). Beispiel: `2014-10-02T15:01:23.045123456Z` |


## PublishHelper – Events veröffentlichen
Um Events als Messages in PubSub zu veröffentlichen, kann der `PublishHelper` genutzt werden.

```java
# Import der Konstanten
import de.hdm.wim.sharedLib.Constants;

# Import der Klasse Event
import de.hdm.wim.sharedLib.events.Event;

# Import der Klasse PublishHelper
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;


public class ExamplePublish {

    public static void main(String[] args) throws Exception {
        # Instanz von PublishHelper erstellen
        PublishHelper ph    = new PublishHelper(false);

        # Event generieren (siehe Abschnitt zur Eventklasse)
        Event event = new Event()
        ....

        # Event auf PubSub Topic veröffentlichen
        ph.Publish(event, Constants.Topic.TOPIC_1);
    }
}
```

## SubscriptionHelper
Pro Gruppe ein MessageReceiver --> Dazu bieten wir Vorlage an
SublishHelper ohne AppEninge + Anbieten über SharedLibrary 
Warten auf Bene mit Prefix + Receiver