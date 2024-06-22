package amcn.amcn.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Slf4j
public class ProxyController {

    @CrossOrigin(origins = "*")
    @GetMapping("/proxy")
    public ResponseEntity<byte[]> proxy(@RequestParam String url) {
        try {
            // URL 디코딩 (필요할 경우)
            String decodedUrl = java.net.URLDecoder.decode(url, "UTF-8");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(new URI(decodedUrl), byte[].class);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(response.getHeaders().getContentType());

            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // 예외를 로깅합니다
            log.info("Error during proxy request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
