package spark.model.factory;

import spark.model.bean.Suggestion;

public class SuggestionIndexing {
	
	private static final SuggestionIndexing SUGGESTIONINDEXING = new SuggestionIndexing();
	
	public static SuggestionIndexing getInstance() {
		return SUGGESTIONINDEXING;
	}
	
	public boolean rebuildIndex() {
		try {
			Connection.getInstance().getFullTextConnection().createIndexer(Suggestion.class).startAndWait();
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
}
