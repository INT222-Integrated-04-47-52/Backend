package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.mongkolthorn.config.TokenSecurityUtil;
import sit.int222.mongkolthorn.exceptions.ApiRequestException;
import sit.int222.mongkolthorn.exceptions.ApiRequestExceptionUnauthorized;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.*;
import sit.int222.mongkolthorn.repositories.*;
import sit.int222.mongkolthorn.services.StorageService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ClosetController {
    @Autowired
    private ClosetRepository closetRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorsRepository colorsRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private StorageService storageService;

    @GetMapping("/admin/allClosets")
    public List<Closet> closet(){
        return closetRepository.findAll();
    }

    @GetMapping("/admin/closet/{account_id}")
    public List<Closet> adminShowClosetPerAccount(@PathVariable Long account_id){
        Account showAccount = new Account();
        showAccount.setAccountId(account_id);
        Account account = accountRepository.findById(showAccount.getAccountId()).orElse(null);
        if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + showAccount.getAccountId() + " does not exist.");
        } else
            return closetRepository.findAllByAccount(showAccount);
    }

    @GetMapping("/user/closet/{account_id}")
    public List<Closet> showClosetPerAccount(@PathVariable Long account_id){
        Account showAccount = new Account();
        showAccount.setAccountId(account_id);
        Long authoAccountId = TokenSecurityUtil.getCurrentAccountId();
        Account account = accountRepository.findById(showAccount.getAccountId()).orElse(null);
        if (!authoAccountId.equals(showAccount.getAccountId())) {
            throw new ApiRequestExceptionUnauthorized("This account id is unauthorized");
        } else if (account == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_ID_NOT_EXIST,
                    "Account id: " + showAccount.getAccountId() + " does not exist.");
        } else
        return closetRepository.findAllByAccount(showAccount);
    }

    @GetMapping("/max-closetId")
    public Long maxClosetId(){
        return closetRepository.getMaxClosetId();
    }

    @PostMapping("/user/addCloset")
    public Closet addCloset(@RequestPart Closet newCloset) {
        Closet findClosetId = closetRepository.findById(newCloset.getClosetId()).orElse(null);
//        Account findAccountId = accountRepository.findById(newCloset.getAccount().getAccountId()).orElse(null);
        Product findProductId = productRepository.findById(newCloset.getProduct().getProductId()).orElse(null);
        Colors findColorId = colorsRepository.findById(newCloset.getColor().getColorId()).orElse(null);
        storageService.loadAsResource(newCloset.getProduct().getImage());
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);

        Long authoAccountId = TokenSecurityUtil.getCurrentAccountId();
        if (!authoAccountId.equals(newCloset.getAccount().getAccountId())) {
            throw new ApiRequestExceptionUnauthorized("This account id is unauthorized");
        } else if (findClosetId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.CLOSET_ID_EXIST,
                    "Can't add. Closet id: " + newCloset.getClosetId() + " already exist.");
        } else if (findProductId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
                    "Can't add. Product id: " + newCloset.getProduct().getProductId() + " does not exist");
        } else if (findColorId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.COLOR_DOES_NOT_EXIST,
                    "Can't add. Color id: " + newCloset.getColor().getColorId() + " does not exist");
        }
//        else if ((newCloset.getPickUpDate() - currentDate) > 0) {
//
//
        else {
            Closet newClosetNosize = new Closet();
            newClosetNosize.setClosetId(newCloset.getClosetId());
            newClosetNosize.setAccount(newCloset.getAccount());
            newClosetNosize.setProduct(newCloset.getProduct());
            newClosetNosize.setColor(newCloset.getColor());
            newClosetNosize.setPickUpDate(newCloset.getPickUpDate());
            closetRepository.save(newClosetNosize);
            List<Size> newSizesList = newCloset.getSize();
            for (Size newSizes : newSizesList){
                newSizes.setCloset(newCloset);
                sizeRepository.save(newSizes);
            }
        }
        return closetRepository.save(newCloset);
    }
}
