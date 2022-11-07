package org.weaveworld.pl.node;

import com.oracle.truffle.api.frame.VirtualFrame;


public final class WValInteger extends WNode {
    private final int value;

    public WValInteger(int value) {
        this.value = value;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return this.value;
    }
}
