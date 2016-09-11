package cn.mayongfa.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	private static Logger log = Logger.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		log.error("ExceptionHandler 捕获的异常：", exception);

		String requestType = request.getHeader("X-Requested-With");
        String type = "api";    //TODO:
		if (!type.equals("api") && StrUtil.isNullOrEmpty(requestType)) {
			// 非API请求

			return new ModelAndView("redirect:/500.html");
		} else {// JSON格式返回

			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("code", -1);
			responseMap.put("msg", "系统异常，请稍后重试！");
			responseMap.put("params", "");
			responseMap.put("rows", "");
			String json = new Gson().toJson(responseMap);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			try {
				response.getWriter().write(json);
				response.getWriter().flush();
				return null;
			} catch (IOException e) {
				log.error("", e);
			}
		}
		return new ModelAndView("redirect:/500.html");
	}

}
