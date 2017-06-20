package de.hdm.wim.sharedLib.testing;

import com.google.common.io.BaseEncoding;
import org.apache.log4j.Logger;

/*import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO.Read;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PInput;
import org.apache.beam.sdk.values.POutput;
import org.apache.beam.sdk.values.TypeDescriptors;*/


/**
 * Created by ben on 17/06/2017.
 */
public class TEST123 {

	private static final Logger LOGGER 	= Logger.getLogger(TEST123.class);

/*	public static void main(String[] args) throws Exception {

		// Start by defining the options for the pipeline.
		PipelineOptions options = PipelineOptionsFactory.create();

		// Then create the pipeline.
		Pipeline p = Pipeline.create(options);

		PCollection<PubsubMessage> streamData =
			p
				.apply(
					PubsubIO
						.readMessages()
						.fromSubscription("projects/hdm-wim-devlab/subscriptions/subscription-pull-topic-1")
				)

				//.setCoder(StringUtf8Coder.of())
			;

		streamData.apply(
			ParDo.of(new test())
		);


		p.run().waitUntilFinish();
	}*/


/*	public static class ShowMessages extends PTransform<PCollection<PubsubMessage>,
      PCollection<PubsubMessage>> {

		@Override
		public PCollection<PubsubMessage> expand(PCollection<PubsubMessage> messages) {

			PCollection<PubsubMessage> data = messages.apply(
				ParDo.of(new test()));

			return data;
		}
	}*/

	/*private static class test extends DoFn<PubsubMessage, PubsubMessage> {
		@ProcessElement
		public void processElement(ProcessContext c) throws Exception {


			// Get the input element from ProcessContext.
			String payload = new String(c.element().getPayload(), "UTF-8");

			LOGGER.info("Payload: " + payload);
			// Use ProcessContext.output to emit the output element.
			c.output(c.element());
		}
	}*/

	private static String decodeBase64(String data) {
		return new String(BaseEncoding.base64().decode(data));
	}




}