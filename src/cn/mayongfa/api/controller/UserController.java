package cn.mayongfa.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.mayongfa.common.MD5Util;
import cn.mayongfa.common.StrUtil;
import cn.mayongfa.model.UserBasis;
import cn.mayongfa.service.UserBasisService;

@Controller
@RequestMapping("/api/user/*")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserBasisService userBasisService;

	/**
	 * get
	 * 
	 * @param oRequest
	 * @param oResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	public String get(@RequestParam("id") long id) {
		int code = -1;
		String msg = "";
		UserBasis entity = userBasisService.getEntity(id);
		try {
			if (entity != null && entity.getId() > 0) {
				code = 1;
				msg = "请求成功!";
			}
		} catch (Exception e) {
			log.error("出错啦！", e);
		}

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("params", entity);
		responseMap.put("rows", "");
		return new Gson().toJson(responseMap);
	}

	/**
	 * getlist
	 * 
	 * @param oRequest
	 * @param oResponse
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@ResponseBody
	@RequestMapping(value = "getlist", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	public String getList() {
		int code = -1;
		String msg = "";

		List<UserBasis> userList = userBasisService.getList();
		if (userList != null && userList.size() > 0) {
			code = 1;
			msg = "请求成功!";
		}
		// 演示异常
		userBasisService.getListCount(null);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("params", "");
		responseMap.put("rows", userList);
		return new Gson().toJson(responseMap);
	}

	/**
	 * post
	 * 
	 * @param oRequest
	 * @param oResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "post", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	public String post(HttpServletRequest oRequest, HttpServletResponse oResponse) {
		int code = -1;
		String msg = "";
		String name = oRequest.getParameter("name") != null ? oRequest.getParameter("name").toString() : "";
		String password = oRequest.getParameter("password") != null ? oRequest.getParameter("password").toString() : "";
		String phone = oRequest.getParameter("phone") != null ? oRequest.getParameter("phone").toString() : "";

		if (StrUtil.isNullOrEmpty(name)) {
			msg = "请填写用户姓名！";
		} else if (StrUtil.isNullOrEmpty(password)) {
			msg = "请填写密码！";
		} else {
			UserBasis entity = new UserBasis();
			entity.setId(StrUtil.DEFAULT_NULL);
			entity.setName(name);
			entity.setPassword(MD5Util.GetMD5Code32(password));
			entity.setPhone(phone);
			entity.setStatus(1);
			entity.setUpdatetime(new Date());
			entity.setCreatetime(new Date());
			long id = userBasisService.Save(entity);
			if (id > 0) {
				code = 1;
				msg = "添加成功！";
			}
		}

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("params", "");
		responseMap.put("rows", "");
		return new Gson().toJson(responseMap);
	}

	/**
	 * delete
	 * 
	 * @param oRequest
	 * @param oResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", produces = "application/json;charset=UTF-8", method = { RequestMethod.DELETE })
	public String delete(@RequestParam("id") long id) {
		int code = -1;
		String msg = "";
		if (userBasisService.Delete(id)) {
			code = 1;
			msg = "删除成功！";
		} else {
			msg = "删除失败！";
		}

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("code", code);
		responseMap.put("msg", msg);
		responseMap.put("params", "");
		responseMap.put("rows", "");
		return new Gson().toJson(responseMap);
	}

	/**
	 * put
	 * 
	 * @param oRequest
	 * @param oResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "put", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	public String put(HttpServletRequest oRequest, HttpServletResponse oResponse) {
		int code = -1;
		String msg = "";

		long id = StrUtil.toLong(oRequest.getParameter("id"), StrUtil.DEFAULT_NULL);
		String name = oRequest.getParameter("name") != null ? oRequest.getParameter("name").toString() : "";

		String phone = oRequest.getParameter("phone") != null ? oRequest.getParameter("phone").toString() : "";

		if (id == StrUtil.DEFAULT_NULL) {
			msg = "请选择用户！";
		} else if (StrUtil.isNullOrEmpty(name)) {
			msg = "请填写用户姓名！";
		} else if (StrUtil.isNullOrEmpty(phone)) {
			// msg = "请填写电话！";
		} else {
			UserBasis entity = userBasisService.getEntity(id);
			if (entity != null && entity.getId() == id) {
				entity.setName(name);
				entity.setPhone(phone);
				entity.setUpdatetime(new Date());
				id = userBasisService.Save(entity);
				if (id > 0) {
					code = 1;
					msg = "更新成功！";
				} else {
					msg = "更新失败！";
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
