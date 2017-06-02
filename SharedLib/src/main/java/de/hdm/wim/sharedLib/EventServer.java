package de.hdm.wim.sharedLib;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Created by ben on 18/04/2017.
 */
public class EventServer {
	private static final Executor SERVER_EXECUTOR	= Executors.newSingleThreadExecutor();
	private static final int PORT					= 9999;
	private static final long Event_PERIOD_SECONDS	= 1;

	/**
	 * The entry point of this application. It will send Events every second until terminated.
	 *
	 * @param args the input arguments
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		BlockingQueue<String> eventQueue        = new ArrayBlockingQueue<>(1000);

		SERVER_EXECUTOR.execute(new SteamingServer(eventQueue));

       while (true) {
            EventGenerator evgg   = new EventGenerator();
            Event Event         = 	evgg.GenerateEvent();
            Gson gson               = new Gson();

            eventQueue.put(gson.toJson(Event));
            Thread.sleep(TimeUnit.SECONDS.toMillis(Event_PERIOD_SECONDS));
        }
	}

	private static class SteamingServer implements Runnable {
		private final BlockingQueue<String> eventQueue;
		private final Logger logger = Logger.getLogger(EventServer.class);

		/**
		 * Instantiates a new Steaming server.
		 *
		 * @param eventQueue the event queue
		 */
		public SteamingServer(BlockingQueue<String> eventQueue) {
			this.eventQueue = eventQueue;
		}

		@Override
		public void run() {
			try (
				ServerSocket serverSocket   = new ServerSocket(PORT);
				Socket clientSocket         = serverSocket.accept();
				PrintWriter out             = new PrintWriter(clientSocket.getOutputStream(), true);
			)
			{
				while (true) {
					String Event = eventQueue.take();

					logger.info(String.format("Writing \"%s\" to the socket.", Event));
					out.println(Event);
				}
			} catch (IOException|InterruptedException e) {
				throw new RuntimeException("Server error", e);
			}
		}
	}
}