/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.qs.service.config.SystemDictService;

/**
 * 字典Controller
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class SystemDictController extends BaseController {

	@Autowired
	private SystemDictService systemDictService;
	
	

}