package uz.pdp.YandexTranslate;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.YandexTranslate.model.DefItem;
import uz.pdp.YandexTranslate.model.Result;
import uz.pdp.YandexTranslate.model.TrItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YandexBot extends TelegramLongPollingBot {
    public static final String botToken = "1994614660:AAGFEWz9CHnNP6HLxvVZg4jY56voX5yhADs"; //UNIQUE TOKEN OF BOT
    public static final String botUserName = "something_by_someone_bot";

    public String getBotUsername() {
        return botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    int level = 0;
    String language = "";


    @SneakyThrows
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        Long chatId;
        String text = "";


        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();

            if (text.equals("/start")) {
                sendMessage.setText("\uD83D\uDC4B Yandex botga Xush kelibsiz!");
                level = 0;
            } else if (text.equals("Tillar ro'yxati")) {
                sendMessage.setText("Tillar ro'yxati : <----->");
                level = 1;
            } else if (text.equals("en-ru") || text.equals("ru-en") || text.equals("ru-tr") || text.equals("tr-ru") || text.equals("tr-en") || text.equals("en-tr")) {
                language = text;
                sendMessage.setText("Qidirayotgan So'zni kiriting: ");
                level = 2;
            } else {
                level = 3;
            }
            sendMessage.setChatId(chatId);
            switch (level) {
                case 0:
                    chooseLang(sendMessage); //BUTTONS FOR SELECTING LANGUAGES
                    level = 1;
                    break;
                case 1:
                    try {
                        getLangs(sendMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    level = 2;
                    break;
                case 2:
                    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(replyKeyboardRemove);
                    level = 3;
                    break;
                case 3:
                    StringBuilder stringBuilder = new StringBuilder();
                    Result result = YandexUtil.lookUp(language, text);
                    List<DefItem> def = result.getDef();
                    for (DefItem defItem : def) {
                        List<TrItem> tr = defItem.getTr();
                        for (TrItem trItem : tr) {
                            stringBuilder.append(trItem.getText()).append("\n");
                        }
                    }
                    sendMessage.setChatId(chatId);
                    sendMessage.setText(String.valueOf(stringBuilder));
                    break;
            }
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void getLangs(SendMessage sendMessage) throws IOException {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true); //SELECTING
        replyKeyboardMarkup.setResizeKeyboard(true); //AUTO SIZE
        replyKeyboardMarkup.setOneTimeKeyboard(false); //IS IT ONE TIME?

        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("en-ru"));
        keyboardRow.add(new KeyboardButton("ru-en"));
        keyboardRow.add(new KeyboardButton("en-tr"));
        keyboardRow.add(new KeyboardButton("tr-en"));
        keyboardRow.add(new KeyboardButton("ru-tr"));
        keyboardRow.add(new KeyboardButton("tr-ru"));
        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public void chooseLang(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();
        firstRow.add(new KeyboardButton("Tillar ro'yxati"));

        keyboardRows.add(firstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

    }
}
