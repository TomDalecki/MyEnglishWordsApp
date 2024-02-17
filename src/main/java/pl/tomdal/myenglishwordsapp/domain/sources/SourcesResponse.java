package pl.tomdal.myenglishwordsapp.domain.sources;

import java.util.List;

public class SourcesResponse {
	private List<SourcesItem> sources;
	private String status;

	public List<SourcesItem> getSources(){
		return sources;
	}

	public String getStatus(){
		return status;
	}
}