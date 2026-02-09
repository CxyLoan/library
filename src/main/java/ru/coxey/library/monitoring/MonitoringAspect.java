package ru.coxey.library.monitoring;

import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.coxey.library.dto.BookLoanDto;
import ru.coxey.library.dto.BookLoanRequest;
import ru.coxey.library.dto.ReaderDto;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
@Slf4j
public class MonitoringAspect {

    private static final String ATTRIBUTE_URI = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";

    private final MonitoringService monitoringService;

    @AfterReturning(value = "execution(* ru.coxey.library.rest.controller.BookLoanController.getBookForReader(..)) && " +
            "args(request,..)", returning = "result", argNames = "request, result")
    public void afterGetBookForReader(BookLoanRequest request, Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> response = (ResponseEntity<?>) result;

            if (response.getBody() instanceof BookLoanDto) {
                monitoringService.counter(MonitoringMetric.ISSUED_BOOK);
            }
        }
    }

    @AfterReturning(value = "execution(* ru.coxey.library.rest.controller.BookLoanController.returnBook(..)) && " +
            "args(id,..)", returning = "result", argNames = "id, result")
    public void afterReturnBook(Long id, Object result) {
        monitoringService.counter(MonitoringMetric.RETURNED_BOOK);
    }

    @AfterReturning(value = "execution(* ru.coxey.library.rest.controller.ReaderController.createReader(..)) && " +
            "args(readerDto,..)", returning = "result", argNames = "readerDto, result")
    public void afterCreateReader(ReaderDto readerDto, Object result) {
        if (result instanceof ResponseEntity<?> response) {
            if (response.getBody() instanceof ReaderDto) {
                monitoringService.counter(MonitoringMetric.REGISTERED_READER);
            }
        }
    }

    @AfterReturning(value = "execution(* ru.coxey.library.rest.controller.BookLoanController.getOverdueBooks(..)) && " +
            "args(..)", returning = "result")
    public void afterGetOverDueBook(ResponseEntity<List<BookLoanDto>> result) {
        if (result.getBody() != null) {
            List<BookLoanDto> overdueBooks = result.getBody();
            monitoringService.counter(MonitoringMetric.OVERDUE_BOOK, overdueBooks.size());
        }
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Around("restControllerMethods() && @annotation(getMapping)")
    public Object handle(ProceedingJoinPoint joinPoint, GetMapping getMapping) throws Throwable {
        return executeTime(joinPoint, "GET");
    }

    @Around("restControllerMethods() && @annotation(postMapping)")
    public Object handle(ProceedingJoinPoint joinPoint, PostMapping postMapping) throws Throwable {
        return executeTime(joinPoint, "POST");
    }

    @Around("restControllerMethods() && @annotation(deleteMapping)")
    public Object handle(ProceedingJoinPoint joinPoint, DeleteMapping deleteMapping) throws Throwable {
        return executeTime(joinPoint, "DELETE");
    }

    @Around("restControllerMethods() && @annotation(putMapping)")
    public Object handle(ProceedingJoinPoint joinPoint, PutMapping putMapping) throws Throwable {
        return executeTime(joinPoint, "PUT");
    }

    private Object executeTime(ProceedingJoinPoint joinPoint, String method) throws Throwable {
        var sample = monitoringService.getTimer();
        String requestStatus = "";
        final var uri = getUri();
        try {
            Object result = joinPoint.proceed();
            requestStatus = "success";
            return result;
        } catch (Exception e) {
            requestStatus = "failed";
            throw e;
        } finally {
            monitoringService.stop(MonitoringMetric.CONTROLLER_METHOD_EXECUTION_TIME, sample,
                    createTags(uri, requestStatus, method));
        }
    }

    private String getUri() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                return attributes.getRequest().getAttribute(ATTRIBUTE_URI).toString();
            }
        } catch (Exception ignored) {}
        return null;
    }

    private List<Tag> createTags(String uri, String requestStatus, String method) {
        return List.of(Tag.of("URI", uri), Tag.of("requestStatus", requestStatus),
                Tag.of("method", method));
    }
}
