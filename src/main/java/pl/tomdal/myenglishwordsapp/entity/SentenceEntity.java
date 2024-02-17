package pl.tomdal.myenglishwordsapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sentence")
public class SentenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sentence_id", nullable = false)
    private Long sentenceId;

    @Column(name = "sentence_with_word")
    private String sentenceWithWord;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private WordEntity wordEntity;

}