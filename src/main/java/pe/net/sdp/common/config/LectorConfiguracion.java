package pe.net.sdp.common.config;

class LectorConfiguracion {
/*
	final static String CONEXION = "conexion";
	final static String PASSWORD = "password";
	final static String USUARIO = "usuario";
	private XMLConfiguration cfg;
	private Configuracion config;

	LectorConfiguracion(Configuracion unaConfiguracion) {
		config = unaConfiguracion;
	}

	private Frecuencia getFrecuencia(String nombre) {
		return config.getFrecuencia(nombre);
	}

	private Notificacion getNotificacion(String nombre) {
		return config.getNotificacion(nombre);
	}

	void init() throws ConfigurationException {
		Configurations configs = new Configurations();
		cfg = configs.xml("monitor.xml");
		initBaseDatos();
		initFrecuencias();
		initNotificaciones();
		initMonitores();
		initQueries();
	}

	private void initBaseDatos() throws ConfigurationException {
		List<HierarchicalConfiguration<ImmutableNode>> lista = cfg.childConfigurationsAt("basedatos");
		if (lista.size() != 1) {
			throw new ConfigurationException("más o menos de una base de datos");
		}
		HierarchicalConfiguration<ImmutableNode> prop = lista.get(0);
		config.addBasedatos(CONEXION, prop.getString(CONEXION));
		config.addBasedatos(USUARIO, prop.getString(USUARIO));
		config.addBasedatos(PASSWORD, prop.getString(PASSWORD));
	}

	private void initFrecuencias() {
		procesar("frecuencias", prop -> {
			String nombre = prop.getString("nombre");
			String tipo = prop.getString("tipo");
			int cada = prop.getInt("cada");
			config.addFrecuencia(nombre, FrecuenciaFactory.crearFrecuencia(tipo, nombre, cada));
			return null;
		});
	}

	private void initMonitores() {
		procesar("servicios", prop -> {
			String direccion = prop.getString("direccion");
			String nombre = prop.getString("nombre");
			int puerto = prop.getInt("puerto");
			String tipo = prop.getString("tipo");
			Servicio servicio = ServicioFactory.crearServicio(tipo, nombre, direccion, puerto);
			Frecuencia frecuencia = getFrecuencia(prop.getString("frecuencia"));
			Notificacion notificacion = getNotificacion(prop.getString("notificacion"));
			config.addMonitor(new Monitor(servicio, frecuencia, notificacion));
			return null;
		});
	}

	private void initNotificaciones() {
		procesar("notificaciones", nodo -> {
			String nombre = nodo.getString("nombre");
			String tipo = nodo.getString("tipo");
			String direccion = nodo.getString("direccion");
			config.addNotificacion(nombre, NotificacionFactory.crearNotificacion(tipo, nombre, direccion));
			return null;
		});
	}

	private void initQueries() {
		Map<String, Query> queries = new HashMap<>();
		procesar("queries", nodo -> {
			config.addQuery(new Query(nodo));
			return null;
		});
	}

	private void procesar(String base, Function<HierarchicalConfiguration<ImmutableNode>, Void> f) {
		List<HierarchicalConfiguration<ImmutableNode>> lista = cfg.childConfigurationsAt(base);
		for (HierarchicalConfiguration<ImmutableNode> prop : lista) {
			f.apply(prop);
		}
	}
*/
}
