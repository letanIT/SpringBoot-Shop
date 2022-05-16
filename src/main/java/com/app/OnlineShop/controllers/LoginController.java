package com.app.OnlineShop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController {

	@RequestMapping(value = "")
	public String getLoginPage() {
		return "Hello Login";
	}
}
