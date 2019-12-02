package pe.net.sdp.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private Connection con;
    private String nombre;
    private String sql;

    public Query() {
    }

    public void cerrar() throws SQLException {
        con.close();
    }

    public ResultSet ejecutar() throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSql() {
        return sql;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
