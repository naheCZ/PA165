package cz.muni.fi.pa165.currency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;

@Named
@Aspect
public class LoggingAspect
{
    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint point) throws Throwable
    {
        System.err.println("Calling: " + point.getSignature());
        Long start = System.nanoTime();

        Object result = point.proceed();

        System.err.println("Method finished: " + point.getSignature());
        Long end = System.nanoTime();

        System.err.println("Time elapsed: " + (end - start)/1000 + " ms");

        return result;
    }
}
