package biv.service.impl;

import biv.domain.UserAccount;
import biv.exception.WrongFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static biv.service.impl.UserAccountValidator.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAccountValidatorTest {

    @InjectMocks
    private UserAccountValidator userAccountValidator;

    @Test
    void validateUser_shouldReturnTrueIfUserAccountIsValid() throws WrongFormatException {
        // GIVEN
        UserAccount userAccount = new UserAccount();
        userAccount.setMobilePhone("+7(999)999-9999");
        userAccount.setPersonalEmail("qwe@yandex.ru");
        userAccount.setWorkEmail("qwe@yandex.ru");

        // WHEN
        boolean result = userAccountValidator.validateUser(userAccount);

        // THEN
        assertTrue(result);
    }

    @Test
    void validateUser_shouldThrowExceptionIfMobilePhoneIsNotValid() {
        // GIVEN
        UserAccount userAccount = new UserAccount();
        userAccount.setMobilePhone("=7(999)999-9999");
        userAccount.setPersonalEmail("qwe@yandex.ru");
        userAccount.setWorkEmail("qwe@yandex.ru");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            userAccountValidator.validateUser(userAccount);
        });

        // THEN
        assertEquals(WRONG_USER_S_MOBILE_PHONE_FORMAT, wrongFormatException.getMessage());
    }

    @Test
    void validateUser_shouldThrowExceptionIfPersonalEmailIsNotValid() {
        // GIVEN
        UserAccount userAccount = new UserAccount();
        userAccount.setMobilePhone("+7(999)999-9999");
        userAccount.setPersonalEmail("qwe7yandex.ru");
        userAccount.setWorkEmail("qwe@yandex.ru");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            userAccountValidator.validateUser(userAccount);
        });

        // THEN
        assertEquals(WRONG_USER_S_PERSONAL_EMAIL_FORMAT, wrongFormatException.getMessage());
    }

    @Test
    void validateUser_shouldThrowExceptionIfWorkEmailIsNotValid() {
        // GIVEN
        UserAccount userAccount = new UserAccount();
        userAccount.setMobilePhone("+7(999)999-9999");
        userAccount.setPersonalEmail("qwe@yandex.ru");
        userAccount.setWorkEmail("qweyandex.ru");

        // WHEN
        WrongFormatException wrongFormatException = assertThrows(WrongFormatException.class, () -> {
            userAccountValidator.validateUser(userAccount);
        });

        // THEN
        assertEquals(WRONG_USER_S_WORK_EMAIL_FORMAT, wrongFormatException.getMessage());
    }
}