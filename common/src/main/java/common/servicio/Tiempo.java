package common.servicio;

class Tiempo {

	private long tiempoFin;
	private long tiempoInicio;

	Tiempo() {
		tiempoFin = 0L;
		tiempoInicio = 0L;
	}

	void start() {
		tiempoInicio = System.currentTimeMillis();
	}

	void stop() {
		tiempoFin = System.currentTimeMillis();
	}

	long total() {
		return tiempoFin - tiempoInicio;
	}

}
