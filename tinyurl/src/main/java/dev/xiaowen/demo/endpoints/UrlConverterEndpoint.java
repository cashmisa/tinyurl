package dev.xiaowen.demo.endpoints;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.xiaowen.demo.data.UrlForm;
import dev.xiaowen.demo.service.UrlConverterService;

@RestController
@RequestMapping(path = "")
public class UrlConverterEndpoint {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private UrlConverterService urlSvc;

	@Autowired
	public UrlConverterEndpoint(UrlConverterService urlSvc) {
		super();
		this.urlSvc = urlSvc;
	}

	@PostMapping("/shorten")
	public String getShortenedUrl(@Valid @RequestBody UrlForm form) {
		System.out.println("form" + form.getUrl());
		return urlSvc.convertToShortUrl(form.getUrl());
	}

	@PostMapping("/turnback")
	public String getOriginal(@Valid @RequestBody UrlForm form) {
		System.out.println("form" + form.getUrl());
		return urlSvc.getOriginalUrlFromShortened(form.getUrl());
	}

	@GetMapping("/{code}")
	public ResponseEntity<Object> redirectToOriginal(@PathVariable String code) {
		System.out.println("pathVariable " + code);
		String url = urlSvc.getOriginalUrlFromCode(code);
		System.out.println(url);
		System.out.println("created url is" + URI.create(url));
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
	}

}
