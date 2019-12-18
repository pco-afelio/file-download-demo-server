package be.afelio.pco.filedownloaddemo;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Log
public class FileDownloadController {

    @Value("classpath:test-file.pdf")
    private Resource resource;

    @GetMapping("download")
    public ResponseEntity<byte[]> download() throws Exception {
        log.info("sending file test-file.pdf");
        final String filename = "test-file.pdf";
        final long fileLength = resource.contentLength();
        final byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(fileLength);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
