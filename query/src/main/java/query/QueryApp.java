package query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryApp {

	private static final Logger LOG = LogManager.getLogger(QueryApp.class.getName());

	public static void main(String[] args) {
		QueryRunner monitor = new QueryRunner();
		monitor.init();
		monitor.run();
	}

}