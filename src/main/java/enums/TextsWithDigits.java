package enums;

import java.util.Arrays;
import java.util.List;

public enum TextsWithDigits {
    EN_TEXTS_WITH_DIGITS("London is the capital123 of Great Britain.", "The postman carr1es mail."),
    EN_TEXTS_WITHOUT_DIGITS1("capital 123"),
    EN_TEXTS_WITHOUT_DIGITS2( "carries",
            "carried",
            "carr1ed"),
    RU_TEXTS_WITH_DIGITS("Лондон - столиц0 Великобритании.", "Почталь0н н0сит почту.");

    public List<String> list;

    TextsWithDigits(String... value) {
        this.list = Arrays.asList(value);
    }
}
