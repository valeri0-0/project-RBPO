package simple;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public String getUsers() {
        return "Список пользователей: Валерия, Виктория";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable String id) {
        return "Пользователь с ID: " + id;
    }
}