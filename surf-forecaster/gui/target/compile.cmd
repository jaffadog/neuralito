@echo off
set CLASSPATH="C:\Users\esteban\.m2\repository\com\google\gwt\gwt-user\1.5.3\gwt-user-1.5.3.jar";"C:\Users\esteban\.m2\repository\com\google\gwt\gwt-dev\1.5.3\gwt-dev-1.5.3-windows.jar";"C:\Users\esteban\workspace\surf-forecaster\gui\src\main\java";"C:\Users\esteban\workspace\surf-forecaster\gui\src\main\resources";"C:\Users\esteban\workspace\surf-forecaster\gui\target\classes";"C:\Users\esteban\.m2\repository\edu\unicen\surf-forecaster\server\1.0-SNAPSHOT\server-1.0-SNAPSHOT.jar";"C:\Users\esteban\.m2\repository\edu\unicen\surf-forecaster\common\1.0-SNAPSHOT\common-1.0-SNAPSHOT.jar";


"C:\Program Files\Java\jdk1.6.0_16\jre\bin\java" -Xmx512m -cp %CLASSPATH%  com.google.gwt.dev.GWTCompiler  -gen "C:\Users\esteban\workspace\surf-forecaster\gui\target\.generated" -logLevel INFO -style DETAILED -out "C:\Users\esteban\workspace\surf-forecaster\gui\target\gui-1.0-SNAPSHOT" edu.unicen.surfforecaster.Application
