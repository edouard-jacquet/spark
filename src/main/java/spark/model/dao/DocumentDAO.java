package spark.model.dao;

import java.util.List;

import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;

import spark.Constant;
import spark.model.bean.Document;

public class DocumentDAO extends Dao<Document> {
	
	private int resultSize = 0;
	

	@SuppressWarnings("unchecked")
	public List<Document> getByQueryAndPageOrderByScoring(String query, int page) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Document.class).get();
		
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.keyword()
				.onField("titleStandard").boostedTo(5)
				.andField("attachmentStandard")
				.ignoreFieldBridge()
				.matching(query)
				.createQuery();
		
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Document.class);
		resultSize = fullTextQuery.getResultSize();
		return (List<Document>) fullTextQuery
				.setMaxResults(Constant.DOCUMENT_MAXRESULT)
				.setFirstResult((page - 1) * Constant.DOCUMENT_MAXRESULT)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Document> getSimilarByDocumentOrderByScoring(Document document) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Document.class).get();
		
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.moreLikeThis()
				.excludeEntityUsedForComparison()
				.comparingField("attachmentVectorStandard")
				.toEntity(document)
				.createQuery();

		return (List<Document>) fullTextEntityManager.createFullTextQuery(luceneQuery, Document.class)
				.setMaxResults(Constant.DOCUMENT_SIMILAR_MAXRESULT)
				.getResultList();
	}
	
	public int getResultSize() {
		return resultSize;
	}
	
	public List<String> document_non_presents(String chaine_id){
		String[] tab = chaine_id.split(",");
		String req = "SELECT nom_colonne FROM (";
			for(int i=0; i< tab.length; i++){
			
				if(i==0)
					req += "SELECT '"+tab[i]+"' AS nom_colonne UNION ALL ";
				else if(i == tab.length-1)
					req += "SELECT '"+tab[i]+"' ";
				else
					req += "SELECT '"+tab[i]+"' UNION ALL ";
			}
			
		req += ") vals WHERE nom_colonne NOT IN (SELECT DOCUMENT_ATTACHMENT FROM spark_document)";
		
		return (List<String>) entityManager.createNativeQuery(req).getResultList();
	}
	
}
