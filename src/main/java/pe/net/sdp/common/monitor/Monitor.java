package pe.net.sdp.common.monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.net.sdp.common.frecuencia.Frecuencia;
import pe.net.sdp.common.notificacion.Notificacion;
import pe.net.sdp.common.servicio.Servicio;

import java.util.Date;

public class Monitor {

    private static final Logger LOG = LogManager.getLogger(Monitor.class);
    private Frecuencia frecuencia;
    private long intervalo;
    private String nombre;
    private Notificacion notificacion;
    private Servicio servicio;
    private long timerUltimo;

    public Monitor(Servicio servicio, Frecuencia frecuencia, Notificacion notificacion) {
        this.frecuencia = frecuencia;
        this.notificacion = notificacion;
        this.servicio = servicio;
        intervalo = frecuencia.getIntervaloMillis();
        timerUltimo = 0l;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public long getIntervalo() {
        return intervalo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreServicio() {
        return servicio.getNombre();
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public long getTimerUltimo() {
        return timerUltimo;
    }

    public void procesar(Date fechaProceso) {
        LOG.info("procesando monitor: " + getNombreServicio());
        servicio.procesar(fechaProceso);
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setTimerUltimo(long timerUltimo) {
        this.timerUltimo = timerUltimo;
    }

}
