package pl.tomdal.myenglishwordsapp.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@With
@Builder
@Value
public class Word {
    Long wordId;
    String wordToLearn;
    String description;
    String translation;
    Category category;
    WordStatus wordStatus;
    Integer counter;
    LocalDateTime timeStamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(wordToLearn, word.wordToLearn) && Objects.equals(description, word.description) && Objects.equals(translation, word.translation) && category == word.category && wordStatus == word.wordStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordToLearn, description, translation, category, wordStatus);
    }
}
