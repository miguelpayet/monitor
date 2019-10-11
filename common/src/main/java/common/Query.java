package common;

import common.config.Configuracion;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

	private Connection con;
	private String nombre;
	private String sql;

	public Query(HierarchicalConfiguration<ImmutableNode> nodo) {
		nombre = nodo.getString("nombre");
		sql = nodo.getString("sql");
	}

	public void cerrar() throws SQLException {
		con.close();
	}

	private void conectar() throws SQLException {
		con = Configuracion.get().getDataSource().getConnection();
	}

	public ResultSet ejecutar() throws SQLException {
		conectar();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public String getNombre() {
		return nombre;
	}

}
