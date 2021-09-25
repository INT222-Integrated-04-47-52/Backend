package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.Product;
import sit.int222.mongkolthorn.models.ProductHasColors;
import sit.int222.mongkolthorn.repositories.ProductHasColorsRepository;
import sit.int222.mongkolthorn.repositories.ProductRepository;
import sit.int222.mongkolthorn.services.StorageService;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductHasColorsRepository productHasColorsRepository;
    @Autowired
    StorageService storageService;

    @GetMapping("/allProducts")
    public List<Product> product() {
        return productRepository.findAll();
    }

    @GetMapping("/max-productId")
    public long maxIcecreamId() {
        return productRepository.getMaxProductId();
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestPart Product newProduct) {
        Product newProductId = productRepository.findById(newProduct.getProductId()).orElse(null);
        Product newProductName = productRepository.findByName(newProduct.getName());
        if (newProductId != null && newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ALREADY_EXIST,
                    "Can't add. Product id: " + newProductId.getProductId()
                            + " Product name: " + newProductName.getName()
                            + " already exist.");
        } else if (newProductId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ID_ALREADY_EXIST,
                    "Can't add. Product id: " + newProductId.getProductId()
                            + " already exist.");
        } else if (newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't add. Product name: " + newProductName.getName()
                            + " already exist.");
        }
        Product productNoColor = new Product(newProduct.getProductId(),newProduct.getName(),newProduct.getImage()
                ,newProduct.getDescription(),newProduct.getKind(),newProduct.getGender(),newProduct.getType());
        productRepository.save(productNoColor);
        List<ProductHasColors> productHasColorses = newProduct.getProductHasColors();
        for (ProductHasColors newProductHasColors : productHasColorses){
            newProductHasColors.setProduct(newProduct);
            productHasColorsRepository.save(newProductHasColors);
        }
        return productRepository.save(newProduct);
    }

    @PostMapping(value = "/addProduct/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProductWithImage
            (@RequestParam(value = "image", required = false) MultipartFile imageFile, @RequestPart Product newProduct) {
        Product newProductId = productRepository.findById(newProduct.getProductId()).orElse(null);
        Product newProductName = productRepository.findByName(newProduct.getName());
        if (newProductId != null && newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ALREADY_EXIST,
                    "Can't add. Product id: " + newProductId.getProductId()
                            + " Product name: " + newProductName.getName()
                            + " already exist.");
        } else if (newProductId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ID_ALREADY_EXIST,
                    "Can't add. Product id: " + newProductId.getProductId()
                            + " already exist.");
        } else if (newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't add. Product name: " + newProductName.getName()
                            + " already exist.");
        } else if (imageFile == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_IMAGE_NULL,
                    "Can't add. Product id: " + newProduct.getProductId());
        } else newProduct.setImage(storageService.store(imageFile, newProduct.getProductId()));
        Product productNoColor = new Product(newProduct.getProductId(),newProduct.getName(),newProduct.getImage()
                ,newProduct.getDescription(),newProduct.getKind(),newProduct.getGender(),newProduct.getType());
        productRepository.save(productNoColor);
        List<ProductHasColors> productHasColorses = newProduct.getProductHasColors();
        for (ProductHasColors newProductHasColors : productHasColorses){
            newProductHasColors.setProduct(newProduct);
            productHasColorsRepository.save(newProductHasColors);
        }
        return productRepository.save(newProduct);
    }

    @DeleteMapping("/delete/{product_id}")
    public void delete(@PathVariable Long product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if (product == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
                    "Can't delete. Product id: " + product_id + " does not exist.");
        } else storageService.delete(product.getImage());
        List<ProductHasColors> listProductHasColor = product.getProductHasColors();
        for (ProductHasColors productHasColors : listProductHasColor) {
            productHasColorsRepository.deleteById(productHasColors.getHasColorsId());
        }
        productRepository.deleteById(product_id);
    }

}
