package org.weaveworld.pl.node;

import com.oracle.truffle.api.frame.VirtualFrame;

public class WExpr {
  
  public static abstract class WExprUnary extends WNode {
    @Child WNode op, op1;

    public WExprUnary(WNode op) {
      this.op = op; 
    }
  }

  public static class WExprPlus extends WExprUnary {
    public WExprPlus(WNode op){ super(op); }

    @Override public int executeInt(VirtualFrame frame) {
      return op.executeInt(frame);
    }
  }
  
  public static class WExprMinus extends WExprUnary {
    public WExprMinus(WNode op){ super(op); }

    @Override public int executeInt(VirtualFrame frame) {
      return -op.executeInt(frame);
    }
  }
  
  public static abstract class WExprBinary extends WNode {
    @Child WNode op, op1;

    public WExprBinary(WNode op, WNode op1) {
      this.op = op; this.op1 = op1;
    }
  }
  
  public static class WExprAdd extends WExprBinary {
    public WExprAdd(WNode op, WNode op1){ super(op, op1); }

    @Override public int executeInt(VirtualFrame frame) {
      return op.executeInt(frame) + op1.executeInt(frame);
    }
  }

  public static class WExprSub extends WExprBinary {
    public WExprSub(WNode op, WNode op1){ super(op, op1); }

    @Override public int executeInt(VirtualFrame frame) {
      return op.executeInt(frame) - op1.executeInt(frame);
    }
  }
  
  public static class WExprMul extends WExprBinary {
    public WExprMul(WNode op, WNode op1){ super(op, op1); }

    @Override public int executeInt(VirtualFrame frame) {
      return op.executeInt(frame) * op1.executeInt(frame);
    }
  }

  public static class WExprDiv extends WExprBinary {
    public WExprDiv(WNode op, WNode op1){ super(op, op1); }

    @Override public int executeInt(VirtualFrame frame) {
      return op.executeInt(frame) / op1.executeInt(frame);
    }
  }
}
