package com.bkbwongo.common.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bkaaron
 * @created on 26/06/2021
 * @project ebaasa-sms
 */
public class SetUtils {

    public static final String GRANTS_SEPARATOR = ",";

    public static Set getSetFromStringWithSeparator(String stringForSet){
        return Arrays.asList(stringForSet.trim().split(GRANTS_SEPARATOR))
                .stream().collect(Collectors.toSet());
    }
}
