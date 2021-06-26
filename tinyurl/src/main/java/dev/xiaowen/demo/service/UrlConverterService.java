package dev.xiaowen.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.xiaowen.demo.data.Url;
import dev.xiaowen.demo.repo.UrlConverterRepo;

@Service
public class UrlConverterService {

	private UrlConverterRepo urlRepo;

	@Autowired
	public UrlConverterService(UrlConverterRepo urlRepo) {
		this.urlRepo = urlRepo;
	}

	public String convertToShortUrl(String url) {
		// TODO: add url check, exception if invalid url
		return "https://localhost:8080/**qwe123**";
	}

	public String getOriginalUrlFromShortened(String url) {
		// TODO: add url check, exception if not shortened
		Url found = urlRepo.findByShortUrl(url);
		if (found == null) {
			throw new RuntimeException("Link doesn't seem to be valid...");
		}
		return found.getLongUrl();
	}

}
