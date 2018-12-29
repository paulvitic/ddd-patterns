package net.vitic.ddd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class Behavior {

    private final List<String> givens = new ArrayList<>();
    private final List<String> whens = new ArrayList<>();
    private final List<String> thens = new ArrayList<>();

    private Behavior(String given, Object... args) {
        givens.add(arrayFormat(given, args));
    }

    public static Behavior given(String given, Object... args) {
        return new Behavior(given, args);
    }

    public Behavior andGiven(String given, Object... args) {
        givens.add(arrayFormat(given, args));
        return this;
    }


    public Behavior when(String when, Object... args) {
        this.whens.add(arrayFormat(when, args));
        return this;
    }


    public Behavior andWhen(String when, Object... args) {
        this.whens.add(arrayFormat(when, args));
        return this;
    }


    public Behavior then(String then, Object... args) {
        this.thens.add(arrayFormat(then, args));
        return this;
    }


    public Behavior andThen(String then, Object... args) {
        this.thens.add(arrayFormat(then, args));
        return this;
    }


    public String success(){
        return ("\n" +

                (char) 27 +

                "[32m==========\n") +

                "SUCCESS\n" +

                "==========\n" +

                this.givenWhenThen() +

                (char) 27 + "[0m";
    }


    public String fail(){
        return ("\n" + (char) 27 +

                "[31m==========\n") +

                "FAIL\n" +

                "==========\n" +

                this.givenWhenThen() +

                (char) 27 + "[0m";
    }


    private String givenWhenThen() {

        final StringBuilder builder = new StringBuilder();

        builder.append("given:\n");

        givens.forEach(given -> builder
                .append("\t")
                .append(given)
                .append("\n"));

        builder.append("when:\n");

        whens.forEach(when -> builder
                .append("\t")
                .append(when)
                .append("\n"));
        builder.append("then:\n");

        thens.forEach(then -> builder
                .append("\t")
                .append(then)
                .append("\n"));

        return builder.toString();
    }

    private String arrayFormat(String messagePattern, Object[] argArray) {
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

    private boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
        if (delimeterStartIndex == 0) {
            return false;
        } else {
            char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
            return potentialEscape == '\\';
        }
    }

    private boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
        return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == '\\';
    }

    private void deeplyAppendParameter(StringBuffer sbuf, Object o, Map seenMap) {
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

    private void safeObjectAppend(StringBuffer sbuf, Object o) {
        try {
            String oAsString = o.toString();
            sbuf.append(oAsString);
        } catch (Throwable var3) {
            System.err.println("Failed toString() invocation on an object of type [" + o.getClass().getName() + "]");
            var3.printStackTrace();
            sbuf.append("[FAILED toString()]");
        }

    }

    private void objectArrayAppend(StringBuffer sbuf, Object[] a, Map seenMap) {
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

    private void booleanArrayAppend(StringBuffer sbuf, boolean[] a) {
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

    private void byteArrayAppend(StringBuffer sbuf, byte[] a) {
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

    private void charArrayAppend(StringBuffer sbuf, char[] a) {
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

    private void shortArrayAppend(StringBuffer sbuf, short[] a) {
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

    private void intArrayAppend(StringBuffer sbuf, int[] a) {
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

    private void longArrayAppend(StringBuffer sbuf, long[] a) {
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

    private void floatArrayAppend(StringBuffer sbuf, float[] a) {
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

    private void doubleArrayAppend(StringBuffer sbuf, double[] a) {
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

