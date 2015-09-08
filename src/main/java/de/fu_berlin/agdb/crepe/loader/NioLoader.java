package de.fu_berlin.agdb.crepe.loader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.fu_berlin.agdb.crepe.core.Tag;
import de.fu_berlin.agdb.nio_tools.AConnectionHandler;
import de.fu_berlin.agdb.nio_tools.NioClient;

public class NioLoader extends AConnectionHandler implements ILoader {
	
	private static final Logger logger = LogManager.getLogger(NioLoader.class);
	
	private ArrayList<String> accumulatedEvents;
	
	public NioLoader(@Tag("port") String port, @Tag("host") String host) throws IOException {
		accumulatedEvents = new ArrayList<String>();
		
		NioClient nioClient = new NioClient(host, Integer.parseInt(port), this);
		Thread thread = new Thread(nioClient);
		thread.start();		
	}
	
	@Override
	public boolean load() {
		synchronized (accumulatedEvents) {
			return !accumulatedEvents.isEmpty();
		}
	}

	@Override
	public String getText() {
		synchronized (accumulatedEvents) {
			if(!accumulatedEvents.isEmpty()){
				String string = accumulatedEvents.get(0);
				accumulatedEvents.remove(0);
				return string;
			}
			return null;
		}
	}

	@Override
	public void handleReceivedData(byte[] data) {
		synchronized (accumulatedEvents) {
			try {
				accumulatedEvents.add(new String(data, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Malformed data received", e);
			}
		}
	}

	@Override
	public boolean hasMoreData() {
		return true;
	}
}
