package com.hw.cto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
class IndexCto {
	
    @RequestMapping("/")
    public String index() {
        return "forward:/login.html";
    }
}
