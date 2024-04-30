package amcn.amcn.file;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            log.info("사진1");
            return null;
        }
        log.info("사진2");

        String originalFilename = multipartFile.getOriginalFilename(); //image.png

        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return storeFileName;
    }

    private static String createStoreFileName(String originalFilename) {
        String ext = extractedExt(originalFilename);
        // 서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();
        //asd3f143as5d4f5.png

        String storeFileName = uuid + "." + ext;

        return storeFileName;
    }

    private static String extractedExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1); //png반환
        return ext;
    }
}
