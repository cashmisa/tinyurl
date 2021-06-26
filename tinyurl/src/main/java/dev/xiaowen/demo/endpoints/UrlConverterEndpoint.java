package dev.xiaowen.demo.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.xiaowen.demo.service.UrlConverterService;

@RestController
@RequestMapping(path="/api")
public class UrlConverterEndpoint {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private UrlConverterService urlSvc;
	
	@Autowired
	public UrlConverterEndpoint(UrlConverterService urlSvc) {
		super();
		this.urlSvc = urlSvc;
	}

	@GetMapping("hello")
	public String hello() {
		return "hello world";
	}
	
	@PostMapping("")
	public String getShortenedUrl(@RequestBody String url) {
		logger.info("request to shorten {}", url);
		return urlSvc.convertToShortUrl(url);
	}
	
	@GetMapping("")
	public String getOriginalUrlFromShortened(@RequestBody String url) {
		return urlSvc.getOriginalUrlFromShortened(url);
	}

}
