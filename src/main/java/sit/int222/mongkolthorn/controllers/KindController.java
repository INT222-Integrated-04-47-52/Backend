package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Kind;
import sit.int222.mongkolthorn.repositories.KindRepository;

import java.util.List;

@RestController
public class KindController {
    @Autowired
    private KindRepository kindRepository;

    @GetMapping("/admin/allKinds")
    public List<Kind> kind() {
        return kindRepository.findAll();
    }
}
