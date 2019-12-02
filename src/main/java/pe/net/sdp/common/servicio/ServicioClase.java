package pe.net.sdp.common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class ServicioClase extends Servicio {

    private static final Logger LOG = LogManager.getLogger(ServicioClase.class);

    public ServicioClase(String nombre, String direccion, Integer puerto) {
        super(nombre, direccion, puerto);
    }

    @Override
    public void procesar(Date unaFecha) {
        LOG.debug("procesar clase " + this.getClass().getCanonicalName());
        try {
            Class c = Class.forName(this.getDireccion());
            Class[] clases = new Class[1];
            clases[0] = Date.class;
            Method m = c.getMethod("run", clases);
            m.setAccessible(true);
            m.invoke(c.getDeclaredConstructor().newInstance(), unaFecha);
        } catch (ClassNotFoundException ex) {
            LOG.error("error al instanciar", ex);
        } catch (NoSuchMethodException ex) {
            LOG.error("error al buscar método", ex);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            LOG.error("error al ejecutar método", ex);
        }
    }

}
