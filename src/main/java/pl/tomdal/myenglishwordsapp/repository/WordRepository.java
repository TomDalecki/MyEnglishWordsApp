package pl.tomdal.myenglishwordsapp.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.repository.jpa.WordJpaRepository;
import pl.tomdal.myenglishwordsapp.repository.mapper.WordEntityMapper;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class WordRepository implements WordDAO {
    WordJpaRepository wordJpaRepository;
    WordEntityMapper wordEntityMapper;

    @Override
    public List<Word> findAllWordsToLearn() {
        log.info("Pobrałem AllWordsToLearn z DB");
        return wordJpaRepository.findAllWordsToLearn().stream()
                .map(wordEntityMapper::mapFromEntity).toList();
    }

    @Override
    public List<Word> findAllByCategory(Category category) {
        log.info("Pobrałem WordsByCategory z DB");
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
