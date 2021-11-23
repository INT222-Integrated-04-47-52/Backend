package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Colors;
import sit.int222.mongkolthorn.repositories.ColorsRepository;

import java.awt.*;
import java.util.List;

@RestController
public class ColorsController {
    @Autowired
    private ColorsRepository colorsRepository;

    @GetMapping("/admin/allColors")
    public List<Colors> colors() {
        return colorsRepository.findAll();
    }

}
