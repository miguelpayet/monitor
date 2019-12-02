package pe.net.sdp.common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class GrabadorMedida {

    private static final Logger LOG = LogManager.getLogger(LectorPaginaHtml.class);
    private final String SQL = "insert into medida (direccion, hora, nombre, duracion_conexion, duracion_descarga, " +
            "duracion_descarga_recursos, tamaño, response_code, excepcion) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private LectorPaginaHtml lector;
    private Medida medida;
    private String nombre;

    GrabadorMedida(String unNombre, Medida unaMedida, LectorPaginaHtml unLector) {
        nombre = unNombre;
        medida = unaMedida;
        lector = unLector;
    }

    void grabar() {
        try {
            PreparedStatement pst = null;
            pst.setString(1, lector.getDireccion());
            pst.setObject(2, lector.getFechaProceso());
            pst.setString(3, nombre);
            pst.setLong(4, medida.getConexion().total());
            pst.setLong(5, medida.getDescarga().total());
            pst.setLong(6, medida.getDescargaRecursos().total());
            pst.setLong(7, medida.getSize());
            pst.setLong(8, medida.getResponseCode());
            pst.setString(9, medida.getExcepcion());
            pst.execute();
        } catch (SQLException ex) {
            LOG.error("al grabar medida", ex);
        }
    }

}
