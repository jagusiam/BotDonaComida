package org.botdonacomida;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    private static Update update;
    private static InlineKeyboardMarkup inKb;
    private static Message msg;
    //private static AnswerCallbackQuery callbackQuery;
    private static CallbackQuery callbackQuery;



    public static void main(String[] args) throws TelegramApiException {
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
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot(); //We moved this line out of the register method, to access it later
            botsApi.registerBot(bot);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }


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

    }
}