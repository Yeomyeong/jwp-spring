package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class PerformanceAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(next.service..*) || within(next.dao..*)")
    public void myPointcut() {}

    @Around("myPointcut()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch(pjp.toShortString());
        stopWatch.start();
        Object retVal = pjp.proceed();
        stopWatch.stop();

        log.debug(stopWatch.toString());
        return retVal;
    }
}
