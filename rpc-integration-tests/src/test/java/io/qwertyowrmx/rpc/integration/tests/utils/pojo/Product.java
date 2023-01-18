package io.qwertyowrmx.rpc.integration.tests.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private ProductPrice price;
    private BigInteger productId;
    private String productName;
}
