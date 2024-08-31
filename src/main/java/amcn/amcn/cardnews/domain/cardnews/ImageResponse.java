package amcn.amcn.cardnews.domain.cardnews;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageResponse {
    private String imagePath;
    private String message;
    private List<String> text;

    // Constructors
    public ImageResponse() {}

    public ImageResponse(String imagePath, String message, List<String> text) {
        this.imagePath = imagePath;
        this.message = message;
        this.text = text;
    }
}
