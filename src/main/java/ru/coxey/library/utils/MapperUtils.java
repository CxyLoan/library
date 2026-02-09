package ru.coxey.library.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class MapperUtils {

    public static <T> T map(Supplier<T> entityFromDb, Supplier<T> entityDto) {
        final T value = entityDto.get();
        return Objects.nonNull(value) ? value : entityFromDb.get();
    }
}
