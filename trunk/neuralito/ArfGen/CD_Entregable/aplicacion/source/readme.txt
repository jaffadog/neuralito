*************************************************
Codigo fuente de la aplicacion SurfForecaster. 
*************************************************

- El codigo fuente de la aplicacion se encuentra divido en dos: Gui y Server.
	- Gui: El codigo fuente de la interfaz gráfica.Este tiene dependencias a Server.
	- Server: Codigo fuente del servidor.

- Proceso de compilacion del codigo fuente:

	- Requisitos: 
		 - Ant.
		 - Maven.

	- Gui utiliza ant para compilar y generar el war deployable de la aplicacion. Mientras que Server utiliza Maven para generar los jars correspondientes.

	- Los pasos a seguir para obtener el War deployable de Surf-forecaster son los siguientes:

		1) Correr el comando maven para compilar Server: "mvn clean install -DskipTests=true".  
			Esto omitirá los tests, compilará todo server y copiara los correspondientes Jars y dependencias generados a la carpeta GUI.
		2) Configurar ant para compilar GUI: modificar los paths de build.xml
		3) Compilar GUI: ejecutar el script de ant "build.xml"
			Esto compilara GUI generando el WAR de SurfForecaster en la carpeta de destino especificada en build.xml.
	

	

