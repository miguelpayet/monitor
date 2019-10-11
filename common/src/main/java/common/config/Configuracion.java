package common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Monitor;
import common.Query;
import common.frecuencia.Frecuencia;
import common.notificacion.Notificacion;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuracion {

    private static final Logger LOG = LogManager.getLogger(Configuracion.class);
    private static Configuracion configuracion;

    public static Configuracion get() {
        if (configuracion == null) {
            try {
                configuracion = new Configuracion();
                configuracion.init();
                configuracion.initHikari();
            } catch (ConfigurationException ex) {
                LOG.error("error al leer configuración", ex);
            }
        }
        return configuracion;
    }

    private Map<String, String> basedatos;
    private HikariDataSource dataSource;
    private Map<String, Frecuencia> frecuencias;
    private List<Monitor> monitores;
    private Map<String, Notificacion> notificaciones;
    private List<Query> queries;

    private Configuracion() {
        basedatos = new HashMap<>();
        frecuencias = new HashMap<>();
        monitores = new ArrayList<>();
        notificaciones = new HashMap<>();
        queries = new ArrayList<>();
    }

    void addBasedatos(String nombre, String valor) {
        basedatos.put(nombre, valor);
    }

    void addFrecuencia(String nombre, Frecuencia frecuencia) {
        frecuencias.put(nombre, frecuencia);
    }

    void addMonitor(Monitor monitor) {
        monitores.add(monitor);
    }

    void addNotificacion(String nombre, Notificacion notificacion) {
        notificaciones.put(nombre, notificacion);
    }

    void addQuery(Query query) {
        this.queries.add(query);
    }

    private String getConexion() {
        return basedatos.get(LectorConfiguracion.CONEXION);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    Frecuencia getFrecuencia(String nombre) {
        return frecuencias.get(nombre);
    }

    public List<Monitor> getMonitores() {
        return monitores;
    }

    Notificacion getNotificacion(String nombre) {
        return notificaciones.get(nombre);
    }

    private String getPassword() {
        return basedatos.get(LectorConfiguracion.PASSWORD);
    }

    public List<Query> getQueries() {
        return queries;
    }

    private String getUsuario() {
        return basedatos.get(LectorConfiguracion.USUARIO);
    }

    private void init() throws ConfigurationException {
        LectorConfiguracion lector = new LectorConfiguracion(this);
        lector.init();
    }

    private void initHikari() {
        System.out.println(getConexion());
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getConexion());
        config.setUsername(getUsuario());
        config.setPassword(getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

}
