package pe.net.sdp.query;

import pe.sdp.net.common.Query;
import pe.sdp.net.common.config.Configuracion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class QueryRunner {

    private static final Logger LOG = LogManager.getLogger(QueryRunner.class);

    QueryWriter writer;

    void init() {
    }

    void run() {
        for (Query q : Configuracion.get().getQueries()) {
            LOG.info("query: " + q.getNombre());
            try {
                ResultSet rs = q.ejecutar();
                writer = new QueryWriter(q, rs);
                writer.write();
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

    public void run(Date unaFecha) {
        try {
            run();
        } catch (Exception ex) {
            LOG.error("error al ejecutar QueryRunner", ex);
        }
    }

}