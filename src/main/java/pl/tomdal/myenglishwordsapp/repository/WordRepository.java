package pl.tomdal.myenglishwordsapp.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.repository.jpa.WordJpaRepository;
import pl.tomdal.myenglishwordsapp.repository.mapper.WordEntityMapper;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;

import java.util.List;

@Repository
@AllArgsConstructor
public class WordRepository implements WordDAO {
    WordJpaRepository wordJpaRepository;
    WordEntityMapper wordEntityMapper;

    @Override
    public List<Word> findAllWordsToLearn() {
        return wordJpaRepository.findAllWordsToLearn().stream()
                .map(wordEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<Word> findWords(Category category, Integer numberOfWords) {
        return wordJpaRepository.find(category, numberOfWords).stream()
                .map(wordEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<Word> findAllByCategory(Category category) {
        return wordJpaRepository.findAllByCategory(category).stream()
                .map(wordEntityMapper::mapFromEntity).toList();
    }

    @Override
    public void statusUpdate(Long wordId, WordStatus wordStatus) {
        wordJpaRepository.statusUpdate(wordId, wordStatus);
    }

    @Override
    public void wordCounterUpdate(Long wordId, Integer newCounterValue) {
        wordJpaRepository.wordCounterUpdate(newCounterValue, wordId);
    }

    @Override
    public void deleteByWordId(Long wordId) {
        wordJpaRepository.deleteByWordId(wordId);
    }

    @Override
    public void saveWord(Word word) {
        wordJpaRepository.save(wordEntityMapper.mapToEntity(word));
    }
}
