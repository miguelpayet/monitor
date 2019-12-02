package pe.net.sdp.query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryApp {

	private static final Logger LOG = LogManager.getLogger(QueryApp.class.getName());

	public static void main(String[] args) {
		LOG.info("inicio");
		QueryRunner runner = new QueryRunner();
		runner.init();
		runner.run();
		LOG.info("fin");
	}

}