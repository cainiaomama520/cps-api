package com.mex.pdd.base.common.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;


/**
 *
 */
public final class DecimalUtil {

    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private static final int SCALE = 6;
    private static final double DOUBLE_ZERO = 0D;

    private DecimalUtil() {
    }

    public static String round(BigDecimal val) {
        return val == null ? "0.0" : val.setScale(2, RoundingMode.FLOOR).toString();
    }

    public static BigDecimal round2(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val.setScale(2, RoundingMode.FLOOR);
    }

    public static BigDecimal round4(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val.setScale(4, RoundingMode.FLOOR);
    }

    public static BigDecimal wrapNullToZero(BigDecimal val) {
        return Optional.ofNullable(val).orElse(BigDecimal.ZERO);
    }

    /**
     * 格式化
     *
     * @param number 需要格式化的数据
     * @return
     */
    public static String formatToPercent(Number number) {
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(number);
    }

    public static String format(Number number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }

    public static Double format(Double number) {
        if (Objects.isNull(number)) return 0D;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Double.valueOf(decimalFormat.format(number));
    }

    public static Double format4(Double number) {
        if (Objects.isNull(number)) return 0D;
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        return Double.valueOf(decimalFormat.format(number));
    }

    public static String format4(Number number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        return decimalFormat.format(number);
    }

    /**
     * 加法, num1 + num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double add(double num1, double num2) {
        return BigDecimal.valueOf(num1).add(BigDecimal.valueOf(num2)).doubleValue();
    }

    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    /**
     * 累加
     *
     * @param decimals
     * @return
     */
    public static BigDecimal add(BigDecimal... decimals) {
        if (Objects.isNull(decimals) || decimals.length == 0)
            return null;
        BigDecimal temp = new BigDecimal(0);
        for (BigDecimal decimal : decimals) {
            temp = temp.add(decimal);
        }
        return temp;
    }

    /**
     * 减法, num1 - num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double subtract(double num1, double num2) {
        return BigDecimal.valueOf(num1).subtract(BigDecimal.valueOf(num2)).doubleValue();
    }

    /**
     * 乘法, num1 * num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double multiply(double num1, double num2) {
        return BigDecimal.valueOf(num1).multiply(BigDecimal.valueOf(num2)).doubleValue();
    }

    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2);
    }

    /**
     * 乘法, num1 * num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal multiply2(double num1, double num2) {
        return BigDecimal.valueOf(num1).multiply(BigDecimal.valueOf(num2));
    }

    /**
     * 除法, num1 / num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double divide(double num1, double num2) {
        if (num2 <= DOUBLE_ZERO) {
            return DOUBLE_ZERO;
        }
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2), SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal divide(BigDecimal n1, BigDecimal n2) {
        if (eq(BigDecimal.ZERO, n1) || eq(BigDecimal.ZERO, n2)) return BigDecimal.ZERO;
        return n1.divide(n2, SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(long num1, long num2) {
        if (num2 <= 0 || num1 <= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2), SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(int num1, int num2) {
        if (num2 <= 0 || num1 <= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2), SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法, num1 / num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal divide2(double num1, double num2) {
        if (num2 <= DOUBLE_ZERO) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2), SCALE, BigDecimal.ROUND_HALF_UP);
    }


    public static boolean isNullOrZero(final BigDecimal value) {
        return value == null || value.signum() == 0;
    }

    public static BigDecimal nullToZero(final BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }


    public static BigDecimal toDecical(String val) {
        if (StringUtils.isNotBlank(val) || StringUtils.isNumeric(val)) {
            return new BigDecimal(val);
        } else {
            return null;
        }
    }

    public static boolean lt(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) < 0;
    }

    public static boolean eq(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) == 0;
    }

    public static boolean gt(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) > 0;
    }

    public static boolean ge(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) >= 0;
    }

    public static boolean ne(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) != 0;
    }

    public static boolean le(final BigDecimal d1, final BigDecimal d2) {
        return d1.compareTo(d2) <= 0;
    }


}
