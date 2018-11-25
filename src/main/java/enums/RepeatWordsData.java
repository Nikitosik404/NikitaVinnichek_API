package enums;

import java.util.Arrays;
import java.util.List;

public enum  RepeatWordsData {
    EN_TEXTS_WITH_REPEAT_WORDS("London is the capital capital of Great Britain.", "The postman carries mail mail."),
    EN_TEXTS_WITHOUT_REPEAT_WORDS("London is the capital of Great Britain.", "The postman carries mail."),
    RU_TEXTS_WITH_REPEAT_WORDS("Я подумаю подумаю над этим позже.", "Почтальон носит носит почту."),
    RU_TEXTS_WITHOUT_REPEAT_WORDS("Лондон - столица Великобритании.", "Почтальон носит почту.");

    public String value;
    public List<String> list;

    RepeatWordsData(String value) {
        this.value = value;
    }

    RepeatWordsData(String... value) {
        this.list = Arrays.asList(value);
    }
}
