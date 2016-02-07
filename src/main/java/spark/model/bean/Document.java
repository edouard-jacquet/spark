package spark.model.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
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
	)
})

@Entity
@Table(name = "SPARK_DOCUMENT")
@Indexed(index = "Document")
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOCUMENT_ID")
	@DocumentId
	private long id;
	
	@Column(name = "DOCUMENT_TITLE", length = 200, nullable = false)
	@Fields({
		@Field(name = "titleStandard",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentStandard")
		)
	})
	private String title;
	
	@Column(name = "DOCUMENT_SUMMARY", length = 1000)
	@Field(index = Index.NO, store = Store.YES, analyze = Analyze.NO)
	private String summary;

	@Column(name = "DOCUMENT_ATTACHMENT", length = 400, nullable = false)
	@Fields({
		@Field(name = "attachmentStandard",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				analyzer = @Analyzer(definition = "documentStandard")
		),
		@Field(name = "attachmentVectorStandard",
				index = Index.YES, store = Store.NO, analyze = Analyze.YES,
				termVector = TermVector.YES,
				analyzer = @Analyzer(definition = "documentStandard")
		)
	})
	@FieldBridge(impl = PdfBridge.class)
	private String attachment;
	
	@Column(name = "DOCUMENT_PUBLICATIONDATE", nullable = true)
	private Date publicationDate;
	
	/*
	 * @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.YEAR)

	 */
	
	@Column(name = "DOCUMENT_UPDATEDATE", nullable = true)
	private Date updateDate;
	
	@Column(name = "DOCUMENT_REF", nullable = true)
	private String DocumentRef;
	
	@ManyToMany
	@JoinTable(name = "SPARK_DOCUMENTAUTHOR",
		joinColumns = @JoinColumn(name = "DOCUMENTAUTHOR_DOCUMENTID", referencedColumnName = "DOCUMENT_ID"),
		inverseJoinColumns = @JoinColumn(name = "DOCUMENTAUTHOR_AUTHORID", referencedColumnName = "AUTHOR_ID"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"DOCUMENTAUTHOR_DOCUMENTID", "DOCUMENTAUTHOR_AUTHORID"})
	)
	@IndexedEmbedded
	private Set<Author> authors = new HashSet<Author>();
	
	@ManyToOne(targetEntity = Source.class)
	@JoinColumn(name = "DOCUMENT_SOURCEID")
	private Source source;
	

	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDocumentRef(String _docid){
		this.DocumentRef = _docid;
	}
	
	public String getDocumentRef(){
		return this.DocumentRef;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getAttachment() {
		return attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}
	
	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	
}
