package com.example.hexagon.albummgt.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class ApplicationQRCodeService {
  public BufferedImage generateQRCodeImage(String text, int width, int height, String filePath)
      throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
    return MatrixToImageWriter.toBufferedImage(bitMatrix);
//    Path path = FileSystems.getDefault().getPath(filePath);
//    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
  }

  @Bean
  HttpMessageConverter<BufferedImage> httpMessageConverter(){
    return new BufferedImageHttpMessageConverter();
  }
}
