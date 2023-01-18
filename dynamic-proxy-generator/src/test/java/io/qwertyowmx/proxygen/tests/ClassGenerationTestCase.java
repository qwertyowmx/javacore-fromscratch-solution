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

package io.qwertyowmx.proxygen.tests;

import io.qwertyowmx.proxygen.ProxyFactory;
import io.qwertyowmx.proxygen.ProxyPool;
import io.qwertyowmx.proxygen.interceptor.ConstantInterceptor;
import io.qwertyowmx.proxygen.tests.pojo.AdderService;
import io.qwertyowmx.proxygen.tests.pojo.ArithmeticService;
import io.qwertyowmx.proxygen.tests.pojo.IAdderService;
import io.qwertyowmx.proxygen.tests.pojo.IListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ClassGenerationTestCase {

    @Test
    public void testGeneratedClassIsNotNull() {

        Class<?> aClass = ProxyPool.of(AdderService.class)
                .enableCache()
                .interceptor(new ConstantInterceptor<>(new Object()))
                .generateClass();

        Assertions.assertNotNull(aClass);
    }

    @Test
    public void testInstantiateProxy() {

        AdderService object = ProxyPool.of(AdderService.class)
                .enableCache()
                .interceptor(new ConstantInterceptor<>(new Object()))
                .generateInstance();

        Assertions.assertNotNull(object);
    }

    @Test
    public void testInstantiateProxyMultipleMethods() {

        ArithmeticService object = ProxyPool.of(ArithmeticService.class)
                .enableCache()
                .interceptor(new ConstantInterceptor<>(31))
                .generateInstance();

        Assertions.assertNotNull(object);
    }

    @Test
    public void testProxyClassesAreSameWhenCacheEnabled() {

        ProxyFactory<ArithmeticService> proxyFactory = ProxyPool.of(ArithmeticService.class)
                .enableCache();

        Class<?> aClassOne = proxyFactory
                .interceptor(new ConstantInterceptor<>(31))
                .generateClass();

        Class<?> aClassTwo = proxyFactory
                .interceptor(new ConstantInterceptor<>(31))
                .generateClass();

        Assertions.assertSame(aClassOne, aClassTwo);
        Assertions.assertEquals(aClassOne, aClassTwo);
    }


    @Test
    public void testProxyClassesAreNotTheSameWhenCacheDisabled() {

        ProxyFactory<ArithmeticService> proxyFactory = ProxyPool.of(ArithmeticService.class);

        Class<?> aClassOne = proxyFactory
                .interceptor(new ConstantInterceptor<>(31))
                .generateClass();

        Class<?> aClassTwo = proxyFactory
                .interceptor(new ConstantInterceptor<>(31))
                .generateClass();

        Assertions.assertNotSame(aClassOne, aClassTwo);
        Assertions.assertNotEquals(aClassOne, aClassTwo);
    }

    @Test
    public void testGenerateClassFromSimpleInterface() {
        IAdderService service = ProxyPool.of(IAdderService.class)
                .interceptor(new ConstantInterceptor<>(10))
                .generateInstance();

        int ret = service.add(2, 2);

        Assertions.assertEquals(10, ret);
    }

    @Test
    public void testGenerateClassWithMethodWhichReturnObject() {
        IListService service = ProxyPool.of(IListService.class).interceptor((o, method, args) ->
                        new ArrayList<>(Integer.parseInt(args[0].toString())))
                .generateInstance();

        ArrayList<String> list = (ArrayList<String>) service.getList(5);
        Assertions.assertInstanceOf(ArrayList.class, list);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }
}
