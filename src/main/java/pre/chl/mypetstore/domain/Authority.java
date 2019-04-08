package pre.chl.mypetstore.domain;

public class Authority {
    private Integer id;
    private AuthorityName name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }


}
