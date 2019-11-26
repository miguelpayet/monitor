package common.servicio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

class LectorPaginaHtml {

	private static final int HILOS = 5;
	private static final Logger LOG = LogManager.getLogger(LectorPaginaHtml.class);
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) Gecko/20100101 Firefox/52.0 MonitorSdP";

	static {
		System.setProperty("http.agent", USER_AGENT);
	}

	private String direccion;
	private Document doc;
	private Date fechaProceso;
	private LectorRecursoHttp lectorRecurso;
	private MedidorPagina medidor;

	LectorPaginaHtml(String unaDireccion) {
		direccion = unaDireccion;
	}

	private void conectar() throws ExcepcionRecursoHttp {
		medidor.iniciarConexion(direccion);
		lectorRecurso.conectar();
		medidor.pararConexion(direccion);
	}

	private void descargar() {
		if (lectorRecurso != null) {
			LOG.debug("descargar");
			LinkedBlockingDeque<String> lista = new LinkedBlockingDeque<>();
			String html = lectorRecurso.getContenidoString();
			doc = Jsoup.parse(html);
			extraerLinks(lista);
			extraerSrc(lista);
			LOG.debug("iniciar descarga recursos");
			medidor.iniciarDescargaRecursos(direccion);
			List<Thread> threads = new ArrayList<>();
			for (int i = 1; i <= HILOS; i++) {
				Thread t = new Thread(() -> descargarRecursos(lista));
				t.start();
				threads.add(t);
			}
			try {
				for (Thread t : threads) {
					t.join();
				}
			} catch (InterruptedException ex) {
				LOG.error("interrupción", ex);
			}
			LOG.debug("parar descarga recursos");
			medidor.pararDescargaRecursos(direccion);
		}
	}

	private void descargarRecursos(LinkedBlockingDeque<String> lista) {
		boolean loopit = true;
		while (loopit) {
			try {
				String direccion = lista.removeFirst();
				LOG.debug("inicio " + direccion);
				LectorRecursoHttp lector = new LectorRecursoHttp(direccion);
				try {
					medidor.iniciarConexion(direccion);
					lector.conectar();
					medidor.pararConexion(direccion);
					medidor.iniciarDescarga(direccion);
					byte[] contenido = lector.leerContenido();
					medidor.pararDescarga(direccion);
					medidor.setSize(direccion, contenido.length);
				} catch (ExcepcionRecursoHttp ex) {
					LOG.error("[" + direccion + "]", ex);
					medidor.setExcepcion(direccion, "excepcion" + ex.getMessage());
				} finally {
					medidor.setResponseCode(direccion, lector.getResponseCode());
				}
				LOG.debug("fin " + direccion);
			} catch (NoSuchElementException ex) {
				loopit = false;
			}
		}
	}

	private void extraerLinks(LinkedBlockingDeque<String> lista) {
		Elements imports = doc.select("link[href]");
		for (Element link : imports) {
			lista.add(link.attr("abs:href"));
		}
	}

	private void extraerSrc(LinkedBlockingDeque<String> lista) {
		Elements media = doc.select("[src]");
		for (Element src : media) {
			if (src.tagName().equals("img") || src.tagName().equals("script")) {
				lista.add(src.attr("abs:src"));
			}
		}
	}

	public String getDireccion() {
		return direccion;
	}

	Date getFechaProceso() {
		return fechaProceso;
	}

	void init() {
		setFechaProceso();
		lectorRecurso = new LectorRecursoHttp(direccion);
		medidor = new MedidorPagina(this);
	}

	private void leerContenido() {
		LOG.debug("leer contenido");
		if (lectorRecurso != null) {
			medidor.iniciarDescarga(direccion);
			lectorRecurso.leerContenido();
			medidor.pararDescarga(direccion);
			medidor.setSize(direccion, (long) lectorRecurso.getContenido().length);
		}
	}

	void procesar() {
		LOG.debug(String.format("procesando %s", direccion));
		try {
			conectar();
			leerContenido();
			descargar();
		} catch (ExcepcionRecursoHttp ex) {
			LOG.error("[" + direccion + "]", ex);
		} finally {
			medidor.setResponseCode(direccion, lectorRecurso.getResponseCode());
		}
		LOG.debug(String.format("grabando medidas %s", direccion));
		medidor.grabar();
	}

	private void setFechaProceso() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		fechaProceso = Date.from(utc.toInstant());
	}

}
