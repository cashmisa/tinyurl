package dev.xiaowen.demo.data;

import javax.validation.constraints.NotEmpty;

public class UrlForm {

	@NotEmpty
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
