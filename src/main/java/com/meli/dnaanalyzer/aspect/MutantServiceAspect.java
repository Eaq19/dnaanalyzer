package com.meli.dnaanalyzer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MutantServiceAspect {

    @Around("execution(* com.meli.dnaanalyzer.service.impl.MutantService.*(..))")
    public boolean countTime(ProceedingJoinPoint punt) throws Throwable {
        Long fist = System.currentTimeMillis();
        boolean response = (boolean) punt.proceed();
        Long finish = System.currentTimeMillis();
        Long mili = (finish - fist);
        System.out.format("El metodo es : %s y el tiempo transcurrido %s milisegundos\n", punt.getSignature().getName(),  mili);
        return response;
    }

}