package com.shopping.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
  public static double roundNumber(final double number) {
    return BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }
}
