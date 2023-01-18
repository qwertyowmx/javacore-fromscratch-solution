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

package io.qwertyowrmx.rpc.client.interceptors;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.rpc.client.network.Network;
import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

@AllArgsConstructor
public class DpgInvocationHandler implements Interceptor {
    private final String ip;
    private final int port;

    @Override
    public Object intercept(Object instance, Method method, Object[] args) {
        var descriptor = new RemoteMethodDescriptor(method.getReturnType(), method.getName(), args);
        return new Network(ip, port).communicate(descriptor);
    }
}
