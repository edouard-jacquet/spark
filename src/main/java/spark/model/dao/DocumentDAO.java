package spark.model.dao;

import java.util.List;

import org.hibernate.search.query.dsl.QueryBuilder;

import spark.Constant;
import spark.model.bean.Document;

public class DocumentDAO extends Dao<Document> {

	@SuppressWarnings("unchecked")
	public List<Document> getByQueryAndPageOrderByScoring(String query, int page) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Document.class).get();
		
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.phrase()
				.withSlop(10)
				.onField("attachmentNgram")
				.andField("attachmentEdgeNgram").boostedTo(5)
				.sentence(query)
				.createQuery();

		return (List<Document>) fullTextEntityManager.createFullTextQuery(luceneQuery, Document.class)
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
				.comparingField("attachment")
				.toEntity(document)
				.createQuery();

		return (List<Document>) fullTextEntityManager.createFullTextQuery(luceneQuery, Document.class)
				.setMaxResults(Constant.DOCUMENT_SIMILAR_MAXRESULT)
				.getResultList();
	}
	
}
