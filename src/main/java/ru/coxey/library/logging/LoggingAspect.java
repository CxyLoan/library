package ru.coxey.library.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.coxey.library.dto.ReaderDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final MaskingService maskingService;

    @Around("@within(org.springframework.web.bind.annotation.RestController) ||" +
            "within(@org.springframework.stereotype.Service *)")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        final var methodName = joinPoint.getSignature().getName();
        final var className = joinPoint.getTarget().getClass().getSimpleName();
        final var args = validateArgs(joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("Пришел запрос в класс: {}, в метод: {}, параметры: {}, время ответа: {}",
                    className, methodName, args, LocalDateTime.now());
            return result;
        } catch (Exception e) {
            log.warn("Произошла ошибка при обработке запроса в классе: {}, в метод: {}, параметры: {}, время ответа: {}",
                    className, methodName, args, LocalDateTime.now(), e);
            throw e;
        }
    }

    private Object[] validateArgs(Object[] args) {
        final List<Object> argList = new ArrayList<>();
        for (var arg : args) {
            if (arg instanceof ReaderDto) {
                argList.add(maskingService.masking((ReaderDto) arg));
                continue;
            }
            argList.add(arg);
        }
        return argList.toArray();
    }
}
