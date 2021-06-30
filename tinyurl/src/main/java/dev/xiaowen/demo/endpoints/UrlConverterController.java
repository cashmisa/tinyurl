package dev.xiaowen.demo.endpoints;

import java.net.URI;

import javax.validation.Valid;

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
public class UrlConverterController {

	private UrlConverterService urlSvc;

	@Autowired
	public UrlConverterController(UrlConverterService urlSvc) {
		super();
		this.urlSvc = urlSvc;
	}

	@GetMapping("/hello")
	public String sayHello() {
		return "hello back";
	}
	
	@PostMapping("/shorten")
	public String getShortenedUrl(@Valid @RequestBody UrlForm form) {
		return urlSvc.convertToShortUrl(form.getUrl());
	}

	@PostMapping("/turnback")
	public String getOriginal(@Valid @RequestBody UrlForm form) {
		return urlSvc.getOriginalUrlFromShortened(form.getUrl());
	}

	@GetMapping("/{code}")
	public ResponseEntity<Object> redirectToOriginal(@PathVariable String code) {
		String url = urlSvc.getOriginalUrlFromCode(code);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
	}

}
