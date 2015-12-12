package spark.model.indexer;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;

public interface IIndexer {

	public boolean rebuild(MassIndexerProgressMonitor massIndexerProgressMonitor);
	
}
