package org.weaveworld.pl.parser;

import org.weaveworld.pl.node.WExpr.*;
import org.weaveworld.pl.node.WNode;
import org.weaveworld.pl.node.WValInteger;

import once.util.format.Parse;

public class PLParser extends Parse{
  
  protected StringBuilder b=new StringBuilder();
  protected String b(){ String s=b.toString(); b.setLength(0); return s; }

  public PLParser(String s){ super(s); }

  public static boolean number(Parse P, StringBuilder b, boolean mayNeg){
    if(Character.isDigit(P.current()) || (mayNeg && P.match('-') && Character.isDigit(P.charAt(1)))){
      b.append(P.skip());
      char c; boolean wasLetter=false, isLetter=false;
      while( ( Character.isDigit(c=P.current()) || (isLetter=Character.isLetter(c) || c=='_'))
          || wasLetter && (c=='+' || c=='-') && Character.isDigit(P.charAt(1))
          || c=='.' && Character.isLetterOrDigit(P.charAt(1))
        ){
        b.append(c); P.skip();
        wasLetter|=isLetter; isLetter=false;
      }
      return true;
    }
    return false;
  }
  public static boolean name(Parse P, StringBuilder b){ char c;
    if(Character.isJavaIdentifierStart(P.current())){  b.append(P.skip());
      while( Character.isJavaIdentifierPart(c=P.current()) && c!=0){
        b.append(c); P.skip();
      }
      return true;
    }
    return false;
  }
  public static boolean string(StringBuilder b, Parse T){ char c=0; boolean was=false;
    if( T.match(c='\'') || T.match(c='"')){ was=true; T.skip();
      while( !T.end() && !T.match(c) ){ int ch=T.skip();
        if(ch=='\\'){ ch=T.skip();
          switch(ch){
            case 'n': ch='\n'; break;
            case 'r': ch='\r'; break;
            case 't': ch='\t'; break;
            case 'f': ch='\f'; break;
            case 'b': ch='\b'; break;
            case 'u': {
              ch =(T.skip()-'0')<<24;
              ch|=(T.skip()-'0')<<16;
              ch|=(T.skip()-'0')<<8;
              ch|=(T.skip()-'0');
              break;
            }
          }
        }
        b.append((char)ch);
      }
      T.skip();
    }
    return was;
  }
  public Object ws(){ return skipWhitespaces(); }    
  
  public WNode value(){ WNode r;
    if( number(this, b, false)){ String s=b();
//      if(s.contains(".") || s.contains("e")) r=Double.parseDouble(s);
//      else 
      r=new WValInteger(Integer.parseInt(s));
      ws();
      return r;
    ///}else if( name(T, b)){ String s=b();    
    }
    return null;
  }
  
  public WNode sgn(){ WNode r; String s;
    if(parse(s="+") || parse(s="-")){ r=sgn();         
        switch(s) {
          case "+": r=new WExprPlus(r); break;
          case "-": r=new WExprMinus(r); break;
        }
    }else{ 
      r=value();
    }      
    return r;
  }
  
  public WNode mul(){ WNode r=sgn(); String s;
    if(r!=null) {
      while(parse(s="*") || parse(s="/")){ WNode v=sgn();
        switch(s) {
          case "*": r=new WExprMul(r,v); break;
          case "/": r=new WExprDiv(r,v); break;
        }
      }
    }
    return r;
  }
  public WNode add(){ WNode r=mul(); String s;
    if(r!=null) {
      while(parse(s="+") || parse(s="-")){ WNode v=mul();
        switch(s) {
          case "+": r=new WExprAdd(r,v); break;
          case "-": r=new WExprSub(r,v); break;
        }
      }
    }
    return r;
  }

  public WNode expr(){ WNode r=add(); 
    return r;
  }
  
  public WNode parse(){ skipWhitespaces();
    WNode r=expr();
    if(!skipWhitespaces().end()){
      throw new RuntimeException("Wrong syntax: "+getPos()+toString());
    }
    return r;
  }
  
  public static WNode parsePL(String s){
    PLParser P=new PLParser(s); WNode d=P.parse();
    return d;
  }
  
  public static void main(String[] args) {
    System.out.println(parsePL("11"));
  }
}