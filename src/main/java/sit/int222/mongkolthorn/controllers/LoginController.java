//package sit.int222.mongkolthorn.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
//import sit.int222.mongkolthorn.exceptions.ProductException;
//import sit.int222.mongkolthorn.models.Account;
//import sit.int222.mongkolthorn.models.Login;
//import sit.int222.mongkolthorn.repositories.AccountRepository;
//import sit.int222.mongkolthorn.repositories.LoginRepository;
//
//import java.util.List;
//
//@RestController
//public class LoginController {
//    @Autowired
//    private LoginRepository loginRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @GetMapping("/login")
//    public List<Login> login() {
//        return loginRepository.findAll();
//    }
//
//    @GetMapping("/max-loginId")
//    public long maxLoginId() {
//        return loginRepository.getMaxLoginId();
//    }
//
//    @GetMapping("/login/{login_id}")
//    public Login showLoginId(@PathVariable Long login_id) {
//        Login login = loginRepository.findById(login_id).orElse(null);
//        if (login == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
//                    "Login id: " + login_id + " does not exist.");
//        } else return loginRepository.getOne(login_id);
//    }
//
//    @DeleteMapping("/delete/login/{login_id}")
//    public void deleteProduct(@PathVariable Long login_id) {
//        Login login = loginRepository.findById(login_id).orElse(null);
//        if (login == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_ID_NOT_EXIST,
//                    "Can't delete. Login id: " + login_id + " does not exist.");
//        } else
//        loginRepository.deleteById(login_id);
//    }
//
//    @PostMapping("/addLogin")
//    public Login addLogin(@RequestPart Login newLogin) {
//        Login newLoginId = loginRepository.findById(newLogin.getLoginId()).orElse(null);
//        Login newLoginEmail = loginRepository.findByEmail(newLogin.getLogEmail());
//        if (newLoginId != null && newLoginEmail != null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_ID_ALREADY_EXIST,
//                    "Can't add. Login id: " + newLogin.getLoginId()
//                            + " Email: " + newLogin.getLogEmail()
//                            + " already exist.");
//        } else if (newLoginId != null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_ID_ALREADY_EXIST,
//                    "Can't add. Login id: " + newLogin.getLoginId()
//                            + " already exist.");
//        } else if (newLoginEmail != null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_EMAIL_ALREADY_EXIST,
//                    "Can't add. Email: " + newLogin.getLogEmail()
//                            + " already exist.");
//        } else if (newLogin.getLogEmail() == null || newLogin.getLogEmail() == "" ||
//        newLogin.getLogPassword() == null || newLogin.getLogPassword() == "") {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_DETAIL_IS_NULL,
//                    "Can't add. Some login detail is null.");
//        }
//        Account newAccount = new Account(newLogin.getAccount().getAccountId(),
//                newLogin.getAccount().getFname(),newLogin.getAccount().getLname(),
//                newLogin.getAccount().getPhone(),newLogin.getLogEmail(),
//                newLogin.getLogPassword());
//        accountRepository.save(newAccount);
//        Login loginWithDetails = new Login(newLogin.getLoginId(),newLogin.getLogEmail(),
//                newLogin.getLogPassword(),newLogin.getLogPosition(),newAccount);
//        loginRepository.save(loginWithDetails);
//        return loginRepository.save(newLogin);
//    }
//
//    @PutMapping("/editLogin")
//    public Login editLogin(@RequestPart Login editLogin) {
//        Login loginId = loginRepository.findById(editLogin.getLoginId()).orElse(null);
//        Login loginEmail = loginRepository.findByEmail(editLogin.getLogEmail());
//        if (loginId == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.LOGIN_ID_NOT_EXIST,
//                    "Can't edit. Login id: " + editLogin.getLoginId() + " does not exist.");
//        } else if (loginEmail != null && loginId.getLoginId() != loginEmail.getLoginId()) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
//                    "Can't edit. Email: " + editLogin.getLogEmail() + " already exist.");
//        } else if (editLogin.getLogEmail() == null || editLogin.getLogEmail() == "" ||
//                editLogin.getLogPassword() == null || editLogin.getLogPassword() == "" ||
//                editLogin.getLogPosition() == null || editLogin.getLogPosition() == "") {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DETAIL_IS_NULL,
//                    "Can't edit. Some login detail is null.");
//        }
//        accountRepository.deleteById(editLogin.getAccount().getAccountId());
//        Account editAccount = new Account(editLogin.getAccount().getAccountId(),
//                editLogin.getAccount().getFname(),editLogin.getAccount().getLname(),
//                editLogin.getAccount().getPhone(),editLogin.getLogEmail(),
//                editLogin.getLogPassword());
//        accountRepository.save(editAccount);
//        Login loginWithDetails = new Login(editLogin.getLoginId(),editLogin.getLogEmail(),
//                editLogin.getLogPassword(),editLogin.getLogPosition(),editAccount);
//        loginRepository.save(loginWithDetails);
//        return loginRepository.save(editLogin);
//    }
//}
