package cn.mayongfa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.mayongfa.activemq.MessageSender;
import cn.mayongfa.common.MD5Util;
import cn.mayongfa.common.StrUtil;
import cn.mayongfa.interceptor.Authority;
import cn.mayongfa.interceptor.AuthorityType;
import cn.mayongfa.model.UserBasis;
import cn.mayongfa.service.UserBasisService;

@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping("/account/*")
public class AccountController {

	@Autowired
	private UserBasisService userBasisService;
	@Autowired
	private MessageSender messageSender;

	/**
	 * login
	 * 
	 */
	@Authority(AuthorityType.Validate)
	@ResponseBody
	@RequestMapping(value = "login", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	public String login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
		int code = -1;
		String msg = "";

		if (StrUtil.isNullOrEmpty(phone)) {
			msg = "请填写用户帐号！";
		} else if (StrUtil.isNullOrEmpty(password)) {
			msg = "请填写密码！";
		} else {
			UserBasis entity = userBasisService.getEntity(phone);
			if (entity != null && entity.getId() > 0) {
				if (entity.getPassword().equals(MD5Util.GetMD5Code32(password))) {
					code = 1;
					msg = "登录成功！";

					messageSender.userLogin(entity.getId(), entity.getName());
					// TODO:

				} else {
					msg = "用户密码错误！";
					
				}
			} else {
				msg = "用户不存在！";
			}
		}

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("params", "");
		responseMap.put("rows", "");
		return new Gson().toJson(responseMap);
	}
}
