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
