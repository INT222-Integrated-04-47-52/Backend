package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Size;
import sit.int222.mongkolthorn.repositories.SizeRepository;

import java.util.List;

@RestController
public class SizeController {
    @Autowired
    private SizeRepository sizeRepository;

    @GetMapping("/admin/allSizes")
    public List<Size> size() {
        return sizeRepository.findAll();
    }
}
