package simple;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Главная страница. Скопируйте ссылку: http://localhost:8080/hello";
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Приветствую!";
    }
}