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

@Entity
@Table(name = "SPARK_SESSION")
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSION_ID")
	private long id;
	
	@Column(name = "SESSION_KEY", length = 200, nullable = false)
	private String key;
	
	@ManyToOne
	@JoinColumn(name = "SESSION_USERID")
	private User user;
	
	@Column(name = "SESSION_DATE", nullable = false)
	private Date date;
	
	@ManyToMany
	@JoinTable(name = "SPARK_SESSIONSEARCH",
		joinColumns = @JoinColumn(name = "SESSIONSEARCH_SESSIONID", referencedColumnName = "SESSION_ID"),
		inverseJoinColumns = @JoinColumn(name = "SESSIONSEARCH_QUERYID", referencedColumnName = "QUERY_ID"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"SESSIONSEARCH_SESSIONID", "SESSIONSEARCH_QUERYID"})
	)
	private Set<Query> querys = new HashSet<Query>();
	
	@ManyToMany
	@JoinTable(name = "SPARK_SESSIONCONSULT",
		joinColumns = @JoinColumn(name = "SESSIONCONSULT_SESSIONID", referencedColumnName = "SESSION_ID"),
		inverseJoinColumns = @JoinColumn(name = "SESSIONCONSULT_DOCUMENTID", referencedColumnName = "DOCUMENT_ID"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"SESSIONCONSULT_SESSIONID", "SESSIONCONSULT_DOCUMENTID"})
	)
	private Set<Document> documents = new HashSet<Document>();
	

	public long getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Set<Query> getQuerys() {
		return querys;
	}

	public void setQuerys(Set<Query> querys) {
		this.querys = querys;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}
