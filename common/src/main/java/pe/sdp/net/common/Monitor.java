package pe.sdp.net.common;

import pe.sdp.net.common.frecuencia.Frecuencia;
import pe.sdp.net.common.notificacion.Notificacion;
import pe.sdp.net.common.servicio.Servicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class Monitor {

	private static final Logger LOG = LogManager.getLogger(Monitor.class);
	private Frecuencia frecuencia;
	private long intervalo;
	private Notificacion notificacion;
	private Servicio servicio;
	private long timerInicial;
	private long timerUltimo;

	public Monitor(Servicio servicio, Frecuencia frecuencia, Notificacion notificacion) {
		this.frecuencia = frecuencia;
		this.notificacion = notificacion;
		this.servicio = servicio;
		intervalo = frecuencia.getIntervaloMillis();
		timerInicial = System.currentTimeMillis();
		timerUltimo = 0l;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public long getIntervalo() {
		return intervalo;
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

	public void setTimerUltimo(long timerUltimo) {
		this.timerUltimo = timerUltimo;
	}

}
