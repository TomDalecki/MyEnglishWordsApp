package pl.tomdal.myenglishwordsapp.service.cash;

import pl.tomdal.myenglishwordsapp.domain.Word;

import java.util.List;

public interface Cash {
    void writeToCash(List<Word> wordList);

    List<Word> readFromCash();
}
