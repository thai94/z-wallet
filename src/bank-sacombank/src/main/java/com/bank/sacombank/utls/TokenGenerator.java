package com.bank.sacombank.utls;

import java.security.SecureRandom;

public class TokenGenerator {
    protected static final SecureRandom random = new SecureRandom();

    public static synchronized String generateToken( String phone ) {
        long longToken = Math.abs( random.nextLong() );
        String random = Long.toString( longToken, 16 );
        return ( phone + ":" + random );
    }
}
