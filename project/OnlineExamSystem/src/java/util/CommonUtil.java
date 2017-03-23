/*
 * Copyright © 2017 Six Idiots Team
 */
package util;

import java.util.function.Function;

/**
 *
 * @author Le Cao Nguyen
 */
public class CommonUtil {

    /**
     * Return a replacement value if the original value is null, otherwise
     * return the original value.
     *
     * @param <T> The type of the original value and result
     * @param value The original value
     * @param valueIfNull The replacement value
     * @return As described above
     */
    public static <T> T getNullable(T value, T valueIfNull) {
        return (value == null) ? valueIfNull : value;
    }

    /**
     * Return a replacement value if the original value is null, otherwise
     * return the conversion result of the original value, using the given
     * converter function.
     *
     * @param <T> The type of the original value
     * @param <U> The type of the result
     * @param value The original value
     * @param valueIfNull The replacement value
     * @param converter The converter function
     * @return As described above
     */
    public static <T, U> U convertNullable(T value, U valueIfNull, Function<T, U> converter) {
        return (value == null) ? valueIfNull : converter.apply(value);
    }
}
