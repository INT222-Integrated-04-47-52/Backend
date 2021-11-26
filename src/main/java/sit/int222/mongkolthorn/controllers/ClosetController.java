package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.*;
import sit.int222.mongkolthorn.repositories.*;

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

    @GetMapping("/admin/allClosets")
    public List<Closet> closet(){
        return closetRepository.findAll();
    }

    @GetMapping("/max-closetId")
    public Long maxClosetId(){
        return closetRepository.getMaxClosetId();
    }

//    @PostMapping("/user/addCloset")
//    public Closet addCloset(@RequestPart Closet newCloset) {
//        Closet findClosetId = closetRepository.findById(newCloset.getClosetId()).orElse(null);
//        Account findAccountId = accountRepository.findById(newCloset.getAccount().getAccountId()).orElse(null);
//        Product findProductId = productRepository.findById(newCloset.getProduct().getProductId()).orElse(null);
//        Colors findColorId = colorsRepository.findById(newCloset.getColor().getColorId()).orElse(null);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH,2);
//        Date currentDate = calendar.getTime();
//        System.out.println(currentDate);
//        if (findClosetId != null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.CLOSET_ID_EXIST,
//                    "Can't add. Closet id: " + newCloset.getClosetId() + " already exist.");
//        } else if (findAccountId == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.ACCOUNT_DOES_NOT_EXIST,
//                    "Can't add. Account id: " + newCloset.getAccount().getAccountId() + " does not exist");
//        } else if (findProductId == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
//                    "Can't add. Product id: " + newCloset.getProduct().getProductId() + " does not exist");
//        } else if (findColorId == null) {
//            throw new ProductException(ExceptionResponse.ERROR_CODE.COLOR_DOES_NOT_EXIST,
//                    "Can't add. Color id: " + newCloset.getColor().getColorId() + " does not exist");
//        } else
//        return newCloset;
//    }

    @PostMapping("/user/addCloset")
    public Closet addCloset(@RequestParam long closetId,
                            @RequestParam(name = "account_id") long accountId,
                            @RequestParam(name = "product_id") long productId,
                            @RequestParam(name = "color_id") long colorId,
                            @RequestParam List<Size> sizes,
                            @RequestParam Date date
                            ){
        Account account = accountRepository.findById(accountId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        Colors color = colorsRepository.findById(colorId).orElse(null);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH,2);
//        Date currentDate = calendar.getTime();

        Closet newCloset = new Closet(closetId,account,product,color,sizes, (java.sql.Date) date);

        return closetRepository.save(newCloset);
    }



}
