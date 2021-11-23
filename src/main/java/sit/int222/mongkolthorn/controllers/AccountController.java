package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sit.int222.mongkolthorn.exceptions.ApiException;
import sit.int222.mongkolthorn.exceptions.ApiRequestException;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.Account;
import sit.int222.mongkolthorn.models.LoginAccount;
import sit.int222.mongkolthorn.repositories.AccountRepository;
import sit.int222.mongkolthorn.services.TokenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Map<String, Object> Login (@RequestBody LoginAccount loginAccount) {
        Account checkEmail = accountRepository.findByEmail(loginAccount.getEmail());
        java.util.Map<String, Object> respone = new HashMap<>();
        if(checkEmail == null){
            throw new ApiRequestException("Email: " + loginAccount.getEmail() + " not found");
        } else if(passwordEncoder.matches(loginAccount.getPassword(), checkEmail.getPassword())) {
            String token = tokenService.tokenize(checkEmail);
            respone.put("account", checkEmail);
            respone.put("token", token);

        }
        return respone;
    }

    @GetMapping("/admin/allAccounts")
    public List<Account> account() {
        return accountRepository.findAll();
    }

    @GetMapping("/admin/max-accountId")
    public long maxAccountId() {
        return accountRepository.getMaxAccountId();
    }

    @GetMapping("/admin/account/{account_id}")
    public Account showAccountId(@PathVariable Long account_id) {
        Account account = accountRepository.findById(account_id).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + account_id + " does not exist.");
        } else return accountRepository.getOne(account_id);
    }

    @GetMapping("/user/account/{account_id}")
    public Account userShowAccountId(@PathVariable Long account_id) {
        Account account = accountRepository.findById(account_id).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + account_id + " does not exist.");
        } else return accountRepository.getOne(account_id);
    }

    @DeleteMapping("/admin/delete/account/{account_id}")
    public void deleteAccount(@PathVariable Long account_id) {
        Account findAccountId = accountRepository.findById(account_id).orElse(null);
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't delete. Account id: " + account_id + " does not exist.");
        } else
        accountRepository.deleteById(account_id);
    }

    @PostMapping("/admin/addAccount")
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
                newAccount.getPhone() == "" || newAccount.getPassword() == "" ||
                newAccount.getRole() == "" || newAccount.getRole() == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
                    "Can't add. Some account detail is null.");
        } else
            newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        return accountRepository.save(newAccount);
    }

    @PutMapping("/admin/editAccount")
    public Account editAccount(@RequestPart Account editAccount) {
        Account findAccountId = accountRepository.findById(editAccount.getAccountId()).orElse(null);
        Account findEmail = accountRepository.findByEmail(editAccount.getEmail());
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't edit. Account id: " + editAccount.getAccountId() + " does not exist.");
        } else if (findEmail != null && findAccountId.getAccountId() != findEmail.getAccountId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_EMAIL_ALREADY_EXIST,
                    "Can't edit. Email: " + editAccount.getEmail() + " already exist.");
        } else if (editAccount.getPassword() == null || editAccount.getPassword() == "") {
//            throw new ApiRequestException("password null");
            editAccount.setPassword(passwordEncoder.encode(findAccountId.getPassword()));
        }
        else if (editAccount.getFname() == null || editAccount.getLname() == null ||
                editAccount.getPhone() == null ||
                editAccount.getFname() == "" || editAccount.getLname() == "" ||
                editAccount.getPhone() == "" ||
                editAccount.getRole() == "" || editAccount.getRole() == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
                    "Can't edit. Some account detail is null.");
        }
        else
            System.out.println(editAccount.getPassword());
            editAccount.setPassword(passwordEncoder.encode(editAccount.getPassword()));
        System.out.println(editAccount.getPassword());
        return accountRepository.save(editAccount);
    }

    @PutMapping("/user/editAccount")
    public Account userEditAccount(@RequestPart Account editAccount) {
        Account findAccountId = accountRepository.findById(editAccount.getAccountId()).orElse(null);
        Account findEmail = accountRepository.findByEmail(editAccount.getEmail());
        String oldPassword = findAccountId.getPassword();
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't edit. Account id: " + editAccount.getAccountId() + " does not exist.");
        } else if (findEmail != null && findAccountId.getAccountId() != findEmail.getAccountId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_EMAIL_ALREADY_EXIST,
                    "Can't edit. Email: " + editAccount.getEmail() + " already exist.");
        } else if (editAccount.getPassword() == null || editAccount.getPassword() == "") {
            editAccount.setPassword(oldPassword);
        }
        else if (editAccount.getFname() == null || editAccount.getLname() == null ||
                editAccount.getPhone() == null ||
                editAccount.getFname() == "" || editAccount.getLname() == "" ||
                editAccount.getPhone() == "" ||
                editAccount.getRole() == "" || editAccount.getRole() == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
                    "Can't edit. Some account detail is null.");
        } else
            editAccount.setPassword(passwordEncoder.encode(editAccount.getPassword()));
        return accountRepository.save(editAccount);
    }
}
