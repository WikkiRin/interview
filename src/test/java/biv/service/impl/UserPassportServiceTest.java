package biv.service.impl;

import biv.domain.PassportRu;
import biv.domain.UserAccount;
import biv.service.BaseDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPassportServiceTest {

    @InjectMocks
    @Spy
    private UserPassportService testObj;

    @Mock
    private BaseDomainService userServiceMock;

    @Mock
    private PassportRuService passportServiceMock;

    @Mock
    private PassportRu passportRuMock;

    @Mock
    private UserAccount userAccountMock;

    @BeforeEach
    public void init() {
        testObj.setPassportService(passportServiceMock);
        testObj.setUserService(userServiceMock);
    }

    @Test
    public void addPassportToUser_shouldCreatePassportAndAddPassportRuToUserAccountIfPassportRuIsNotExists() throws OperationNotSupportedException {
        // GIVEN
        List<PassportRu> passports = mock(List.class);
        Stream<PassportRu> passportRuStream = mock(Stream.class);
        Optional<PassportRu> passportRuOptional = mock(Optional.class);

        when(testObj.getAllPassportsUser(userAccountMock)).thenReturn(passports);
        when(userServiceMock.read(anyLong())).thenReturn(userAccountMock);

        when(passports.stream()).thenReturn(passportRuStream);
        when(passportRuStream.filter(any())).thenReturn(passportRuStream);
        when(passportRuStream.findFirst()).thenReturn(passportRuOptional);
        when(passportRuOptional.orElse(null)).thenReturn(passportRuMock);

        doNothing().when(testObj).makePassportUserIrrelevant(passportRuMock);

        when(passports.add(passportRuMock)).thenReturn(true);
        when(passportServiceMock.create(passportRuMock)).thenReturn(true);
        doNothing().when(userServiceMock).update(userAccountMock);

        // WHEN
        testObj.addPassportToUser(passportRuMock, userAccountMock);

        //THEN
        verify(passportServiceMock).create(passportRuMock);
        verify(userServiceMock).update(userAccountMock);
    }

    @Test
    public void addPassportToUser_shouldCreatePassportAndAddPassportRuToUserAccountIfPassportRuIsExists() throws OperationNotSupportedException {
        // GIVEN
        List<PassportRu> passports = mock(List.class);
        Stream<PassportRu> passportRuStream = mock(Stream.class);
        Optional<PassportRu> passportRuOptional = mock(Optional.class);

        when(testObj.getAllPassportsUser(userAccountMock)).thenReturn(passports);
        when(userServiceMock.read(anyLong())).thenReturn(userAccountMock);
        when(passports.stream()).thenReturn(passportRuStream);
        when(passportRuStream.filter(any())).thenReturn(passportRuStream);
        when(passportRuStream.findFirst()).thenReturn(passportRuOptional);
        when(passportRuOptional.orElse(null)).thenReturn(null);

        when(passports.add(passportRuMock)).thenReturn(true);
        when(passportServiceMock.create(passportRuMock)).thenReturn(true);
        doNothing().when(userServiceMock).update(userAccountMock);

        // WHEN
        testObj.addPassportToUser(passportRuMock, userAccountMock);

        //THEN
        verify(passportServiceMock).create(passportRuMock);
        verify(userServiceMock).update(userAccountMock);
    }

    @Test
    public void getAllPassportsUser_shouldReturnAllPassportsUser() {
        // GIVEN
        List<PassportRu> passports = List.of(passportRuMock);
        when(userAccountMock.getPassport()).thenReturn(passports);

        // WHEN
        List<PassportRu> result = testObj.getAllPassportsUser(userAccountMock);

        // THEN
        verify(userAccountMock).getPassport();
        assertEquals(passports, result);
    }

    @Test
    public void getAllActualPassportsUser_shouldReturnAllActualPassportsUser() {
        // GIVEN
        List<PassportRu> passports = mock(List.class);
        Stream<PassportRu> passportRuStream = mock(Stream.class);

        when(testObj.getAllPassportsUser(userAccountMock)).thenReturn(passports);

        when(passports.stream()).thenReturn(passportRuStream);
        when(passportRuStream.filter(any())).thenReturn(passportRuStream);
        when(passportRuStream.collect(any())).thenReturn(passports);

        // WHEN
        List<PassportRu> result = testObj.getAllActualPassportsUser(userAccountMock);

        // THEN
        assertEquals(passports, result);
    }

    @Test
    public void makePassportUserIrrelevant_shouldMakePassportUserIrrelevant() {
        // GIVEN
        doNothing().when(passportRuMock).setActual(false);
        doNothing().when(passportServiceMock).update(passportRuMock);

        // WHEN
        testObj.makePassportUserIrrelevant(passportRuMock);

        // THEN
        verify(passportRuMock).setActual(false);
        verify(passportServiceMock).update(passportRuMock);
    }

    @Test
    public void getAllActualPassportsUser_shouldThrowNotFoundException_when_userIdNotFoundById() {
        // GIVEN
        long userId = 1L;
        when(userAccountMock.getId()).thenReturn(userId);
        when(userServiceMock.read(userId)).thenReturn(null);

        // THEN
        assertThrows(OperationNotSupportedException.class, () -> {
            testObj.addPassportToUser(passportRuMock, userAccountMock);
        });
    }
}