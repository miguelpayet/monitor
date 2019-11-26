package monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonitorApp {

	private static final Logger LOG = LogManager.getLogger(MonitorApp.class);

	public static void main(String[] args) {
		MonitorRunner monitor = new MonitorRunner();
		monitor.init();
		monitor.run();
	}

}
