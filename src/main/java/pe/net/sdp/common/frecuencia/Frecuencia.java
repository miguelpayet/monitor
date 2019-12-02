package pe.net.sdp.common.frecuencia;

public class Frecuencia {

    private String cada;
    private Integer intervalo;
    private String nombre;
    private String tipo;

    public Frecuencia() {
    }

    public String getCada() {
        return cada;
    }

    Integer getIntervalo() {
        return intervalo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCada(String cada) {
        this.cada = cada;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
