package pl.tomdal.myenglishwordsapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tomdal.myenglishwordsapp.configuration.AppConfig;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.service.cash.Cache;
import pl.tomdal.myenglishwordsapp.service.dao.WordDAO;
import pl.tomdal.myenglishwordsapp.util.TestDataFixtures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.tomdal.myenglishwordsapp.configuration.AppConfig.WORDS_IN_TABLE;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {
    @Mock
    private Cache cacheMock;

    @Mock
    private WordDAO wordDAOMock;

    @InjectMocks
    private WordService wordService;

    @Test
    void thatCheckCashNotUpdatesCashWhen_CashIsUpToDate() {
        //given
        AppConfig.setIsCacheUpToDate(true);

        //when
        wordService.checkCache();

        //then
        verify(cacheMock, times(0)).writeToCache(wordDAOMock.findAllWordsToLearn());
        assertEquals(true, AppConfig.getIsCacheUpToDate());
    }

    @Test
    void thatCheckCashUpdatesCashWhen_CashIsNotUpToDate() {
        //given
        AppConfig.setIsCacheUpToDate(false);

        //when
        wordService.checkCache();

        //then
        verify(cacheMock, times(1)).writeToCache(wordDAOMock.findAllWordsToLearn());
        assertEquals(true, AppConfig.getIsCacheUpToDate());
    }

    @Test
    void thatFindWordsToLearnByCounterValue_ContainsOnlyToLearnStatusWords() {
        //given
        List<Word> wordsList = new ArrayList<>();
        wordsList.add(TestDataFixtures.someWord1());
        wordsList.add(TestDataFixtures.someWord2());
        wordsList.add(TestDataFixtures.someWord3());
        wordsList.add(TestDataFixtures.someWord4());
        when(cacheMock.readFromCache()).thenReturn(wordsList);

        //when
        List<Word> result = wordService.findWordsToLearnByCounterValue();

        //then
        assertEquals(3, result.size());
    }


    @Test
    void thatFindWordsToLearnByCounterValue_IsSortedAscByCounterValue() {
        //given
        List<Word> wordsList = new ArrayList<>();
        wordsList.add(TestDataFixtures.someWord1());
        wordsList.add(TestDataFixtures.someWord2());
        wordsList.add(TestDataFixtures.someWord3());
        wordsList.add(TestDataFixtures.someWord4());
        when(cacheMock.readFromCache()).thenReturn(wordsList);

        //when
        List<Word> result = wordService.findWordsToLearnByCounterValue();

        //then
        assertEquals(TestDataFixtures.someWord3(), result.get(0));
    }


    @Test
    void thatFindWordsToLearnByCounterValue_ContainsLimitedNumberOfWords() {
        //given
        List<Word> wordsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            wordsList.add(TestDataFixtures.someWord1());
            wordsList.add(TestDataFixtures.someWord2());
            wordsList.add(TestDataFixtures.someWord3());
            wordsList.add(TestDataFixtures.someWord4());
        }
        when(cacheMock.readFromCache()).thenReturn(wordsList);

        //when
        List<Word> result = wordService.findWordsToLearnByCounterValue();

        //then
        assertEquals(WORDS_IN_TABLE, result.size());
    }

    @Test
    void thatPreparationRandomListOfWordsToLearnWorksCorrectly() {
        //given
        int numberOfWordsInResult = 5;
        List<Word> wordsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            wordsList.add(TestDataFixtures.someWord1());
            wordsList.add(TestDataFixtures.someWord2());
            wordsList.add(TestDataFixtures.someWord3());
            wordsList.add(TestDataFixtures.someWord4());
        }
        when(cacheMock.readFromCache()).thenReturn(wordsList);

        //when
        List<Word> result = wordService.prepareRandomListOfWordsToLearn(numberOfWordsInResult);

        //then
        assertEquals(numberOfWordsInResult, result.size());
    }

    @Test
    void thatRandomDigitsAreUnique() {
        //given
        Integer numberOfDigitsInResult = 10;
        int drawRange = 50;

        //when
        Set<Integer> result = wordService.drawRandomDigits(numberOfDigitsInResult, drawRange);

        //then
        assertEquals(numberOfDigitsInResult, result.size());

        for (int number : result) {
            assertTrue(number >= 0 && number < drawRange);
        }
    }

    @Test
    void thatCounterUpdateWorksCorrectly(){
        //given
        Long wordId = 1L;
        int currentCounterValue = 1;

        //when
        wordService.worldCounterUpdate(wordId, currentCounterValue);

        //then
        verify(wordDAOMock, times(1))
                .wordCounterUpdate(wordId, currentCounterValue + 1);

        assertFalse(AppConfig.getIsCacheUpToDate());
    }

    @Test
    void thatDeleteByWordIdChangesCacheStatusCorrectly(){
        //given
        Long wordId = 1L;

        //when
        wordService.deleteByWordId(wordId);

        //then
        assertFalse(AppConfig.getIsCacheUpToDate());
    }

    @Test
    void thatWordCategoryFinderWorksCorrectly(){
        //given
        String correctText = "verb";
        String incorrectText = "vverb";

        //when
        Category result1 = wordService.wordCategoryFinder(correctText);

        //then
        assertEquals(Category.VERB, result1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> wordService.wordCategoryFinder(incorrectText));

        assertEquals("No enum with text " + incorrectText + " found", ex.getMessage());
    }
}