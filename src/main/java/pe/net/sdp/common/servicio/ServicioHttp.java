package pe.net.sdp.common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

// todo: https://validator.w3c.org
// todo: separar en 2 clases ?

public class ServicioHttp extends Servicio {

	private static final Logger LOG = LogManager.getLogger(ServicioHttp.class);

	public ServicioHttp(String nombre, String direccion, Integer puerto) {
		super(nombre, direccion, puerto);
	}


	public void procesar(Date unaFecha) {
		LOG.debug(String.format("procesando: %s", getNombre()));
		LectorPaginaHtml lector = new LectorPaginaHtml(getDireccion(), unaFecha);
		lector.init();
		lector.procesar();
	}

}
