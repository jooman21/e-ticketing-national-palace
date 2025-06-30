package com.example.e_ticketing.sys.common.exception.user;

import com.palace.museum.common.exception.base.BaseException;

public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
