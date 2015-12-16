package spark.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPARK_CONFIGURATION")
public class Configuration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONFIGURATION_ID")
	private long id;
	
	@Column(name = "CONFIGURATION_KEY", length = 200, nullable = false, unique = true)
	private String key;
	
	@Column(name = "CONFIGURATION_VALUE", length = 200, nullable = false)
	private String value;

	
	public long getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
