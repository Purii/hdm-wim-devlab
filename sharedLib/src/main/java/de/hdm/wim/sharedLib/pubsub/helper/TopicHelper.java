package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.PagedResponseWrappers;
import com.google.cloud.pubsub.spi.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

/**
 * TopicHelper
 *
 * @author Benedikt Benz
 * @createdOn 24.06.2017
 */
public class TopicHelper {

	private static final Logger LOGGER 	= Logger.getLogger(TopicHelper.class);
	private String PROJECT_ID			= ServiceOptions.getDefaultProjectId();

	/**
	 * Instantiates a new TopicHelper.
	 */
	public TopicHelper() {}

	/**
	 * Instantiates a new TopicHelper.
	 *
	 * @param projectId the project id
	 */
	public TopicHelper(String projectId){
		this.PROJECT_ID = projectId;
	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {
		return PROJECT_ID;
	}

	/**
	 * Create a topic with the given name for a specific project, if topic exists it
	 * returns the existing topic.
	 *
	 * @param topicId the topic id, eg. "my-topic-id" EXAMPLE: {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic.CEP_SESSIONINSIGHTS#TOPIC_ID}
	 * @return the topic
	 * @throws Exception the exception
	 */
	public Topic createTopicIfNotExists( String topicId ) throws Exception {
		LOGGER.info("Creating Topic \"" + topicId +"\" ...");

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {

			Iterable<Topic> topics 	= getTopics();

			for(Topic topic : topics){
				if(topic.getNameAsTopicName().getTopic().equals(topicId))
				{
					LOGGER.info("Topic already exists, lets use it!");
					return topic;
				}
			}

			LOGGER.info("Successfully created topic: " + topicId);
			TopicName topicName 	= TopicName.create(PROJECT_ID, topicId);
			return topicAdminClient.createTopic(topicName);
		}
	}

	/**
	 * Get all topics.
	 *
	 * @return List of topics
	 * @throws Exception the exception
	 */
	public Iterable<Topic> getTopics() throws Exception{
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {

			ListTopicsRequest listTopicsRequest =
					ListTopicsRequest.newBuilder()
							.setProjectWithProjectName(ProjectName.create(PROJECT_ID))
							.build();

			PagedResponseWrappers.ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);
			Iterable<Topic> topics = response.iterateAll();
			LOGGER.info("Existing topics:");
			for (Topic topic : topics) {
				LOGGER.info(topic.getName());
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
			TopicName topicName = TopicName.create(PROJECT_ID, topicId );
			topicAdminClient.deleteTopic(topicName);
		}catch(Exception ex) {
			LOGGER.warn("Could not delete topic!", ex);
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
			TopicName topicName = TopicName.create(PROJECT_ID, topicId);
			topic = topicAdminClient.getTopic(topicName);
		}catch (Exception ex) {
			LOGGER.info("Failed to get topic: {} " + topicId, ex);
		}
		return topic;
	}
}
