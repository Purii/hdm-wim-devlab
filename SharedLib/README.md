# SharedLib

library für Klassen, die von allen Gruppen genutzt werden.

## Inhalt

### Message

Eine Message, die an PubSub gesendet wird. Sie enthält `data`, `topic` und `attributes`

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