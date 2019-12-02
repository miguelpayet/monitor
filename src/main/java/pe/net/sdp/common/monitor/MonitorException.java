package pe.net.sdp.common.monitor;

public class MonitorException extends Exception {

    public MonitorException(String msg) {
        super(msg);
    }

    public MonitorException(String msg, Exception ex) {
        super(msg, ex);
    }

}
