package pl.tomdal.myenglishwordsapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.domain.news.ArticlesItem;
import pl.tomdal.myenglishwordsapp.domain.sources.SourcesItem;
import pl.tomdal.myenglishwordsapp.entity.enums.Category;
import pl.tomdal.myenglishwordsapp.news_client.NewsClientImp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsClientImp newsClientImp;

    @GetMapping("/top-headers")
    public String getTopHeaders(Model model, Word newWord, String dataSource){
        List<ArticlesItem> articlesItemList;
        articlesItemList = newsClientImp.getTopHeadlines();

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("articlesItemList", articlesItemList);
        model.addAttribute("category", categoryValues);
        model.addAttribute("word", newWord);
        model.addAttribute("dataSource", dataSource);

        return "news";
    }

    @GetMapping("/{newsCategory}")
    public String getHeadlinesByCategory(@PathVariable String newsCategory, Model model, Word newWord, String dataSource){
        List<SourcesItem> articlesItemList;
        articlesItemList = newsClientImp.getHeadlinesByCategory(newsCategory);

        Category[] categoryValues = Category.values();
        Arrays.sort(categoryValues, Comparator.comparing(Enum::name));

        model.addAttribute("articlesItemList", articlesItemList);
        model.addAttribute("category", categoryValues);
        model.addAttribute("word", newWord);
        model.addAttribute("dataSource", dataSource);

        return "source_news";
    }
}
