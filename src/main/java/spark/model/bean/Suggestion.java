package spark.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDefs({
	@AnalyzerDef(name = "suggestionStandard",
			tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
			filters = {
					@TokenFilterDef(factory = LowerCaseFilterFactory.class),
					@TokenFilterDef(factory = WordDelimiterFilterFactory.class)
			}
	),
	@AnalyzerDef(name = "suggestionNgram",
			tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
			filters = {
					@TokenFilterDef(factory = LowerCaseFilterFactory.class),
					@TokenFilterDef(factory = WordDelimiterFilterFactory.class),
					@TokenFilterDef(factory = NGramFilterFactory.class, params = {
							@Parameter(name = "minGramSize", value = "3"),
							@Parameter(name = "maxGramSize", value = "5")
					})
			}
	),
	@AnalyzerDef(name = "suggestionEdgeNgram",
			tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),
			filters = {
					@TokenFilterDef(factory = LowerCaseFilterFactory.class),
					@TokenFilterDef(factory = StopFilterFactory.class),
					@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
							@Parameter(name = "minGramSize", value = "3"),
							@Parameter(name = "maxGramSize", value = "50")
					})
			}
	)
})

@Entity
@Table(name = "SPARK_SUGGESTION")
@Indexed(index = "Suggestion")
public class Suggestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUGGESTION_ID")
	@DocumentId
	private long id;
	
	@Column(name = "SUGGESTION_LABEL", length = 200, nullable = false)
	@Field(index = Index.NO, store = Store.YES, analyze = Analyze.NO)
	private String label;
	
	@Column(name = "SUGGESTION_QUERY", length = 200, nullable = false)
	@Fields({
		@Field(
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "suggestionStandard")
		),
		@Field(name = "queryNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "suggestionNgram")
		),
		@Field(name = "queryEdgeNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "suggestionEdgeNgram")
		)
	})
	private String query;
	
	@Column(name = "SUGGESTION_COUNTER")
	@Field(index = Index.NO, store = Store.YES, analyze = Analyze.NO)
	private long counter;

	
	public long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}

}
