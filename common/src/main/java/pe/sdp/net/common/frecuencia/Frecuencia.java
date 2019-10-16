package pe.sdp.net.common.frecuencia;

public abstract class Frecuencia {

	private Integer intervalo;
	private String nombre;

	Frecuencia(String nombre, Integer unidad) {
		this.nombre = nombre;
		this.intervalo = unidad;
	}

	public abstract Long getIntervaloMillis();

	public String getNombre() {
		return nombre;
	}

	Integer getIntervalo() {
		return intervalo;
	}
}
