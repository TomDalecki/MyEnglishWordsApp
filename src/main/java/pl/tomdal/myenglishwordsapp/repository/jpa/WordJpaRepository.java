package pl.tomdal.myenglishwordsapp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.WordEntity;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.util.List;

@Repository
public interface WordJpaRepository extends JpaRepository<WordEntity, Long> {
    @Query("""
            SELECT w FROM WordEntity w
            WHERE w.category = :category AND w.wordStatus = 'TO_LEARN'
            ORDER BY w.timeStamp DESC
            LIMIT :numberOfWords
            """)
    List<WordEntity> find(Category category, Integer numberOfWords);

    @Query("""
            SELECT w FROM WordEntity w
            WHERE w.category = :category
            ORDER BY w.timeStamp DESC
            """)
    List<WordEntity> findAllByCategory(Category category);

    @Query("""
            SELECT w FROM WordEntity w
            WHERE w.wordStatus = 'TO_LEARN'
            """)
    List<WordEntity> findAllWordsToLearn();

    @Transactional
    @Modifying
    @Query("update WordEntity w set w.wordStatus = ?2 where w.wordId = ?1")
    void statusUpdate(Long wordId, WordStatus wordStatus);

    @Transactional
    @Modifying
    @Query("update WordEntity w set w.counter = ?1 where w.wordId = ?2")
    void wordCounterUpdate(Integer counter, Long wordId);

    @Transactional
    void deleteByWordId(Long wordId);
}