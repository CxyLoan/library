package ru.coxey.library.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coxey.library.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class CustomHealthCheckService {

    private final BookRepository repository;

    public void healthCheck() {
        repository.healthCheck();
    }

    public void healthAnotherService() {
        /**
         * Представим, что у нас есть внешний сервис anotherService
         * тут бы мы вызвали его с помощью метода HEAD
         * и в зависимости от ответа внешнего сервиса вернули бы DTO
         * с соответствующим сообщение (UP, DOWN)
         * */
    }
}
