package common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// todo: https://validator.w3c.org
// todo: separar en 2 clases ?

public class ServicioHttp extends Servicio {

	private static final Logger LOG = LogManager.getLogger(ServicioHttp.class);

	public ServicioHttp(String nombre, String direccion, Integer puerto) {
		super(nombre, direccion, puerto);
	}


	public void procesar() {
		LOG.debug(String.format("procesando: %s", getNombre()));
		LectorPaginaHtml lector = new LectorPaginaHtml(getDireccion());
		lector.init();
		lector.procesar();
	}

}
