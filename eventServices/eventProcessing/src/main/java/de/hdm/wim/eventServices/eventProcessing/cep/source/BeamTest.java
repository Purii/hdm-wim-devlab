package de.hdm.wim.eventServices.eventProcessing.cep.source;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_1;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.log4j.Logger;

/**
 * Created by ben on 17/06/2017.
 */
public class BeamTest {

	private static final Logger LOGGER 	= Logger.getLogger(BeamTest.class);
	private static Helper helper = new Helper();
	private static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {

		String subscription = "projects/hdm-wim-devlab/subscriptions/subscription-pull-topic-1";
		String topic = "projects/hdm-wim-devlab/topics/" + TOPIC_1.TOPIC_ID;

		// Start by defining the options for the pipeline.
		PipelineOptions options = PipelineOptionsFactory.create();

		// Then create the pipeline.
		Pipeline p = Pipeline.create(options);

		PCollection<PubsubMessage> streamData =
			p
				.apply(
					PubsubIO
						.readMessagesWithAttributes()
						.fromTopic(topic)
				)
				//.setCoder(StringUtf8Coder.of())
			;

		streamData.apply(
			ParDo.of(new test())
		);

		p.run().waitUntilFinish();
	}

	private static String decodeBase64(String data) {
		return new String(BaseEncoding.base64().decode(data));
	}

	public static class ShowMessages extends PTransform<PCollection<PubsubMessage>,
		PCollection<IEvent>> {

		@Override
		public PCollection<IEvent> expand(PCollection<PubsubMessage> messages) {

			PCollection<IEvent> data = messages.apply(
				ParDo.of(new test()));

			return data;
		}
	}

	private static class test extends DoFn<PubsubMessage, IEvent> {
		@ProcessElement
		public void processElement(ProcessContext c) throws Exception {

			// Get the input element from ProcessContext.
			//String payload = new String(c.element().getPayload(), "UTF-8");
			String msgJson = gson.toJson(c.element(), com.google.pubsub.v1.PubsubMessage.class);

			IEvent evt = helper.convertToIEvent(msgJson);

			LOGGER.info("Payload: " + evt.getData());
			LOGGER.info("Attributes: " + evt.getAttributesAsString());
			// Use ProcessContext.output to emit the output element.
			c.output(evt);
		}
	}
}