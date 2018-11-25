package core;


public class YandexSpellerConstant {

    public enum ParamsRequest {
        TEXT("text"),
        LANG("lang"),
        OPTIONS("options"),
        FORMAT("format"),
        YANDEX_TEXTS("https://speller.yandex.net/services/spellservice.json/checkTexts");

        public String value;

        ParamsRequest(String value) {
            this.value = value;
        }
    }

    public enum WrongEnglishTexts {
        WRONG_SISTER("sester"),
        WRONG_BROTHER("bruther"),
        WRONG_COMMUNICATION("commanication");

        public String value;

        WrongEnglishTexts(String value) {
            this.value = value;
        }

    }

    public enum TextsForIgnoreDigits {
        DIGITS_123("hello123 sester"),
        DIGITS_234("brother234 commanication");

        public String value;

        TextsForIgnoreDigits(String value) {
            this.value = value;
        }
    }
}