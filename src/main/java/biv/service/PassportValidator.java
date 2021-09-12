package biv.service;

import biv.domain.PassportRu;
import biv.exception.WrongFormatException;

public interface PassportValidator {

    public boolean validatePassport(PassportRu passport) throws WrongFormatException;
}
