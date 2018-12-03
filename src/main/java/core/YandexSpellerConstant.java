package core;


public class YandexSpellerConstant {


    public enum ParamsRequest {
        TEXT("text"),
        LANG("lang"),
        OPTIONS("options"),
        YANDEX_TEXTS("https://speller.yandex.net/services/spellservice.json/checkTexts"),
        PARAM_FORMAT( "format"),
        UNSUPPORTED_FORMAT("Unsupported");;

        public String value;

        ParamsRequest(String value) {
            this.value = value;
        }
    }

    public enum Options {
        IGNORE_DIGITS("2"),
        FIND_REPEAT_WORDS("8");

        private String optionValue;

        Options(String option){
            this.optionValue = option;
        }

        @Override
        public String toString() {
            return optionValue;
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
}