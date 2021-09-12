package biv.service.impl;

import biv.domain.PassportRu;
import biv.exception.WrongFormatException;
import biv.repository.PassportRuRepository;
import biv.service.BaseDomainService;
import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class PassportRuService implements BaseDomainService<PassportRu, Long> {

    @Inject
    private PassportRuRepository passportRuRepository;

    @Inject
    private PassportRuValidator passportRuValidator;

    @Override
    @Transactional
    public boolean create(@NonNull PassportRu passport) {
        if (passportRuRepository.isPersistent(passport))
            return false;
        try {
            if (passportRuValidator.validatePassport(passport))
                passportRuRepository.persist(passport);
        } catch (WrongFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public PassportRu read(@NonNull Long id) {
        return passportRuRepository.findById(id);
    }

    @Override
    public void update(@NonNull PassportRu passport) {
        passportRuRepository.persist(passport);
    }

    @Override
    public void delete(@NonNull Long id) {
        passportRuRepository.delete(passportRuRepository.findById(id));
    }
}
