package io.qwertyowrmx.eventrouter.tests;

import io.qwertyowrmx.eventrouter.EventRouter;
import io.qwertyowrmx.eventrouter.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CustomEventTest {

    @Test
    public void testFireSimpleEvent() {
        EventRouter eventRouter = new EventRouter();
        CustomEventSubscriber subscriber = new CustomEventSubscriber();
        eventRouter.addSubscriber(subscriber);
        eventRouter.fireEvent("123");
        Assertions.assertFalse(subscriber.isEventReceived());
        eventRouter.fireEvent(new CustomEvent("test2", 5));
        Assertions.assertTrue(subscriber.isEventReceived());
    }

    @Test
    public void testMultipleSubscribersReceiveEvent() {
        List<CustomEventSubscriber> subscribers = List.of(
                new CustomEventSubscriber(),
                new CustomEventSubscriber(),
                new CustomEventSubscriber(),
                new CustomEventSubscriber(),
                new CustomEventSubscriber()
        );
        subscribers.forEach(subscriber -> Assertions.assertFalse(subscriber.isEventReceived()));

        EventRouter eventRouter = new EventRouter();
        subscribers.forEach(eventRouter::addSubscriber);
        eventRouter.fireEvent(new CustomEvent("test2", 5));
        subscribers.forEach(subscriber -> Assertions.assertTrue(subscriber.isEventReceived()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomEvent {
        private String argOne;
        private int argTwo;
    }

    @Slf4j
    public static class CustomEventSubscriber {
        @Getter
        private boolean eventReceived = false;

        @Subscribe
        public void receiveEvent(CustomEvent event) {
            LOG.info("Received event: {}", event);
            if (event.argOne.equals("test2") && event.argTwo == 5) {
                eventReceived = true;
            }
        }
    }

}
