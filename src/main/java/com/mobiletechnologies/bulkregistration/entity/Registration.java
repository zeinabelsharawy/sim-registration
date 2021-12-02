package com.mobiletechnologies.bulkregistration.entity;

import com.mobiletechnologies.bulkregistration.enums.GenderEnum;
import com.mobiletechnologies.bulkregistration.enums.SIMTypeEnum;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import validator.Enum;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Registration {
//    MSISDN, SIM_TYPE, NAME, DATE_OF_BIRTH, GENDER, ADDRESS, ID_NUMBER

    @CsvBindByName(column = "MSISDN")
    @NotNull(message = "MSISDN can't be null")
    @NotBlank(message = "MSISDN can't be empty")
    @Pattern(regexp = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$", message = "MSISDN should comply to country's standard")
    private String msisdn;


    @CsvBindByName(column = "SIM_TYPE")
    @NotNull(message = "SIM TYPE can't be null")
    @NotBlank(message = "SIM_TYPE can't be empty")
    @Enum(enumClass = SIMTypeEnum.class, message = "SIM Type must be PREPAID or POSTPAIDF")
    private String simType;

    @CsvBindByName(column = "NAME")
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "NAME can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name shouldn't have any special character")
    private String name;

    @CsvBindByName(column = "DATE_OF_BIRTH")
    @CsvDate(value = "yyyy-MM-dd")
    @NotNull(message = "DATE_OF_BIRTH can't be null")
//    @NotBlank(message = "DATE_OF_BIRTH can't be empty")
    @Past(message = "DATE_OF_BIRTH shouldn't be TODAY or FUTURE")
    private Date dateOfBirth;

    @CsvBindByName(column = "GENDER")
    @NotNull(message = "Gender can't be null")
    @NotBlank(message = "GENDER can't be empty")
    @Enum(enumClass = GenderEnum.class, message = "Gender must be M or F")
    private String gender;

    @CsvBindByName(column = "ADDRESS")
    @NotNull(message = "ADDRESS can't be null")
    @NotBlank(message = "ADDRESS can't be empty")
    @Size(min = 20, message = "Address must at least be 20 characters long")
    private String address;

    @CsvBindByName(column = "ID_NUMBER")
    @NotNull(message = "ID_NUMBER can't be null")
    @NotBlank(message = "ID_NUMBER can't be empty")
    @Pattern(regexp = "(([A-Za-z].*[0-9])|([0-9].*[A-Za-z]))", message = "ID_NUMBER should be a mix of characters & numbers")
    private String id;

    private boolean valid = true;


    private String errorMessage;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Registration other = (Registration) obj;
        return Objects.equals(id, other.id);
    }
}
