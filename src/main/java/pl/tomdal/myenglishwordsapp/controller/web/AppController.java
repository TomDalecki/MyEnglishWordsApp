package pl.tomdal.myenglishwordsapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.entity.enums.WordStatus;
import pl.tomdal.myenglishwordsapp.service.WordService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final WordService wordService;
    private static final String PREFIX = "redirect:/";
    private static final String PREFIX_HOME = "redirect:/home";


    @GetMapping("/home")
    public String homePage(Model model, Word newWord, String dataSource){
        Integer numberOfWordsInResult = 10;
        List<Word> allWordsToLearn = wordService.findAllToLearn();
        List<Word> wordsToLearn = wordService.findWordsToLearnByCounterValue(allWordsToLearn);
        List<Word> randomWordsToLearn = wordService
                .prepareRandomListOfWordsToLearn(allWordsToLearn, numberOfWordsInResult);

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("wordsToLearn", wordsToLearn);
        model.addAttribute("randomWordsToLearn", randomWordsToLearn);
        model.addAttribute("word", newWord);
        model.addAttribute("dataSource", dataSource);
        model.addAttribute("category", categoryValues);

        return "home";
    }

    @GetMapping(value = "/home/{text}")
    public String nounsSite(@PathVariable String text, Model model, Word newWord){

        Category wordsCategory = wordService.wordCategoryFinder(text);
        List<Word> allWordsFromCategory = wordService.findAllByCategory(wordsCategory);
        List<Word> wordsToLearn = wordService.findWordsToLearnByCounterValue(allWordsFromCategory);

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("wordsToLearn", wordsToLearn);
        model.addAttribute("allWords", allWordsFromCategory);
        model.addAttribute("word", newWord);
        model.addAttribute("category", categoryValues);
        model.addAttribute("wordsCategory", wordsCategory);
        return "words_exercises";
    }

    @PostMapping(value = "/home/saveWord")
    public String saveNewWord(@ModelAttribute Word newWord, String dataSource) {
        wordService.saveWord(newWord);
        return PREFIX + dataSource;
    }

    @PostMapping(value = "/home/statusUpdate")
    public String statusUpdate(Long wordId, String dataSource) {
        wordService.statusUpdate(wordId, WordStatus.LEARNED);
        return PREFIX_HOME + dataSource;
    }

    @PostMapping(value = "/home/counterUpdate")
    public String worldCounterUpdate(Long wordId, Integer currentCounterValue, String dataSource) {
        wordService.worldCounterUpdate(wordId, currentCounterValue);
        return PREFIX_HOME + dataSource;
    }

    @PostMapping(value = "/home/delete")
    public String deleteByWordId(Long wordId, String dataSource) {
        wordService.deleteByWordId(wordId);
        return PREFIX_HOME + dataSource;
    }
}
