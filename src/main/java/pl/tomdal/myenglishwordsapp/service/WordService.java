package pl.tomdal.myenglishwordsapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static pl.tomdal.myenglishwordsapp.configuration.AppConfig.WORDS_IN_TABLE;

@Service
@AllArgsConstructor
public class WordService {
    WordDAO wordDAO;

    public List<Word> findWords(Category category, Integer numberOfWords) {
        return wordDAO.findWords(category, numberOfWords);
    }

    public List<Word> findAllByCategory(Category category) {
        return wordDAO.findAllByCategory(category);
    }

    public void saveWord(Word dataFromForm) {
        Word word = Word.builder()
                .wordToLearn(dataFromForm.getWordToLearn())
                .description(dataFromForm.getDescription())
                .translation(dataFromForm.getTranslation())
                .category(dataFromForm.getCategory())
                .wordStatus(WordStatus.TO_LEARN)
                .counter(0)
                .timeStamp(LocalDateTime.now())
                .build();

        wordDAO.saveWord(word);
    }

    public List<Word> findWordsToLearn(List<Word> allWords) {
        Comparator<Word> comparing = Comparator.comparing(Word::getCounter);

        return allWords.stream()
                .filter(word -> word.getWordStatus().equals(WordStatus.TO_LEARN))
                .sorted(comparing)
                .limit(WORDS_IN_TABLE)
                .toList();
    }

    public void statusUpdate(Long wordId, WordStatus wordStatus) {
        wordDAO.statusUpdate(wordId, wordStatus);
    }

    public void worldCounterUpdate(Long wordId, Integer currentCounterValue) {
        Integer newCounterValue = currentCounterValue + 1;
        wordDAO.wordCounterUpdate(wordId, newCounterValue);
    }

    public void deleteByWordId(Long wordId) {
        wordDAO.deleteByWordId(wordId);
    }

    public List<Word> findAllToLearn() {
        return wordDAO.findAllWordsToLearn();
    }

    public Category wordCategoryFinder(String text) {
        for (Category category : Category.values()) {
            if (category.getText().equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum with text " + text + " found");
    }
}
