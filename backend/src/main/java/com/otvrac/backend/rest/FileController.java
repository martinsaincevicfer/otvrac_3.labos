package com.otvrac.backend.rest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String fileName) {
        try {
            String fileStoragePath = "./src/main/resources/static";
            Path filePath = Paths.get(fileStoragePath, fileName).normalize();
            File file = filePath.toFile();

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            FileSystemResource resource = new FileSystemResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/openapi")
    public ResponseEntity<FileSystemResource> serveOpenApiSpec() {
        try {
            String fileStoragePath = "./src/main/resources/static";
            Path filePath = Paths.get(fileStoragePath, "openapi.json").normalize();
            File file = filePath.toFile();

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            FileSystemResource resource = new FileSystemResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}

