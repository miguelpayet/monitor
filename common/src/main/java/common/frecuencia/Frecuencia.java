package common.frecuencia;

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

	public Integer getIntervalo() {
		return intervalo;
	}
}
