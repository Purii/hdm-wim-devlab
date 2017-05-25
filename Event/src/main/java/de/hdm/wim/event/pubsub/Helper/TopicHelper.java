package de.hdm.wim.event.pubsub.Helper;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.PagedResponseWrappers;
import com.google.cloud.pubsub.spi.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

/**
 * Created by ben on 24/05/2017.
 */
public class TopicHelper {

	private static final Logger _logger = Logger.getLogger(TopicHelper.class);
	private final String _projectId;

	/**
	 * Instantiates a new TopicHelper.
	 */
	public TopicHelper() {
		this._projectId = ServiceOptions.getDefaultProjectId();
	}

	/**
	 * Instantiates a new TopicHelper.
	 *
	 * @param projectId unique project identifier, eg. "my-project-id"
	 */
	public TopicHelper(String projectId) {
		this._projectId = projectId;
	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {
		return _projectId;
	}

	/**
	 * Create a topic with the given name for a specific project.
	 *
	 * @param topicId the topic id, eg. "my-topic-id"
	 * @return the topic
	 * @throws Exception the exception
	 */
	public Topic createTopicIfNotExists( String topicId ) throws Exception {
		_logger.info("Creating Topic \"" + topicId +"\" ...");

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {

			Iterable<Topic> topics 	= getTopics();

			for(Topic topic : topics){
				if(topic.getNameAsTopicName().getTopic().equals(topicId))
				{
					_logger.info("Topic already exists: " + topicId);
					return topic;
				}
			}

			_logger.info("Successfully created topic: " + topicId);
			TopicName topicName 	= TopicName.create(_projectId, topicId);
			return topicAdminClient.createTopic(topicName);
		}
	}

	/**
	 * Get list of topics.
	 *
	 * @return the list of topics
	 * @throws Exception the exception
	 */
	public Iterable<Topic> getTopics() throws Exception{
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {

			ListTopicsRequest listTopicsRequest =
					ListTopicsRequest.newBuilder()
							.setProjectWithProjectName(ProjectName.create(_projectId))
							.build();

			PagedResponseWrappers.ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);
			Iterable<Topic> topics = response.iterateAll();
			for (Topic topic : topics) {
				_logger.info(topic.getName());
			}
			return topics;
			//return response;
		}
	}

	/**
	 * Deleting a topic with given id.
	 *
	 * @param topicId the topic id
	 * @throws Exception the exception
	 */
	public void deleteTopic(String topicId) throws Exception {

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			TopicName topicName = TopicName.create( _projectId, topicId );
			topicAdminClient.deleteTopic(topicName);
		}catch(Exception ex) {
			_logger.warn("Could not delete topic!", ex);
		}
	}

	/**
	 * Get topic by id.
	 *
	 * @param topicId the topic id
	 * @return the topic
	 * @throws Exception the exception
	 */
	public Topic getTopic(String topicId) throws Exception {

		//TODO: optimize => remove try catch
		Topic topic = null;
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			TopicName topicName = TopicName.create(_projectId, topicId);
			topic = topicAdminClient.getTopic(topicName);
		}catch (Exception ex) {
			_logger.info("Failed to get topic: {} " + topicId, ex);
		}
		return topic;
	}
}
