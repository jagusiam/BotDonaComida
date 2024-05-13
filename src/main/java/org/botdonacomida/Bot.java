package org.botdonacomida;

import org.botdonacomida.model.Usuario;
import org.botdonacomida.persistence.DBCPDataSourceFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;


/**
 * Getting updates: There are two mutually exclusive ways of receiving updates for your bot
 * 1.getUpdates and 2. webhooks.
 * Incoming updates are stored on the server until the bot receives them either way,
 * but they will not be kept longer than 24 hours.
 * Regardless of the option - we receive JSON-serialized Update objects as a result.
 * getUpdates - this method to receive incoming updates using long polling. Returns an Array of Update objects.
 */
//This bot extends abstract class TelegramLongPollingBot that will use long-polling method to get updates



//Webhooks vs GetUpdates: Aqui se escoge GetUpdates, creando un bot que extiende de TelegramLongPollingBot
public class Bot extends TelegramLongPollingBot {

    // Set variables
    String user_first_name;
    String user_last_name;
    long user_id;
    String user_username;

    String message_text;
    //long chat_id = update.getMessage().getChatId();
    Long chat_id;

    //IMPLEMENTATION OF REQUIRED PARENT CLASS METHODS
    @Override
    public String getBotUsername() {

        return "donaComidaBot";
        //return "MY_BOT_USERNAME";
    }


    @Override
    public String getBotToken() {
        //TODO Store the token outside of the source code: environment variables
        return "6426402313:AAGgLDZHJQkNXFyqUsRiV_7YFGDbJZW8lGA";
        //return "MY_BOT_TOKEN";
    }



    //TODO: Implement all the proper checks in the code to handle every type of update with the appropriate methods
    // messages or other.
    //Update: This object represents an incoming update.
    @Override
    public void onUpdateReceived(Update update) {

        //********************************************************************
        //text message or photo
        //TEXT MESSAGE RECEIVED
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            // Variables impresicndibles para todos los métodos: mensaje recibido y id de chat
            //String message_text = update.getMessage().getText(); //cuerpo mensaje
            //long chat_id = update.getMessage().getChatId();
            //cambie de primitivo a clase Long para parsear mas facil a String - simple toString()
            //Long chat_id = update.getMessage().getChatId(); //id del chat

            //****************************************
            //DATABASE
            //when user presses /start - check if a user exists in database - and if not - create a new user
            // Set variables
          user_first_name = update.getMessage().getChat().getFirstName();
          user_last_name = update.getMessage().getChat().getLastName();
          user_id = update.getMessage().getChat().getId();
          user_username = update.getMessage().getChat().getUserName();

          message_text = update.getMessage().getText();
            //long chat_id = update.getMessage().getChatId();
          chat_id = update.getMessage().getChatId();

            //**********************************************************************************************************


           /* SendMessage insertMessage = SendMessage.builder() // Create a message object
                    .chatId(Long.toString(chat_id))
                    .text("Guardando usuario")
                    .build();

            try {
                execute(insertMessage);
                checkInsert(user_first_name, user_last_name, toIntExact(user_id), user_username);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
            //****************************************


            //**********************************************************************************
            //logging
            // Set variables
/*            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            //String message_text = update.getMessage().getText();
            //long chat_id = update.getMessage().getChatId();
            String answer = message_text;
            SendMessage loginMessage = SendMessage.builder() // Create a message object
                    .chatId(Long.toString(chat_id))
                    .text(answer)
                    .build();
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
            try {
                execute(loginMessage); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
            //*********************************************************************************





            if (message_text.equals("/pic")) {
                // User sent /pic
                //originalmente segun la fuente DA ERROR
/*                SendPhoto msg = new SendPhoto()
                        .setChatId(chat_id)
                        .setPhoto("AgACAgQAAxkBAANpZe_70P_JHenew9iJCx8yeOG7EiIAApu-MRuzAoBTVdhEjO_dt7EBAAMCAAN5AAM0BA")
                        .setCaption("Oferta nº 1: Caldo gallego 500 mltr");*/

                //cambiado a:
                SendPhoto sendPhoto = SendPhoto.builder()
                        .chatId(chat_id.toString())
                        //accede por ref. de la foto cargada desde el chat y asignada por Telegram
                        .photo(new InputFile("AgACAgQAAxkBAANpZe_70P_JHenew9iJCx8yeOG7EiIAApu-MRuzAoBTVdhEjO_dt7EBAAMCAAN5AAM0BA"))
                        //.setPhoto(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png")
                        .caption("Ejemplo de oferta: Caldo gallego 500 mltr")
                        .build();
                try {
                    //sendPhoto(msg); // ORIGINAL DA ERROR Call method to send the photo
                    execute(sendPhoto); //Cambiado

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (message_text.equals("/markup")) {
                SendMessage message = SendMessage.builder()
                        .chatId(chat_id.toString())
                        .text("Here is your keyboard")
                        .build();
                // Create ReplyKeyboardMarkup object
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                // Create the keyboard (list of keyboard rows)
                List<KeyboardRow> keyboard = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow row = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                row.add("Row 1 Button 1");
                row.add("Row 1 Button 2");
                row.add("Row 1 Button 3");
                // Add the first row to the keyboard
                keyboard.add(row);
                // Create another keyboard row
                row = new KeyboardRow();
                // Set each button for the second line
                row.add("Row 2 Button 1");
                row.add("Row 2 Button 2");
                row.add("Row 2 Button 3");
                // Add the second row to the keyboard
                keyboard.add(row);
                // Set the keyboard to the markup
                keyboardMarkup.setKeyboard(keyboard);
                // Add it to the message
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Oferta nº 1")) {
                SendPhoto sendPhoto1 = SendPhoto.builder()
                        .chatId(chat_id.toString())
                        .photo(new InputFile("AgACAgQAAxkBAANpZe_70P_JHenew9iJCx8yeOG7EiIAApu-MRuzAoBTVdhEjO_dt7EBAAMCAAN5AAM0BA"))
                        //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                        .caption("Oferta nº 1: Caldo gallego 500 mltr")
                        .build();
                    try {
                        execute(sendPhoto1);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            } else if (message_text.equals("Oferta nº 2")) {
            SendPhoto sendPhoto1 = SendPhoto.builder()
                    .chatId(chat_id.toString())
                    .photo(new InputFile("AgACAgQAAxkBAANnZe_7klxuepZXdA9BFMPLTq6PLsgAApm-MRuzAoBTxX7sKZAv9zEBAAMCAAN4AAM0BA"))
                    //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                    .caption("Oferta nº 2: Arroz de marisco 500 mltr")
                    .build();
                try {
                    execute(sendPhoto1);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("Oferta nº 3")) {
                SendPhoto sendPhoto1 = SendPhoto.builder()
                        .chatId(chat_id.toString())
                        .photo(new InputFile("AgACAgQAAxkBAANrZe_8Rc5YjzCgGM9wbBOciIYZPVQAApy-MRuzAoBTkiL1EmQRQ1sBAAMCAAN5AAM0BA"))
                        //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                        .caption("Oferta nº 3: Pulpo a la gallega, 1 ración")
                        .build();
                try {
                    execute(sendPhoto1);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/hide")) {
                SendMessage msg = SendMessage.builder()
                        .chatId(chat_id.toString())
                        .text("Keyboard hidden")
                        .build();
                ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                msg.setReplyMarkup(keyboardMarkup);

                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else {
                responderComandosPersonalizados(message_text, chat_id);

            }


            //PHOTO
            //si el mensaje es una imagen - recoger los datos
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
                // Message contains photo
                // Set variables
                //long chat_id = update.getMessage().getChatId();
                Long chat_id = update.getMessage().getChatId();

                // Array with photo objects with different sizes
                // We will get the biggest photo from that array
                List<PhotoSize> photos = update.getMessage().getPhoto();
                // Know file_id
                String f_id = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getFileId();
                // Know photo width
                int f_width = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getWidth();
                // Know photo height
                int f_height = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getHeight();
                // Set photo caption
                String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);

                SendPhoto sendPhoto = SendPhoto.builder()
                        .chatId(chat_id.toString())
                        //.setPhoto(f_id)
                        .photo(new InputFile(f_id))
                        //.setPhoto(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png")
                        .caption(caption)
                        .build();
                try {
                    execute(sendPhoto);// Call method to send the photo with caption
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }


        //**************************************************************
        //RESPUESTAS A COMANDOS
        //METODO responderComandosPersonalizados
/*        String comando = "";
        comando = update.getMessage().getText();
        Long id = update.getMessage().getChatId();
        responderComandosPersonalizados(comando, id);*/
        //*************************************************************+


        //********************************************
        //TODOS
        //TODO:  this simplified logic will change the bot's behavior for everyone –
        // not just the person who sent the command. This won't work in a production environment –
        // consider using a Map, dictionary or equivalent data structure to assign settings
        // for individual users.

        //TODO: Remember to always implement a few basic global commands.
        //TODO: Implement the /start command.

        //TODO: send a new menu for each new user
        //TODO: In a production environment, commands should be isolated into different executor classes –
        // modular and separated from the main logic.
        //********************************************

    } //fin onUpdateReceived()



    //*****************************************************************************************************

    //CUSTOM PRIVATE METHODS
    //METODO PARA DEFINIR COMPORTAMIENTO SEGUN LOS COMANDOS ESCOGIDOS
    private SendMessage responderComandosPersonalizados(String comando, Long id) {
        //String comando = update.getMessage().getText();

        String mensaje = "";
        switch (comando) {
            case "/start": mensaje = "Este es el bot de la iniciativa DonaComida. \n" +
                    "Este Bot te ayudará en automatizar " +
                    "algunas acciones dentro del grupo de usuarios de grupo local de donaciones de excedentes de comida.\n " +
                    "Federación Española de Bancos de Alimentos: \n" +
                    "https://www.fesbal.org.es/ \n" +
                    "Banco Alimentos de Vigo: \n" +
                    "https://bancoalimentosvigo.org/ \n" +
                    "Facebook Banco Alimentos de Vigo: \n" +
                    "https://www.facebook.com/bancoalimentosvigo/?locale=es_ES";
            break;
            case "/help": mensaje = "Bot DonaComida. \n Opciones de comandos: \n /start - inicio y descripción del bot, \n /help - ayuda, \n" +
                    "/publicardonacion - publica tu oferta, \n /solicitarrecogida - solicita recogida de alimento ofrecido \n /markup - crear un teclado \n /hide - esconder el teclado";
            break;
            case "/settings": mensaje = "";
            break;
            case "/menu": mensaje = "";
            break;
            case "/publicardonacion": mensaje = "¿Quieres publicar donación? Introduce los siguientes datos para completarla.";
            break;
            case "/solicitarrecogida": mensaje = "Has solicitado la recogida. Indica cual de las ofertas publicadas te interesa.";

            break;
            default: mensaje = "Comando incorrecto. \n Escoge una opción del menu de comandos. \n " +
                    "Puedes consultar la lista de comandos tecleando /help.";
            break;

        }

        SendMessage sm = new SendMessage();

        if (comando.equals("/solicitarrecogida")) {
            sm = SendMessage.builder()
                    .chatId(id.toString()) //Who are we sending a message to
                    .text(mensaje).build();    //Message content (String)

            //SendMessage respuesta = new SendMessage(); //BotAPI method to send messages to chat user
            //respuesta.setChatId(update.getMessage().getChatId().toString());//indcate the correct chat user
            //respuesta.setText(mensaje);

            SendPhoto sendPhoto1 = SendPhoto.builder()
                    .chatId(id.toString())
                    .photo(new InputFile("AgACAgQAAxkBAANpZe_70P_JHenew9iJCx8yeOG7EiIAApu-MRuzAoBTVdhEjO_dt7EBAAMCAAN5AAM0BA"))
                    //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                    .caption("Oferta nº 1: Caldo gallego 500 mltr")
                    .build();
            SendPhoto sendPhoto2 = SendPhoto.builder()
                    .chatId(id.toString())
                    .photo(new InputFile("AgACAgQAAxkBAANnZe_7klxuepZXdA9BFMPLTq6PLsgAApm-MRuzAoBTxX7sKZAv9zEBAAMCAAN4AAM0BA"))
                    //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                    .caption("Oferta nº 2: Arroz de marisco 500 mltr")
                    .build();
            SendPhoto sendPhoto3 = SendPhoto.builder()
                    .chatId(id.toString())
                    .photo(new InputFile("AgACAgQAAxkBAANrZe_8Rc5YjzCgGM9wbBOciIYZPVQAApy-MRuzAoBTkiL1EmQRQ1sBAAMCAAN5AAM0BA"))
                    //.photo(new InputFile("src/main/resources/imagenes comidas/arroz_marisco.png"))
                    .caption("Oferta nº 3: Pulpo a la gallega, 1 ración")
                    .build();

            //imprimir teclado
            // Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Oferta nº 1");
            row.add("Oferta nº 2");
            row.add("Oferta nº 3");
            // Add the first row to the keyboard
            keyboard.add(row);

            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            sm.setReplyMarkup(keyboardMarkup);


            //llamada al metodo de creacion del teclado - NO FUNCIONA
            //crearTeclado(id);

            try {
                execute(sm);
                execute(sendPhoto1);
                execute(sendPhoto2);
                execute(sendPhoto3);

            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }

        } else if (comando.equals("/start")) {

            //when user presses /start - check if a user exists in database - and if not - create a new user
            // Set variables
/*            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            long user_id = update.getMessage().getChat().getId();
            String user_username = update.getMessage().getChat().getUserName();

            String message_text = update.getMessage().getText();
            //long chat_id = update.getMessage().getChatId();
            Long chat_id = update.getMessage().getChatId();*/

            //**********************************************************************************************************


           SendMessage insertMessage = SendMessage.builder() // Create a message object
                    .chatId(id.toString())
                    .text("Guardando usuario")
                    .build();
            try {
                execute(insertMessage);
                checkInsert(user_first_name, user_last_name, toIntExact(user_id), user_username);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


    } else {
            sm = SendMessage.builder()
                    .chatId(id.toString()) //Who are we sending a message to
                    .text(mensaje).build();    //Message content (String)

            //SendMessage respuesta = new SendMessage(); //BotAPI method to send messages to chat user
            //respuesta.setChatId(update.getMessage().getChatId().toString());//indcate the correct chat user
            //respuesta.setText(mensaje);
            try {
                execute(sm);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }

        return sm;
    }


    //METODO PARA CREAR UN TECLADO CUSTOM
    private ReplyKeyboardMarkup crearTeclado (Long id) {
        SendMessage message = new SendMessage(); // Create a message object object
        message = SendMessage.builder()
                .chatId(id.toString()) //Who are we sending a message to
                .build();

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Oferta nº 1");
        row.add("Oferta nº 2");
        row.add("Oferta nº 3");
        // Add the first row to the keyboard
        keyboard.add(row);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return keyboardMarkup;
    }



    //METODO PARA LA FUNCION DEL LOGIN
    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }


    //METODO PARA LA CONEXION CON LA BASE DE DATOS
    //SI EL USUARIO NO EXISTE CREA UNO CON ESOS DATOS:
    //nombre, apellido, id_usuario_tlgrm, nobre_usuario_tlgrm
    //EJEMPLO DE MONSTER DEVELOPER CON MONGO DB
    //hacen falta drivers: MongoDB's driver for Java - to download or import it from Maven.
    // With IntelliJ Idea: File < Project Structure... < Libraries < + < From Maven and search for org.mongodb:mongo-java-driver
    //To be downloaded too: org.slf4j:slf4j-nop library - needed to disable additional logging.
/*    private String check(String first_name, String last_name, int user_id, String username) {
        // Set loggers
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        //Here we set new connection to MongoDB's server.
        //Replace host:port with your Mongo's host and port.
        // You can find how to setup MongoDB server for Ubuntu here:
        // https://www.digitalocean.com/community/tutorials/how-to-install-mongodb-on-ubuntu-16-04
        MongoClientURI connectionString = new MongoClientURI("mongodb://host:port");
        MongoClient mongoClient = new MongoClient(connectionString);
        //Then we set our database and collection. Replace this names with your own.
        MongoDatabase database = mongoClient.getDatabase("db_name");
        MongoCollection<Document> collection = database.getCollection("users");
        //We search users:
        long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        //And just check if user exists or not.
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }
    } //fin check() with MongoDB*/




    //MI VERSION - CONEXION CON UNA BBDD SQL Server
    //
    private String check(String first_name, String last_name, int user_id, String username) {

    final String SEPARATOR = "\t\t\t\t";
    //TODO: corregir, para que la cadena de conexion no este directamente en el codigo
    //sino guardada aparte
    String url = "jdbc:sqlserver://localhost:1433;database=dona_comida;user=user;password=abc123.;encrypt=true;trustServerCertificate=true";
        try (
                //objeto Connection a traves de DriverManager a partir de la url de la bbdd
                Connection conexion = DriverManager.getConnection(url);
                //una vez creada la conex - creamos un statement
                Statement sentencia = conexion.createStatement();
                //Con Statement podemos ejecutar ya una consulta con sentencia sql
                ResultSet result = sentencia.executeQuery("SELECT * FROM dbo.DEPT");) {

            int columnas = result.getMetaData().getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                System.out.print(result.getMetaData().getColumnName(i) + SEPARATOR);
            }

            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            while (result.next()) {
                System.out.println(result.getInt(1) + SEPARATOR + result.getString(2) + SEPARATOR + result.getString(3) + SEPARATOR);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }

        //cambiar el codigo para que consulte la base de datos por el usuario que se conecta
        return "exists";

    } //fin check con SQLServer




    //private static void insertarUsuario(Usuario usuario) {
    //private String checkInsert(String first_name, String last_name, int user_id, String username) {
    private void checkInsert(String first_name, String last_name, int user_id, String username) {
        DataSource ds = DBCPDataSourceFactory.getDataSource();

        try (
                Connection conexion = ds.getConnection();
                //este metodo devuelve la clave primaria autogenerada
                PreparedStatement pstmt = conexion.prepareStatement(
                        "INSERT INTO [dbo].[usuarios]( nombreUsu, apellidoUsu, idUsuTlgrm, nomUsuTlgrm) VALUES(?, ?, ?, ?);",
                        Statement.RETURN_GENERATED_KEYS
                );) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setLong(3, user_id);
            pstmt.setString(4, username);

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
            System.out.println("User did not existed in the database. Written.");
            //return "not exists";

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
        //return "exists";
    }




    //*******************************************************************************
    //OTHER CUSTOM METHODS
    //Custom method scream (to use in Option4: executing commands)
/*    private void scream(Long id, Message msg) {
        if(msg.hasText())
            sendText(id, msg.getText().toUpperCase());
        else
            copyMessage(id, msg.getMessageId());  //We can't really scream a sticker
    }*/


    //SENDING MESSAGES BY THE BOT (used in OPTION2)
    /**
     * To send a private text message, you generally need three things:
     *     The user must have contacted your bot first.
     *     TODO: (Unless the user sent a join request to a group where your bot is an admin, but that's a more advanced scenario).
     *     You must have previously saved the User ID (user.getId())
     *     A String object containing the message text, 1-4096 characters.
     */
/*
    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content (String)
        try {
            execute(sm);                        //executing the sending of the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    } //end of sendText method
*/

    /** TODO: sendVoice method
     * Audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or Document).
     * On success, the sent Message is returned. Up to 50 MB in size.
     * Params: required:
     * chat_id - type: Integer or String - Unique identifier for the target chat or username of the target channel
     * (in the format @channelusername)
     * voice - type: InputFile or String - Audio file to send. Pass a file_id as String to send a file that exists
     * on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet,
     * or upload a new one using multipart/form-data.
     * TODO: revise the rest of optional params
     */
    //Other possible interesting methods: sendPhoto, sendSticker, sendDice,
    // sendPoll - for possible polls


    //COPING: copy messages and send them back (to use in Option3: Echo bot)
/*
    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())  //Coping from the user
                .chatId(who.toString())      //And sending it back to them
                .messageId(msgId)            //Specifying the message
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    } //end copyMessage() method
*/

    //Sending Keyboards
/*    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt) //formatting option - allows to use HTML tags and add formatting to the text
                .replyMarkup(kb).build(); //Sending a keyboard requires specifying a reply markup for the message.

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }*/


    //Navigation: Basic Button handler - With this handler, whenever a button is tapped,
    // the bot will automatically navigate between inline menus.
    //CallbackQueries - results we get after the user taps on a button.
    //A CallbackQuery is essentially composed of three main parameters:
    //    queryId - Needed to close the query. It is necessary to close new queries after processing them
    //    – if you don't, a loading symbol will keep showing on the user's side on top of each button.
    //    data - This identifies which button was pressed.
    //    from - The user who pressed the button.
    //private void buttonTap(Long id, String queryId, String data, int msgId) throws TelegramApiException
/*    public void buttonTap(Long id, String queryId, String data, int msgId) throws TelegramApiException

    {

        EditMessageText newTxt = EditMessageText.builder()
                .chatId(id.toString())
                .messageId(msgId).text("").build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(id.toString()).messageId(msgId).build();

        if(data.equals("next")) { //Next button to lead the user to the second menu.
            newTxt.setText("MENU 2");
            newKb.setReplyMarkup(keyboardM2);
        } else if(data.equals("back")) { //Back button will send us back.
            newTxt.setText("MENU 1");
            newKb.setReplyMarkup(keyboardM1);
        }
        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();
        execute(close);
        execute(newTxt);
        execute(newKb);
    }*/

    //************************************************************************************


} //end class Bot
