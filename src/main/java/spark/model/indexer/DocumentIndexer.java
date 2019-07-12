package spark.model.indexer;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.hibernate.search.batchindexing.impl.SimpleIndexingProgressMonitor;
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
	
	public boolean rebuild(MassIndexerProgressMonitor massIndexerProgressMonitor) {
		try {
			if(massIndexerProgressMonitor == null) {
				massIndexerProgressMonitor = new SimpleIndexingProgressMonitor();
			}
			
			Database.getInstance().getFullTextConnection()
					.createIndexer(Document.class)
					.progressMonitor(massIndexerProgressMonitor)
					.startAndWait();
			
			logger.info("Document index have been rebuilded.");
			return true;
		} catch (InterruptedException exception) {
			logger.error("Document index have not been rebuilded.");
			return false;
		}
	}
	
}
