package io.qwertyowrmx.proxygen.tests.pojo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdderService {
    public Integer add(int x, int y) {
        LOG.debug("AdderService { x = {}, y = {} }", x, y);
        return x + y;
    }
}
