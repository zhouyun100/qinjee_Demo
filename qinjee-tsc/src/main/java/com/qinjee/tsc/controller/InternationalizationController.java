/**
 * 
 */
package com.qinjee.tsc.controller;

import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qinjee.tsc.i18n.LocaleConfig;

/**
 * @author Administrator
 *
 */

@Controller
public class InternationalizationController {

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private LocaleConfig localConfig;
	
	@RequestMapping("/show")
	public String hello(Model model){
		
        model.addAttribute("welcome", localConfig.getMessage("welcome"));
        return "index";
    }
	
	@RequestMapping("/restLanguage")
	public @ResponseBody String hello(){
		Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("welcome", null, locale);
    }
	
}
