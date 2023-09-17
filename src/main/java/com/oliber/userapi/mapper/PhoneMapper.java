package com.oliber.userapi.mapper;

import com.oliber.userapi.dto.request.PhoneRequestDTO;
import com.oliber.userapi.model.Phone;

public class PhoneMapper {

    private PhoneMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Phone toEntity(PhoneRequestDTO phone) {
        return new Phone(phone.getNumber(), phone.getCitycode(), phone.getContrycode());
    }
}
