package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Account;
import sit.int222.mongkolthorn.repositories.AccountRepository;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/allAccounts")
    public List<Account> account() {
        return accountRepository.findAll();
    }
}
