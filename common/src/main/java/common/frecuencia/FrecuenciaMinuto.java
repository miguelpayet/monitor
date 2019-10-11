package common.frecuencia;

public class FrecuenciaMinuto extends Frecuencia {

	public FrecuenciaMinuto(String nombre, Integer unidad) {
		super(nombre, unidad);
	}

	@Override
	public Long getIntervaloMillis() {
		Long l = getIntervalo() * 60 * 1000L;
		return l;
	}

}