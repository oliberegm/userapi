package com.oliber.userapi.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneRequestDTO {
    private final String number;
    private final String citycode;
    private final String contrycode;
}
