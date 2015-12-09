package spark.model.factory;

import spark.model.bean.Suggestion;

public class SuggestionIndexer {
	
	private static final SuggestionIndexer SUGGESTIONINDEXER = new SuggestionIndexer();
	
	public static SuggestionIndexer getInstance() {
		return SUGGESTIONINDEXER;
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
