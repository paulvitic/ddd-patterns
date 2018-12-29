package net.vitic.ddd.util;

import java.util.function.Consumer;

public class ExceptionWrapper {

    public static <T> Consumer<T> wrapException(
        ExceptionWrapperConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
