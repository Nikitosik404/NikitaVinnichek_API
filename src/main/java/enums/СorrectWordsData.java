package enums;

import java.util.Arrays;
import java.util.List;

public enum СorrectWordsData {
    СORRECT_EN_WORDS("mother", "sister", "brother"),
    СORRECT_RU_WORDS("мать", "сестра", "брат");


    public List<String> value;

    СorrectWordsData(String... value) {
        this.value = Arrays.asList(value);
    }
}
