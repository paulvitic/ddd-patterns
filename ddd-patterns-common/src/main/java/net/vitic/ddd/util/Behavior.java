package net.vitic.ddd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class Behavior {

    private static final ThreadLocal<Behavior> behaviorContext = new ThreadLocal<>();

    private final List<String> given = new ArrayList<>();
    private final List<String> when = new ArrayList<>();
    private final List<String> then = new ArrayList<>();

    private Behavior(String given, Object... args) {
        this.given.add(arrayFormat(given, args));
    }

    public static void given(String msg, Object... args) {
        behaviorContext.set(new Behavior(msg, args));
    }

    public static void andGiven(String msg, Object... args) {
        behaviorContext.get().given.add(arrayFormat(msg, args));
    }


    public static void when(String msg, Object... args) {
        behaviorContext.get().when.add(arrayFormat(msg, args));
    }


    public static void andWhen(String msg, Object... args) {
        behaviorContext.get().when.add(arrayFormat(msg, args));
    }


    public static void then(String msg, Object... args) {
        behaviorContext.get().then.add(arrayFormat(msg, args));
    }


    public static void andThen(String msg, Object... args) {
        behaviorContext.get().then.add(arrayFormat(msg, args));
    }


    public static void success(){
        if (behaviorContext.get()==null) {
            System.out.print((char) 27 +
                             "[35mNo behavior context found. Test result will not be printed." +
                             (char) 27 + "[0m");
        } else {
            String res =  ("\n" +

                           (char) 27 +

                           "[32m==========\n") +

                          "SUCCESS\n" +

                          "==========\n" +

                          givenWhenThen() +

                          (char) 27 + "[0m";

            System.out.print(res);
        }
    }


    public static String fail(){
        if (behaviorContext.get()==null) {
            return ((char) 27 +
                    "[35mNo behavior context found. Test result will not be printed." +
                    (char) 27 + "[0m");
        } else {
            return ("\n" + (char) 27 +

                "[31m==========\n") +

                "FAIL\n" +

                "==========\n" +

                givenWhenThen() +

                (char) 27 + "[0m";
        }
    }


    private static String givenWhenThen() {

        final StringBuilder builder = new StringBuilder();

        builder.append("given:\n");

        behaviorContext.get().given.forEach(given -> builder
                .append("\t")
                .append(given)
                .append("\n"));

        builder.append("when:\n");

        behaviorContext.get().when.forEach(when -> builder
                .append("\t")
                .append(when)
                .append("\n"));
        builder.append("then:\n");

        behaviorContext.get().then.forEach(then -> builder
                .append("\t")
                .append(then)
                .append("\n"));

        return builder.toString();
    }

    private static String arrayFormat(String messagePattern, Object[] argArray) {
        if (messagePattern == null) {
            return null;
        } else if (argArray == null) {
            return messagePattern;
        } else {
            int i = 0;
            StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);

            for(int L = 0; L < argArray.length; ++L) {
                int j = messagePattern.indexOf("{}", i);
                if (j == -1) {
                    if (i == 0) {
                        return messagePattern;
                    }

                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }

                if (isEscapedDelimeter(messagePattern, j)) {
                    if (!isDoubleEscaped(messagePattern, j)) {
                        --L;
                        sbuf.append(messagePattern, i, j - 1);
                        sbuf.append('{');
                        i = j + 1;
                    } else {
                        sbuf.append(messagePattern, i, j - 1);
                        deeplyAppendParameter(sbuf, argArray[L], new HashMap());
                        i = j + 2;
                    }
                } else {
                    sbuf.append(messagePattern, i, j);
                    deeplyAppendParameter(sbuf, argArray[L], new HashMap());
                    i = j + 2;
                }
            }

            sbuf.append(messagePattern.substring(i));
            return sbuf.toString();
        }
    }

    private static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
        if (delimeterStartIndex == 0) {
            return false;
        } else {
            char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
            return potentialEscape == '\\';
        }
    }

    private static boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
        return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == '\\';
    }

    private static void deeplyAppendParameter(StringBuffer sbuf, Object o, Map seenMap) {
        if (o == null) {
            sbuf.append("null");
        } else {
            if (!o.getClass().isArray()) {
                safeObjectAppend(sbuf, o);
            } else if (o instanceof boolean[])
                booleanArrayAppend(sbuf, ((boolean[]) o));
            else if (o instanceof byte[])
                byteArrayAppend(sbuf, ((byte[]) o));
            else if (o instanceof char[])
                charArrayAppend(sbuf, ((char[]) o));
            else if (o instanceof short[]) {
                shortArrayAppend(sbuf, (short[]) o);
            } else if (o instanceof int[])
                intArrayAppend(sbuf, ((int[]) o));
            else if (o instanceof long[])
                longArrayAppend(sbuf, ((long[]) o));
            else if (o instanceof float[])
                floatArrayAppend(sbuf, ((float[]) o));
            else if (o instanceof double[])
                doubleArrayAppend(sbuf, ((double[]) o));
            else {
                objectArrayAppend(sbuf, (Object[]) o, seenMap);
            }

        }
    }

    private static void safeObjectAppend(StringBuffer sbuf, Object o) {
        try {
            String oAsString = o.toString();
            sbuf.append(oAsString);
        } catch (Throwable var3) {
            System.err.println("Failed toString() invocation on an object of type [" + o.getClass().getName() + "]");
            var3.printStackTrace();
            sbuf.append("[FAILED toString()]");
        }

    }

    private static void objectArrayAppend(StringBuffer sbuf, Object[] a, Map seenMap) {
        sbuf.append('[');
        if (!seenMap.containsKey(a)) {
            //noinspection unchecked
            seenMap.put(a, null);
            int len = a.length;

            for(int i = 0; i < len; ++i) {
                deeplyAppendParameter(sbuf, a[i], seenMap);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            seenMap.remove(a);
        } else {
            sbuf.append("...");
        }

        sbuf.append(']');
    }

    private static void booleanArrayAppend(StringBuffer sbuf, boolean[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void byteArrayAppend(StringBuffer sbuf, byte[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void charArrayAppend(StringBuffer sbuf, char[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void shortArrayAppend(StringBuffer sbuf, short[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void intArrayAppend(StringBuffer sbuf, int[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void longArrayAppend(StringBuffer sbuf, long[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void floatArrayAppend(StringBuffer sbuf, float[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }

    private static void doubleArrayAppend(StringBuffer sbuf, double[] a) {
        sbuf.append('[');
        int len = a.length;

        for(int i = 0; i < len; ++i) {
            sbuf.append(a[i]);
            if (i != len - 1) {
                sbuf.append(", ");
            }
        }

        sbuf.append(']');
    }
}

