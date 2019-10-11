package monitor;

import common.Monitor;
import common.config.Configuracion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

class MonitorRunner {

	private static final Logger LOG = LogManager.getLogger(MonitorRunner.class);

	private boolean corriendo;
	private List<Monitor> monitores;

	MonitorRunner() {
		monitores = Configuracion.get().getMonitores();
		programarMonitores();
	}

	private Date getFechaProceso() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		return Date.from(utc.toInstant());
	}

	void init() {
		initShutdownHook();
	}

	private void initShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				terminar();
			}
		});
	}

	private void procesarMonitores() {
		Date fechaProceso = getFechaProceso();
		long tiempo = System.currentTimeMillis();
		for (Monitor m : monitores) {
			LOG.debug(String.format("ultimo timer es %d", m.getTimerUltimo()));
			LOG.debug(String.format("timer actual es %d", tiempo));
			LOG.info(String.format("%s - intervalo %d - lapso transcurrido %d", m.getNombreServicio(), m.getIntervalo(), tiempo - m.getTimerUltimo()));
			if ((tiempo - m.getTimerUltimo()) > m.getIntervalo()) {
				Thread thread = new Thread(() -> {
					m.procesar(fechaProceso);
					m.setTimerUltimo(tiempo);
				});
				try {
					thread.join();
				} catch (InterruptedException ex) {
					LOG.info("interrupted exception", ex);
				}
				thread.start();
			}
		}
	}

	private void programarMonitores() {
		long tiempo = System.currentTimeMillis();
		for (Monitor m : monitores) {
			LOG.info("monitoreando servicio: " + m.getNombreServicio());
			m.setTimerUltimo(tiempo);
		}
	}

	void run() {
		LOG.info("iniciando proceso");
		corriendo = true;
		while (corriendo) {
			try {
				Thread.sleep(10000);
				procesarMonitores();
			} catch (InterruptedException ex) {
				LOG.info("registró interrupción");
			}
		}
		shutdown();
	}

	private void shutdown() {
		LOG.info("shutdown");
	}

	private void terminar() {
		LOG.info("terminando");
		corriendo = false;
	}

}

