package com.bkbwongo.common.utils;

/**
 * @author bkaaron
 * @created on 12/06/2021
 * @project ebaasa-sms
 */
public class CodeGenerator {

    private CodeGenerator() {}

    static char digits[] = {'0','1','2','3','4','5','6','7','8','9'};

    public static char randomDecimalDigit() {
        return digits[(int)Math.floor(Math.random() * 10)];
    }

    public static String numericStringCode(int length){
        var result = new StringBuilder();
        for(var i = 0; i < length; i++) {
            result.append(randomDecimalDigit());
        }
        return result.toString();
    }
}
