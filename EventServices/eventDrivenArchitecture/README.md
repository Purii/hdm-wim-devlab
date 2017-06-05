# PubSub

## PubSub in a nutshell
- Ein `publisher` erstellt eine `topic`.
- Ein `subscriber` erstellt eine `subscription` auf diese `topic`.
- Der `publisher` sendet eine `message` an diese `topic`.
- Der `subscriber` empfängt die `message` via `push` oder `pull`, je nach Konfiguration.
- Der `subscriber` bestätigt den Empfang der `message` und diese wird aus der `queue` gelöscht.

## PubSub messages

Die Messages welche von PubSub verschickt werden haben folgendes Format:

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