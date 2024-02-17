package pl.tomdal.myenglishwordsapp.dto;

import pl.tomdal.myenglishwordsapp.domain.news.ArticlesItem;
import pl.tomdal.myenglishwordsapp.domain.sources.SourcesItem;

import java.util.List;

public interface NewsDto {
    List<ArticlesItem> getTopHeadlines();

    List<SourcesItem> getHeadlinesByCategory(String newsCategory);
}
