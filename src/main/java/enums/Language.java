package enums;

public enum Language {
    RU("ru"),
    UK("uk"),
    EN("en"),
    EN_WRONG("английский");
    public String languageCode;

    Language(String lang) {
        this.languageCode = lang;
    }
}