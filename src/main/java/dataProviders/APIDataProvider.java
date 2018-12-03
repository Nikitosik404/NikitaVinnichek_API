package dataProviders;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

import static enums.Language.*;

public class APIDataProvider {

    @DataProvider(parallel = true)
    public Object[][] wrongWordDataProvider() {
        return new Object[][]{
                //Russian
                {new String[]{"расия", "малоко"},
                        RU,
                        new List[]{Arrays.asList("россия", "сария", "расья"),
                                Arrays.asList("молоко", "молока", "малого")}},

                //Ukrainian
                {new String[]{"заборр", "яблако"},
                        UK,
                        new List[]{Arrays.asList("забор", "забора", "заборы"),
                                Arrays.asList("яблоко", "яблочко", "яблуко", "яблока")}},

                //English
                {new String[]{"englis", "moter"},
                        EN,
                        new List[]{Arrays.asList("english", "anglais", "englisch", "angles", "anglia", "engels", "englihs", "anglers"),
                                Arrays.asList("mother", "motor", "meter")}}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] capitalizationDataProvider() {
        return new Object[][]{
                //Russian
                {new String[]{"никита", "ленин"},
                        RU,
                        new List[]{Arrays.asList("Никита"),
                                Arrays.asList("Ленин")}},

                //Ukrainian
                {new String[]{"тэтяна", "одесса"},
                        UK,
                        new List[]{Arrays.asList("Тэтяна"),
                                Arrays.asList("Одесса")}},


                //English
                {new String[]{"london", "washington"},
                        EN,
                        new List[]{Arrays.asList("London"),
                                Arrays.asList("Washington")}}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] correctWordsDataProvider() {
        return new Object[][]{
                //Russian
                {new String[]{"мать", "отец"}, RU},

                //Ukrainian
                {new String[]{"матір", "батько"}, UK},

                //English
                {new String[]{"mother", "father"}, EN}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] wordsWithDigitsDataProvider() {
        return new Object[][]{
                //Russian
                {new String[]{"мат1ь", "оте4ц"}, RU},

                //Ukrainian
                {new String[]{"ма5тір", "бат6ько"}, UK},

                //English
                {new String[]{"mothe1r", "fa4ther"}, EN}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] repeatWordsDataProvider() {
        return new Object[][]{
                //Ukrainian case
                {new String[]{"Вам варто варто відвідати Білорусь.",
                        "Срібло дорожче дорожче золота."},

                        new String[]{"Вам варто відвідати Білорусь.",
                                "Срібло дорожче золота."},
                        UK},

                //Russian case
                {new String[]{"Вам стоит стоит посетить Беларусь.",
                        "Серебро дороже дороже золота."},

                        new String[]{"Вам стоит посетить Беларусь.",
                                "Серебро дороже золота."},
                        RU},

                //English case
                {new String[]{"You should visit visit Belarus.",
                        "Silver is more expensive than than gold."},

                        new String[]{"You should visit Belarus.",
                                "Silver is more expensive than gold."},
                        EN}
        };
    }
}
