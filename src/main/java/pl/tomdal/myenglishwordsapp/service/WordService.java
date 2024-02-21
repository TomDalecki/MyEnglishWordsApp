package pl.tomdal.myenglishwordsapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;

import java.time.LocalDateTime;
import java.util.*;

import static pl.tomdal.myenglishwordsapp.configuration.AppConfig.WORDS_IN_TABLE;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordDAO wordDAO;
    private final Random random = new Random();

    public List<Word> findWords(Category category, Integer numberOfWords) {
        return wordDAO.findWords(category, numberOfWords);
    }

    public List<Word> findAllToLearn() {
        return wordDAO.findAllWordsToLearn();
    }

    public List<Word> findAllByCategory(Category category) {
        return wordDAO.findAllByCategory(category);
    }

    public List<Word> findWordsToLearnByCounterValue(List<Word> allWords) {
        Comparator<Word> comparing = Comparator.comparing(Word::getCounter);

        return allWords.stream()
                .filter(word -> word.getWordStatus().equals(WordStatus.TO_LEARN))
                .sorted(comparing)
                .limit(WORDS_IN_TABLE)
                .toList();
    }

    public List<Word> prepareRandomListOfWordsToLearn(List<Word> allWordsToLearn, Integer numberOfWordsInResult) {
        List<Word> result = new ArrayList<>();
        Set<Integer> generatedRandomNumbers = new HashSet<>();

        while(generatedRandomNumbers.size() < numberOfWordsInResult){
            generatedRandomNumbers.add(this.random.nextInt(allWordsToLearn.size()));
        }

        for (Integer i : generatedRandomNumbers) {
            result.add(allWordsToLearn.get(i));
        }

        return result;
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

    public Category wordCategoryFinder(String text) {
        for (Category category : Category.values()) {
            if (category.getText().equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum with text " + text + " found");
    }
}
