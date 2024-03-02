package pl.tomdal.myenglishwordsapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "words")
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", nullable = false)
    private Long wordId;

    @Column(name = "wordToLearn", nullable = false)
    private String wordToLearn;

    @Column(name = "description")
    private String description;

    @Column(name = "translation")
    private String translation;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "word_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private WordStatus wordStatus;

    @Column(name = "counter", nullable = false)
    private Integer counter;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy = "wordEntity")
    private List<SentenceEntity> sentenceEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordEntity that = (WordEntity) o;
        return Objects.equals(wordToLearn, that.wordToLearn) && Objects.equals(description, that.description) && Objects.equals(translation, that.translation) && category == that.category && wordStatus == that.wordStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordToLearn, description, translation, category, wordStatus);
    }
}
