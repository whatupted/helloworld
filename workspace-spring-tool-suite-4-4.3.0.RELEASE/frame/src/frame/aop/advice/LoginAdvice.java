package frame.aop.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Aspect
public class LoginAdvice {
	
	@Around("execution(public String login*(..))")
	public Object around(ProceedingJoinPoint pj)throws Throwable {
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();// 현재 객체가 생성되어있는 request객체를 리턴
		HttpServletRequest request = sra.getRequest(); // 넣어놧다가 꺼내서 사용
		HttpSession session = request.getSession();
		System.out.println("asdad");
		Object pro = "/test/loginForm";
		if(session != null) {
			String memId = (String)session.getAttribute("memId");
			if(memId != null && !memId.equals("")) {
				pro = pj.proceed();
			}
		}
		System.out.println(pj);
		return pro;
	}
}

/*
 * Object [] arg = pj.getArgs(); //해당빈의 전달 매개변수만 가로챔 HttpServletRequest request=
 * null; for(Object o : arg) { if(o instanceof HttpServletRequest) { request
 * =(HttpServletRequest)o ; System.out.println(request); }
 * 
 * }
 */

