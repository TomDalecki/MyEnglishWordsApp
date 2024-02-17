package pl.tomdal.myenglishwordsapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/home")
    public String homePage(Model model, Word newWord, String dataSource){
        List<Word> allWordsToLearn = wordService.findAllToLearn();
        List<Word> wordsToLearn = wordService.findWordsToLearn(allWordsToLearn);

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("wordsToLearn", wordsToLearn);
        model.addAttribute("word", newWord);
        model.addAttribute("dataSource", dataSource);
        model.addAttribute("category", categoryValues);

        return "home";
    }

    @GetMapping(value = "/home/{text}")
    public String nounsSite(@PathVariable String text, Model model, Word newWord){

        Category wordsCategory = wordService.wordCategoryFinder(text);
        List<Word> allWordsFromCategory = wordService.findAllByCategory(wordsCategory);
        List<Word> wordsToLearn = wordService.findWordsToLearn(allWordsFromCategory);

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("wordsToLearn", wordsToLearn);
        model.addAttribute("allWords", allWordsFromCategory);
        model.addAttribute("word", newWord);
        model.addAttribute("category", categoryValues);
        model.addAttribute("wordsCategory", wordsCategory);
        return "wordsExercises";
    }

    @PostMapping(value = "/home/saveWord")
    public String saveNewWord(@ModelAttribute Word newWord, String dataSource) {
        wordService.saveWord(newWord);
        return "redirect:/" + dataSource;
    }

    @PostMapping(value = "/home/statusUpdate")
    public String statusUpdate(Long wordId, String dataSource) {
        wordService.statusUpdate(wordId, WordStatus.LEARNED);
        return "redirect:/home" + dataSource;
    }

    @PostMapping(value = "/home/counterUpdate")
    public String worldCounterUpdate(Long wordId, Integer currentCounterValue, String dataSource) {
        wordService.worldCounterUpdate(wordId, currentCounterValue);
        return "redirect:/home" + dataSource;
    }

    @PostMapping(value = "/home/delete")
    public String deleteByWordId(Long wordId, String dataSource) {
        wordService.deleteByWordId(wordId);
        return "redirect:/home" + dataSource;
    }
}
