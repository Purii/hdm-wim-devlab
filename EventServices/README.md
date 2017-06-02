# Event Gruppe

Als Framework zur Realisierung von `CEP` nutzt die Gruppe [`Apache Flink`](https://flink.apache.org/). Für `Flink` wird ein [Docker Image](https://flink.apache.org/news/2017/05/16/official-docker-image.html) angeboten, welches von der Community bereitgestellt und von der `Apache Flink PMC` beworben wird.

```
# Ausgabe der Logs
docker run -t -p 8081:8081 flink local

# Detached mode
docker run -d -p 8081:8081 flink local
```
