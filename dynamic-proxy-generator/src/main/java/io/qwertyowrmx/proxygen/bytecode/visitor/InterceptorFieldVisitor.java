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

package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.bytecode.BytecodeConst;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

@Slf4j
public class InterceptorFieldVisitor extends FieldVisitor {

    private final ProxyBytecodeWriter writer;

    protected InterceptorFieldVisitor(ProxyBytecodeWriter writer, int api) {
        super(api);
        this.writer = writer;
    }

    @Override
    public void visitEnd() {
        LOG.debug("Generating interceptor field for class {}", writer.getProxyName());
        createInterceptorField();
    }

    private void createInterceptorField() {
        String descriptor = Type.getDescriptor(Interceptor.class);
        FieldVisitor fieldVisitor = writer.visitField(
                ACC_PUBLIC + ACC_STATIC, BytecodeConst.INTERCEPTOR_FIELD_NAME,
                descriptor,
                null, null);

        fieldVisitor.visitEnd();
    }
}
