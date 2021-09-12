package biv.service.impl;

import biv.domain.UserAccount;
import biv.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserAccount userAccountMock;

    @Test
    void create_shouldReturnTrueIfUserAccountDoesNotExist() {
        // GIVEN
        when(userRepositoryMock.isPersistent(userAccountMock)).thenReturn(false);
        doNothing().when(userRepositoryMock).persist(userAccountMock);

        // WHEN
        boolean result = userAccountService.create(userAccountMock);

        // THEN
        verify(userRepositoryMock).isPersistent(userAccountMock);
        verify(userRepositoryMock).persist(userAccountMock);
        assertTrue(result);
    }

    @Test
    void create_shouldReturnFalseIfUserAccountIsExist() {
        // GIVEN
        when(userRepositoryMock.isPersistent(userAccountMock)).thenReturn(true);

        // WHEN
        boolean result = userAccountService.create(userAccountMock);

        // THEN
        verify(userRepositoryMock).isPersistent(userAccountMock);
        assertFalse(result);
    }

    @Test
    void read_shouldReturnUserAccountById() {
        // GIVEN
        long userAccountId = 1L;
        when(userRepositoryMock.findById(userAccountId)).thenReturn(userAccountMock);

        // WHEN
        UserAccount result = userAccountService.read(userAccountId);

        // THEN
        verify(userRepositoryMock).findById(userAccountId);
        assertEquals(userAccountMock, result);
    }

    @Test
    void update_shouldPersistUserAccount() {
        // GIVEN
        doNothing().when(userRepositoryMock).persist(userAccountMock);

        // WHEN
        userAccountService.update(userAccountMock);

        // THEN
        verify(userRepositoryMock).persist(userAccountMock);
    }

    @Test
    void delete_shouldDeleteUserAccountById() {
        // GIVEN
        long userAccountId = 1L;
        when(userRepositoryMock.findById(userAccountId)).thenReturn(userAccountMock);
        doNothing().when(userRepositoryMock).delete(userAccountMock);

        // WHEN
        userAccountService.delete(userAccountId);

        // THEN
        verify(userRepositoryMock).findById(userAccountId);
        verify(userRepositoryMock).delete(userAccountMock);
    }
}