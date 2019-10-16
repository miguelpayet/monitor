package pe.sdp.net.common.servicio;

class Medida {

	private Tiempo conexion;
	private Tiempo descarga;
	private Tiempo descargaRecursos;
	private String direccion;
	private String excepcion;
	private int responseCode;
	private long size;

	Medida() {
		setConexion(new Tiempo());
		setDescarga(new Tiempo());
		setDescargaRecursos(new Tiempo());
	}

	public Tiempo getConexion() {
		return conexion;
	}

	public Tiempo getDescarga() {
		return descarga;
	}

	public Tiempo getDescargaRecursos() {
		return descargaRecursos;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public long getSize() {
		return size;
	}

	void iniciarConexion() {
		conexion.start();
	}

	void iniciarDescarga() {
		descarga.start();
	}

	void iniciarDescargaRecursos() {
		descargaRecursos.start();
	}

	void pararConexion() {
		conexion.stop();
	}

	void pararDescarga() {
		descarga.stop();
	}

	void pararDescargaRecursos() {
		descargaRecursos.stop();
	}

	private void setConexion(Tiempo conexion) {
		this.conexion = conexion;
	}

	private void setDescarga(Tiempo descarga) {
		this.descarga = descarga;
	}

	private void setDescargaRecursos(Tiempo descargaRecursos) {
		this.descargaRecursos = descargaRecursos;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	void setSize(long size) {
		this.size = size;
	}

}
