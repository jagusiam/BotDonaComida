package org.botdonacomida.persistence;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

//clase DBCPDataSourceFactory:
//factoría para la creación de la conexion
// en vez de usar DriverManager - se usara BasicDataSource
//cual imlementa la interfaz DataSource de API JDBC de Java
public class DBCPDataSourceFactory {
    private static final String DB_CONFIG_FILE = Paths.get("config", "db.properties").toString();
    //Las claves del fichero Properties:
    private static final String DB_DRIVER_CLASS = "_DB_DRIVER_CLASS";
    private static final String DB_URL = "_DB_URL";
    private static final String DB_USERNAME = "_DB_USERNAME";
    private static final String DB_PASSWORD = "_DB_PASSWORD";

    //Los SGBD soportados
    private static final String SELECTED_SGBD = "SELECTED_SGBD";

    public static DataSource getDataSource() {

        String driverClassName;
        String url;
        String username;
        String password;

        //se hará uso de la clase Properties para
        //https://docs.oracle.com/javase/8/docs/api/java/util/Properties.html
        Properties props = new Properties();

        try (
                FileInputStream fis = new FileInputStream(DB_CONFIG_FILE);) {
            props.load(fis);

            String dbType = props.getProperty(SELECTED_SGBD);

            driverClassName = props.getProperty(concatString(dbType, DB_DRIVER_CLASS));
            url = props.getProperty(concatString(dbType, DB_URL));
            username = props.getProperty(concatString(dbType, DB_USERNAME));
            password = props.getProperty(concatString(dbType, DB_PASSWORD));


            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(driverClassName);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);

            return ds;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String concatString(String dbType, String propertySuffix) {
        return dbType + propertySuffix;
    }

}
