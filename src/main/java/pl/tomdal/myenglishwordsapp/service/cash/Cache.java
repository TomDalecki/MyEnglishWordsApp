package pl.tomdal.myenglishwordsapp.service.cash;

import pl.tomdal.myenglishwordsapp.domain.Word;

import java.util.List;

public interface Cache {
    void writeToCache(List<Word> wordList);

    List<Word> readFromCache();
}
