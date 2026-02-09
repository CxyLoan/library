package ru.coxey.library.monitoring;

import io.micrometer.core.instrument.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MonitoringService {

    private final MeterRegistry meterRegistry;

    public void counter(MonitoringMetric metric) {
        Counter.builder(metric.name())
                .description(metric.getDescription())
                .register(meterRegistry)
                .increment();
    }

    public void counter(MonitoringMetric metric, int count) {
        Counter.builder(metric.name())
                .description(metric.getDescription())
                .register(meterRegistry)
                .increment(count);
    }

    public Timer.Sample getTimer() {
        return Timer.start(meterRegistry);
    }

    public void stop(MonitoringMetric metric, Timer.Sample sample, Collection<Tag> tags) {
        if (Objects.nonNull(sample)) {
            sample.stop(Timer.builder(metric.name())
                    .description(metric.getDescription())
                    .tags(tags)
                    .register(meterRegistry));
        }
    }
}
