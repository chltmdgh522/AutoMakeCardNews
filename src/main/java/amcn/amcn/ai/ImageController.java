package amcn.amcn.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;

@Controller
@Slf4j
public class ImageController {

    @GetMapping("/saveImage")
    public String getImage() {
        return "test";
    }

    @PostMapping("/saveImage")
    @ResponseBody
    public String saveImage(@RequestParam("imageData") String imageData) {
        log.info(imageData);
        try {
            // Base64 문자열에서 데이터 부분만 추출
            String base64Image = imageData.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // 바이트 배열을 BufferedImage로 변환
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

            // 변환된 이미지가 null인지 확인
            if (img == null) {
                return "Failed to decode image.";
            }

            // 파일로 저장
            File outputfile = new File("saveee.png");
            ImageIO.write(img, "png", outputfile);

            return "Image saved successfully at " + outputfile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save image: " + e.getMessage();
        }
    }
}
