package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sit.int222.mongkolthorn.config.TokenSecurityUtil;
import sit.int222.mongkolthorn.exceptions.ApiException;
import sit.int222.mongkolthorn.exceptions.ApiRequestException;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.Account;
import sit.int222.mongkolthorn.models.LoginAccount;
import sit.int222.mongkolthorn.models.RequestAccount;
import sit.int222.mongkolthorn.repositories.AccountRepository;
import sit.int222.mongkolthorn.services.TokenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Map<String, Object> Login (@RequestBody LoginAccount loginAccount) throws Exception {
        Account checkEmail = accountRepository.findByEmail(loginAccount.getEmail());
        java.util.Map<String, Object> respone = new HashMap<>();
        if(checkEmail == null){
            throw new ApiRequestException("Email: " + loginAccount.getEmail() + " not found");
        } else if(!passwordEncoder.matches(loginAccount.getPassword(), checkEmail.getPassword())) {
            throw new ApiRequestException("Login password failed!");
        } else if(passwordEncoder.matches(loginAccount.getPassword(), checkEmail.getPassword())) {
            String token = tokenService.tokenize(checkEmail);
            respone.put("account", checkEmail);
            respone.put("token", token);
        } else{
            throw new Exception();
        }
        return respone;
    }

    @GetMapping("/user/refresh-token")
    public String refreshToken() {
        Long currentAccountId = TokenSecurityUtil.getCurrentAccountId();
        if (currentAccountId == null) {
            throw new ApiRequestException("This account id is unauthorized");
        }
        Long accountId = currentAccountId;
        Optional<Account> findAccountId = accountRepository.findById(Long.valueOf(accountId));
        if (findAccountId.isEmpty()) {
            throw new ApiRequestException("Account not found");
        }
        Account account = findAccountId.get();
        String response = tokenService.tokenize(account);
        return response;
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
        Long authoAccountId = TokenSecurityUtil.getCurrentAccountId();
        Account account = accountRepository.findById(account_id).orElse(null);
        if (!authoAccountId.equals(account_id)) {
            throw new ApiRequestException("This account id is unauthorized");
        } else
            if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + account_id + " does not exist.");
        } else
            return accountRepository.getOne(authoAccountId);
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
    public Account addAccount(@RequestPart RequestAccount requestAccount) {

        Account newAccount = new Account();
        newAccount.setAccountId(requestAccount.getAccountId());
        newAccount.setFname(requestAccount.getFname());
        newAccount.setLname(requestAccount.getLname());
        newAccount.setPhone(requestAccount.getPhone());
        newAccount.setEmail(requestAccount.getEmail());
        newAccount.setPassword(passwordEncoder.encode(requestAccount.getPassword()));
        newAccount.setRole(requestAccount.getRole());

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
        } else
        return accountRepository.save(newAccount);
    }

    @PutMapping("/admin/editAccount")
    public Account editAccount(@RequestPart RequestAccount requestAccount) {

        Account editAccount = new Account();
        editAccount.setAccountId(requestAccount.getAccountId());
        editAccount.setFname(requestAccount.getFname());
        editAccount.setLname(requestAccount.getLname());
        editAccount.setPhone(requestAccount.getPhone());
        editAccount.setEmail(requestAccount.getEmail());
        editAccount.setPassword(requestAccount.getPassword());
        editAccount.setRole(requestAccount.getRole());

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
        } else
            editAccount.setPassword(passwordEncoder.encode(editAccount.getPassword()));
        return accountRepository.save(editAccount);
    }

    @PutMapping("/user/editAccount")
    public Account userEditAccount(@RequestPart RequestAccount requestAccount) {

        Account editAccount = new Account();
        editAccount.setAccountId(requestAccount.getAccountId());
        editAccount.setFname(requestAccount.getFname());
        editAccount.setLname(requestAccount.getLname());
        editAccount.setPhone(requestAccount.getPhone());
        editAccount.setEmail(requestAccount.getEmail());
        editAccount.setPassword(requestAccount.getPassword());
        editAccount.setRole(requestAccount.getRole());

        Account findAccountId = accountRepository.findById(editAccount.getAccountId()).orElse(null);
        Account findEmail = accountRepository.findByEmail(editAccount.getEmail());
        if (findAccountId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Can't edit. Account id: " + editAccount.getAccountId() + " does not exist.");
        }
        String oldPassword = findAccountId.getPassword();
        Long authoAccountId = TokenSecurityUtil.getCurrentAccountId();
        if (!authoAccountId.equals(editAccount.getAccountId())) {
            throw new ApiRequestException("This account id is unauthorized");
        } else if (findEmail != null && findAccountId.getAccountId() != findEmail.getAccountId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_EMAIL_ALREADY_EXIST,
                    "Can't edit. Email: " + editAccount.getEmail() + " already exist.");
        } else if (editAccount.getPassword() == null || editAccount.getPassword() == "") {
            editAccount.setPassword(oldPassword);
        } else
            editAccount.setPassword(passwordEncoder.encode(requestAccount.getPassword()));
        return accountRepository.save(editAccount);
    }
}
