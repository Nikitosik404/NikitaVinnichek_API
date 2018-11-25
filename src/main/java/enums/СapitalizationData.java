package enums;

import java.util.Arrays;
import java.util.List;

public enum СapitalizationData {
    EN_WORDS_WITH_WRONG_REGISTER("london is the capital of Great Britain.", "berlin"),
    EN_WORDS_WITH_RIGHT_REGISTER("London", "Berlin"),
    RU_WORDS_WITH_WRONG_REGISTER("лондон - сталица Великобритании.", "Почтальон носид почту в берлин."),
    RU_WORDS_WITH_RIGHT_REGISTER("Лондон", "Берлин");

    public List<String> list;

    СapitalizationData(String... value) {
        this.list = Arrays.asList(value);
    }
}
