package common.frecuencia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

//todo agregar validación de que exista en el map

public class FrecuenciaFactory {

	private static final Logger LOG = LogManager.getLogger(FrecuenciaFactory.class);
	private static final Map<String, Class<? extends Frecuencia>> TIPOS;

	static {
		TIPOS = new HashMap<>();
		TIPOS.put("minuto", FrecuenciaMinuto.class);
	}

	public static Frecuencia crearFrecuencia(String tipo, String nombre, Integer intervalo) {
		Frecuencia nuevaFrec = null;
		try {
			Class<? extends Frecuencia> clase = TIPOS.get(tipo);
			Constructor<? extends Frecuencia> constructor = clase.getConstructor(String.class, Integer.class);
			nuevaFrec = constructor.newInstance(nombre, intervalo);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
			LOG.error("error al crear common.frecuencia ", ex);
		}
		return nuevaFrec;
	}

}
