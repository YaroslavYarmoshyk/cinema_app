package mate.academy.spring.service.impl;

import java.util.Set;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleServiceImpl roleService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     RoleServiceImpl roleService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
