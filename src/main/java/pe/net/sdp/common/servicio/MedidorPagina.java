package pe.net.sdp.common.servicio;

import java.util.HashMap;
import java.util.Map;

class MedidorPagina {

	private LectorPaginaHtml lector;
	private Map<String, Medida> medidas;

	MedidorPagina(LectorPaginaHtml unLector) {
		medidas = new HashMap<>();
		lector = unLector;
	}

	void grabar() {
		medidas.forEach((k, v) -> (new GrabadorMedida(k, v, lector)).grabar());
	}

	void iniciarConexion(String slot) {
		Medida t = obtenerMedida(slot);
		t.iniciarConexion();
	}

	void iniciarDescarga(String slot) {
		Medida t = obtenerMedida(slot);
		t.iniciarDescarga();
	}

	void iniciarDescargaRecursos(String slot) {
		Medida t = obtenerMedida(slot);
		t.iniciarDescargaRecursos();
	}

	private Medida obtenerMedida(String slot) {
		if (!medidas.containsKey(slot)) {
			medidas.put(slot, new Medida());
		}
		return medidas.get(slot);
	}

	void pararConexion(String slot) {
		Medida t = obtenerMedida(slot);
		t.pararConexion();
	}

	void pararDescarga(String slot) {
		Medida t = obtenerMedida(slot);
		t.pararDescarga();
	}

	void pararDescargaRecursos(String slot) {
		Medida t = obtenerMedida(slot);
		t.pararDescargaRecursos();
	}

	void setExcepcion(String slot, String excepcion) {
		Medida t = obtenerMedida(slot);
		t.setExcepcion(excepcion);
	}

	void setResponseCode(String slot, int responseCode) {
		Medida t = obtenerMedida(slot);
		t.setResponseCode(responseCode);
	}

	void setSize(String slot, long size) {
		Medida t = obtenerMedida(slot);
		t.setSize(size);
	}

}