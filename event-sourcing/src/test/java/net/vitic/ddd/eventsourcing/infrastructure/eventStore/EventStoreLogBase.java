package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;

public class EventStoreLogBase {

    protected ListAppender<ILoggingEvent> eventStoreLog;

    @BeforeEach
    void setupBeforeClass() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger eventStoreLogger = context.getLogger(EventStore.logger().getName());
        eventStoreLog = new ListAppender<>();
        eventStoreLog.start();
        eventStoreLogger.addAppender(eventStoreLog);

        eventStoreLogger.setLevel(Level.ALL);
        eventStoreLog.clearAllFilters();
        eventStoreLog.list.clear();
    }
}
