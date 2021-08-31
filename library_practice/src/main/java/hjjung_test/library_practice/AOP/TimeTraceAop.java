package hjjung_test.library_practice.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

// AOP 지정자
@Aspect
public class TimeTraceAop {

    // 패키지 하위의 모든 함수에 대한 적용
    @Around("execution(* hjjung_test.library_practice..*(..))")
    public Object execut(ProceedingJoinPoint joinPoint) throws Throwable{
        
        // ProceedingJoinPoint : 현재 하는 작업
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
