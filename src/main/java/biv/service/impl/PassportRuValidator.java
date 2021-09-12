package biv.service.impl;

import biv.domain.PassportRu;
import biv.exception.WrongFormatException;
import biv.service.PassportValidator;

import javax.enterprise.context.ApplicationScoped;
import java.util.regex.Pattern;

@ApplicationScoped
public class PassportRuValidator implements PassportValidator {

    public static final String WRONG_PASSPORT_S_SERIAL_FORMAT = "Wrong passport's serial format";
    public static final String WRONG_PASSPORT_S_NUMBER_FORMAT = "Wrong passport's number format";
    public static final String WRONG_PASSPORT_S_ISSUER_CODE_FORMAT = "Wrong passport's issuer code format";

    @Override
    public boolean validatePassport(PassportRu passport) throws WrongFormatException {

        Pattern serial = Pattern.compile("[0-9]{4}");
        Pattern number = Pattern.compile("[0-9]{6}");
        Pattern issuerCode = Pattern.compile("[0-9]{3}-[0-9]{3}");

        if (!passport.getPassportSerial().matches(String.valueOf((serial))))
            throw new WrongFormatException(WRONG_PASSPORT_S_SERIAL_FORMAT);

        if (!passport.getPassportNumber().matches(String.valueOf((number))))
            throw new WrongFormatException(WRONG_PASSPORT_S_NUMBER_FORMAT);

        if (!passport.getIssuerCode().matches(String.valueOf((issuerCode))))
            throw new WrongFormatException(WRONG_PASSPORT_S_ISSUER_CODE_FORMAT);

        return true;
    }
}
