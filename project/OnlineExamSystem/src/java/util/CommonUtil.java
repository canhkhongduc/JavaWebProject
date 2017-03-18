/*
 * Copyright © 2017 Six Idiots Team
 */
package util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * Map the collection to a string collection using the mapping function,
     * then concatenate all the strings in the collection, of which two
     * consecutive strings are separated with comma.
     *
     * @param <T> The type of the element of the original collection
     * @param values The original collection
     * @param map The mapping function
     * @return The sequence string
     */
    public static <T> String toSequenceString(Collection<T> values, Function<T, String> map) {
        return values.stream().map(map).collect(Collectors.joining(", "));
    }
}
