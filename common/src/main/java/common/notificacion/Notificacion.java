package common.notificacion;

public class Notificacion {

	private String direccion;
	private String nombre;

	public Notificacion(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNombre() {
		return nombre;
	}

}
