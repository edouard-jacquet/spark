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
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import spark.model.bridge.PdfBridge;

@AnalyzerDefs({
	@AnalyzerDef(name = "documentStandard",
			tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
			filters = {
					@TokenFilterDef(factory = LowerCaseFilterFactory.class),
					@TokenFilterDef(factory = WordDelimiterFilterFactory.class)
			}
	),
	@AnalyzerDef(name = "documentNgram",
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
	@AnalyzerDef(name = "documentEdgeNgram",
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
@Table(name = "SPARK_DOCUMENT")
@Indexed(index = "Document")
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DocumentId
	private long id;
	
	@Column(name = "DOCUMENT_TITLE", length = 200, nullable = false)
	@Fields({
		@Field(
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentStandard")
		),
		@Field(name = "titleNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentNgram")
		),
		@Field(name = "titleEdgeNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentEdgeNgram")
		)
	})
	private String title;
	
	@Column(name = "DOCUMENT_ATTACHMENT", length = 400, nullable = false)
	@Fields({
		@Field(
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentStandard")
		),
		@Field(name = "attachmentNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentNgram")
		),
		@Field(name = "attachmentEdgeNgram",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentEdgeNgram")
		)
	})
	@FieldBridge(impl = PdfBridge.class)
	private String attachment;
	
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAttachment() {
		return attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
}
