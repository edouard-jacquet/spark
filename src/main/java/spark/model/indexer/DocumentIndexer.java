package spark.model.indexer;

import org.jboss.logging.Logger;

import spark.model.bean.Document;
import spark.model.connection.Database;

public class DocumentIndexer extends Indexer {
	
	private static final DocumentIndexer DOCUMENTINDEXER = new DocumentIndexer();
	
	
	private DocumentIndexer() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public static DocumentIndexer getInstance() {
		return DOCUMENTINDEXER;
	}
	
	public boolean rebuild() {
		try {
			Database.getInstance().getFullTextConnection()
					.createIndexer(Document.class)
					.startAndWait();
			
			logger.info("Document index have been rebuilded.");
			return true;
		} catch (InterruptedException exception) {
			logger.error("Document index have not been rebuilded.");
			return false;
		}
	}
	
}
