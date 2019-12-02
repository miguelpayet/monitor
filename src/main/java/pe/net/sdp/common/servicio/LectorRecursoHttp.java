package pe.net.sdp.common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class LectorRecursoHttp {

	private static final Logger LOG = LogManager.getLogger(LectorRecursoHttp.class);
	private HttpURLConnection conn;
	private byte[] contenido;
	private String direccion;
	private Integer responseCode;

	LectorRecursoHttp(String unaDireccion) {
		direccion = unaDireccion;
		responseCode = -1;
	}

	void conectar() throws ExcepcionRecursoHttp {
		try {
			URL url = new URL(direccion);
			conn = (HttpURLConnection) url.openConnection();
			responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new ExcepcionRecursoHttp(String.format("%s: response_code: %d", direccion, conn.getResponseCode()));
			}
		} catch (IOException | ExcepcionRecursoHttp ex) {
			throw new ExcepcionRecursoHttp(String.format("direccion [%s] - %s", direccion, ex.getMessage()), ex);
		}
	}

	byte[] getContenido() {
		return contenido;
	}

	String getContenidoString() {
		return new String(contenido);
	}

	private String getProtocolo() {
		String protocolo = "";
		int iend = direccion.indexOf(":");
		if (iend != -1) {
			protocolo = direccion.substring(0, iend);
		}
		return protocolo;
	}

	int getResponseCode() {
		return responseCode == null ? -1 : responseCode;
	}

	byte[] leerContenido() {
		try {
			InputStream stream = conn.getInputStream();
			contenido = stream.readAllBytes();
		} catch (IOException ex) {
			LOG.error("[" + direccion + "]", ex);
		}
		return contenido;
	}

}
