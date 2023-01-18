package io.qwertyowrmx.rpc.integration.tests.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private BigInteger id;
    private OrderStatus status;
    private List<Product> products;
}
