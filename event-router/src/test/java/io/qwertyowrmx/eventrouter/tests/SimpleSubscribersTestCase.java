package io.qwertyowrmx.eventrouter.tests;

import io.qwertyowrmx.eventrouter.EventRouter;
import io.qwertyowrmx.eventrouter.Subscribe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleSubscribersTestCase {

    @Test
    public void testFireSimpleEvent() {
        EventRouter eventRouter = new EventRouter();
        SimpleSubscriber subscriber = new SimpleSubscriber();
        eventRouter.addSubscriber(subscriber);
        Assertions.assertFalse(subscriber.isEventReceived());
        eventRouter.fireEvent("TEST");
        Assertions.assertTrue(subscriber.isEventReceived());
    }

    @Test
    public void testMultipleSubscribersReceiveEvent() {
        List<SimpleSubscriber> subscribers = List.of(
                new SimpleSubscriber(),
                new SimpleSubscriber(),
                new SimpleSubscriber(),
                new SimpleSubscriber(),
                new SimpleSubscriber()
        );
        subscribers.forEach(subscriber -> Assertions.assertFalse(subscriber.isEventReceived()));

        EventRouter eventRouter = new EventRouter();
        subscribers.forEach(eventRouter::addSubscriber);
        eventRouter.fireEvent("TEST");
        subscribers.forEach(subscriber -> Assertions.assertTrue(subscriber.isEventReceived()));
    }

    @Slf4j
    public static class SimpleSubscriber {
        @Getter
        private boolean eventReceived = false;

        @Subscribe
        public void receiveEvent(String event) {
            LOG.info("Received event: {}", event);
            if (event.equals("TEST")) {
                eventReceived = true;
            }
        }
    }
}
