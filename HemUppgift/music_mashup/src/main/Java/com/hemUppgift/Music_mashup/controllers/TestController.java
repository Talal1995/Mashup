package com.hemUppgift.Music_mashup.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	/**
	 *
	 * This testController is just an empty welcome to make sure that requestmapping is working
	 * @author Talal Attar
	 */
	@RequestMapping("/welcome")
	public String welcomepage() {
		return "Welcome to Talals place";
	}

}