package com_brandao.dscommerce.services;

import com_brandao.dscommerce.entities.User;
import com_brandao.dscommerce.factory.UserFactory;
import com_brandao.dscommerce.repositories.UserRepository;
import com_brandao.dscommerce.services.exceptions.ForbiddenException;
import com_brandao.dscommerce.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService auth;

    @Mock
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private CustomUserUtil userUtil;

    private Long clientId;
    private Long notClientId;
    private User adminUser;
    private User clientUser;


    @BeforeEach
    void setUp() throws Exception {

        clientId = 1L;
        notClientId = 999L;

        adminUser = UserFactory.createAdminUser();
        clientUser = UserFactory.createUser();
    }

    @Test
    public void validateSelfOrAdminShouldDoNothingWhenAdminUser() {

        Mockito.when(service.authenticated()).thenReturn(adminUser);

        Assertions.assertDoesNotThrow(() -> {
            auth.validateSelfOrAdmin(clientId);
        });
    }

    @Test
    public void validateSelfOrAdminShouldDoNothingWhenClientId() {

        Mockito.when(service.authenticated()).thenReturn(clientUser);

        Assertions.assertDoesNotThrow(() -> {
            auth.validateSelfOrAdmin(clientId);
        });
    }

    @Test
    public void validateSelfOrAdminShouldThrowForbiddenExceptionWhenNotAdminUserAndNotClientUser() {

        Mockito.when(service.authenticated()).thenReturn(clientUser);

        Assertions.assertThrows(ForbiddenException.class, () -> {
            auth.validateSelfOrAdmin(notClientId);
        });
    }
}