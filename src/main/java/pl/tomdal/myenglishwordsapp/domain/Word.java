package pl.tomdal.myenglishwordsapp.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.time.LocalDateTime;

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
}
