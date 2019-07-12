package spark.model.dao;

import java.util.List;

import org.hibernate.search.query.dsl.QueryBuilder;

import spark.Constant;
import spark.model.bean.Suggestion;

public class SuggestionDAO extends Dao<Suggestion> {
	
	@SuppressWarnings("unchecked")
	public List<Suggestion> getByQueryOrderByScoring(String query) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Suggestion.class).get();
		
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.phrase()
				.withSlop(2)
				.onField("queryEdgeNgram").boostedTo(5)
				.andField("queryNgram")
				.sentence(query)
				.createQuery();

		return (List<Suggestion>) fullTextEntityManager.createFullTextQuery(luceneQuery, Suggestion.class)
				.setMaxResults(Constant.SUGGESTION_MAXRESULT)
				.getResultList();
	}

}
