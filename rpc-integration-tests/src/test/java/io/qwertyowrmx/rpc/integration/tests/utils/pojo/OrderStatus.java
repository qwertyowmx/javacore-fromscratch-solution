package io.qwertyowrmx.rpc.integration.tests.utils.pojo;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NEWLY_CREATED,
    IN_PROGRESS,
    PURCHASED,
    IN_ARCHIVE
}
