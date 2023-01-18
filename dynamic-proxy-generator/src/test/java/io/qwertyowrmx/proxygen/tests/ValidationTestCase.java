package io.qwertyowrmx.proxygen.tests;


import io.qwertyowrmx.proxygen.ProxyPool;
import io.qwertyowrmx.proxygen.exceptions.ProxyGenerationException;
import io.qwertyowrmx.proxygen.interceptor.ConstantInterceptor;
import io.qwertyowrmx.proxygen.tests.pojo.AdderService;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

public class ValidationTestCase {

    @Test
    public void testFailIfSourceClassIsNull() {
        assertThrows(ProxyGenerationException.class, () -> {
            Class<?> aClass = ProxyPool.of(null)
                    .interceptor(new ConstantInterceptor<>(new Object())).generateClass();
        });
    }

    @Test
    public void testFailIfInterceptorIsNull() {
        assertThrows(ProxyGenerationException.class, () -> {
            Class<?> aClass = ProxyPool.of(AdderService.class).interceptor(null).generateClass();
        });
    }
}
