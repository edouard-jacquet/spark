package spark.model.factory;

import spark.model.bean.Document;

public class DocumentIndexer {
	
	private static final DocumentIndexer DOCUMENTINDEXER = new DocumentIndexer();
	
	public static DocumentIndexer getInstance() {
		return DOCUMENTINDEXER;
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
