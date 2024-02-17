package pl.tomdal.myenglishwordsapp.news_client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.tomdal.myenglishwordsapp.domain.news.Response;
import pl.tomdal.myenglishwordsapp.domain.sources.SourcesItem;
import pl.tomdal.myenglishwordsapp.domain.sources.SourcesResponse;
import pl.tomdal.myenglishwordsapp.dto.NewsDto;
import pl.tomdal.myenglishwordsapp.domain.news.ArticlesItem;

import java.util.ArrayList;
import java.util.List;

import static pl.tomdal.myenglishwordsapp.configuration.AppConfig.API_KEY;

@Component
@AllArgsConstructor
public class NewsClientImp implements NewsDto {
    private final WebClient webClient;

    @Override
    public List<ArticlesItem> getTopHeadlines() {

        List<ArticlesItem> articlesItemList = new ArrayList<>();

        try {

            Response response = webClient
                    .get()
                    .uri("top-headlines?country=us&apiKey=" + API_KEY)
                    .retrieve()
                    .bodyToMono(Response.class)
                    .block();

            if (response != null && response.getArticles() != null) {
                articlesItemList = response.getArticles().stream()
                        .filter(articlesItem -> !articlesItem.getTitle().equals("[Removed]"))
                        .toList();
            }

            return articlesItemList;
        } catch (Exception e) {
            return articlesItemList;
        }
    }

    @Override
    public List<SourcesItem> getHeadlinesByCategory(String newsCategory) {
        List<SourcesItem> sourcesItemList = new ArrayList<>();

        try {

            SourcesResponse response = webClient
                    .get()
                    .uri("top-headlines/sources?country=us&category=" + newsCategory + "&apiKey=" + API_KEY)
                    .retrieve()
                    .bodyToMono(SourcesResponse.class)
                    .block();


            if (response != null && response.getSources() != null) {
                sourcesItemList = response.getSources().stream()
                        .filter(sourcesItem -> !sourcesItem.getDescription().equals("[Removed]"))
                        .toList();
            }

            return sourcesItemList;
        } catch (Exception e) {
            return sourcesItemList;
        }
    }
}
