package drinksystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* drinksystem.*Factory.*(..)) || " +
            "execution(* drinksystem.*Strategy.*(..)) || " +
            "execution(* drinksystem.*Visitor.*(..))")
    public Object logParamsAndResult(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("[AOP] ВЫЗОВ: " + joinPoint.getSignature().getName());
        System.out.println("[AOP] ПАРАМЕТРЫ: " + Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        System.out.println("[AOP] РЕЗУЛЬТАТ: " + result);
        System.out.println("----------------------------------------");

        return result;
    }
}