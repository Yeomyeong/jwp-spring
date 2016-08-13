package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class ArgumentPrintAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(next.service..*) || within(next.dao..*)")
    public void myPointcut() {}

    @Around("myPointcut()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        log.debug(pjp.toShortString() + " | agrs : " + Arrays.toString(pjp.getArgs()));

        return result;
    }
}
