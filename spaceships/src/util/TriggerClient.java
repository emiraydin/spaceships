package util;

public class TriggerClient implements Runnable {

	@Override
	public void run() {
		TCPClient.start();
	}

}
