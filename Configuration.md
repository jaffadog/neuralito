## Maven ##
Configure environment variable:
**MAVEN\_OPTS=-Xmx900m**
## Eclipse ##
**VM arguments:**
Eclipse->Preferences->Installed JRE(The one used to run the project)->Edit->VM arguments
  * -Xmx900m
> Extra memory is needed when decoding the grib files.
  * -Dfile.encoding=utf8
> When hibernate hbm2ddlTool inserts data from import.sql the data is inserted using the platform file encoding. So its necessary to config the eclipse virtual machine to use Utf8 encoding.
    * **Note: When running on tomcat this issue should be considered.**




## DB ##

  1. Download and Install Mysql version 5.1.
  1. Configure db.properties located in:(?) with port, schema, user and pass.
  1. Add this line to the mysql config file below `[mysqld]` tag:
```
   #
   # The number of files that the operating system allows mysqld to open.
   #
   open-files-limit=3000
```
  1. Start the mysql server.

## TOMCAT ##
Memory size of Tomcat should be incresed, grib decoder uses much memory;

In Windows system, this can be done by editing / adding JAVA\_OPTS variable (should be early in the file) in CATALINA\_HOME/bin/catalina.bat or catalina.sh for Linux/Unix systems

For catalina.bat there now should be a line in the file that looks like this:  set JAVA\_OPTS=-Xms256m -Xmx256m

For catalina.sh:  JAVA\_OPTS='-Xms800m -Xmx1024m'