package pl.tomdal.myenglishwordsapp.service.dao;

import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;

import java.util.List;

public interface WordDAO {
    List<Word> findAllWordsToLearn();

    List<Word> findAllByCategory(Category category);

    void saveWord(Word word);

    void statusUpdate(Long wordId, WordStatus wordStatus);

    void wordCounterUpdate(Long wordId, Integer newCounterValue);

    void deleteByWordId(Long wordId);
}
