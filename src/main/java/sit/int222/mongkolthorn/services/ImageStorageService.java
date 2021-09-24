package sit.int222.mongkolthorn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.mongkolthorn.exceptions.StorageException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements StorageService{

    final Path rootLocation;

    @Autowired
    public ImageStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage location ", exception);
        }
    }

    @Override
    public String store(MultipartFile image, Long productId) {
        String imagename = "thai silk (" + productId.toString()+ ").jpg";
        try {
            if (image.isEmpty()) {
                throw new StorageException("Failed to store empty file " + imagename);
            }
            if (imagename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + imagename);
            }
            Files.copy(image.getInputStream(), this.rootLocation.resolve(imagename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + imagename, e);
        }
        return imagename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String imageName) {
        return rootLocation.resolve(imageName);
    }

    @Override
    public Resource loadAsResource(String imageName) {
        try {
            Path file = rootLocation.resolve(imageName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(String imageName) {
        rootLocation.resolve(imageName).toFile().delete();
    }

}
