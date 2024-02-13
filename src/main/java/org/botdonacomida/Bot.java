package org.botdonacomida;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


/**
 * Getting updates: There are two mutually exclusive ways of receiving updates for your bot
 * 1.getUpdates and 2. webhooks.
 * Incoming updates are stored on the server until the bot receives them either way,
 * but they will not be kept longer than 24 hours.
 * Regardless of the option - we receive JSON-serialized Update objects as a result.
 * getUpdates - this method to receive incoming updates using long polling. Returns an Array of Update objects.
 */
//This bot extends abstract class TelegramLongPollingBot that will use long-polling method to get updates
public class Bot extends TelegramLongPollingBot {

    //Global variable to store the current mode - to use with option4: execution of commands
    //to reply in uppercase when it's in scream mode and normally otherwise.
    private boolean screaming = false;

    //inline keyboards variables
    private InlineKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;

    public InlineKeyboardMarkup getKeyboardM1() {
        return keyboardM1;
    }

    public void setKeyboardM1(InlineKeyboardMarkup keyboardM1) {
        this.keyboardM1 = keyboardM1;
    }

    public InlineKeyboardMarkup getKeyboardM2() {
        return keyboardM2;
    }

    public void setKeyboardM2(InlineKeyboardMarkup keyboardM2) {
        this.keyboardM2 = keyboardM2;
    }

    //IMPLEMENTATION OF REQUIRED PARENT CLASS METHODS
    @Override
    public String getBotUsername() {
        return "donaComidaBot";
    }

    @Override
    public String getBotToken() {
        //TODO Store the token outside of the source code: environment variables
        return "6426402313:AAGgLDZHJQkNXFyqUsRiV_7YFGDbJZW8lGA";
    }


    //TODO: Implement all the proper checks in the code to handle every type of update with the appropriate methods
    // messages or other.
    //Update: This object represents an incoming update.
    @Override
    public void onUpdateReceived(Update update) {
        //INLINE BUTTONS
        var next = InlineKeyboardButton.builder()
                .text("Next").callbackData("next") //text: the text that appears on the button
                .build();

        var back = InlineKeyboardButton.builder()
                .text("Back").callbackData("back") //callback:  sent back to the code instance as part of a new Update, so we can quickly identify what button was clicked.
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api") //opens the given link
                .build();

        //INLINE KEYBOARDS instantiation
        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(next)).build();

        //Buttons are wrapped in lists since each keyboard is a set of button rows
        keyboardM2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .keyboardRow(List.of(url))
                .build();


        //OPTION 0: the first run
        //System.out.println(update);

        /** Receiving Messages:
         * Every time someone sends a private message to the bot, onUpdateReceived method will be called automatically
         * Here we handle the update parameter, which contains the message.
         */
        //OPTION 1: SIMPLE ONE
        //The message - What was sent. Access it via update.getMessage().
        //var msg = update.getMessage();
        //The user - Who sent the message. Access it via update.getMessage().getFrom().
        //var user = msg.getFrom();
        //System.out.println(user.getFirstName() + " wrote " + msg.getText());

        //OPTION 2: sending messages
        //The message - What was sent. Access it via update.getMessage().
        //var msg = update.getMessage();
        //The user - Who sent the message. Access it via update.getMessage().getFrom().
        //var user = msg.getFrom();
        //System.out.println(user.getFirstName() + " wrote " + msg.getText());


        //OPTION 3 - EchoBot
        //3A Simple EchoBot
        //Trying EchoBot option - every text message it receives will be sent right back to the user
        //By saving the User ID and calling sendText right after each update.
        //var msg = update.getMessage();
        //var user = msg.getFrom();
        //var id = user.getId();
        //sendText(id, msg.getText()); //the bot retrives a message and returns it to user


        //3B fully functional EchoBot - using custom copyMessage method
        //var msg = update.getMessage();
        //var user = msg.getFrom();
        //var id = user.getId();
        //copyMessage(id, msg.getMessageId());


        //Option 4 - executing commands - pending to revise
        //var msg = update.getMessage();
        //var user = msg.getFrom();
        //var id = user.getId();

        //4.1 whisper and scream commands
        //processing commands before replying
        /* It checks if the message is a command. If it is, the bot enters scream mode.
         * In the update method, we check which mode we are in and either copy the message
         * or convert it to upper case before sending it back.
         * The bot can execute commands and change its behavior accordingly.
         */
        /*if(msg.isCommand()){
            if(msg.getText().equals("/scream"))         //If the command was /scream, we switch gears
                screaming = true;
            else if (msg.getText().equals("/whisper"))  //Otherwise, we return to normal
                screaming = false;

            return;                                     //We don't want to echo commands, so we exit
        }


        if(screaming)                            //If screaming
            scream(id, update.getMessage());     //Call a custom method
        else
            copyMessage(id, msg.getMessageId()); //Else proceed normally
*/
        //TODO:  this simplified logic will change the bot's behavior for everyone –
        // not just the person who sent the command. This won't work in a production environment –
        // consider using a Map, dictionary or equivalent data structure to assign settings
        // for individual users.

        //TODO: Remember to always implement a few basic global commands.
        //TODO: Implement the /start command.


        //4.2: setting Keyboard menu buttons
/*        var txt = msg.getText();
        if(msg.isCommand()) {
            if (txt.equals("/scream"))
                screaming = true;
            else if (txt.equals("/whisper"))
                screaming = false;
            else if (txt.equals("/menu"))
                //sendMenu(id, "<b>Menu 1</b>", keyboardM1);
                sendMenu(id, "<b>Menu 2</b>", keyboardM2);

            return;
        }*/

        //TODO: send a new menu for each new user
        //TODO: In a production environment, commands should be isolated into different executor classes –
        // modular and separated from the main logic.


        //4.3 commands - tests
        //TODO - LLAMAR MEJOR AL METODO
        //TODO: APLICAR SWITCH MEJORADO EN VEZ DE IF ELSE
        String command = update.getMessage().getText();
        String message = "";
        if (command.equals("/scream")) {
            message = "Screaming"; //the message
            //SendMessage response = new SendMessage(); //object responsible for response to from chatbot to the user
            //response.setChatId(update.getMessage().getChatId().toString()); // chatId - the response should go to the exact user
            //response.setText(message); //actual message

/*            try {
                execute(response);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }*/

        } else if (command.equals("/solicitarrecogida")) {
            message = "Gracias. Has solicitado la recogida.";
/*            SendMessage response = new SendMessage(); //object responsible for response to from chatbot to the user
            response.setChatId(update.getMessage().getChatId().toString()); // chatId - the response should go to the exact user
            response.setText(message); //actual message
            try {
                execute(response);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }*/
            
        } else if (command.equals("/publicardonacion")) {
            message = "Introduce los datos";
/*            SendMessage response = new SendMessage(); //object responsible for response to from chatbot to the user
            response.setChatId(update.getMessage().getChatId().toString()); // chatId - the response should go to the exact user
            response.setText(message); //actual message
            try {
                execute(response);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }*/
        }

        SendMessage response = new SendMessage(); //object responsible for response to from chatbot to the user
        response.setChatId(update.getMessage().getChatId().toString()); // chatId - the response should go to the exact user
        response.setText(message); //actual message
        try {
            execute(response);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }


    }





    //CUSTOM METHODS
    //Custom method scream (to use in Option4: executing commands)
    private void scream(Long id, Message msg) {
        if(msg.hasText())
            sendText(id, msg.getText().toUpperCase());
        else
            copyMessage(id, msg.getMessageId());  //We can't really scream a sticker
    }


    //SENDING MESSAGES BY THE BOT (used in OPTION2)
    /**
     * To send a private text message, you generally need three things:
     *     The user must have contacted your bot first.
     *     TODO: (Unless the user sent a join request to a group where your bot is an admin, but that's a more advanced scenario).
     *     You must have previously saved the User ID (user.getId())
     *     A String object containing the message text, 1-4096 characters.
     */
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

    //Sending Keyboards
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt) //formatting option - allows to use HTML tags and add formatting to the text
                .replyMarkup(kb).build(); //Sending a keyboard requires specifying a reply markup for the message.

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    //Navigation: Basic Button handler - With this handler, whenever a button is tapped,
    // the bot will automatically navigate between inline menus.
    //CallbackQueries - results we get after the user taps on a button.
    //A CallbackQuery is essentially composed of three main parameters:
    //    queryId - Needed to close the query. It is necessary to close new queries after processing them
    //    – if you don't, a loading symbol will keep showing on the user's side on top of each button.
    //    data - This identifies which button was pressed.
    //    from - The user who pressed the button.
    //private void buttonTap(Long id, String queryId, String data, int msgId) throws TelegramApiException
    public void buttonTap(Long id, String queryId, String data, int msgId) throws TelegramApiException

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
    }


} //end class Bot
