package es.heroesfactory.anotations.executor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutorCalculateDuration {
    @Around("@annotation(es.heroesfactory.anotations.CalculateDuration)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Method Name: "+ point.getSignature().getName() + "--> Execution time is: " + (endTime-startTime) +"ms");
        return object;
    }
}
