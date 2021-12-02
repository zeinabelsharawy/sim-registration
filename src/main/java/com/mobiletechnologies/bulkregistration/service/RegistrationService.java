package com.mobiletechnologies.bulkregistration.service;

import com.mobiletechnologies.bulkregistration.entity.Registration;
import com.mobiletechnologies.bulkregistration.enums.GenderEnum;
import com.mobiletechnologies.bulkregistration.exceptions.EmptyFileException;
import com.mobiletechnologies.bulkregistration.exceptions.FileNotFoundException;
import com.mobiletechnologies.bulkregistration.helper.Constants;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    public boolean register(MultipartFile file) {

        List<Registration> registrations = readRegistrations(file);

        List<Registration> duplicateRegistrations = getDuplicateRegistration(registrations);

        registrations.forEach(reg -> {
            if (!duplicateRegistrations.contains(reg)) {
                validateRegistration(reg);
                if (reg.isValid() && !isRegistrationExistBefore(reg)) {
                    writeRegistrationToFile(reg);
                    sendMsg(reg);
                }
            }
            writeRegistrationToConsole(reg);
        });
        return false;
    }

    private List<Registration> readRegistrations(MultipartFile file) {
        List<Registration> registrations = null;
        if (file.isEmpty()) {
            throw new FileNotFoundException("The file is empty!");
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<Registration> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Registration.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                registrations = csvToBean.parse();
                if (registrations == null || registrations.isEmpty()) {
                    System.out.println("The file is empty!");
                    throw new EmptyFileException("The file is empty!");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new FileNotFoundException(e.getMessage());
            }
        }
        return registrations;
    }

    private void validateRegistration(Registration registration) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Registration>> violations = validator.validate(registration);
        if (!violations.isEmpty()) {
            String errorMsg = "";
            for (ConstraintViolation<?> error : violations) {
                errorMsg += "(" + error.getInvalidValue() + ")" + error.getMessage() + ",";
            }
            registration.setValid(false);
            registration.setErrorMessage(errorMsg);

        }
    }

    private void writeRegistrationToConsole(Registration registration) {
        if (registration.isValid()) {
            System.out.println("[MSISDN: " + registration.getMsisdn() + "]" + " has been inserted successfully");
        } else {
            System.out.println("[MSISDN: " + registration.getMsisdn() + "]" + " has not been inserted because " + registration.getErrorMessage());
        }
    }

    private List<Registration> getDuplicateRegistration(List<Registration> registrations) {
        Set<Registration> items = new HashSet<>();
        return registrations.stream()
                .filter(n -> !items.add(n))
                .peek(r -> r.setValid(false))
                .peek(r -> r.setErrorMessage(r.getMsisdn() + " MSISDN can't be duplicated"))
                .collect(Collectors.toList());

    }

    private void writeRegistrationToFile(Registration registration) {
        try {
            File directory = new File(Constants.SIM_REGISTRATION_Folder);
            if (!directory.exists()) {
                directory.mkdir();
            }

            Files.write(Paths.get(Constants.SIM_REGISTRATION_Folder + registration.getMsisdn() + ".txt"),
                    registration.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Exception while writing file '" +
                    Constants.SIM_REGISTRATION_Folder + registration.getMsisdn() + ".txt" + "'," +
                    " Error Message:" + e.getMessage());
            throw new FileNotFoundException("Exception while writing file '" +
                    Constants.SIM_REGISTRATION_Folder + registration.getMsisdn() + ".txt" + "'," +
                    " Error Message:" + e.getMessage());
        }
    }

    private void sendMsg(Registration registration) {
        String title = "Mr.";
        if (registration.getGender().equals(GenderEnum.F)) {
            title = "Ms.";
        }
        System.out.println(title + registration.getName() + " Thanks for your registration");
    }

    private boolean isRegistrationExistBefore(Registration registration) {
        return new File((Constants.SIM_REGISTRATION_Folder + registration.getMsisdn() + ".txt")).exists();
    }
}
