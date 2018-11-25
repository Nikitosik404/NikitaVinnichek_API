package enums;

import java.util.Arrays;
import java.util.List;

public enum  TextWithURL {
    EN_TEXTS_WITH_URL("London is the capital123 of Great Britain.", "The postman carr1es mail."),
    RU_TEXTS_WITH_URL("Лондон - столиц0 Великобритании.", "Почталь0н н0сит почту.");

    public List<String> list;

    TextWithURL(String... value) {
        this.list = Arrays.asList(value);
    }
}
