package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Gender;
import sit.int222.mongkolthorn.repositories.GenderRepository;

import java.util.List;

@RestController
public class GenderController {
    @Autowired
    private GenderRepository genderRepository;

    @GetMapping("/admin/allGenders")
    public List<Gender> products(){
        return genderRepository.findAll();
    }
}
