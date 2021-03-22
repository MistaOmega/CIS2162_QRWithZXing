package mistaomega.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Generates a QR code and writes to path
 */
public class QRGen {
    public static void GenerateQR(String inputData, String outputPath, String charset,
                                  int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(inputData.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height); //Represents a 2D matrix of bits
        MatrixToImageWriter.writeToPath(matrix, outputPath.substring(outputPath.lastIndexOf('.') + 1), Paths.get(outputPath)); // output
    }


}
