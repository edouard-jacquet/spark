package spark.model.indexer;

import org.jboss.logging.Logger;

import spark.model.bean.Suggestion;
import spark.model.connection.Database;

public class SuggestionIndexer extends Indexer {
	
	private static final SuggestionIndexer SUGGESTIONINDEXER = new SuggestionIndexer();
	
	
	private SuggestionIndexer() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public static SuggestionIndexer getInstance() {
		return SUGGESTIONINDEXER;
	}
	
	public boolean rebuild() {
		try {
			Database.getInstance().getFullTextConnection()
					.createIndexer(Suggestion.class)
					.startAndWait();
			
			logger.info("Suggestion index have been rebuilded.");
			return true;
		} catch (InterruptedException exception) {
			logger.error("Suggestion index have not been rebuilded.");
			return false;
		}
	}
	
}
