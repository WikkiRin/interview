package biv.service.impl;

import biv.domain.UserAccount;
import biv.repository.UserRepository;
import biv.service.BaseDomainService;
import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserAccountService implements BaseDomainService<UserAccount, Long> {

    @Inject
    private UserRepository userRepository;

    @Override
    public boolean create(@NonNull UserAccount userAccount) {
        if (userRepository.isPersistent(userAccount))
            return false;
        userRepository.persist(userAccount);
        return true;
    }

    @Override
    public UserAccount read(@NonNull Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(@NonNull UserAccount userAccount) {
        userRepository.persist(userAccount);
    }

    @Override
    public void delete(@NonNull Long id) {
        userRepository.delete(userRepository.findById(id));
    }
}
