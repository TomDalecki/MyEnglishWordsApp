package pl.tomdal.myenglishwordsapp.service.cash;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.tomdal.myenglishwordsapp.domain.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CacheImp implements Cache {
    private List<Word> wordsInCache = new ArrayList<>();

    @Override
    public void writeToCache(List<Word> wordList) {
        log.info("Writing to cache");
        wordsInCache = Collections.unmodifiableList(wordList);
    }

    @Override
    public List<Word> readFromCache() {
        log.info("Reading from cache");
        return wordsInCache;
    }
}
