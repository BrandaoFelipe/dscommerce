package com_brandao.dscommerce.services;

import com_brandao.dscommerce.dtos.UserDTO;
import com_brandao.dscommerce.entities.User;
import com_brandao.dscommerce.factory.UserFactory;
import com_brandao.dscommerce.projections.UserDetailsProjection;
import com_brandao.dscommerce.repositories.UserRepository;
import com_brandao.dscommerce.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private CustomUserUtil userUtil;

    private String validUsername;
    private String invalidUsername;
    private List<UserDetailsProjection> result;
    private UserDetailsProjection projection;
    private User user;

    @BeforeEach
    void setUp() throws Exception {

        validUsername = "teste@gmail.com";
        invalidUsername = "example@gmail.com";
        user = UserFactory.createUser();
        projection = Mockito.mock(UserDetailsProjection.class);

        Mockito.when(projection.getUsername()).thenReturn(validUsername);
        Mockito.when(projection.getPassword()).thenReturn("123");
        Mockito.when(projection.getRoleId()).thenReturn(1L);
        Mockito.when(projection.getAuthority()).thenReturn("ROLE_ADMIN");

        result = List.of(projection);

        Mockito.when(repository.searchUserAndRolesByEmail(validUsername)).thenReturn(result);
        Mockito.when(repository.searchUserAndRolesByEmail(invalidUsername)).thenReturn(List.of()); //returns empty list
        Mockito.when(repository.findByEmail(validUsername)).thenReturn(Optional.of(user));
    }

    @Test
    public void loadUserByUsernameShouldReturnUserWhenUsernameIsValid() {

        UserDetails user = service.loadUserByUsername(validUsername);

        Assertions.assertNotNull(user);
        System.out.println("Not null");

        Assertions.assertEquals(validUsername, result.get(0).getUsername());
        System.out.println("username: " + result.get(0).getUsername());

        Assertions.assertEquals("123", result.get(0).getPassword());
        System.out.println("Password: " + result.get(0).getPassword());
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUsernameNotFound() {

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(invalidUsername);
            System.out.println("threw UsernameNotFoundException");
        });
    }
    @Test
    public void authenticatedShouldReturnUserWhenUsernameExists() {

        Mockito.when(userUtil.getUserLogged()).thenReturn(validUsername);

        User result = service.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(validUsername, result.getEmail());
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserNotExists() {

        Mockito.doThrow(ClassCastException.class).when(userUtil).getUserLogged();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
            System.out.println("authenticated method");
            System.out.println("threw UsernameNotFoundException");
        });
    }

    @Test
    public void getMeShouldReturnUserDTOWhenCalled(){

        Mockito.when(userUtil.getUserLogged()).thenReturn(validUsername);
        UserDTO dto = service.getMe();
        System.out.println("DTO: " + dto.getEmail() + ", " + dto.getName());

        Assertions.assertNotNull(dto);

    }
}
