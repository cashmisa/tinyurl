package dev.xiaowen.demo.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
	@SequenceGenerator(name = "url_seq", sequenceName = "url_seq", initialValue = 100)
	private long id;

	@Column(name = "longUrl", length = 2083)
	private String longUrl;

	public Url() {
	}

	public Url(String longUrl) {
		this.longUrl = longUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

}
