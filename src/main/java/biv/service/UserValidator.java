package biv.service;

import biv.domain.UserAccount;
import biv.exception.WrongFormatException;

public interface UserValidator {

    public boolean validateUser(UserAccount user) throws WrongFormatException;
}
