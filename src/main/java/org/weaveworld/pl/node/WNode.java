package org.weaveworld.pl.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;


public abstract class WNode extends Node {

    public abstract int executeInt(VirtualFrame frame);
}
