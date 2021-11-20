package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/max-accountId")
    public long maxAccountId() {
        return accountRepository.getMaxAccountId();
    }

    @GetMapping("/account/{account_id}")
    public Account showAccountId(@PathVariable Long account_id) {
        Account account = accountRepository.findById(account_id).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + account_id + " does not exist.");
        } else return accountRepository.getOne(account_id);
    }

    @DeleteMapping("/delete/account/{account_id}")
    public void deleteAccount(@PathVariable Long account_id) {
        Account findAccountId = accountRepository.findById(account_id).orElse(null);
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't delete. Account id: " + account_id + " does not exist.");
        } else
        accountRepository.deleteById(account_id);
    }

    @PostMapping("/addAccount")
    public Account addAccount(@RequestPart Account newAccount) {
        Account newAccountId = accountRepository.findById(newAccount.getAccountId()).orElse(null);
        Account newEmail = accountRepository.findByEmail(newAccount.getEmail());
        if (newAccountId != null && newEmail != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_ALREADY_EXIST,
                    "Can't add. Account id: " + newAccount.getAccountId()
                            + " Email: " + newAccount.getEmail()
                            + " already exist.");
        } else if (newAccountId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_ALREADY_EXIST,
                    "Can't add. Account id: " + newAccount.getAccountId()
                            + " already exist.");
        } else if (newEmail != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_EMAIL_ALREADY_EXIST,
                    "Can't add. Email: " + newAccount.getEmail()
                            + " already exist.");
        } else if (newAccount.getFname() == null || newAccount.getLname() == null ||
                newAccount.getPhone() == null || newAccount.getPassword() == null ||
                newAccount.getFname() == "" || newAccount.getLname() == "" ||
                newAccount.getPhone() == "" || newAccount.getPassword() == "") {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
                    "Can't add. Some account detail is null.");
        } else
        return accountRepository.save(newAccount);
    }

    @PutMapping("/editAccount")
    public Account editAccount(@RequestPart Account editAccount) {
        Account findAccountId = accountRepository.findById(editAccount.getAccountId()).orElse(null);
        Account findEmail = accountRepository.findByEmail(editAccount.getEmail());
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't edit. Account id: " + editAccount.getAccountId() + " does not exist.");
        } else if (findEmail != null && findAccountId.getAccountId() != findEmail.getAccountId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't edit. Email: " + editAccount.getEmail() + " already exist.");
        } else if (editAccount.getFname() == null || editAccount.getLname() == null ||
                editAccount.getPhone() == null || editAccount.getPassword() == null ||
                editAccount.getFname() == "" || editAccount.getLname() == "" ||
                editAccount.getPhone() == "" || editAccount.getPassword() == "") {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
                    "Can't edit. Some account detail is null.");
        } else
        return accountRepository.save(editAccount);
    }
}
