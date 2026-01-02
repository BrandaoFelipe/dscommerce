package com_brandao.dscommerce.factory;

import com_brandao.dscommerce.entities.Category;
import com_brandao.dscommerce.entities.Product;
import com_brandao.dscommerce.entities.Role;
import com_brandao.dscommerce.entities.User;

import java.time.LocalDate;

public class UserFactory {
    public static User createUser() {

        User user = new User(
                1L,
                "Teste",
                "teste@gmail.com",
                "99-999999999",
                LocalDate.of(1990,03,30),
                "123");

        Role role = new Role(1L, "ROLE_CLIENT");
        user.addRole(role);
        return user;
    }

    public static User createAdminUser() {

        User user = new User(
                2L,
                "Admin",
                "admin@gmail.com",
                "99-999999999",
                LocalDate.of(1990,03,30),
                "123");

        Role role = new Role(2L, "ROLE_ADMIN");
        user.addRole(role);
        return user;
    }
}
