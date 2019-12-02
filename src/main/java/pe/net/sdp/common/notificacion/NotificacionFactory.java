package pe.net.sdp.common.notificacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

//todo agregar validación de que exista en el map

public class NotificacionFactory {

	private static final Logger LOG = LogManager.getLogger(NotificacionFactory.class);
	private static final Map<String, Class<? extends Notificacion>> TIPOS;

	static {
		TIPOS = new HashMap<>();
		TIPOS.put("correo", NotificacionCorreo.class);
	}

	public static Notificacion crearNotificacion(String tipo, String nombre, String direccion) {
		Notificacion nuevaNotif = null;
		try {
			Class<? extends Notificacion> clase = TIPOS.get(tipo);
			Constructor<? extends Notificacion> constructor = clase.getConstructor(String.class, String.class);
			nuevaNotif = constructor.newInstance(nombre, direccion);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
			LOG.error("error al crear notificación ", ex);
		}
		return nuevaNotif;
	}

}
