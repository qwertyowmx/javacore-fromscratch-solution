/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
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
