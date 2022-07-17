package com.meli.dnaanalyzer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MutantServiceAspect {

    private Logger logger = LoggerFactory.getLogger(MutantServiceAspect.class);

    @Around("execution(* com.meli.dnaanalyzer.service.impl.MutantService.*(..))")
    public boolean countTime(ProceedingJoinPoint punt) throws Throwable {
        Long fist = System.currentTimeMillis();
        boolean response = (boolean) punt.proceed();
        Long finish = System.currentTimeMillis();
        Long mili = (finish - fist);
        if (logger.isInfoEnabled()) {
            logger.info(String.format("The method is: %s and the elapsed time %s milliseconds", punt.getSignature().getName(), mili));
        }
        return response;
    }

}