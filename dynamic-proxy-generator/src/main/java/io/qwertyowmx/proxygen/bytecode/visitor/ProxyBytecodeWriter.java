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

package io.qwertyowmx.proxygen.bytecode.visitor;

import io.qwertyowmx.proxygen.Interceptor;
import io.qwertyowmx.proxygen.bytecode.BytecodeConst;
import io.qwertyowmx.proxygen.utils.ClassIndexSequence;
import lombok.Getter;
import org.objectweb.asm.ClassWriter;

public class ProxyBytecodeWriter extends ClassWriter {
    @Getter
    private final Class<?> source;
    @Getter
    private final String proxyName;
    @Getter
    private final Interceptor interceptor;

    public ProxyBytecodeWriter(Class<?> source, Interceptor interceptor, int flags) {
        super(flags);
        this.source = source;
        this.proxyName = source.getName() + BytecodeConst.PROXY_CLASS_PREFIX + ClassIndexSequence.nextIndex();
        this.interceptor = interceptor;
    }

    public byte[] emitByteCode() {
        RootClassVisitor visitor = new RootClassVisitor(this, this.api, interceptor);
        visitor.visitEnd();
        return toByteArray();
    }
}
