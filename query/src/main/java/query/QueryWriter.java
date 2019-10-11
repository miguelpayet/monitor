package query;

import common.Query;
import html.MyHtmlDocumentImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class QueryWriter {

	private static final Logger LOG = LogManager.getLogger(QueryWriter.class);

	private Document html;
	private Query query;
	private ResultSet rs;

	QueryWriter(Query unQuery, ResultSet unRs) {
		query = unQuery;
		rs = unRs;
	}

	private void agregarTitulo(Element body) {
		Element h1 = html.createElement("h1");
		h1.setTextContent(query.getNombre());
		body.appendChild(h1);
	}

	private Element crearCelda(Element fila, String contenido) {
		Element td = html.createElement("td");
		td.setTextContent(contenido);
		fila.appendChild(td);
		return td;
	}

	private Element crearDocumento() {
		html = MyHtmlDocumentImpl.makeBasicHtmlDoc("query");
		Element head = (Element) html.getElementsByTagName("head").item(0);
		Element estilo = html.createElement("link");
		estilo.setAttribute("href", "/query.css");
		estilo.setAttribute("rel", "stylesheet");
		estilo.setAttribute("type", "text/css");
		head.appendChild(estilo);
		Element body = (Element) html.getElementsByTagName("body").item(0);
		agregarTitulo(body);
		return body;
	}

	private Element crearFila(Element table) {
		return crearFilaTipo(table, "tr");
	}

	private Element crearFilaTipo(Element table, String tipo) {
		Element tr = html.createElement(tipo);
		table.appendChild(tr);
		return tr;
	}

	private Element crearFilaTitulo(Element table) {
		Element thead = html.createElement("thead");
		table.appendChild(thead);
		Element fila = crearFilaTipo(table, "tr");
		fila.setAttribute("class", "titulo");
		thead.appendChild(fila);
		return fila;
	}

	private Element crearTabla(Element body) {
		Element table = html.createElement("table");
		body.appendChild(table);
		return table;
	}

	private Element crearTableBody(Element table) {
		Element tbody = html.createElement("tbody");
		table.appendChild(tbody);
		return tbody;
	}

	private void grabar() {
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS domImplLS = (DOMImplementationLS) registry.getDOMImplementation(("LS"));
			LSSerializer lsS = domImplLS.createLSSerializer();
			DOMConfiguration domConfig = lsS.getDomConfig();
			domConfig.setParameter("format-pretty-print", true);
			LSOutput lsO = domImplLS.createLSOutput();
			lsO.setEncoding("UTF-8");
			try {
				OutputStream os = new FileOutputStream(new File("query.html"));
				lsO.setByteStream(os);
				lsS.write(html, lsO);
			} catch (IOException ex) {
				LOG.error("query.txt", ex);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			LOG.error("class not found", ex);
		}
	}

	private ResultSetMetaData leerTitulos(ResultSet rs, Element table) throws SQLException {
		Element tr = crearFilaTitulo(table);
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			crearCelda(tr, rsmd.getColumnLabel(i));
		}
		return rsmd;
	}

	void write() throws SQLException {
		Element body = crearDocumento();
		Element table = crearTabla(body);
		Element tableBody = crearTableBody(table);
		ResultSetMetaData rsmd = leerTitulos(rs, table);
		while (rs.next()) {
			Element tr = crearFila(tableBody);
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				Element celda = crearCelda(tr, String.format("%s", rs.getObject(i)));
				celda.setAttribute("class", rsmd.getColumnTypeName(i));
			}
		}
		grabar();
	}
}
