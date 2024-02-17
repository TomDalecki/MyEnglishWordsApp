package pl.tomdal.myenglishwordsapp.domain.news;

public class ArticlesItem{
	private String publishedAt;
	private String author;
	private Object urlToImage;
	private Object description;
	private Source source;
	private String title;
	private String url;
	private Object content;

	public String getPublishedAt(){
		return publishedAt;
	}

	public String getAuthor(){
		return author;
	}

	public Object getUrlToImage(){
		return urlToImage;
	}

	public Object getDescription(){
		return description;
	}

	public Source getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}

	public Object getContent(){
		return content;
	}
}
