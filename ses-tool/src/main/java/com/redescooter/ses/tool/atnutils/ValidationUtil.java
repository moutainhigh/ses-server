package com.redescooter.ses.tool.atnutils;

import com.redescooter.ses.api.common.annotation.*;
import com.redescooter.ses.api.common.exception.ValidationException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by jerry  on 2016-6-1.
 */
public class ValidationUtil {

    public static void validation(Object obj) throws ParseException {
        if (obj == null) {
            return;
        }
        Field[] fieldArray = obj.getClass().getDeclaredFields();
        if (fieldArray == null) {
            return;
        }
        for (Field field : fieldArray) {
            validationNotNull(obj, field);
            validationNotEmpty(obj, field);
            validationRange(obj, field);
            validationRegexp(obj, field);
            validationMoreThanZero(obj, field);
            validationNotZero(obj, field);
            validationMaximumLength(obj, field);
            validationMinimumLength(obj, field);

        }
    }

    private static void validationMaximumLength(Object obj, Field field) {
        MaximumLength annotation = field.getAnnotation(MaximumLength.class);
        if (annotation == null) {
            return;
        }
        Object fieldValue = getFieldValueByName(field.getName(), obj);
        if (fieldValue instanceof String) {
            String value = (String) fieldValue;
            if (value.length() > Integer.parseInt(annotation.value())) {
                throw new ValidationException(annotation.code(), annotation.message());
            }
        }
    }

    private static void validationMinimumLength(Object obj, Field field) {
        MinimumLength annotation = field.getAnnotation(MinimumLength.class);
        if (annotation == null) {
            return;
        }
        Object fieldValue = getFieldValueByName(field.getName(), obj);
        if (fieldValue instanceof String) {
            String value = (String) fieldValue;
            if (value.length() < Integer.parseInt(annotation.value())) {
                throw new ValidationException(annotation.code(), annotation.message());
            }
        }
    }

    private static void validationNotNull(Object obj, Field field) {
        NotNull notNull = field.getAnnotation(NotNull.class);
        if (notNull == null) {
            return;
        }
        if (getFieldValueByName(field.getName(), obj) == null) {
            if (notNull.message() != null && notNull.message().length() > 0) {
                throw new ValidationException(notNull.code(), notNull.message());
            } else {
                throw new ValidationException(notNull.code(), field.getName() + " is Null");
            }
        }
    }

    private static void validationNotEmpty(Object obj, Field field) {
        NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
        if (notEmpty == null) {
            return;
        }
        Object fieldValue = getFieldValueByName(field.getName(), obj);
        if (fieldValue == null) {
            if (notEmpty.message() != null && notEmpty.message().length() > 0) {
                throw new ValidationException(notEmpty.code(), notEmpty.message());
            } else {
                throw new ValidationException(notEmpty.code(), field.getName() + " is null");
            }
        }
        if (fieldValue.getClass().isArray()) {
            Object value = getFieldValueByName(field.getName(), obj);
            if (Array.getLength(value) == 0) {
                throwNotEmptyMessageException(field, notEmpty);
            }
        } else if (fieldValue instanceof Collection) {
            Collection<?> value = (Collection<?>) getFieldValueByName(field.getName(), obj);
            if (value.size() == 0) {
                throwNotEmptyMessageException(field, notEmpty);
            }
        } else if (fieldValue instanceof Map) {
            Map<?, ?> value = (Map<?, ?>) getFieldValueByName(field.getName(), obj);
            if (value == null || value.size() == 0) {
                throwNotEmptyMessageException(field, notEmpty);
            }
        } else if (fieldValue instanceof Iterator) {
            Iterator<?> value = (Iterator<?>) getFieldValueByName(field.getName(), obj);
            if (!value.hasNext()) {
                throwNotEmptyMessageException(field, notEmpty);
            }
        } else if (fieldValue instanceof String) {
            String value = (String) getFieldValueByName(field.getName(), obj);
            if (value.length() == 0) {
                throwNotEmptyMessageException(field, notEmpty);
            }
        }
    }


    private static void validationRegexp(Object obj, Field field) {
        Regexp regexp = field.getAnnotation(Regexp.class);
        if (regexp == null) {
            return;
        }
        Object value = getFieldValueByName(field.getName(), obj);
        if (regexp.value() != null || regexp.value().length() > 0) {
            if (value instanceof String) {
                if (!((String) value).matches(regexp.value())) {
                    throw new ValidationException(regexp.code(), field.getName() + " is illegal");
                }
            } else {
                throw new ValidationException(regexp.code(), field.getName() + " is illegal");
            }
        }
    }

    private static void validationRange(Object obj, Field field) throws ParseException {
        Range range = field.getAnnotation(Range.class);
        if (range == null) {
            return;
        }
        Object valueObj = getFieldValueByName(field.getName(), obj);
        BigDecimal value = null;
        if (valueObj == null) {
            value = new BigDecimal("0");
        } else {
            value = new BigDecimal(String.valueOf(valueObj));
        }
        String minStr = range.min();
        String maxStr = range.max();
        boolean minIsNotEmpty = false;
        boolean maxIsNotEmpty = false;
        BigDecimal min = null;
        BigDecimal max = null;
        if (minStr != null && minStr.length() > 0) {
            min = new BigDecimal(minStr);
            minIsNotEmpty = true;
        }
        if (maxStr != null && maxStr.length() > 0) {
            max = new BigDecimal(maxStr);
            maxIsNotEmpty = true;
        }
        if (minIsNotEmpty && maxIsNotEmpty) {
            if (value.compareTo(min) < 0 || value.compareTo(max) >= 0) {
                if (range.message() != null && range.message().length() > 0) {
                    throw new ValidationException(range.code(), range.message());
                } else {
                    throw new ValidationException(range.code(), field.getName()
                            + " must be greater than or equal to " + min.longValue() + " and less than " + max.longValue());
                }
            }
        } else if (minIsNotEmpty) {
            if (value.compareTo(min) < 0) {
                if (range.message() != null && range.message().length() > 0) {
                    throw new ValidationException(range.code(), range.message());
                } else {
                    throw new ValidationException(range.code(), field.getName()
                            + " must be greater than or equal to " + min.longValue());
                }
            }
        } else if (maxIsNotEmpty) {
            if (value.compareTo(max) >= 0) {
                if (range.message() != null && range.message().length() > 0) {
                    throw new ValidationException(range.code(), range.message());
                } else {
                    throw new ValidationException(range.code(), field.getName() + " must be less than "
                            + max.longValue());
                }
            }
        }

    }

    private static void validationNotZero(Object obj, Field field) throws ParseException {
        NotZero notZero = field.getAnnotation(NotZero.class);
        if (notZero == null) {
            return;
        }
        Object valueObj = getFieldValueByName(field.getName(), obj);
        BigDecimal value = null;
        if (valueObj == null) {
            value = new BigDecimal("0");
        } else {
            value = new BigDecimal(String.valueOf(valueObj));
        }
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            throw new ValidationException(notZero.code(), notZero.message());
        }

    }


    private static void validationMoreThanZero(Object obj, Field field) throws ParseException {
        MoreThanZero moreThanZero = field.getAnnotation(MoreThanZero.class);
        if (moreThanZero == null) {
            return;
        }
        Object valueObj = getFieldValueByName(field.getName(), obj);
        BigDecimal value = null;
        if (valueObj == null) {
            value = new BigDecimal("0");
        } else {
            value = new BigDecimal(String.valueOf(valueObj));
        }
        if (value.compareTo(BigDecimal.ZERO) == 0 || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(moreThanZero.code(), moreThanZero.message());
        }

    }

    private static void throwNotEmptyMessageException(Field field, NotEmpty notEmpty) {
        if (notEmpty.message() != null && notEmpty.message().length() > 0) {
            throw new ValidationException(notEmpty.code(), notEmpty.message());
        } else {
            throw new ValidationException(notEmpty.code(), field.getName() + " is empty");
        }
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    //生成随机数字和字母,
    public static String getStringRandom() {
        String val = "";
        Random random = new Random(); //length为几位密码
        for (int i = 0; i < 12; i++) {
            String charOrNum = random
                    .nextInt(2) % 2 == 0 ? "char" : "num"; //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}


