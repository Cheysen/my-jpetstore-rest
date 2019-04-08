package pre.chl.mypetstore.domain;

import java.io.Serializable;

public class Sequence implements Serializable {

  private static final long serialVersionUID = 8278780133180137281L;
 
  private String squencename;
  private int nextId;

  public Sequence(){}
  public Sequence(String squencename, int nextId) {
    this.squencename = squencename;
    this.nextId = nextId;
  }

  public String getName() {
    return squencename;
  }

  public void setName(String squencename) {
    this.squencename = squencename;
  }

  public int getNextId() {
    return nextId;
  }

  public void setNextId(int nextId) {
    this.nextId = nextId;
  }

}
