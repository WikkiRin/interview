package biv.service.impl;

import biv.domain.PassportRu;
import biv.domain.UserAccount;
import biv.repository.PassportRuRepository;
import biv.service.BaseDomainService;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Setter
public class UserPassportService {

    @Inject
    private BaseDomainService<UserAccount, Long> userService;
    @Inject
    private BaseDomainService<PassportRu, Long> passportService;
    @Inject
    private PassportRuRepository passportRuRepository;


    public void addPassportToUser(PassportRu passportRu, UserAccount userAccount) throws OperationNotSupportedException {
        userAccount = userService.read(userAccount.getId());
        if (userAccount == null) {
            throw new OperationNotSupportedException("User not found");
        }
        List<PassportRu> passports = getAllPassportsUser(userAccount);
        PassportRu oldUserPassport = passports.stream()
                .filter(passport -> passport.getLocale().equals(passportRu.getLocale()) && passport.isActual())
                .findFirst()
                .orElse(null);
        if (oldUserPassport != null)
            makePassportUserIrrelevant(oldUserPassport);

        passports.add(passportRu);
        passportService.create(passportRu);
        userService.update(userAccount);

    }

    public List<PassportRu> getAllPassportsUser(UserAccount userAccount) {
        return userAccount.getPassport();
    }

    public List<PassportRu> getAllActualPassportsUser(UserAccount userAccount) {
        List<PassportRu> passports = getAllPassportsUser(userAccount);
        return passports.stream()
                .filter(passport -> passport.isActual())
                .collect(Collectors.toList());
    }

    public void makePassportUserIrrelevant(PassportRu passportRu) {
        passportRu.setActual(false);
        passportService.update(passportRu);

    }
}
