package com.trablock.web.service.file;

import com.trablock.web.domain.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Service
public class FileService {

    private final Path dirLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        dirLocation = Path.of(fileStorageProperties.getUploadDir());
        try {
            Files.createDirectories(dirLocation);
        } catch (IOException e) {
            log.error("디렉토리 생성에 실패했습니다... 이유는 = ", e);
        }
    }

    /**
     * @param multipartFile
     * @return /저장경로/filename
     * filename 네이밍 규칙 생성 필요 = 중복 파일명 방지, 사용자와 일치하는 사진을 가져와야 한다!!
     */
    public String saveFile(MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            filename = filename.substring(0, filename.lastIndexOf("."))
                    .replace(".", "") + "." + filename.substring(filename.lastIndexOf(".") + 1);
            Path targetLocation = dirLocation.resolve(filename);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();

        } catch (IOException e) {
            throw new RuntimeException("해당 파일을 저장에 실패했습니다. 이유는 ..." + filename, e);
        }
    }

    /**
     * @param filename
     * @return filename.png, filename.txt, etc ...
     * @throws FileNotFoundException
     */
    public Resource loadFile(String filename) throws FileNotFoundException {
        try {
            Path file = dirLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다. 파일명 : " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일이 디렉토리에 없습니다. 파일명 : " + filename, e);
        }
    }
}
