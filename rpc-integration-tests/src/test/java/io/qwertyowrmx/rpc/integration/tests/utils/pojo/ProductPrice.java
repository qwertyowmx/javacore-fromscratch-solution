package io.qwertyowrmx.rpc.integration.tests.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrice implements Serializable {
    private double value;
}
