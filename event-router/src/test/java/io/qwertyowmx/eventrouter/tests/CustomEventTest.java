/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.qwertyowmx.eventrouter.tests;

import io.qwertyowmx.eventrouter.EventRouter;
import io.qwertyowmx.eventrouter.Subscribe;
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
