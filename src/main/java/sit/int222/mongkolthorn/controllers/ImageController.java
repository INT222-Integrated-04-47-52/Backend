package sit.int222.mongkolthorn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import sit.int222.mongkolthorn.models.ImageDetail;
import sit.int222.mongkolthorn.services.StorageService;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class ImageController {
    final StorageService storageService;

    @Autowired
    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/admin/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        String message;
        Random rand = new Random();
        Long randomName;
        do  {
            randomName = rand.nextLong();
        } while (randomName < 0);
        try {
            storageService.store(image, randomName);
            message = "Upload image: " + image.getOriginalFilename() + " successfully.";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Upload image: " + image.getOriginalFilename() + " failed.";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/admin/images")
    public List<ImageDetail> getListImages() {
        return storageService.loadAll().map(path -> {
            String imageName = path.getFileName().toString();
            String imageUrl = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class,
                            "showImage", path.getFileName().toString()).build().toString();
            return new ImageDetail(imageName, imageUrl);
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/admin/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public Resource showImage(@PathVariable String imageName) {
        //add exception
        return storageService.loadAsResource(imageName);
    }
}
