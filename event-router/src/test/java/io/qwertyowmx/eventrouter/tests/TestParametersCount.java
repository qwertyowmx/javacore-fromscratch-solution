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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestParametersCount {

    @Test
    public void testFailIfSubscriberHaveMultipleParams() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            EventRouter router = new EventRouter();
            router.addSubscriber(new IncorrectSubscriber());
            router.fireEvent(null);
        });
    }

    public static class IncorrectSubscriber {
        @Subscribe
        public void receiveEvent(int a, int b) {
        }
    }
}
