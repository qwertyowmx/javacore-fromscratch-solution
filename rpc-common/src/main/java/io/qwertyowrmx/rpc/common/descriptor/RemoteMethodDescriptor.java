package io.qwertyowrmx.rpc.common.descriptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteMethodDescriptor implements Serializable {
    private transient Class<?> returnType;
    private String methodName;
    private Object[] args;
}
