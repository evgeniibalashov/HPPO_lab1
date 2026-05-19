package drinksystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* drinksystem.*Factory.*(..)) || " +
            "execution(* drinksystem.*Strategy.*(..)) || " +
            "execution(* drinksystem.*Visitor.*(..))")
    public void logSpringBeanCall(JoinPoint joinPoint) {
        System.out.println(">>> [SPRING AOP] Вызван: " + joinPoint.getSignature().getName());
    }
}