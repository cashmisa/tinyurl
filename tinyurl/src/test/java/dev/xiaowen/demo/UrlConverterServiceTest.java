package dev.xiaowen.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.xiaowen.demo.exception.InvalidUrlException;
import dev.xiaowen.demo.service.UrlConverterService;

@SpringBootTest
public class UrlConverterServiceTest {
	@Autowired
	UrlConverterService service;
	
	public String longUrl = "https://blog.gds-gov.tech/terragrunt-in-retro-i-would-have-done-these-few-things-e5aaac451942?gi=e718a19fe0e4";
	public String googleUrl = "https://www.google.com/search?q=%E4%BD%A0%E5%A5%BD&sxsrf=ALeKk01YxtBXPd-xU8rlnqnmjHapzkqxdg%3A1625064887790&ei=t4XcYKjML9fB3LUP_46vgA0&oq=%E4%BD%A0%E5%A5%BD&gs_lcp=Cgdnd3Mtd2l6EAMyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECcyBwgjEOoCECc6CQgAELADEAcQHkoECEEYAVChtQZYldwGYNDdBmgCcAB4AIABMYgBMZIBATGYARugAQGqAQdnd3Mtd2l6sAEKyAEKwAEB&sclient=gws-wiz&ved=0ahUKEwjog_66zr_xAhXXILcAHX_HC9AQ4dUDCA4&uact=5";
	public String rareDomainUrl = "https://dotmobi.mobi/";

	@Test
    void shortenedAndTurnedBack() {
		String shortUrl = service.convertToShortUrl(longUrl);
        assertThat(service.getOriginalUrlFromShortened(shortUrl)).isEqualTo(longUrl);
        shortUrl = service.convertToShortUrl(googleUrl);
        assertThat(service.getOriginalUrlFromShortened(shortUrl)).isEqualTo(googleUrl);
        shortUrl = service.convertToShortUrl(rareDomainUrl);
        assertThat(service.getOriginalUrlFromShortened(shortUrl)).isEqualTo(rareDomainUrl);
    }
	
	@Test
	void exceptionTesting() {
	    InvalidUrlException thrown = assertThrows(
	    		InvalidUrlException.class,
	           () -> service.convertToShortUrl("wwwinvalidurlcom")
	    );
	    assertEquals("Not a valid url", thrown.getMessage());
	    
	    thrown = assertThrows(
	    		InvalidUrlException.class,
	           () -> service.convertToShortUrl("https//blog.gds-gov.tech")
	    );
	    assertEquals("Not a valid url", thrown.getMessage());
	}

}