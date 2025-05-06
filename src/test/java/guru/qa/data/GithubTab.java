package guru.qa.data;

public enum GithubTab {

    CODE("Code"), ISSUES("Issues"), PULL_REQUESTS("Pull requests");

    public final String title;

    GithubTab(String title) {
        this.title = title;

    }
}
