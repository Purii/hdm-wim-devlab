package de.hdm.wim;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import de.hdm.wim.sharedLib.classes.Message;
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

	@Override
	public void save(Message message) {
		// Save message to "messages"
		Datastore datastore = getDatastoreInstance();
		Key key = datastore.allocateId(keyFactory.newKey());

		Entity.Builder messageEntityBuilder = Entity.newBuilder(key)
			.set("messageId", message.getId());

		if (message.getData() != null) {
			messageEntityBuilder = messageEntityBuilder.set("data", message.getData());
		}

		if (message.getTopic() != null) {
			messageEntityBuilder = messageEntityBuilder.set("topic", message.getData());
		}

		if (message.getPublishTime() != null) {
			messageEntityBuilder = messageEntityBuilder.set("publishTime", message.getPublishTime());
		}
		datastore.put(messageEntityBuilder.build());
	}

	@Override
	public List<Message> retrieve(int limit) {
		// Get de.hdm.wim.Message saved in Datastore
		Datastore datastore = getDatastoreInstance();
		Query<Entity> query =
			Query.newEntityQueryBuilder()
				.setKind(messagesKind)
				.setLimit(limit)
				.addOrderBy(StructuredQuery.OrderBy.desc("publishTime"))
				.build();
		QueryResults<Entity> results = datastore.run(query);

		List<Message> messages = new ArrayList<>();
		while (results.hasNext()) {
			Entity entity 	= results.next();
			Message message = new Message(entity.getString("id"));

			String data = entity.getString("data");
			if (data != null) {
				message.setData(data);
			}
			String topic = entity.getString("topic");
			if (topic != null) {
				message.setTopic(topic);
			}
			String publishTime = entity.getString("publishTime");
			if (publishTime != null) {
				message.setPublishTime(publishTime);
			}
			messages.add(message);
		}
		return messages;
	}

	private Datastore getDatastoreInstance() {
		return DatastoreOptions.getDefaultInstance().getService();
	}

	private MessageRepositoryImpl() {
	}

	// retrieve a singleton instance
	public static synchronized  MessageRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new MessageRepositoryImpl();
		}
		return instance;
	}
}