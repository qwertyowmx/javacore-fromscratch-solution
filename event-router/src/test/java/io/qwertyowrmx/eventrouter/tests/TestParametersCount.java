package io.qwertyowrmx.eventrouter.tests;

import io.qwertyowrmx.eventrouter.EventRouter;
import io.qwertyowrmx.eventrouter.Subscribe;
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
