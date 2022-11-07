package org.weaveworld.pl.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public final class WRootNode extends RootNode {
  @SuppressWarnings("FieldMayBeFinal")
  @Child
  private WNode node;

  public WRootNode(WNode node) {
    super(null);
    this.node = node;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return node.executeInt(frame);
  }
}
