package spark.model.factory;

import spark.model.bean.Document;

public class DocumentIndexing {
	
	private static final DocumentIndexing DOCUMENTINDEXING = new DocumentIndexing();
	
	public static DocumentIndexing getInstance() {
		return DOCUMENTINDEXING;
	}
	
	public boolean rebuildIndex() {
		try {
			Connection.getInstance().getFullTextConnection().createIndexer(Document.class).startAndWait();
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
}
