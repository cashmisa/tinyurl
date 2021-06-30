package dev.xiaowen.demo.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.xiaowen.demo.data.Url;
import dev.xiaowen.demo.exception.InvalidLinkException;
import dev.xiaowen.demo.repo.UrlConverterRepo;

@Service
public class UrlConverterService {

	public static final String BASE_URL_STRING = "http://localhost:8899/";
	public static final int URL_CODE_LENGTH = 6;
	private static final int RANDOM_INDEX_FIRST = 0;
	private static final int RANDOM_INDEX_SECOND = 3; // initial value is 100, code min length is 2
	private static final String CODE_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int base = CODE_STRING.length();
	private SecureRandom rnd = new SecureRandom();
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
		String code = encodeLongIdToCode(saved.getId());
		return BASE_URL_STRING + code;
	}

	public String getOriginalUrlFromShortened(String shortUrl) {
		System.out.println("shortUrl is: " + shortUrl);
		if (!isValidUrl(shortUrl)) {
			throw new InvalidLinkException("Not a valid url");
		}
		System.out.println("is valid");
		if (!shortUrl.startsWith(BASE_URL_STRING)) {
			System.out.println("Not start with base");
			throw new InvalidLinkException("URL provided does not belong to us...");
		}
		System.out.println("starts with base");
		String code = shortUrl.substring(BASE_URL_STRING.length());
		return getOriginalUrlFromCode(code);
	}

	public Boolean isValidUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (MalformedURLException | URISyntaxException e) {
			return false;
		}
	}

	public String getOriginalUrlFromCode(String code) {
		long id = decodeCodeToLongId(code);
		Url found = urlRepo.findById(id).orElse(null);
		if (found == null) {
			throw new InvalidLinkException("Unable to find site's URL to redirect to.");
		}
		return found.getLongUrl();
	}

	private String encodeLongIdToCode(long num) {
		StringBuilder sBuilder = new StringBuilder();
		while (num > 0) {
			int index = (int) (num % base);
			sBuilder.insert(0, CODE_STRING.charAt(index));
			num /= base;
		}
		sBuilder.insert(RANDOM_INDEX_FIRST, CODE_STRING.charAt(rnd.nextInt(base)));
		sBuilder.insert(RANDOM_INDEX_SECOND, CODE_STRING.charAt(rnd.nextInt(base)));
		return sBuilder.toString();
	}

	private long decodeCodeToLongId(String code) {
		code = code.substring(0, RANDOM_INDEX_FIRST) + code.substring(RANDOM_INDEX_FIRST + 1, RANDOM_INDEX_SECOND)
				+ code.substring(RANDOM_INDEX_SECOND + 1);
		long decoded = 0;
		for (int i = 0; i < code.length(); i++) {
			decoded = decoded * base + CODE_STRING.indexOf(code.charAt(i));
		}
		return decoded;
	}

}
