package io.qwertyowrmx.proxygen.tests.interceptors;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.ProxyPool;
import io.qwertyowrmx.proxygen.interceptor.ConstantInterceptor;
import io.qwertyowrmx.proxygen.interceptor.ForwardingInterceptor;
import io.qwertyowrmx.proxygen.tests.pojo.AdderService;
import io.qwertyowrmx.proxygen.tests.pojo.ArithmeticService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InterceptorTestCase {

    @Test
    public void testMethodReturnsValueFromInterceptor() {

        AdderService object = ProxyPool.of(AdderService.class)
                .interceptor(new ConstantInterceptor<>(31))
                .generateInstance();
        int result = object.add(2, 2);
        Assertions.assertEquals(31, result);
    }

    @Test
    public void testInterceptionForwardedToSourceMethod() {

        AdderService object = ProxyPool.of(AdderService.class)
                .interceptor(new ForwardingInterceptor<>(AdderService.class))
                .generateInstance();

        Integer result = object.add(2, 2);
        Assertions.assertEquals(Integer.valueOf(4), result);
    }

    @Test
    public void testInterceptionMultipleMethods() {

        Interceptor interceptor = (instance, method, args) -> -10;

        ArithmeticService service = ProxyPool.of(ArithmeticService.class)
                .interceptor(interceptor)
                .generateInstance();

        Assertions.assertNotNull(service);

        Assertions.assertEquals(Integer.valueOf(-10), service.add(2, 2));
        Assertions.assertEquals(Integer.valueOf(-10), service.mult(2, 2));
        Assertions.assertEquals(Integer.valueOf(-10), service.sub(2, 1));
    }
}
