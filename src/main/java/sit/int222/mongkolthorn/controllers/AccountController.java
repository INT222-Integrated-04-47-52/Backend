package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
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

    @GetMapping("/account/{account_id}")
    public Account showAccountId(@PathVariable Long account_id) {
        Account account = accountRepository.findById(account_id).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + account_id + " does not exist.");
        } else return accountRepository.getOne(account_id);
    }

    @DeleteMapping("/delete/{account_id}")
    public void deleteProduct(@PathVariable Long account_id) {
        Account account = accountRepository.findById(account_id).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't delete. Account id: " + account_id + " does not exist.");
        } else
        accountRepository.deleteById(account_id);
    }
}
