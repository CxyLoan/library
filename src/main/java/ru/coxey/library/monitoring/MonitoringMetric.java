package ru.coxey.library.monitoring;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonitoringMetric {

    ISSUED_BOOK("Количество выданных книг"),
    OVERDUE_BOOK("Количество просроченных книг"),
    RETURNED_BOOK("Количество возвращенных книг"),
    REGISTERED_READER("Количество зарегистрированных читателей"),
    CONTROLLER_METHOD_EXECUTION_TIME("Время обработки запроса")
    ;

    private final String description;
}
