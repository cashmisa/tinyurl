package dev.xiaowen.demo.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.xiaowen.demo.data.Url;
import dev.xiaowen.demo.exception.InvalidLinkException;
import dev.xiaowen.demo.repo.UrlConverterRepo;

@Service
public class UrlConverterService {

	public static final String BASE_URL_STRING = "http://localhost:8899/";
	public static final int URL_CODE_LENGTH = 6;
	private static final String CODE_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int base = CODE_STRING.length();
	private UrlConverterRepo urlRepo;

	@Autowired
	public UrlConverterService(UrlConverterRepo urlRepo) {
		this.urlRepo = urlRepo;
	}

	public String convertToShortUrl(String longUrl) {
		if (!isValidUrl(longUrl)) {
			throw new InvalidLinkException("Not a valid url");
		}
		Url saved = urlRepo.save(new Url(longUrl));
		return encodeLongIdToShortUrl(saved.getId());
	}

	public String getOriginalUrlFromShortened(String shortUrl) {
		if (!isValidUrl(shortUrl)) {
			throw new InvalidLinkException("Not a valid url");
		}
		if (!shortUrl.startsWith(BASE_URL_STRING)) {
			throw new InvalidLinkException("URL provided does not belong to us...");
		}
		long id = decodeShortUrlToLongId(shortUrl);
		Url found = urlRepo.findById(id).orElse(null);
		if (found == null) {
			throw new InvalidLinkException("Unable to find site's URL to redirect to.");
		}
		return found.getLongUrl();
	}

	public Boolean isValidUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (MalformedURLException | URISyntaxException e) {
			return false;
		}
	}

	private String encodeLongIdToShortUrl(long num) {
		StringBuilder sBuilder = new StringBuilder();
		while (num > 0) {
			int index = (int) (num % base);
			sBuilder.insert(0, CODE_STRING.charAt(index));
			num /= base;
		}
		return BASE_URL_STRING + sBuilder.toString();
	}

	private long decodeShortUrlToLongId(String shortUrl) {
		String code = shortUrl.substring(BASE_URL_STRING.length());
		long decoded = 0;
		for (int i = 0; i < code.length(); i++) {
			decoded = decoded * base + CODE_STRING.indexOf(code.charAt(i));
		}
		return decoded;
	}

}
