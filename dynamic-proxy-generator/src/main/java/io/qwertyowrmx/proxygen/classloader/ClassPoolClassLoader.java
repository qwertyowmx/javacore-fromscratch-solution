package io.qwertyowrmx.proxygen.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassPoolClassLoader extends ClassLoader {

    public Class<?> defineClass(String name, byte[] bytecode) {
        LOG.debug("Starting define class {}, bytecode len {}", name, bytecode.length);
        return super.defineClass(name, bytecode, 0, bytecode.length);
    }
}
