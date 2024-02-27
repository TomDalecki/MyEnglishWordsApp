package pl.tomdal.myenglishwordsapp.util;

import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.time.LocalDateTime;

public class TestDataFixtures {

    public static Word someWord1() {
        return Word.builder()
                .wordToLearn("word1")
                .description("description1")
                .translation("translation1")
                .category(Category.NOUN)
                .wordStatus(WordStatus.TO_LEARN)
                .counter(2)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static Word someWord2() {
        return Word.builder()
                .wordToLearn("word2")
                .description("description2")
                .translation("translation2")
                .category(Category.NOUN)
                .wordStatus(WordStatus.TO_LEARN)
                .counter(1)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static Word someWord3() {
        return Word.builder()
                .wordToLearn("word3")
                .description("description3")
                .translation("translation3")
                .category(Category.NOUN)
                .wordStatus(WordStatus.TO_LEARN)
                .counter(0)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static Word someWord4() {
        return Word.builder()
                .wordToLearn("word4")
                .description("description4")
                .translation("translation4")
                .category(Category.NOUN)
                .wordStatus(WordStatus.LEARNED)
                .counter(3)
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
