# SharedLib

library für Klassen, die von allen Gruppen genutzt werden.

## Install

`mvn clean install` um lib im lokalen maven repository zu installieren

## Inhalt

### Message

Eine Message, die an PubSub gesendet wird. Sie enthält `data`, `topic` und `attributes`. `data` ist der payload und `topic` ist die topic unter der die Message auf PubSub veröffentlicht wird.

### PubSubMessage

Nachdem die Message durch den Publisher an PubSub übermittelt wurde, liegt sie in diesem Format vor
````
{
  "data": string,
  "attributes": {
    string: string,
    ...
  },
  "messageId": string,
  "publishTime": string,
}
````
[source](https://cloud.google.com/pubsub/docs/reference/rest/v1/PubsubMessage)

`data` ist der Inhalt der Message und ist base64-encoded. Es können bis zu 100 `attributes` als Metainformation zur Message festgelegt
werden. Format: `key:value`, hier bitte `constants` aus der `SharedLib
nutzen, damit das ganze einheitlich bleibt.

`messageId` und `publishTime` werden vom System festgelegt.

### MessageServer

Der MessageServer dient der Simulation von eintreffenden PubSubMessages. Standardmäßig wird alle `10` Sekunden eine PubSubMessage an `localhost:9999` geschickt.
Mithilfe von `telnet localhost port` kann man die eintreffenden PubSubMessages sehen.

### PublishHelper

Mit Hilfe des PublishHelpers, werden Messages an PubSub übermittelt.

Beispiel:

```
// publish one message every second
public static void main(String[] args) throws Exception {
    int i 				= 1;

    PublishHelper ph 	= new PublishHelper(true);

    while (i <= 3) {
    	Message message   = Message.generate("blubb_" + i, Topic.TOPIC_1);

        ph.Publish(message);

        Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

	i++;
    }
}
```

`ExamplePublish.java`

### SubscriptionHelper
### TopicHelper

Mit dem Topic helper werden topics erstellt, existieren die Topics bereits, werden diese verwendet.