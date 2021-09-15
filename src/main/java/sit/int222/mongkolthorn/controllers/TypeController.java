package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Type;
import sit.int222.mongkolthorn.repositories.TypeRepository;

import java.util.List;

@RestController
public class TypeController {
    @Autowired
    private TypeRepository typeRepository;

    @GetMapping("/allTypes")
    public List<Type> type() {
        return typeRepository.findAll();
    }
}
