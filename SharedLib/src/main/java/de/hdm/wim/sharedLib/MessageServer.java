package de.hdm.wim.sharedLib;

import com.google.gson.Gson;
import de.hdm.wim.sharedLib.classes.PubSubMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 * Created by ben on 2/06/2017.
 * this class is only for testing purposes!
 */
public class MessageServer {

	private static final Executor _serverExecutor	= Executors.newSingleThreadExecutor();
	private static final int _port 					= 9999;
	private static final long _messagePeriodSeconds = 10;
	private static final Logger _logger 			= Logger.getLogger(MessageServer.class);

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

		_serverExecutor.execute(new SteamingServer(messageQueue));

		while (true) {
            PubSubMessage message   = PubSubMessage.generate("blubb",Integer.toString(id));
            Gson gson               = new Gson();

            messageQueue.put(gson.toJson(message));
            Thread.sleep(TimeUnit.SECONDS.toMillis(_messagePeriodSeconds));

            id++;
        }
	}

	private static class SteamingServer implements Runnable {
		private final BlockingQueue<String> messageQueue;

		/**
		 * Instantiates a new Steaming server.
		 *
		 * @param messageQueue the event queue
		 */
		private SteamingServer(BlockingQueue<String> messageQueue) {
			this.messageQueue = messageQueue;
		}

		@Override
		public void run() {

			try (
				ServerSocket serverSocket   = new ServerSocket(_port);
				Socket clientSocket         = serverSocket.accept();
				PrintWriter pw             = new PrintWriter(clientSocket.getOutputStream(), true)
			)
			{
				while (true) {
					String message = messageQueue.take();

					_logger.info(message);
					pw.println(message);
				}
			} catch (IOException|InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}