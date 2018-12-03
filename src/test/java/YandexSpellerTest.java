
import beans.YandexSpellerResponse;
import core.YandexSpellerApi;
import dataProviders.APIDataProvider;
import enums.Language;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerConstant.Options.FIND_REPEAT_WORDS;
import static core.YandexSpellerConstant.Options.IGNORE_DIGITS;
import static core.YandexSpellerConstant.ParamsRequest.*;
import static core.YandexSpellerConstant.WrongEnglishTexts.*;
import static enums.Language.EN_WRONG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class YandexSpellerTest {

    @Test(dataProvider = "correctWordsDataProvider", dataProviderClass = APIDataProvider.class)
    public void checkCorrectText(String[] texts, Language lang) {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .language(lang)
                        .texts(texts)
                        .callApi()
        );

        //Check that the answer contains the correct number of options.
        assertThat(answers.size(), equalTo(texts.length));

        //Verification of the expected response and valid
        try (AutoCloseableSoftAssertions soft = new AutoCloseableSoftAssertions()) {
            for (int i = 0; i < texts.length; i++) {
                //Check that current response array item is empty
                soft.assertThat(answers.get(i)).as("Suggestion for the correct word <" + texts[i] + ">")
                        .isEmpty();
            }
        }
    }

    @Test(dataProvider = "wrongWordDataProvider", dataProviderClass = APIDataProvider.class)
    public void checkWrongTexts(String[] texts, Language lang, List[] expectedResult) {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .texts(texts)
                        .language(lang)
                        .callApi()
        );

        //Check that the answer contains the correct number of options.
        assertThat(answers.toArray(), arrayWithSize(texts.length));

        //Verification of the expected response and valid
        try (AutoCloseableSoftAssertions soft = new AutoCloseableSoftAssertions()) {
            for (int i = 0; i < texts.length; i++) {
                //Check that current response array item is not empty
                if (!answers.get(i).isEmpty()) {
                    soft.assertThat(answers.get(i).get(0).s)
                            .as("Actual suggestions")
                            .isEqualTo(expectedResult[i]);
                } else {
                    soft.assertThat(answers.get(i)).as("Actual suggestions").isEmpty();
                }
            }
        }

    }


    @Test(dataProvider = "repeatWordsDataProvider", dataProviderClass = APIDataProvider.class)
    public void checkTextRepeatWords(String[] texts, String[] expectedResult, Language lang) {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .texts(texts)
                        .language(lang)
                        .options(FIND_REPEAT_WORDS)
                        .callApi()
        );

        //Check that the answer contains the correct number of options.
        assertThat(answers.toArray(), arrayWithSize(texts.length));

        //Verification of the expected response and valid
        try (AutoCloseableSoftAssertions soft = new AutoCloseableSoftAssertions()) {
            for (int i = 0; i < texts.length; i++) {
                //Check that current response array item is not empty
                soft.assertThat(answers.get(i))
                        .as("Suggestion for string with repeated words <" + texts[i] + "> and lang <" + lang + ">")
                        .isEqualTo(expectedResult[i]);
            }
        }
    }

    @Test(dataProvider = "wordsWithDigitsDataProvider", dataProviderClass = APIDataProvider.class)
    public void checkTextsWithDigits(String[] texts, Language lang) {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .texts(texts)
                        .language(lang)
                        .options(IGNORE_DIGITS)
                        .callApi()
        );

        //Check that the answer contains the correct number of options.
        assertThat(answers.toArray(), arrayWithSize(texts.length));

        //Verification of the expected response and valid
        try (AutoCloseableSoftAssertions soft = new AutoCloseableSoftAssertions()) {
            for (int i = 0; i < texts.length; i++) {
                //Check that current response array item is empty
                soft.assertThat(answers.get(i)).as("Suggestion for the alphanumeric string <" + texts[i] + ">")
                        .isEmpty();
            }
        }
    }

    @Test(dataProvider = "capitalizationDataProvider", dataProviderClass = APIDataProvider.class)
    public void checkWordsWithWrongRegister(String[] texts, Language lang, List[] expectedSuggestions) {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .texts(texts)
                        .language(lang)
                        .callApi()
        );

        //Check that the answer contains the correct number of options.
        assertThat(answers.toArray(), arrayWithSize(texts.length));

        //Verification of the expected response and valid
        try (AutoCloseableSoftAssertions soft = new AutoCloseableSoftAssertions()) {
            for (int i = 0; i < texts.length; i++) {
                //Check that current response array item is not empty
                if (!answers.get(i).isEmpty()) {
                    soft.assertThat(answers.get(i).get(0).s)
                            .as("Actual suggestions")
                            .isEqualTo(expectedSuggestions[i]);
                } else {
                    soft.assertThat(answers.get(i)).as("Actual suggestions for text <" + texts[i] + ">")
                            .isEqualTo(expectedSuggestions[i]);
                }
            }
        }
    }


    @Test
    public void checkWrongLanguage() {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .params(TEXT.value, WRONG_BROTHER.value)
                .params(LANG.value, EN_WRONG.languageCode)
                .log().all()
                .when()
                .get(YANDEX_TEXTS.value)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("SpellerService: Invalid parameter 'lang'"));
    }


    @Test
    public void checkSentTexts() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(Arrays.asList(WRONG_SISTER.value + " " + WRONG_BROTHER.value, WRONG_COMMUNICATION.value))
                        .callApi()
        );
        assertEquals(answers.get(0).get(0).word, WRONG_SISTER.value);
        assertEquals(answers.get(0).get(1).word, WRONG_BROTHER.value);
        assertEquals(answers.get(1).get(0).word, WRONG_COMMUNICATION.value);
    }

    @Test
    public void incorrectFormatTest() {
        RestAssured
                .given()
                .queryParams(TEXT.value, WRONG_BROTHER)
                .param(PARAM_FORMAT.value, UNSUPPORTED_FORMAT.value)
                .log().all()
                .when()
                .get(YANDEX_TEXTS.value)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("SpellerService: Invalid parameter 'format'"));
    }
}