- Datos de prueba de pronósticos: 

	Al finalizar la instalación la aplicación no cuenta con ningún pronostico. Esto es debido a que primero debe descargarse el ultimo pronostico y procesarlo. Este proceso puede demorar varios minutos.

	En caso de querer probar la aplicacion sin esperar dicho proceso hemos incluido un script para cargar datos de un pronostico(para todo el oceano) ya procesado. Este pronostico sera solo de demostracion ya que pertenece a un dia ocurrido en el pasdo.

		1) El script con datos de pronostico se encuentra compactado en "aplicacion/datos de prueba/pronostico/pronostico.rar"
			a)Descomprimir el script.
			b)El script debe ser insertado en la base de datos de de SurfForecaster mediante alguna herramienta sql.
			  Importante: debido al gran tamaño de este archivo la insercion del mismo deber hacerse mediante la ejecucion directa del archivo sql(Ejemplo: comando "source" de Mysql.exe). De intentarse abrir el archivo en modo edición de texto para luego ejecutarse es muy probable que se generen errores.

- Datos de prueba para entrenar pronosticadores especializados: 

	Al finalizar la instalación la aplicación no posee datos historicos del estado del mar, ya que estos son archivados cuando la aplicacion descarga los pronosticos diarios. Sin datos historicos es imposible utilizar la funcionalidad de pronosticos mejorados.

	Hemos incluido por separado dos conjuntos de datos que permiten experimentar la funcionalidad de pronosticos mejorados, sin tener que esperar a recolectar una cantidad suficiente de información:

	En la carpeta "aplicacion/datos de prueba" se encuentran estos dos conjuntos: 

	1) Archivo del WaveWatch III para la isla de Oahu, Hawaii en el rango de años 1997-2004.
		Este conjunto de datos está en un script sql, el mismo debe ser insertado en la base de datos de SurfForecaster mediante alguna herramienta sql.
		
		
	2) Observaciones visuales de la altura de las olas,para el periodo 1997-2004, de las playas de Sunset,Makaha, AlaMoana,DiamondHead y Makapu.
		Estos datos se cargaran a traves de la interfaz grafica de la aplicacion en el menu "Nueva Ola"->"Entrenar pronosticador especializado".
	





