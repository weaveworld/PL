package org.weaveworld.pl;

import org.weaveworld.pl.node.WNode;
import org.weaveworld.pl.node.WRootNode;
import org.weaveworld.pl.parser.PLParser;

import com.oracle.truffle.api.*;

@TruffleLanguage.Registration(id = "PL", name = "PeaL")
public class PLLanguage extends TruffleLanguage<Void> {
  
    public PLLanguage(){}

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        WNode exprNode = PLParser.parsePL(request.getSource().getCharacters().toString());
        WRootNode rootNode = new WRootNode(exprNode);
        return rootNode.getCallTarget();
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}

