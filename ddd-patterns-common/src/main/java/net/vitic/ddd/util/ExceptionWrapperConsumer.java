package net.vitic.ddd.util;

@FunctionalInterface
public interface ExceptionWrapperConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}

