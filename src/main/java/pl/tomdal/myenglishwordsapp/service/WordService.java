package pl.tomdal.myenglishwordsapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.service.cash.Cache;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;

import java.time.LocalDateTime;
import java.util.*;

import static pl.tomdal.myenglishwordsapp.configuration.AppConfig.*;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordDAO wordDAO;
    private final Random random = new Random();
    private final Cache cache;

    void checkCache() {
        if (getIsCacheUpToDate().equals(false)) {
            cache.writeToCache(wordDAO.findAllWordsToLearn());
            setIsCacheUpToDate(true);
        }
    }

    public List<Word> findAllToLearnByCategory(Category category) {
        return wordDAO.findAllByCategory(category);
    }

    public List<Word> findWordsToLearnByCounterValue() {
        checkCache();

        Comparator<Word> comparing = getWordComparator();

        return cache.readFromCache().stream()
                .filter(word -> word.getWordStatus().equals(WordStatus.TO_LEARN))
                .sorted(comparing)
                .limit(WORDS_IN_TABLE)
                .toList();
    }

    public List<Word> findWordsToLearnByCounterValue(List<Word> someWordsList) {

        Comparator<Word> comparing = getWordComparator();

        return someWordsList.stream()
                .filter(word -> word.getWordStatus().equals(WordStatus.TO_LEARN))
                .sorted(comparing)
                .limit(WORDS_IN_TABLE)
                .toList();
    }

    public List<Word> prepareRandomListOfWordsToLearn(Integer numberOfWordsInResult) {
        List<Word> result = new ArrayList<>();

        Integer drawnRange = cache.readFromCache().size();
        Set<Integer> randomDigits = drawRandomDigits(numberOfWordsInResult, drawnRange);

        for (Integer i : randomDigits) {
            result.add(cache.readFromCache().get(i));
        }

        return result;
    }

    public Set<Integer> drawRandomDigits(Integer digitsInResult, Integer drawnRange) {
        Set<Integer> randomDigits = new HashSet<>();
        while (randomDigits.size() < digitsInResult) {
            randomDigits.add(this.random.nextInt(drawnRange));
        }
        return randomDigits;
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
        setIsCacheUpToDate(false);
    }

    public void statusUpdate(Long wordId, WordStatus wordStatus) {
        wordDAO.statusUpdate(wordId, wordStatus);
        setIsCacheUpToDate(false);
    }

    public void worldCounterUpdate(Long wordId, Integer currentCounterValue) {
        Integer newCounterValue = currentCounterValue + 1;
        wordDAO.wordCounterUpdate(wordId, newCounterValue);
        setIsCacheUpToDate(false);
    }

    public void deleteByWordId(Long wordId) {
        wordDAO.deleteByWordId(wordId);
        setIsCacheUpToDate(false);
    }

    public Category wordCategoryFinder(String text) {
        for (Category category : Category.values()) {
            if (category.getText().equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum with text " + text + " found");
    }

    private static Comparator<Word> getWordComparator() {
        return Comparator.comparing(Word::getCounter);
    }
}
