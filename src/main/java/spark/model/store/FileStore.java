package spark.model.store;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.store.Directory;
import org.hibernate.search.indexes.spi.DirectoryBasedIndexManager;
import org.hibernate.search.spi.BuildContext;
import org.hibernate.search.spi.IndexingMode;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.impl.DirectoryProviderHelper;
import org.hibernate.search.store.spi.DirectoryHelper;

import spark.Constant;

public class FileStore implements DirectoryProvider<Directory> {
	
	private Directory directory;
	

	@SuppressWarnings("deprecation")
	@Override
	public void initialize(String directoryProviderName, Properties properties, BuildContext buildContext) {
		boolean manual = IndexingMode.MANUAL == buildContext.getIndexingMode();
		String path = Constant.STORAGE_INDEX_FOLDER + directoryProviderName.toLowerCase();
		
		File index = DirectoryHelper.getVerifiedIndexDir(path, properties, manual);
		
		try {
			directory = DirectoryProviderHelper.createFSIndex(index, properties, buildContext.getServiceManager());
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void start(DirectoryBasedIndexManager indexManager) {
		
	}

	@Override
	public void stop() {
		try {
			directory.close();
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
	}
	
	@Override
	public Directory getDirectory() {
		return directory;
	}

}
