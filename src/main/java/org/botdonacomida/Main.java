package org.botdonacomida;

import org.botdonacomida.model.Usuario;
import org.botdonacomida.persistence.DBCPDataSourceFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi; //registro del bot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;
import java.sql.*;

//import org.telegram.telegrambots.meta.logging.BotLogger;

public class Main {

    private static Update update;
    private static InlineKeyboardMarkup inKb;
    private static Message msg;
    //private static AnswerCallbackQuery callbackQuery;
    private static CallbackQuery callbackQuery;

    //para pruebas bbdd
    //una convecncion aplicada para separar los elementos de la consulta
    final static String SEPARATOR = "\t\t\t\t";

    public static void main(String[] args) throws TelegramApiException {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot(); //We moved this line out of the register method, to access it later
            botsApi.registerBot(bot);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }


        //******************
        //pruebas db
        //LLAMADAS A METODOS DE CONEX CON BBDD
        //consultarUsuarios();
        //borrarUsuarios(123456789);
        // Departamento operacionesDept = new Departamento(40, "OPERATIONS", "BOSTON");
        //Usuario usuario1 = new Usuario(123456789, "Ana", "López Perez", 10101010110L, "anaLopez");
        //insertarUsuarioConIdentity(usuario1);
        //consultarUsuarios();
//        insertarDepartamentoConIdentity(operacionesDept);
        //Usuario usuario2 = new Usuario("Jorge", "López Gómez", 121331211L, "jLopez");

        //insertarUsuario(usuario2);
        //
        //consultarUsuarios();
        // insertarDepartamento(operacionesDept);
//        operacionesDept.setDeptName("OPERACIONES 2");
//        actualizarDept(operacionesDept);

        //Usuario usuario1 = new Usuario("Ana", "López Perez", 10101010110L, "anaLopez");
        //usuario1.setNomUsuarioTlgrm("anaLopez2");
        //actualizarUsuario(usuario1);
        //Usuario usuario2 = new Usuario("Jorge", "López Gómez", 121331211L, "jLopez");
        //usuario2.setNomUsuarioTlgrm("jorgeLG");
        //usuario2.setNombreUsuario("Jorge Juan");
        //actualizarUsuario(usuario2);
        //Usuario usuario3 = new Usuario("Victoria", "Pereira", 12141618816151L, "vicky");
        //insertarUsuario(usuario3);
        //usuario3.setNomUsuarioTlgrm("vickyP");
        //usuario3.setApellidos("Pereira Moreira");
        //actualizarUsuario(usuario3);
        //
        //Usuario usuario4 = new Usuario("Artur", "Conan Doyle", 101010103333L, "adoyle");
        //usuario4.setNomUsuarioTlgrm("aConan");
        //actualizarUsuario(usuario4); //el metodo de actualziar no funciona , TODO: CORREGIR


    }


    //METODOS BBDD
    //Metodo para consultar la tabla usuarios
    private static void consultarUsuarios() {
        //llamadaa la factoría para crear la conex.
        DataSource ds = DBCPDataSourceFactory.getDataSource();
        try (
                Connection conexion = ds.getConnection(); //crear conexion
                Statement sentencia = conexion.createStatement(); //sentencia a partir de la conex.
                //conjunto de respuesta a traves de la query/consulta SQL
                //para obtenerlo - se ejecuta executeQuery() sobre la sentencia
                ResultSet result = sentencia.executeQuery("SELECT * FROM dbo.usuarios");) {

            int columnas = result.getMetaData().getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                System.out.print(result.getMetaData().getColumnName(i) + SEPARATOR);
            }

            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            //mientras haya resultado (metodo next()) - se devuelven nombres de sig. col.
            while (result.next()) {
                //para ResultSet el indice empieza en 1, no en 0
                //para cada tipo de dato en sql hay uno correspondiente en Java
                //para: bigint - long
                System.out.println(result.getInt(1) + SEPARATOR + result.getString(2) + SEPARATOR
                        + result.getString(3) + SEPARATOR + result.getLong(4) + SEPARATOR +
                        result.getString(5) + SEPARATOR);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
    }


    //Metodo para borrar usuario
    //se le pase el int id como parametro
    private static void borrarUsuarios(int idUsuDb) {
        DataSource ds = DBCPDataSourceFactory.getDataSource();

        try (
                Connection conexion = ds.getConnection();
                //aqui usamos PreparedStatement - precompilados
                PreparedStatement pstmt = conexion.prepareStatement("DELETE FROM usuarios WHERE idUsuDb=?");) {

            pstmt.setInt(1, idUsuDb);

            //uso de metodo executeUpdate()
            int result = pstmt.executeUpdate();

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            System.out.println("El número de filas afectadas  es: " + result);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
    }

    //Metodo para insertar Usuario con id
    //param: Usuario
    private static void insertarUsuarioConIdentity(Usuario usuario) {
        DataSource ds = DBCPDataSourceFactory.getDataSource();

        try (
                Connection conexion = ds.getConnection();
                //en preparedStatement hay que forzar el identity insert: ON , y luego poner a OFF

                //permite usar params ? que van a ser sustituidos luego por los datos recuperados

                PreparedStatement pstmt = conexion.prepareStatement(
                "SET IDENTITY_INSERT dbo.usuarios ON; \n"
                        + "INSERT INTO [dbo].[usuarios](idUsuDb, nombreUsu, apellidoUsu, idUsuTlgrm, nomUsuTlgrm) VALUES(?, ?, ?, ?, ?);\n"
                        + " SET IDENTITY_INSERT dbo.usuarios OFF");) {

            //proporcionar los datos de las columnas a la prepared statement
            //1 - la primera ocurrencia del interrogante - la 1º columna
            pstmt.setInt(1, usuario.getIdUsuarioDb());
            pstmt.setString(2, usuario.getNombreUsuario());
            pstmt.setString(3, usuario.getApellidos());
            pstmt.setLong(4,usuario.getIdUsuarioTlgrm());
            pstmt.setString(5, usuario.getNomUsuarioTlgrm());

            //ejecutar actualización
            //devuelve filas afectadas
            int result = pstmt.executeUpdate();

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            System.out.println("El número de filas afectadas es: " + result);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
    }

    //Método para insertar un usuario
    //id se autogenera y autoincrementa
    private static void insertarUsuario(Usuario usuario) {
        DataSource ds = DBCPDataSourceFactory.getDataSource();

        try (
                Connection conexion = ds.getConnection();
                //este metodo devuelve la clave primaria autogenerada
                PreparedStatement pstmt = conexion.prepareStatement(
                "INSERT INTO [dbo].[usuarios]( nombreUsu, apellidoUsu, idUsuTlgrm, nomUsuTlgrm) VALUES(?, ?, ?, ?);",
                        Statement.RETURN_GENERATED_KEYS
        );) {

            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setLong(3, usuario.getIdUsuarioTlgrm());
            pstmt.setString(4, usuario.getNomUsuarioTlgrm());

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            int result = pstmt.executeUpdate();
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            System.out.println("El número de filas afectadas es: " + result);

            //devuelve la clave primaria autogenerada
            //si insertariamos mas de una fila - solo no devolveria la clave de la ultm fila unicamente
            ResultSet clavesResultado = pstmt.getGeneratedKeys();

            while (clavesResultado.next()) {
                System.out.println("La clave asignada al nuevo registro es: " + clavesResultado.getInt(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
    }

    //Método para actualizar usuario
    //param: objeto Usuario
    private static void actualizarUsuario(Usuario usuario) {
        DataSource ds = DBCPDataSourceFactory.getDataSource();

        try (
                Connection conexion = ds.getConnection();
                PreparedStatement pstmt = conexion.prepareStatement(
                        "UPDATE [dbo].[usuarios]  SET nombreUsu=?,  apellidoUsu=?, idUsuTlgrm=?, nomUsuTlgrm=? WHERE idUsuDb = ?")) {

            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setLong(3, usuario.getIdUsuarioTlgrm());
            pstmt.setString(4, usuario.getNomUsuarioTlgrm());
            pstmt.setInt(5, usuario.getIdUsuarioDb());

            //ejecutamos la actulizacion
            int result = pstmt.executeUpdate();

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            System.out.println("El número de filas afectadas es: " + result);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
    }






        //fin pruebas bbdd
        //***********************************************************




        //Registrar el bot creado a traves de la instancia de la clase:
        //org.telegram.telegrambots.meta.TelegramBotsApi
/*        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            telegramBotsApi.registerBot(new MyProjectHandler());//cambiar por el mio
            telegramBotsApi.registerBot(new Bot());
            //ejemplos rubenlagus
            //telegramBotsApi.registerBot(new ChannelHandlers());
            //telegramBotsApi.registerBot(new DirectionsHandlers());
            //telegramBotsApi.registerBot(new RaeHandlers());
            //telegramBotsApi.registerBot(new WeatherHandlers());
            //telegramBotsApi.registerBot(new TransifexHandlers());
            //telegramBotsApi.registerBot(new FilesHandlers());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        //or
/*      BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());
        try {
            BotLogger.registerLogger(new BotsFileHandler());
        } catch (IOException e) {
            BotLogger.severe(LOGTAG, e);
        }
       TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MyProjectHandler());//cambiar por el mio
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }*/

        //end catch()



        //OPTION 0-1: first run test in command line
        //TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        //botsApi.registerBot(new Bot());

        //OPTION 2: sending messages by the bot
        /**
         * for test: my userId
         * TODO: Sending messages to groups or channels – PENDING replacing with the ID of the respective chat.
         * */
        /*TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(); //We moved this line out of the register method, to access it later
        botsApi.registerBot(bot);
        //assuming here UserID is  my userID id=6633814885
        bot.sendText(6633814885L, "Hello World!");  //The L for Long
*/

        //OPTION 3 - EchoBot
        //TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        //Bot bot = new Bot(); //We moved this line out of the register method, to access it later
        //botsApi.registerBot(new Bot());


        //OPTION 4 - commands
/*        update = new Update();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(); //We moved this line out of the register method, to access it later
        botsApi.registerBot(bot);
        bot.onUpdateReceived(update);*/

        //OPTION 4.2 - sending keyboards - sendMenu
/*        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(); //We moved this line out of the register method, to access it later
        botsApi.registerBot(bot);
        inKb = bot.getKeyboardM1();
        bot.sendMenu(6633814885L, "/next", inKb); */

        //OPTION 4.3 testing commands



        //OPTION 5 - navigation between buttons - No funciona
/*
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot(); //We moved this line out of the register method, to access it later
        botsApi.registerBot(bot);
        update = new Update();
        bot.onUpdateReceived(update);
        inKb = bot.getKeyboardM1();
        msg = update.getMessage();
        int msgId = msg.getMessageId();
        callbackQuery = update.getCallbackQuery();
        String queryId = callbackQuery.getId();
               // .getCallbackQueryId();
        bot.buttonTap(6633814885L, queryId, "next", msgId);
*/



        //********************************************
        //imports necesarios
        //import org.telegram.telegrambots.ApiContextInitializer;
        //import org.telegram.telegrambots.TelegramBotsApi;
        //import org.telegram.telegrambots.exceptions.TelegramApiException;

/*        ApiContextInitializer.init();//error de la clase ApiContextInitializer - revisar en que version de la api?

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("PhotoBot successfully started!");*/
        //*********************************************



}