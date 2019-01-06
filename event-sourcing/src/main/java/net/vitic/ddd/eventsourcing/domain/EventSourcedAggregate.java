package net.vitic.ddd.eventsourcing.domain;

import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.domain.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class EventSourcedAggregate extends AggregateRoot {

    protected abstract void mutate(DomainEvent event);

    protected void reConstruct(List<DomainEvent> events) {
        events.forEach(this::mutate);
    }

    protected MutateMethodSelector select() {
        return new MutateMethodSelector();
    }

    protected class MutateMethodSelector {

        private final List<ArgumentClassPattern> patterns = new ArrayList<>();

        private MutateMethodSelector() {}

        @SuppressWarnings("unused")
        public <T> MutateMethodSelector when(Class<T> clazz, Function<T, Object> function) {
            patterns.add(new ArgumentClassPattern<>(clazz, function));
            return this;
        }

        @SuppressWarnings("unused")
        public <T> MutateMethodSelector when(Class<T> clazz, Consumer<T> consumer) {
            patterns.add(new ArgumentClassPattern<>(clazz, t -> {
                consumer.accept(t);
                return t;
            }));
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Object mutate(Object value) {
            //noinspection unchecked
            return patterns.stream()
                           .filter(pattern -> pattern.matches(value))
                           .map(pattern -> pattern.apply(value))
                           .findFirst()
                           .orElseThrow(() -> new IllegalArgumentException("cannot match " + value));
        }
    }


    class ArgumentClassPattern<T> {

        private Class<T> clazz;

        private Function<T, Object> function;

        private ArgumentClassPattern(Class<T> clazz, Function<T, Object> function) {
            this.clazz = clazz;
            this.function = function;
        }

        boolean matches(Object value) {
            return clazz.getName().equals(value.getClass().getName());
        }

        Object apply(T value) {
            return function.apply(value);
        }
    }
}

