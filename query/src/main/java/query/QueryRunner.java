package query;

import common.Query;
import common.config.Configuracion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class QueryRunner {

	private static final Logger LOG = LogManager.getLogger(QueryRunner.class);

	QueryRunner() {
	}

	void init() {
	}

	private ResultSetMetaData leerTitulos(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			LOG.debug("columna: " + rsmd.getColumnLabel(i));
		}
		return rsmd;
	}

	void run() {
		for (Query q : Configuracion.get().getQueries()) {
			try {
				ResultSet rs = q.ejecutar();
				ResultSetMetaData rsmd = leerTitulos(rs);
				while (rs.next()) {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						int type = rsmd.getColumnType(i);
						Object o = rs.getObject(i);
						LOG.debug(String.format("campo: %s - tipo %d - valor %s", rsmd.getColumnLabel(i), type, o));
					}
				}
			} catch (SQLException ex) {
				LOG.error("error al ejecutar query " + q.getNombre(), ex);
			} finally {
				try {
					q.cerrar();
				} catch (SQLException ex) {
					LOG.error("error al ejecutar query " + q.getNombre(), ex);
				}
			}
		}
	}

}