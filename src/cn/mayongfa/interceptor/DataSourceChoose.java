package cn.mayongfa.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.mayongfa.service.imp.JdbcContextHolder;

public class DataSourceChoose {
	
	private Map<String, String> map;
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void before(JoinPoint point){
		Object target = point.getTarget();  
        String method = point.getSignature().getName();  
        Class<?>[] classz = target.getClass().getInterfaces();  
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);  
            if (m!=null && m.isAnnotationPresent(DataSource.class)) {  
                DataSource data = m.getAnnotation(DataSource.class);  
                JdbcContextHolder.clearJdbcType();
                JdbcContextHolder.setJdbcType(data.value().getName());
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        } 
	}

}
