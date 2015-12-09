package spark.model.manager;

import spark.model.bean.Source;
import spark.model.dao.SourceDAO;

public class ManageSource extends Manager {
	
	SourceDAO sourceDAO = new SourceDAO();
	String[][] sources = {
		{"personal", ""}
	};
	
	public void deploy() {
		if(sourceDAO.getAll().size() == 0) {
			for(String[] entry : sources) {
				Source source = new Source();
				source.setName(entry[0]);
				source.setLocation(entry[1]);
				sourceDAO.create(source);
			}
		}
	}

}
