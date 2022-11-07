package once.util.format;

public class Parse{
  protected String string;
  protected int length, pos;
  public int getPos(){ return pos; }
  //public int setPos(int pos){ int r=this.pos; this.pos=pos; return r; }
  @Override  
  public String toString(){ return pos<length ? string.substring(pos) :""; }
  @SuppressWarnings("static-method")
  protected boolean nextLine(){ return false; }
  public boolean end(){ if(pos<length) return false;
    return !nextLine() || (pos>=length);
  }

  public static char charAt(String s, int i){ return i<s.length() ? s.charAt(i) :'\0'; }
  public char charAt(int i){ int j; return (j=pos+i)<length ? string.charAt(j) :0; }

  private void next(){ ++pos; }

  public char current(){    return charAt(string,pos);  }

  public boolean match(char c){ return c==current(); }
  public boolean match(String s){
    return (s.length()==1 ? s.charAt(0)==charAt(string,pos) : string.startsWith(s,pos));
  }
  public boolean matchIgnoreCase(String s){
    return (s.length()==1 ? s.charAt(0)==charAt(string,pos) 
                          : (s.regionMatches(true, 0, string, pos, s.length())));
 }

  public char skip(){ char c=current(); next(); return c; }
  public Parse skip(int n){ pos+=n; return this; }

  public boolean parse(char c){ boolean f; if(f=match(c)) skip(); return f; }
  public boolean parse(String s){ boolean f; if(f=match(s)){ skip(s.length()); } return f; }

  public boolean parseWhitespaces(){ char c; boolean was=false;
    for( ; (c=current())!=0 && Character.isWhitespace(c); skip()){ was=true; }
    return was;
  }
  public Parse skipWhitespaces(){ parseWhitespaces(); return this; }

  public Parse(String s){ this.string=s; this.length=s.length(); }

}