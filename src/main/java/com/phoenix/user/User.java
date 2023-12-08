package com.phoenix.user;

import java.math.BigInteger;

public record User(BigInteger userID, BigInteger spending, BigInteger earning, int reputation) {
}
