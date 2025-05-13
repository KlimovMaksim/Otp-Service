package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final String FILE_PATH = "src/main/resources/text/otp.txt";

    @Override
    public void writeToFile(String text) {
        Path path = Paths.get(FILE_PATH);
        try {
            log.debug("Attempting to write to file: {}", FILE_PATH);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.write(path, text.getBytes(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE);
            log.info("Successfully wrote to file: {}", FILE_PATH);
        } catch (IOException e) {
            log.error("Error writing to file: {}", FILE_PATH, e);
        }
    }
}
