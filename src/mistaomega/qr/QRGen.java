package mistaomega.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Generates a QR code and writes to path
 */
public class QRGen {
    /**
     * Generate as file
     * @param inputData QR code data
     * @param outputPath file output path
     * @param charset what character set, UTF-8 is used here
     * @param height image height
     * @param width image width
     * @throws WriterException fail to write QR code
     * @throws IOException fail to output as file
     */
    public static void GenerateQRAsFile(String inputData, String outputPath, String charset,
                                  int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(inputData.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height); //Represents a 2D matrix of bits
        MatrixToImageWriter.writeToPath(matrix, outputPath.substring(outputPath.lastIndexOf('.') + 1), Paths.get(outputPath)); // output
    }

    /**
     * Generate QR code as buffered image
     * @param inputData QR code data
     * @param charset what character set, UTF-8 is used here
     * @param height Image height
     * @param width Image width
     * @return BufferedImage form of QR code
     * @throws WriterException fail to write QR code
     * @throws IOException fail to form BufferedImage from data
     */
    public static BufferedImage GenerateQRAsBufferedImage(String inputData, String charset,
                                                          int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(inputData.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height); //Represents a 2D matrix of bits
        return MatrixToImageWriter.toBufferedImage(matrix); // output
    }


}
