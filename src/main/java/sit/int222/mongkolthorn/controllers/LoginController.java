package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.Login;
import sit.int222.mongkolthorn.repositories.LoginRepository;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public List<Login> login() {
        return loginRepository.findAll();
    }

    @GetMapping("/login/{login_id}")
    public Login showLoginId(@PathVariable Long login_id) {
        Login login = loginRepository.findById(login_id).orElse(null);
        if (login == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Login id: " + login_id + " does not exist.");
        } else return loginRepository.getOne(login_id);
    }

    @DeleteMapping("/delete/{login_id}")
    public void deleteProduct(@PathVariable Long login_id) {
        Login login = loginRepository.findById(login_id).orElse(null);
        if (login == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_ID_NOT_EXIST,
                    "Can't delete. Login id: " + login_id + " does not exist.");
        } else
            loginRepository.deleteById(login_id);
    }
}
