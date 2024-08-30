package com.example.hexagon.albummgt.qrcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/qrcode")
public class QRCodeController {

  private final ApplicationQRCodeService qrCodeService;
  private ObjectMapper mapper = new ObjectMapper();

  public QRCodeController(ApplicationQRCodeService qrCodeService) {
    this.qrCodeService = qrCodeService;
  }

  @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
  public BufferedImage genQRCode() throws Exception {
    var text = UUID.randomUUID().toString() + " " + LocalDateTime.now().toString();
    return qrCodeService.generateQRCodeImage(text, 350, 350, null);
  }

  @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
  public BufferedImage genQRCodeWithData(@RequestBody Object request) throws Exception {
    return qrCodeService.generateQRCodeImage(mapper.writeValueAsString(request), 350, 350, null);
  }
}
