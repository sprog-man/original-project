package com.waimai.skycommon.exception;


/**
 * 账号锁定异常
 */

public class AccountLockedException extends BaseException {
    public AccountLockedException() {
    }
    public AccountLockedException(String msg) {
        super(msg);
    }
}
