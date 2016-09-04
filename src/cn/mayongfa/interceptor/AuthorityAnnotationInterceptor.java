package cn.mayongfa.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

/**
 * 权限认证拦截器
 *
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;

			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			try {
				if (clazz != null && m != null) {
					boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
					boolean isMethondAnnotation = m.isAnnotationPresent(Authority.class);
					Authority authority = null;
					// 如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
					if (isMethondAnnotation) {
						authority = m.getAnnotation(Authority.class);
					} else if (isClzAnnotation) {
						authority = clazz.getAnnotation(Authority.class);
					}
					int code = -1;
					String msg = "";
					if (authority != null) {
						if (AuthorityType.NoValidate == authority.value()) {
							// 标记为不验证,放行
							return true;
						} else if (AuthorityType.NoAuthority == authority.value()) {
							// 不验证权限，验证是否登录
							// TODO:
							return true;
						} else {
							// 验证登录及权限
							// TODO:

							code = 1;
							msg = "验证成功！";
							return true;
						}
					}

					// //跳转
					// String url = "";
					// response.getWriter().write("<script>top.location.href='"
					// + url + "'</script>");
					// return false;

					// 未通过验证，返回提示json
					Map<String, Object> responseMap = new HashMap<String, Object>();
					responseMap.put("code", code);
					responseMap.put("msg", msg);
					responseMap.put("params", "");
					responseMap.put("rows", "");
					String json = new Gson().toJson(responseMap);
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write(json);
					return false;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}
}
