package com.example.java_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

/**
 * @author tuhalang
 * @created on 12/18/19
 */

@RestController
public class FileUploadController {

    private static String directory = System.getProperty("user.dir");
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(
            @RequestParam("many-files") MultipartFile[] files) {
        Long start = 0l;
        Long end = 0l;
        try {
            start = (new Date()).getTime();
            Arrays.asList(files)
                    .stream()
                    .forEach(file -> {
                        try {
                            String filename = (new Date()).getTime() +'-'+ file.getOriginalFilename();
                            String filepath = Paths.get(directory, filename).toString();

                            // Save the file locally
                            BufferedOutputStream stream = null;
                            stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
                            stream.write(file.getBytes());
                            stream.close();
                        }catch (Exception e){
                        }
                    });
            end = (new Date()).getTime();
            LOGGER.error("time execute: " + (end - start));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Files are uploaded. Time execute: " + ((new Date()).getTime() - start), HttpStatus.OK);
    }
}
