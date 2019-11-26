package common.servicio;

public class ExcepcionRecursoHttp extends Exception {

	public ExcepcionRecursoHttp(String message) {
		super(message);
	}

	public ExcepcionRecursoHttp(String message, Throwable throwable) {
		super(message, throwable);
	}

}
