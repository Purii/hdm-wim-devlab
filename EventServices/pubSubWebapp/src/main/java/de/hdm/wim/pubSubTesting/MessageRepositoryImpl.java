package de.hdm.wim.pubSubTesting;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import de.hdm.wim.sharedLib.events.Event;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 4/06/2017.
 */
/** Storage for de.hdm.wim.Message objects using Cloud Datastore. */
public class MessageRepositoryImpl implements MessageRepository {

	private static MessageRepositoryImpl instance;

	private String messagesKind = "messages";
	private KeyFactory keyFactory = getDatastoreInstance().newKeyFactory().setKind(messagesKind);

	private MessageRepositoryImpl() {
	}

	// retrieve a singleton instance
	public static synchronized MessageRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new MessageRepositoryImpl();
		}
		return instance;
	}

	@Override
	public void save(Event event) {
		// Save message to "messages"
		Datastore datastore = getDatastoreInstance();
		Key key = datastore.allocateId(keyFactory.newKey());

		Entity.Builder messageEntityBuilder = Entity.newBuilder(key)
			.set("messageId", event.getId());

		if (event.getData() != null) {
			messageEntityBuilder = messageEntityBuilder.set("data", event.getData());
		}

/*		if (message.getTopic() != null) {
			messageEntityBuilder = messageEntityBuilder.set("topic", message.getData());
		}*/

		if (event.getPublishTime() != null) {
			messageEntityBuilder = messageEntityBuilder.set("publishTime", event.getPublishTime());
		}
		datastore.put(messageEntityBuilder.build());
	}

	@Override
	public List<Event> retrieve(int limit) {
		// Get de.hdm.wim.Message saved in Datastore
		Datastore datastore = getDatastoreInstance();
		Query<Entity> query =
			Query.newEntityQueryBuilder()
				.setKind(messagesKind)
				.setLimit(limit)
				.addOrderBy(StructuredQuery.OrderBy.desc("publishTime"))
				.build();
		QueryResults<Entity> results = datastore.run(query);

		List<Event> events = new ArrayList<>();
		while (results.hasNext()) {
			Entity entity 	= results.next();
			Event event = new Event(entity.getString("id"));

			String data = entity.getString("data");
			if (data != null) {
				event.setData(data);
			}
/*			String topic = entity.getString("topic");
			if (topic != null) {
				event.setTopic(topic);
			}*/
			String publishTime = entity.getString("publishTime");
			if (publishTime != null) {
				event.setPublishTime(publishTime);
			}
			events.add(event);
		}
		return events;
	}

	private Datastore getDatastoreInstance() {
		return DatastoreOptions.getDefaultInstance().getService();
	}
}