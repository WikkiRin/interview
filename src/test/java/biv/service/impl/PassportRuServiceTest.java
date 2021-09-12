package biv.service.impl;

import biv.domain.PassportRu;
import biv.exception.WrongFormatException;
import biv.repository.PassportRuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassportRuServiceTest {

    @InjectMocks
    private PassportRuService passportRuService;

    @Mock
    private PassportRuRepository passportRuRepositoryMock;

    @Mock
    private PassportRu passportRuMock;

    @Mock
    private PassportRuValidator passportRuValidator;

    @Test
    void create_shouldReturnTrueIfPassportRuDoesNotExist() throws WrongFormatException {
        // GIVEN
        when(passportRuRepositoryMock.isPersistent(passportRuMock)).thenReturn(false);
        when(passportRuValidator.validatePassport(passportRuMock)).thenReturn(true);
        doNothing().when(passportRuRepositoryMock).persist(passportRuMock);

        // WHEN
        boolean result = passportRuService.create(passportRuMock);

        // THEN
        verify(passportRuRepositoryMock).isPersistent(passportRuMock);
        verify(passportRuRepositoryMock).persist(passportRuMock);
        assertTrue(result);
    }

    @Test
    void create_returnFalseWhenPassportIsNotValid() throws WrongFormatException {
        // GIVEN
        when(passportRuRepositoryMock.isPersistent(passportRuMock)).thenReturn(false);
        when(passportRuValidator.validatePassport(passportRuMock)).thenThrow(WrongFormatException.class);

        // WHEN
        boolean result = passportRuService.create(passportRuMock);

        // THEN
        assertFalse(result);
    }

    @Test
    void create_shouldReturnFalseIfPassportRuDoesNotExist() {
        // GIVEN
        when(passportRuRepositoryMock.isPersistent(passportRuMock)).thenReturn(true);

        // WHEN
        boolean result = passportRuService.create(passportRuMock);

        // THEN
        verify(passportRuRepositoryMock).isPersistent(passportRuMock);
        assertFalse(result);
    }

    @Test
    void read_shouldReturnPassportRuById() {
        // GIVEN
        long passportRuId = 1L;
        when(passportRuRepositoryMock.findById(passportRuId)).thenReturn(passportRuMock);

        // WHEN
        PassportRu result = passportRuService.read(passportRuId);

        // THEN
        verify(passportRuRepositoryMock).findById(passportRuId);
        assertEquals(passportRuMock, result);
    }

    @Test
    void update_shouldPersistPassportRu() {
        // GIVEN
        doNothing().when(passportRuRepositoryMock).persist(passportRuMock);

        // WHEN
        passportRuService.update(passportRuMock);

        // THEN
        verify(passportRuRepositoryMock).persist(passportRuMock);
    }

    @Test
    void delete_shouldDeletePassportRuById() {
        // GIVEN
        long passportRuId = 1L;
        when(passportRuRepositoryMock.findById(passportRuId)).thenReturn(passportRuMock);
        doNothing().when(passportRuRepositoryMock).delete(passportRuMock);

        // WHEN
        passportRuService.delete(passportRuId);

        // THEN
        verify(passportRuRepositoryMock).findById(passportRuId);
        verify(passportRuRepositoryMock).delete(passportRuMock);
    }
}