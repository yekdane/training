package service.validation;

import model.*;
import model.common.Common;
import service.exception.ValidationException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Validation {
    public static <T> String validStringField(String str) throws ValidationException {
        return validStringField(str, null, null);
    }

    public static <T> String validStringField(String str, T currentId,
                                              List<Common> items) throws ValidationException {
        Objects.requireNonNull(str);
        str = str.trim();
        if (str.length() == 0)
            throw new ValidationException("your word is not valid");
        //it just for obligation
        if (items != null) {
            for (Common item : items) {
                if (!item.getId().equals(currentId)) {
                    if (item instanceof Obligation && ((Obligation) item).getName().equals(str))
                        throw new ValidationException(
                                String.format("'%s' is repeated in record with Id '%d'", str, item.getId()));
                }
            }
        }
        return str;
    }

    public static Long validNumberField(Long num) throws ValidationException {
        Objects.requireNonNull(num);
        if (num < 0)
            throw new ValidationException("your number must be bigger than 0");
        return num;
    }

    public static Byte validAgeField(Byte age, Byte compareVal,
                                     Byte currentId, List<AgeGroup> items) throws ValidationException {
        Objects.requireNonNull(age);
        if (age < 0)
            throw new ValidationException("your age is not valid");
        if (compareVal != null && compareVal > 0 && age < compareVal)
            throw new ValidationException("your Age must be bigger than " + compareVal);
        Byte fromAge, toAge;
        for (AgeGroup item : items) {
            fromAge = item.getFromAge();
            toAge = item.getToAge();
            if (item.getId() != currentId && (age >= fromAge && age <= toAge)) {
                throw new ValidationException(
                        String.format("your Age must not be between %d and %d",
                                fromAge, toAge));
            }
        }
        return age;
    }

    public static Integer validQuantityField(Integer intField, Integer compareVal,
                                             Byte currentId, List<InsuredGroup> items) throws ValidationException {
        Objects.requireNonNull(intField);
        if (intField < 0)
            throw new ValidationException("your number is not valid");
        if (compareVal != null && compareVal > 0 && intField < compareVal)
            throw new ValidationException("your number must be bigger than " + compareVal);
        Integer fromQty, toQty;
        for (InsuredGroup item : items) {
            fromQty = item.getFromQty();
            toQty = item.getToQty();
            if (item.getId() != currentId &&
                    (intField >= fromQty && intField <= toQty))
                throw new ValidationException(
                        String.format("your number must not be between %d and %d", fromQty, toQty));
        }
        return intField;
    }

    public static String validDate(String date, String compareVal, Long currentId, List<Common> items, Boolean isBirthDate)
            throws ValidationException {
        Objects.requireNonNull(date);

        String[] dates = date.split("/");

        if (dates.length != 3)
            throw new ValidationException("date is not valid");

        int year = Integer.valueOf(dates[0]),
                month = Integer.valueOf(dates[1]),
                days = Integer.valueOf(dates[2]);

        if (year < 1300 || (month <= 0 || month > 12)
                || (days < 1 || (month > 6 && days > 30) || (month <= 6 && days > 31))
        )
            throw new ValidationException("date is not valid");

        if (isBirthDate != null && isBirthDate && service.common.Common.convertToMilady(date).after(new Date()))
            throw new ValidationException("date must not be bigger than " + service.common.Common.convertToShamsi(new Date()));

        if (compareVal != null && date.compareTo(compareVal) < 0)
            throw new ValidationException("date must not be bigger than " + compareVal);
        //check date for one policyholder in contracts
        if (items != null) {
            for (Common item : items) {
                if (item instanceof Contract) {
                    String endDate = ((Contract) item).getEndDate();
                    String startDate = ((Contract) item).getStartDate();

                    if (item.getId() != currentId && (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0)) {
                        throw new ValidationException(
                                String.format("your date must not be between %s and %s ",
                                        startDate, endDate));
                    }
                }
            }
        }
        return date;
    }

    public static Integer validType(Integer type) throws ValidationException {
        Objects.requireNonNull(type);
        if (type < 0 || type > 1)
            throw new ValidationException("type is not valid");
        return type;
    }

    public static String validNationalCode(String nationalCode, Long currentId, List<Person> items) throws ValidationException {
        validStringField(nationalCode);

        Person person = items.stream().filter(item ->
                !item.getId().equals(currentId) && item.getNationalCode().equals(nationalCode)
        ).findFirst().orElse(null);

        if (person != null)
            throw new ValidationException(
                    String.format("'%s' is repeated in record with Id '%d'", nationalCode, person.getId()));

        return nationalCode;
    }

    public static String validPostalCode(String postalCode) throws ValidationException {
        validStringField(postalCode);
        return postalCode;
    }

    public static Double validRateField(Double rate) throws ValidationException {
        Objects.requireNonNull(rate);
        if (rate < 0)
            throw new ValidationException("your rate is not valid");
        return rate;
    }

    public static Byte validPercentField(Byte percentField) throws ValidationException {
        Objects.requireNonNull(percentField);
        if (percentField < 0 || percentField > 100)
            throw new ValidationException("your number is not valid");
        return percentField;
    }
}
