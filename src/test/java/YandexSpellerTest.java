
import beans.YandexSpellerResponse;
import core.YandexSpellerApi;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerApi.baseRequestConfiguration;
import static core.YandexSpellerConstant.ParamsRequest.LANG;
import static core.YandexSpellerConstant.ParamsRequest.TEXT;
import static core.YandexSpellerConstant.WrongEnglishTexts.*;
import static enums.Language.EN;
import static enums.Language.EN_WRONG;
import static enums.Language.RU;
import static enums.RepeatWordsData.RU_TEXTS_WITHOUT_REPEAT_WORDS;
import static enums.RepeatWordsData.RU_TEXTS_WITH_REPEAT_WORDS;
import static enums.TextsWithDigits.*;
import static enums.WrongWordData.*;
import static enums.СapitalizationData.EN_WORDS_WITH_RIGHT_REGISTER;
import static enums.СapitalizationData.EN_WORDS_WITH_WRONG_REGISTER;
import static enums.СorrectWordsData.СORRECT_EN_WORDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class YandexSpellerTest {

    @Test
    public void checkCorrectText() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .language(EN.languageCode)
                        .text(СORRECT_EN_WORDS.value)
                        .callApi()
        );
        for(int i=0; i < answers.size(); i++) {
            assertEquals(0, answers.get(i).size());
        }
    }

    @Test
    public void checkWrongEnglishTexts() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(EN_WORDS_WITH_MISTAKE.list)
                        .language(EN.languageCode)
                        .callApi()
        );
        System.out.println("Answer size " + answers.size());

        List[] expectdResult = new List[]{EN_WORDS_WITHOUT_MISTAKE1.list, EN_WORDS_WITHOUT_MISTAKE2.list};
        for (int i = 0; i < answers.size(); i++) {
            assertThat(answers.get(i).get(0).s, equalTo(expectdResult[i]));
        }
    }

    @Test
    public void checkWrongRussianTexts() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(RU_WORDS_WITH_MISTAKE.list)
                        .language(RU.languageCode)
                        .callApi()
        );

        List[] expectdResult = new List[]{RU_WORDS_WITHOUT_MISTAKE1.list, RU_WORDS_WITHOUT_MISTAKE2.list};
        for (int i = 0; i < answers.size(); i++) {
            assertThat(answers.get(i).get(0).s, equalTo(expectdResult[i]));
        }
    }

    @Test
    public void checkTextsWithRepeat() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(RU_TEXTS_WITH_REPEAT_WORDS.list)
                        .language(RU.languageCode)
                        .callApi()
        );

        List[] expectdResult = new List[]{RU_TEXTS_WITHOUT_REPEAT_WORDS.list};
        for (int i = 0; i < answers.size(); i++) {
            assertThat(answers.get(i).get(0).s, equalTo(expectdResult[i]));
        }
    }

    @Test
    public void checkTextsWithDigits() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(EN_TEXTS_WITH_DIGITS.list)
                        .language(EN.languageCode)
                        .callApi()
        );

        List[] expectdResult = new List[]{EN_TEXTS_WITHOUT_DIGITS1.list, EN_TEXTS_WITHOUT_DIGITS2.list};
        for (int i = 0; i < answers.size(); i++) {
            assertThat(answers.get(i).get(0).s, equalTo(expectdResult[i]));
        }
    }

    @Test
    public void checkTextsWithWrongRegister() {
        List<List<YandexSpellerResponse>> answers = YandexSpellerApi.getYandexSpellerResponses(
                YandexSpellerApi.with()
                        .text(EN_WORDS_WITH_WRONG_REGISTER.list)
                        .language(EN.languageCode)
                        .callApi()
        );

        assertThat(answers.size(), equalTo(EN_WORDS_WITH_WRONG_REGISTER.list.size()));

        List[] expectdResult = new List[]{EN_WORDS_WITH_RIGHT_REGISTER.list, EN_WORDS_WITH_RIGHT_REGISTER.list};
        for (int i = 0; i < answers.size(); i++) {
            assertThat(answers.get(i).get(0).s, equalTo(expectdResult[i]));
        }
    }

    @Test
    public void checkSuccessResponce() {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .params(TEXT.value, СORRECT_EN_WORDS.value)
                .get().prettyPeek()
                .then().specification(YandexSpellerApi.successResponse());
    }

    @Test
    public void checkWrongLanguage() {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .params(TEXT.value, СORRECT_EN_WORDS.value)
                .params(LANG.value, EN_WRONG.languageCode)
                .get().prettyPeek()
                .then()
                .body(Matchers.allOf(Matchers.containsString("SpellerService: Invalid parameter 'lang'")));
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
    public void checkCorrectStatus() {
        RestAssured
                .given(baseRequestConfiguration())
                .queryParam(TEXT.value, EN_WORDS_WITH_MISTAKE)
                .log().everything()
                .when()
                .get()
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}