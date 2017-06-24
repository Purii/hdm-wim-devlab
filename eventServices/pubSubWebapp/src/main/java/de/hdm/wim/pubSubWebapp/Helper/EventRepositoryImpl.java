/**
 * Copyright 2017 Google Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.hdm.wim.pubSubWebapp.Helper;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.events.IEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Storage for Message objects using Cloud Datastore.
 * Created by ben on 24/06/2017.
 */

/**
 * Storage for Message objects using Cloud Datastore.
 */
public class EventRepositoryImpl implements EventRepository {

	private static EventRepositoryImpl instance;

	private String eventsKind = "events";
	private KeyFactory keyFactory = getDatastoreInstance().newKeyFactory().setKind(eventsKind);

	private EventRepositoryImpl() {
	}

	// retrieve a singleton instance
	public static synchronized EventRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new EventRepositoryImpl();
		}
		return instance;
	}

	@Override
	public void save(IEvent event) {
		// Save event to "events"
		Datastore datastore = getDatastoreInstance();
		Key key = datastore.allocateId(keyFactory.newKey());

		Entity.Builder eventEntityBuilder = Entity.newBuilder(key)
			.set("eventId", event.getId());

		// get the data from event

		if (event.getData() != null) {
			eventEntityBuilder = eventEntityBuilder.set("data", event.getData());
		}

		if (event.getAttributes() != null) {
			eventEntityBuilder = eventEntityBuilder
				.set("attributes", event.getAttributesAsString());
		}

		if (event.getPublishTime() != null) {
			eventEntityBuilder = eventEntityBuilder.set("publishTime", event.getPublishTime());
		}

		datastore.put(eventEntityBuilder.build());
	}

	@Override
	public List<IEvent> retrieve(int limit) {

		// Get events saved in Datastore
		Datastore datastore = getDatastoreInstance();
		Query<Entity> query =
			Query.newEntityQueryBuilder()
				.setKind(eventsKind)
				.setLimit(limit)
				.addOrderBy(StructuredQuery.OrderBy.desc("publishTime"))
				.build();

		QueryResults<Entity> results = datastore.run(query);

		List<IEvent> events = new ArrayList<>();
		while (results.hasNext()) {
			Entity entity = results.next();

			Event event = new Event(entity.getString("eventId"));

			String data = entity.getString("data");
			if (data != null) {
				event.setData(data);
			}
			String attributes = entity.getString("attributes");
			if (attributes != null) {
				event.setAttributes(data);
			}
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