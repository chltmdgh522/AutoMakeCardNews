package amcn.amcn.file;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileStore2 {
    @Value("${ai.dir}")
    private String fileDir;

    @Value("${json.dir}")
    private String jsonDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }


    public String getFullPath2(String fileName) {
        return jsonDir + fileName;
    }

}
