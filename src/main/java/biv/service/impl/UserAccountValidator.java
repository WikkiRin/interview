package biv.service.impl;

import biv.domain.UserAccount;
import biv.exception.WrongFormatException;
import biv.service.UserValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserAccountValidator implements UserValidator {

    public static final String WRONG_USER_S_MOBILE_PHONE_FORMAT = "Wrong user's mobile phone format";
    public static final String WRONG_USER_S_PERSONAL_EMAIL_FORMAT = "Wrong user's personal email format";
    public static final String WRONG_USER_S_WORK_EMAIL_FORMAT = "Wrong user's work email format";

    @Override
    public boolean validateUser(UserAccount user) throws WrongFormatException {

        Pattern phone = Pattern.compile("[+]7\\(\\d\\d\\d\\)\\d\\d\\d-\\d\\d\\d\\d");

        if (!user.getMobilePhone().matches(String.valueOf(phone)))
            throw new WrongFormatException(WRONG_USER_S_MOBILE_PHONE_FORMAT);

        InternetAddress emailAddress = null;
        try {
            emailAddress = new InternetAddress(user.getPersonalEmail());
            emailAddress.validate();
        } catch (AddressException e) {
            throw new WrongFormatException(WRONG_USER_S_PERSONAL_EMAIL_FORMAT);
        }

        try {
            emailAddress = new InternetAddress(user.getWorkEmail());
            emailAddress.validate();
        } catch (AddressException e) {
            throw new WrongFormatException(WRONG_USER_S_WORK_EMAIL_FORMAT);
        }


        return true;
    }
}
