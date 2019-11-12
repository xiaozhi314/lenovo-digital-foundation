package com.lenovo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("portal")
public class PortalController {
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
