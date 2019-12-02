package pe.net.sdp.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pe.net.sdp.common.frecuencia.Frecuencia;
import pe.net.sdp.common.monitor.Monitor;
import pe.net.sdp.common.notificacion.Notificacion;
import pe.net.sdp.query.Query;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "monitor")
@Validated
public class Configuracion {

    private List<Frecuencia> frecuencias;
    private List<Monitor> monitores;
    private List<Notificacion> notificaciones;
    private List<Query> queries;

    public Configuracion() {
    }

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public List<Monitor> getMonitores() {
        return monitores;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public void setMonitores(List<Monitor> monitores) {
        this.monitores = monitores;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

}
