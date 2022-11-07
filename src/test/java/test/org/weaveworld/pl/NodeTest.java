package test.org.weaveworld.pl;

import static org.junit.Assert.assertEquals;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.Ignore;
import org.junit.Test;
import org.weaveworld.pl.node.WNode;
import org.weaveworld.pl.node.WRootNode;
import org.weaveworld.pl.parser.PLParser;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;

@SuppressWarnings("static-method")
public class NodeTest {

    public static Object eval(String string) {
      WNode exprNode = PLParser.parsePL(string);
      WRootNode rootNode = new WRootNode(exprNode);
      CallTarget callTarget = rootNode.getCallTarget();

      return callTarget.call();
    }
  
    @Test
    public void operations() {
        // + - * / operations
        assertEquals(3,  eval("1+2"));
        assertEquals(-1, eval("1-2"));
        assertEquals(14, eval("7*2"));
        assertEquals(3,  eval("7/2"));
        assertEquals(19, eval("4*3/2+6/3*7-1"));
        
        // unary + -
        assertEquals(-1,  eval("+1+-2"));
    }
    @Test @Ignore
    public void testLanguage(){
      
      Context context = Context.create();
      Value result = context.eval("PL", "5*4-1");      
    }
}
