package enums;

import java.util.Arrays;
import java.util.List;

public enum WrongWordData {

    EN_WORDS_WITH_MISTAKE("London is the capitall of Great Britain.", "The postman caries mail."),
    EN_WORDS_WITHOUT_MISTAKE1("capital"),
    EN_WORDS_WITHOUT_MISTAKE2("carries", "carried", "carrier"),
    RU_WORDS_WITH_MISTAKE("Лондон - сталица Великобритании.", "Почтальон носид почту."),
    RU_WORDS_WITHOUT_MISTAKE1("столица", "столицы"),
    RU_WORDS_WITHOUT_MISTAKE2("носит", "носил", "носить");

    public List<String> list;

    WrongWordData(String... value) {
        this.list = Arrays.asList(value);
    }
}