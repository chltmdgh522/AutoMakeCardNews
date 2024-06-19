package amcn.amcn.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
public class GifController {

    @GetMapping("/generateMovingGif")
    public ResponseEntity<byte[]> generateMovingGif() {
        try {
            // 1. 이미지 로드 (로컬 파일에서 로드)
            File imageFile = new File("src/main/resources/static/ai/89c1f997-fbfb-42ee-820c-c613719f948b.png");
            BufferedImage originalImage = ImageIO.read(imageFile);

            // 2. 변형된 이미지들 생성
            List<BufferedImage> frames = createTransformedFrames(originalImage);

            // 3. GIF 생성
            ByteArrayOutputStream gifOutputStream = new ByteArrayOutputStream();
            AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
            gifEncoder.start(gifOutputStream);
            gifEncoder.setDelay(100); // 각 프레임의 지연 시간 (0.1초)

            for (BufferedImage frame : frames) {
                gifEncoder.addFrame(frame);
            }

            gifEncoder.finish();

            // 4. Spring에서 GIF 반환
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/gif");
            return new ResponseEntity<>(gifOutputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<BufferedImage> createTransformedFrames(BufferedImage originalImage) {
        List<BufferedImage> frames = new ArrayList<>();

        // 회전 변형
        for (int i = -10; i <= 10; i += 2) {
            frames.add(rotateImage(originalImage, i));
        }

        // 확대/축소 변형
        for (double scale = 0.9; scale <= 1.1; scale += 0.05) {
            frames.add(scaleImage(originalImage, scale));
        }

        // 이동 변형 (좌우로)
        for (int x = -5; x <= 5; x += 2) {
            frames.add(translateImage(originalImage, x, 0));
        }

        return frames;
    }

    // 이미지 회전 메서드
    private BufferedImage rotateImage(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = rotated.createGraphics();
        g2d.rotate(Math.toRadians(angle), w / 2, h / 2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotated;
    }

    // 이미지 확대/축소 메서드
    private BufferedImage scaleImage(BufferedImage img, double scale) {
        int w = (int) (img.getWidth() * scale);
        int h = (int) (img.getHeight() * scale);
        BufferedImage scaled = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = scaled.createGraphics();
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return scaled;
    }

    // 이미지 이동 메서드
    private BufferedImage translateImage(BufferedImage img, int x, int y) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage translated = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = translated.createGraphics();
        g2d.drawImage(img, x, y, null);
        g2d.dispose();
        return translated;
    }
}
