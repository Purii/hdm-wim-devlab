package de.hdm.wim.sharedLib.testing;

import com.google.gson.Gson;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentInformationEvent;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import de.hdm.wim.sharedLib.pubsub.classes.PubSubMessage;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Time;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by nilsb on 24.06.2017.
 */
public class MessageServer2 {

	private static final Executor SERVER_EXECUTOR 	 = Executors.newSingleThreadExecutor();
	private static final int PORT 					 = 9999;
	private static final long MESSAGE_PERIOD_SECONDS = 6;
	private static final Logger LOGGER 				 = Logger.getLogger(MessageServer2.class);

	/**
	 * The entry point of this application. It will send one message every 10 seconds until terminated
	 * or the queue is full
	 *
	 * use "telnet localhost port" in cmd to see the messages
	 *
	 * @param args the input arguments
	 * @throws IOException the io exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		BlockingQueue<String> messageQueue  = new ArrayBlockingQueue<>(100);
		int id 								= 0;

		SERVER_EXECUTOR.execute(new MessageServer2.SteamingServer(messageQueue));
		DocumentInformationEvent docinfevt = new DocumentInformationEvent();
		docinfevt.setId("1");
		docinfevt.setData("bla");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		docinfevt.setPublishTime(timeStamp);
		docinfevt.setEventSource(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION);
		docinfevt.setDocumentId("123");
		docinfevt.setDocumentBelongsToProject("High Net");

		DocumentInformationEvent docinfevt2 = new DocumentInformationEvent();
		docinfevt2.setId("2");
		docinfevt2.setData("bla");
		docinfevt2.setEventSource(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION);
		docinfevt2.setPublishTime(timeStamp);
		docinfevt2.setDocumentId("233");
		docinfevt2.setDocumentBelongsToProject("High Net");

		DocumentInformationEvent docinfevt3 = new DocumentInformationEvent();
		docinfevt3.setId("3");
		docinfevt3.setData("bla");
		docinfevt3.setEventSource(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION);
		docinfevt3.setPublishTime(timeStamp);
		docinfevt3.setDocumentId("124");
		docinfevt3.setDocumentBelongsToProject("High Net");


		StayAliveEvent stayAlive = new StayAliveEvent();
		stayAlive.setId("4");
		stayAlive.setData("blubb");
		stayAlive.setPublishTime(timeStamp);
		stayAlive.setUserId("1234");

		while (true) {
			PubSubMessage message   = PubSubMessage.getRandom("blubb_" + id,Integer.toString(id));
			//String message 			= "test";
			Gson gson               = new Gson();

			messageQueue.put(gson.toJson(docinfevt));
			messageQueue.put(gson.toJson(stayAlive));
			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
			messageQueue.put(gson.toJson(docinfevt2));
			if(id<5) {
				messageQueue.put(gson.toJson(stayAlive));
				Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
				messageQueue.put(gson.toJson(docinfevt3));
				messageQueue.put(gson.toJson(stayAlive));
				Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
			}
			else{
				Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
				Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
				Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
			}
			id++;
			System.out.println(id);
		}
	}

	private static class SteamingServer implements Runnable {
		private final BlockingQueue<String> messageQueue;

		/**
		 * Instantiates a new Steaming server.
		 *
		 * @param messageQueue the EVENT queue
		 */
		private SteamingServer(BlockingQueue<String> messageQueue) {
			this.messageQueue = messageQueue;
		}

		@Override
		public void run() {

			try (
				ServerSocket serverSocket   = new ServerSocket(PORT);
				Socket clientSocket         = serverSocket.accept();
				PrintWriter pw             = new PrintWriter(clientSocket.getOutputStream(), true)
			)
			{
				while (true) {
					String message = messageQueue.take();

					LOGGER.info(message);
					pw.println(message);
				}
			} catch (IOException|InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
