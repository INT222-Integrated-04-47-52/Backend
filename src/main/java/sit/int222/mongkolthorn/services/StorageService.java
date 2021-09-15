package sit.int222.mongkolthorn.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    String store(MultipartFile image, Long icecreamId);

    Stream<Path> loadAll();

    Path load(String imageName);

    Resource loadAsResource(String imageName);

    void deleteAll();

    void delete(String imageName);

}
