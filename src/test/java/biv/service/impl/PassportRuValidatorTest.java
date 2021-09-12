package biv.service.impl;

import biv.domain.PassportRu;
import biv.exception.WrongFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static biv.service.impl.PassportRuValidator.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PassportRuValidatorTest {

    @InjectMocks
    private PassportRuValidator passportRuValidator;

    @Test
    void validatePassport_shouldReturnTrueIfPassportIsValid() throws WrongFormatException {
        // GIVEN
        PassportRu passportRu = new PassportRu();
        passportRu.setPassportSerial("2819");
        passportRu.setPassportNumber("123456");
        passportRu.setIssuerCode("123-456");

        // WHEN
        boolean result = passportRuValidator.validatePassport(passportRu);

        // THEN
        assertTrue(result);
    }

    @Test
    void validatePassport_shouldThrowExceptionIfPassportSerialIsNotValid() {
        // GIVEN
        PassportRu passportRu = new PassportRu();
        passportRu.setPassportSerial("sss");
        passportRu.setPassportNumber("123456");
        passportRu.setIssuerCode("123-456");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            passportRuValidator.validatePassport(passportRu);
        });

        // THEN
        assertEquals(WRONG_PASSPORT_S_SERIAL_FORMAT, wrongFormatException.getMessage());
    }

    @Test
    void validatePassport_shouldThrowExceptionIfPassportNumberIsNotValid() {
        // GIVEN
        PassportRu passportRu = new PassportRu();
        passportRu.setPassportSerial("2819");
        passportRu.setPassportNumber("sss");
        passportRu.setIssuerCode("123-456");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            passportRuValidator.validatePassport(passportRu);
        });

        // THEN
        assertEquals(WRONG_PASSPORT_S_NUMBER_FORMAT, wrongFormatException.getMessage());
    }

    @Test
    void validatePassport_shouldThrowExceptionIfPassportIssuerCodeIsNotValid() {
        // GIVEN
        PassportRu passportRu = new PassportRu();
        passportRu.setPassportSerial("2819");
        passportRu.setPassportNumber("123456");
        passportRu.setIssuerCode("1234-56");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            passportRuValidator.validatePassport(passportRu);
        });

        // THEN
        assertEquals(WRONG_PASSPORT_S_ISSUER_CODE_FORMAT, wrongFormatException.getMessage());
    }
}