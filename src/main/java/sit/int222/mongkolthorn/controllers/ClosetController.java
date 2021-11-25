package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int222.mongkolthorn.models.Closet;
import sit.int222.mongkolthorn.repositories.ClosetRepository;

import java.util.List;

@RestController
public class ClosetController {
    @Autowired
    private ClosetRepository closetRepository;

    @GetMapping("/admin/allClosets")
    public List<Closet> closet(){
        return closetRepository.findAll();
    }

    @GetMapping("/max-closetId")
    public Long maxClosetId(){
        return closetRepository.getMaxClosetId();
    }


}
