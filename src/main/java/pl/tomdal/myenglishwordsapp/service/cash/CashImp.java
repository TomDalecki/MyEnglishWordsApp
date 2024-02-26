package pl.tomdal.myenglishwordsapp.service.cash;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.tomdal.myenglishwordsapp.domain.Word;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CashImp implements Cash{
    private final List<Word> wordsInCash = new ArrayList<>();

    @Override
    public void writeToCash(List<Word> wordList) {
        log.info("Writing to cash");
        wordsInCash.addAll(wordList);
    }

    @Override
    public List<Word> readFromCash() {
        log.info("Reading from cash");
        return wordsInCash;
    }
}
