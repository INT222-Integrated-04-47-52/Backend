package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.ProductHasColors;
import sit.int222.mongkolthorn.repositories.ProductHasColorsRepository;

import java.util.List;

@RestController
public class ProductHasColorsController {
    @Autowired
    private ProductHasColorsRepository productHasColorsRepository;

    @GetMapping("/admin/allProductHasColors")
    public List<ProductHasColors> productHasColorsRepository(){
        return productHasColorsRepository.findAll();
    }

    @GetMapping("/admin/max-productHasColorsId")
    public long maxIcecreamId() {
        return productHasColorsRepository.getMaxProductHasColorsId();
    }
}
