package pe.net.sdp.common.frecuencia;

public class FrecuenciaMinuto extends Frecuencia {

    public FrecuenciaMinuto(String nombre, Integer unidad) {
        super();
        setNombre(nombre);
        setIntervalo(unidad);
    }

    public Long getIntervaloMillis() {
        Long l = getIntervalo() * 60 * 1000L;
        return l;
    }

}