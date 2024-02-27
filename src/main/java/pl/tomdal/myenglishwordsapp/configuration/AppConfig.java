package pl.tomdal.myenglishwordsapp.configuration;

import lombok.Getter;
import lombok.Setter;


public class AppConfig {
    public static final int WORDS_IN_TABLE = 10;
    public static final String API_KEY = "2830fd16454943e5b2b44ada3746567f";
    @Setter
    @Getter
    private static Boolean isCacheUpToDate = false;

    private AppConfig() {
        throw new AssertionError("This class should not be instantiated.");
    }
}
