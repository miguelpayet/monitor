package pe.net.sdp.common.monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.net.sdp.common.config.Configuracion;
import pe.net.sdp.common.frecuencia.Frecuencia;
import pe.net.sdp.common.notificacion.Notificacion;
import pe.net.sdp.query.Query;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
@SuppressWarnings("unused")
class MonitorRunner implements CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(MonitorRunner.class);

    @Autowired
    private Configuracion config;
    private boolean corriendo;
    private List<Frecuencia> frecuencias;
    private List<Monitor> monitores;
    private List<Notificacion> notificaciones;
    private List<Query> queries;

    @SuppressWarnings("unused")
    MonitorRunner() {
    }

    public Configuracion getConfig() {
        return config;
    }

    private Date getFechaProceso() {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        return Date.from(utc.toInstant());
    }

    void init() throws MonitorException {
        if (config == null) {
            throw new MonitorException("config es nulo");
        }
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
        for (Monitor m : config.getMonitores()) {
            LOG.debug(String.format("ultimo timer es %d", m.getTimerUltimo()));
            LOG.debug(String.format("timer actual es %d", tiempo));
            LOG.info(String.format("%s - intervalo %d - lapso transcurrido %d", m.getNombreServicio(), m.getIntervalo(), tiempo - m.getTimerUltimo()));
            if ((m.getTimerUltimo() == 0) || (tiempo - m.getTimerUltimo() > m.getIntervalo())) {
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

    @Override
    public void run(String... args) throws Exception {
        LOG.info("iniciando proceso");
        try {
            init();
            corriendo = true;
            while (corriendo) {
                try {
                    Thread.sleep(10000);
                    procesarMonitores();
                } catch (InterruptedException ex) {
                    LOG.info("registró interrupción");
                }
            }
        } catch (MonitorException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        shutdown();
    }

    public void setConfig(Configuracion config) {
        this.config = config;
    }

    private void shutdown() {
        LOG.info("shutdown");
    }

    private void terminar() {
        LOG.info("terminando");
        corriendo = false;
    }
}

