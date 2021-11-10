package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import sit.int222.mongkolthorn.models.Login;
import sit.int222.mongkolthorn.repositories.LoginRepository;

import java.util.List;

public class LoginController {
    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public List<Login> login() {
        return loginRepository.findAll();
    }
}
