package com.esimed.quizz.resemble;

import com.esimed.quizz.resemble.analysis.ResembleAnalysis;
import com.esimed.quizz.resemble.analysis.ResembleAnalysisOptions;
import com.esimed.quizz.resemble.analysis.ResembleAnalysisResults;
import com.esimed.quizz.resemble.analysis.ResembleAnaylsisOptionsTemplates;
import com.esimed.quizz.resemble.parser.ResembleParser;
import com.esimed.quizz.resemble.parser.ResembleParserData;
import com.esimed.quizz.resemble.utils.ImageUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("test")
public class TestController {
    @PostMapping("1")
    public String test1() {
        // Initially assigning null
        BufferedImage imgA = null;
        BufferedImage imgB = null;
        // Try block to check for exception
        try {
            // Reading file from local directory by
            // creating object of File class

            URL url = new URL("https://newyorkmonamour.fr/wp-content/uploads/2018/08/central-park-wollman-rink.jpg");
            BufferedImage img = ImageIO.read(url);
            File fileA = new File("downloaded.jpg");
            ImageIO.write(img, "jpg", fileA);

            URL urlB = new URL("https://www.okvoyage.com/wp-content/uploads/2019/10/patinoire-central-park-1024x768.jpg");
            BufferedImage imgC = ImageIO.read(urlB);
            File fileB = new File("downloaded2.jpg");
            ImageIO.write(imgC, "jpg", fileB);

            // Reading files
            imgA = ImageIO.read(fileA);
            imgB = ImageIO.read(fileB);
        }
        // Catch block to check for exceptions
        catch (IOException e) {
            // Display the exceptions on console
            System.out.println(e);
        }
        // Assigning dimensions to image
        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();
        // Checking whether the images are of same size or
        // not
        if ((width1 != width2) || (height1 != height2))
            // Display message straightaway
            System.out.println("Error: Images dimensions"
                    + " mismatch");
        else {
            // By now, images are of same size
            long difference = 0;
            // treating images likely 2D matrix
            // Outer loop for rows(height)
            for (int y = 0; y < height1; y++) {
                // Inner loop for columns(width)
                for (int x = 0; x < width1; x++) {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA)&0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB)&0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
            // Total number of red pixels = width * height
            // Total number of blue pixels = width * height
            // Total number of green pixels = width * height
            // So total number of pixels = width * height *
            // 3
            double total_pixels = width1 * height1 * 3;
            // Normalizing the value of different pixels
            // for accuracy
            // Note: Average pixels per color component
            double avg_different_pixels
                    = difference / total_pixels;
            // There are 255 values of pixels in total
            double percentage
                    = (avg_different_pixels / 255) * 100;
            // Lastly print the difference percentage

            return "Difference de : " + percentage + " %";
        }
        return "error";
    }

    @PostMapping("2")
    public String test2() throws IOException {
        File imgFile1 = new File("1.png");
        File imgFile2 = new File("3.png");

        BufferedImage img1 = ImageUtils.readImage(imgFile1);
        BufferedImage img2 = ImageUtils.readImage(imgFile2);

        ResembleAnalysisOptions options = ResembleAnaylsisOptionsTemplates.ignoringAntialiasing();

        ResembleAnalysisResults results = new ResembleAnalysis(options).analyseImages(img1, img2);

        ResembleParserData dataResult = ResembleParser.parse(results.getOutputImage());

        return String.format("File 1: '%s'\n" +
                        "File 2: '%s'\n\n" +
                        "Info: %s\n\n" +
                        "Output image:\n%s\n\n" +
                        "Options:\n%s\n\n" +
                        "Mismatch percentage: %.2f %%\n" +
                        "Analysis time: %d miliseconds\n" +
                        "Difference bounds: %s",
                imgFile1, imgFile2, dataResult, results.getOutputImage(), options,
                results.getMismatchPercentage(), results.getAnalysisTime().toMillis(),
                results.getDiffBounds());
    }
}
