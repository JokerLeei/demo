package com.demo.mybatis;

import org.apache.commons.lang3.StringUtils;


/**
 * @author: lijiawei04
 * @date: 2021/4/15 4:17 下午
 */
public class Test {

    public static void main(String[] args) {
        String name0 = "";
        String name1 = "啊";
        String name2 = "啊啊";
        String name3 = "啊啊啊";
        String name4 = "啊啊啊啊";
        String name5 = "啊啊啊啊啊啊啊啊暗呀";

        System.out.println("-----");
        System.out.println(getDisplayName(name0, 6));
        System.out.println(getDisplayName(name1, 6));
        System.out.println(getDisplayName(name2, 6));
        System.out.println(getDisplayName(name3, 6));
        System.out.println(getDisplayName(name4, 6));
        System.out.println(getDisplayName(name5, 6));
        System.out.println("-----");

    }

    /**
     * 隐藏真实姓名的[2, (n-1)]位
     * getDisplayName("ab") ==> a*
     * getDisplayName("abcdef") ==> a****f
     */
    private static String getDisplayName(String realName) {
        if (StringUtils.isBlank(realName) || realName.length() < 1) {
            return realName;
        }
        if (realName.length() == 2) {
            return realName.charAt(0) + "*";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < realName.length(); i++) {
            if (i == 0 || i == realName.length() - 1) {
                sb.append(realName.charAt(i));
                continue;
            }
            sb.append("*");
        }
        return sb.toString();
    }

    /**
     * 隐藏真实姓名，隐藏真实姓名的[2, (n-1)]位，且生成的隐藏串长度最大为maxLength
     * getDisplayName("abcd", 6) ==> a**d
     * getDisplayName("abcdefghijk", 6) ==> a****k
     */
    private static String getDisplayName(String realName, Integer maxLength) {
        if (StringUtils.isBlank(realName)) {
            return realName;
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength is negative, illegal value!");
        }
        if (realName.length() == 2) {
            return getDisplayName(realName);
        }
        if (realName.length() > maxLength) {
            StringBuilder sb = new StringBuilder(String.valueOf(realName.charAt(0)));
            for (int i = 0; i < maxLength - 2; i++) {
                sb.append("*");
            }
            sb.append(realName.charAt(realName.length() - 1));
            return sb.toString();
        }

        return getDisplayName(realName);
    }

}
