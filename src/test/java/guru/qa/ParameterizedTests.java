package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.data.GithubTab;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedTests {

    @ValueSource(strings = {
            "Selenide",
            "JUnit 5",
            "Allure Report"
    })
    @ParameterizedTest(name = "Поиск на GitHub по запросу {0} должен возвращать результаты")
    @Tag("WEB:SMOKE")
    void githubSearchShouldReturnResults(String searchQuery) {
        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("#query-builder-test").setValue(searchQuery).pressEnter();
        $$("[data-testid='results-list']").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @EnumSource(GithubTab.class)
    @ParameterizedTest(name = "Таб {0} должен быть отображен на странице репозитория")
    @Tag("WEB:SMOKE")
    void githubTabsShouldBeVisible(GithubTab tab) {
        open("https://github.com/selenide/selenide");
        $$(".UnderlineNav-item").findBy(text(tab.title)).shouldBe(visible);
    }

    //Вариант данных из аннотации
    @CsvSource({
            "https://github.com/features, Features",
            "https://github.com/explore, Explore",
            "https://github.com/marketplace, Enhance your workflow with extensions"
    })
    @ParameterizedTest(name = "GitHub (из аннотации): {0} содержит текст {1}")
    @Tag("WEB:SMOKE")
    void githubPagesShouldContainExpectedHeaderCsvSource(String url, String expectedText) {
        open(url);
        $("body").shouldHave(text(expectedText));
    }

    //Вариант данных из файла
    @CsvFileSource(resources = "/test_data/githubPages.csv")
    @ParameterizedTest(name = "GitHub (из файла): {0} содержит текст {1}")
    @Tag("WEB:SMOKE")
    void githubPagesShouldContainExpectedHeaderCsvFileSource(String url, String expectedText) {
        open(url);
        $("body").shouldHave(text(expectedText));
    }

    static Stream<Arguments> githubRepoHeaderShouldBeCorrect() {
        return Stream.of(
                Arguments.of("selenide/selenide", "selenide"),
                Arguments.of("junit-team/junit5", "junit5")
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Для репозитория {0} должен отображаться заголовок {1}")
    @Tag("WEB:SMOKE")
    void githubRepoHeaderShouldBeCorrect(String repo, String expectedTitle) {
        open("https://github.com/" + repo);
        $("strong.mr-2").shouldHave(text(expectedTitle));
    }
}
