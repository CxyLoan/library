package ru.coxey.library.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.coxey.library.dto.ReaderDto;
import ru.coxey.library.mapper.ReaderMapper;

@Component
@RequiredArgsConstructor
public class MaskingService {

    private final ReaderMapper readerMapper;

    public ReaderDto masking(ReaderDto dto) {
        final var result = readerMapper.copy(dto);
        result.setFirstName(maskingString(result.getFirstName()));
        result.setLastName(maskingString(result.getLastName()));
        result.setPhoneNumber(maskingString(result.getPhoneNumber()));
        result.setEmail(maskingString(result.getEmail()));
        return result;
    }

    private String maskingString(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
}
