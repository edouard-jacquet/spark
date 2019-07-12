package spark.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPARK_SOURCE")
public class Source {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SOURCE_ID")
	private long id;
	
	@Column(name = "SOURCE_NAME", length = 100, nullable = false)
	private String name;
	
	@Column(name = "SOURCE_LOCATION", length = 400)
	private String location;
	
	@Column(name = "SOURCE_ACTIVE", length = 1)
	private int active;
	
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int _active) {
		this.active = _active;
	}

}
