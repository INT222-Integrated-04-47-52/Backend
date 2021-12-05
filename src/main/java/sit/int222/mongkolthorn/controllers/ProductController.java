package sit.int222.mongkolthorn.controllers;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.mongkolthorn.exceptions.ApiRequestException;
import sit.int222.mongkolthorn.exceptions.ApiRequestExceptionNotFound;
import sit.int222.mongkolthorn.exceptions.ExceptionResponse;
import sit.int222.mongkolthorn.exceptions.ProductException;
import sit.int222.mongkolthorn.models.Product;
import sit.int222.mongkolthorn.models.ProductHasColors;
import sit.int222.mongkolthorn.repositories.ProductHasColorsRepository;
import sit.int222.mongkolthorn.repositories.ProductRepository;
import sit.int222.mongkolthorn.services.StorageService;

import java.util.ArrayList;
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

    @GetMapping("/admin/max-productId")
    public long maxProductId() {
        return productRepository.getMaxProductId();
    }

    @GetMapping("/admin/product/{product_id}")
    public Product showProductId(@PathVariable Long product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if (product == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
                    "Product id: " + product_id + " does not exist.");
        } else return productRepository.getOne(product_id);
    }

    @PostMapping(value = "/admin/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProduct(@RequestPart Product newProduct) {
        Product newProductId = productRepository.findById(newProduct.getProductId()).orElse(null);
        Product newProductName = productRepository.findByName(newProduct.getName());
        if (newProductId != null && newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ALREADY_EXIST,
                    "Can't add. Product id: " + newProduct.getProductId()
                            + " Product name: " + newProduct.getName()
                            + " already exist.");
        } else if (newProductId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ID_ALREADY_EXIST,
                    "Can't add. Product id: " + newProductId.getProductId()
                            + " already exist.");
        } else if (newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't add. Product name: " + newProduct.getName()
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

    @PostMapping(value = "/admin/addProduct/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProductWithImage
            (@RequestParam(value = "image", required = false) MultipartFile imageFile, @RequestPart Product newProduct) {
        Product newProductId = productRepository.findById(newProduct.getProductId()).orElse(null);
        Product newProductName = productRepository.findByName(newProduct.getName());
        if (newProductId != null && newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ALREADY_EXIST,
                    "Can't add. Product id: " + newProduct.getProductId()
                            + " Product name: " + newProduct.getName()
                            + " already exist.");
        } else if (newProductId != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_ID_ALREADY_EXIST,
                    "Can't add. Product id: " + newProduct.getProductId()
                            + " already exist.");
        } else if (newProductName != null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't add. Product name: " + newProduct.getName()
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

    @DeleteMapping("/admin/delete/{product_id}")
    public void deleteProduct(@PathVariable Long product_id) {
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

    @PutMapping(value = "/admin/editProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product editProduct(@RequestPart Product editProduct) {
        Product productId = productRepository.findById(editProduct.getProductId()).orElse(null);
        Product productName = productRepository.findByName(editProduct.getName());
        if (productId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
                    "Can't edit. Product id: " + editProduct.getProductId() + " does not exist.");
        } else if (productName != null && productId.getProductId() != productName.getProductId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't edit. Product name: " + editProduct.getName() + " already exist.");
        }
        List<ProductHasColors> beforeEditProduct = productId.getProductHasColors();
        for (ProductHasColors productHasColors : beforeEditProduct) {
            productHasColorsRepository.deleteById(productHasColors.getHasColorsId());
        }
        List<ProductHasColors> productHasColors = editProduct.getProductHasColors();
        for (ProductHasColors newProductHasColors : productHasColors) {
            newProductHasColors.setProduct(editProduct);
            productHasColorsRepository.save(newProductHasColors);
        }
        return productRepository.save(editProduct);
    }

    @PutMapping(value = "/admin/editProduct/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product editProductWithImage(@RequestParam(value = "image", required = false) MultipartFile imageFile, @RequestPart Product editProduct) {
        Product productId = productRepository.findById(editProduct.getProductId()).orElse(null);
        Product productName = productRepository.findByName(editProduct.getName());
        if (productId == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_DOES_NOT_EXIST,
                    "Can't edit. Product id: " + editProduct.getProductId() + " does not exist.");
        } else if (productName != null && editProduct.getProductId() != productName.getProductId()) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_NAME_ALREADY_EXIST,
                    "Can't edit. Product name: " + editProduct.getName() + " already exist.");
        } else if (imageFile == null) {
            throw new ProductException(ExceptionResponse.ERROR_CODE.PRODUCT_IMAGE_NULL,
                    "Can't add. Product id: " + editProduct.getProductId() + " is no image.");
        }
        storageService.delete(productId.getImage());
        editProduct.setImage(storageService.store(imageFile, editProduct.getProductId()));
        List<ProductHasColors> beforeEditProduct = productId.getProductHasColors();
        for (ProductHasColors productHasColors : beforeEditProduct) {
            productHasColorsRepository.deleteById(productHasColors.getHasColorsId());
        }
        Product productNoColor = new Product(editProduct.getProductId(),editProduct.getName(),editProduct.getImage()
                ,editProduct.getDescription(),editProduct.getKind(),editProduct.getGender(),editProduct.getType());
        productRepository.save(productNoColor);
        List<ProductHasColors> productHasColors = editProduct.getProductHasColors();
        for (ProductHasColors newProductHasColors : productHasColors) {
            newProductHasColors.setProduct(editProduct);
            productHasColorsRepository.save(newProductHasColors);
        }
        return productRepository.save(editProduct);
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@Param("keyword") String keyword) {
        if(keyword == null || keyword.isEmpty()){
            throw new ApiRequestException("Keyword search is null");
        } else if(productRepository.findAllByKeyword(keyword).isEmpty()){
            throw new ApiRequestExceptionNotFound(keyword + " not found");
        }
        return productRepository.findAllByKeyword(keyword);
    }

    @GetMapping("/filter/kind")
    public List<Product> filterProductByKind(@Param("kind") String kind) {
        if(kind == null || kind.isEmpty()) {
            throw new ApiRequestException("Product kind name is null");
        } else if(productRepository.findAllByProductKind(kind).isEmpty()){
            throw new ApiRequestExceptionNotFound("Product kind: " + kind + " not found");
        }
        return productRepository.findAllByProductKind(kind);
    }

    @GetMapping("/filter/type")
    public List<Product> filterProductByType(@Param("type") String type) {
        if(type == null || type.isEmpty()) {
            throw new ApiRequestException("Product type name is null");
        } else if(productRepository.findAllByProductType(type).isEmpty()){
            throw new ApiRequestExceptionNotFound("Product type: " + type + " not found");
        }
        return productRepository.findAllByProductType(type);
    }

    @GetMapping("/filter/gender")
    public List<Product> filterProductByGender(@Param("gender") String gender) {
        if(gender == null || gender.isEmpty()) {
            throw new ApiRequestException("Product gender name is null");
        } else if(productRepository.findAllByProductGender(gender).isEmpty()){
            throw new ApiRequestExceptionNotFound("Product gender: " + gender + " not found");
        }
        return productRepository.findAllByProductGender(gender);
    }
}
