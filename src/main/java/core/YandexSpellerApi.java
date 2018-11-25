package core;

import beans.YandexSpellerResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static core.YandexSpellerConstant.ParamsRequest.*;
import static org.hamcrest.Matchers.lessThan;

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

        public ApiBuilder options(List<String> options) {
            spellerApi.params.put(OPTIONS.value, options);
            return this;
        }

        public ApiBuilder language(String lang) {
            spellerApi.params.put(LANG.value, Collections.singletonList(lang));
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