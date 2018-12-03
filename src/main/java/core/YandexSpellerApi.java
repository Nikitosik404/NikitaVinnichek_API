package core;

import beans.YandexSpellerResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Language;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.*;

import static core.YandexSpellerConstant.ParamsRequest.*;
import static org.hamcrest.Matchers.lessThan;
import static core.YandexSpellerConstant.*;

public class YandexSpellerApi {

    private YandexSpellerApi() {
    }

    private HashMap<String, List<String>> params = new HashMap<>();

    public static class ApiBuilder {
        YandexSpellerApi spellerApi;

        private ApiBuilder(YandexSpellerApi spellerApi) {
            this.spellerApi = spellerApi;
        }

        public ApiBuilder text(List<String> text) {
            spellerApi.params.put(TEXT.value, text);
            return this;
        }

        public ApiBuilder texts(String... texts) {
            List<String> textsContainer = new ArrayList<>();
            textsContainer.addAll(Arrays.asList(texts));
            spellerApi.params.put(TEXT.value, textsContainer);
            return this;
        }

        public ApiBuilder options(Options options) {
            spellerApi.params.put(OPTIONS.value, Collections.singletonList(options.toString()));
            return this;
        }

        public ApiBuilder language(Language lang) {
            spellerApi.params.put(LANG.value, Collections.singletonList(lang.languageCode));
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get(YANDEX_TEXTS.value)
                    .prettyPeek();
        }
    }

    public static ApiBuilder with() {
        YandexSpellerApi api = new YandexSpellerApi();
        return new ApiBuilder(api);
    }

    public static List<List<YandexSpellerResponse>> getYandexSpellerResponses(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<List<YandexSpellerResponse>>>() {
        }.getType());
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .setBaseUri(YANDEX_TEXTS.value)
                .build();
    }
}