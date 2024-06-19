package amcn.amcn.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${ai.dir}")
    private String fileDir;

    public String saveImageFromUrl(String imageUrl) throws IOException {
        if (StringUtils.isEmpty(imageUrl)) {
            throw new IllegalArgumentException("해당 이미지 URL이 없습니다.");
        }

        URL url = new URL(imageUrl);
        String contentType = url.openConnection().getContentType();
        String fileExtension = getFileExtensionFromContentType(contentType);
        if (fileExtension.isEmpty()) {
            throw new IllegalArgumentException("이미지 포멧이 안맞습니다.");
        }

        String uuid = UUID.randomUUID().toString() + "." + fileExtension;
        Path destinationPath = Paths.get(fileDir, uuid);
        log.info("서비스:" + uuid);

        try (InputStream in = url.openStream()) {
            BufferedImage image = ImageIO.read(in);
            ImageIO.write(image, fileExtension, destinationPath.toFile());
        }

        return destinationPath.toString();
    }

    private String getFileExtensionFromContentType(String contentType) {
        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg":
                    return "jpg";
                case "image/png":
                    return "png";
                // 다른 이미지 형식에 대한 처리 추가
                default:
                    log.info("왔ㄴ구나");
                    return "png";
            }
        }
        return "png";
    }
}
