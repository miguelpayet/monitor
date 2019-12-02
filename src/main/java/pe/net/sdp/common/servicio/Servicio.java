package pe.net.sdp.common.servicio;

import java.util.Date;

public abstract class Servicio {

	private String direccion;
	private String nombre;
	private Integer puerto;

	public Servicio(String nombre, String direccion, Integer puerto) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.puerto = puerto;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getPuerto() {
		return puerto;
	}

	public abstract void procesar(Date unaFecha);

}
