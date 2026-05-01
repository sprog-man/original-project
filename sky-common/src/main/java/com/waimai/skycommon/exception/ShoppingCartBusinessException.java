package com.waimai.skycommon.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

public class ShoppingCartBusinessException extends BaseException {
    public ShoppingCartBusinessException(String message) {
        super(message);
    }
}
