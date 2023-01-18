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

package io.qwertyowrmx.rpc.client;


import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;

import java.util.Objects;


// TODO: add javadocs

/**
 * @param <T>
 */
public abstract class AbstractStubBuilder<T> implements StubBuilder<T> {
    protected String ipAddress;
    protected Class<T> stubClass;
    protected int port;

    @Override
    public StubBuilder<T> stubClass(Class<T> aClass) {
        this.stubClass = aClass;
        return this;
    }

    @Override
    public StubBuilder<T> port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public T createStub() {

        if (Objects.isNull(ipAddress)) {
            throw new StubCreationException("Unable to create stub, ip address is null");
        }

        if (Objects.isNull(stubClass)) {
            throw new StubCreationException("Unable to create stub, class is null");
        }

        return createStubInternal();
    }

    @Override
    public StubBuilder<T> ip(String ip) {
        this.ipAddress = ip;
        return this;
    }

    protected abstract T createStubInternal();
}
