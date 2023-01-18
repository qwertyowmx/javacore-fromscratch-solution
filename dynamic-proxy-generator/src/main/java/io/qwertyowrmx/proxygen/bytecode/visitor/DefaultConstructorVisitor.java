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

import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

@Slf4j
public class DefaultConstructorVisitor extends MethodVisitor {

    private final ProxyBytecodeWriter writer;

    protected DefaultConstructorVisitor(ProxyBytecodeWriter writer, int api) {
        super(api);
        this.writer = writer;
    }

    @Override
    public void visitEnd() {
        LOG.debug("Generating default constructor for class {}", writer.getProxyName());
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        visitor.visitCode();
        visitor.visitVarInsn(ALOAD, 0); // load "this"

        Class<?> source = writer.getSource();
        if (source.isInterface()) {
            visitor.visitMethodInsn(INVOKESPECIAL, // call superclass constructor
                    ProxyFactoryUtils.replaceDotsToSlashes(Object.class.getName()),
                    "<init>", "()V",
                    false);
        } else {
            visitor.visitMethodInsn(INVOKESPECIAL, // call superclass constructor
                    ProxyFactoryUtils.replaceDotsToSlashes(source.getName()),
                    "<init>", "()V",
                    false);
        }

        visitor.visitInsn(RETURN);
        visitor.visitMaxs(1, 1);
        visitor.visitEnd();
    }
}
