package com.mobiletechnologies.bulkregistration.controller;

import com.mobiletechnologies.bulkregistration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping("/upload-csv-file")
    public void uploadCSVFile(@RequestParam("file") MultipartFile file)  {
        registrationService.register(file);
    }
}
