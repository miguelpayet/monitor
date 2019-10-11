package common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServicioFactory {

    private static final Logger LOG = LogManager.getLogger(ServicioFactory.class);
    private static final Map<String, Class<? extends Servicio>> TIPOS;

    static {
        TIPOS = new HashMap<>();
        TIPOS.put("http", ServicioHttp.class);
        TIPOS.put("clase", ServicioClase.class);
    }

    public static Servicio crearServicio(String tipo, String nombre, String direccion, int puerto) {
        Servicio servicioNuevo = null;
        try {
            Class<? extends Servicio> clase = TIPOS.get(tipo);
            Constructor<? extends Servicio> constructor = clase.getConstructor(String.class, String.class, Integer.class);
            servicioNuevo = constructor.newInstance(nombre, direccion, puerto);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            LOG.error("error al crear servicio ", ex);
        }
        return servicioNuevo;
    }

}
