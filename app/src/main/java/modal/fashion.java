package modal;

import java.io.Serializable;

public class fashion implements Serializable {
    Integer Id;
    String tenfs,giafs,motafs;

    public fashion() {
    }

    public fashion(Integer id, String tenfs, String giafs, String motafs) {
        Id = id;
        this.tenfs = tenfs;
        this.giafs = giafs;
        this.motafs = motafs;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTenfs() {
        return tenfs;
    }

    public void setTenfs(String tenfs) {
        this.tenfs = tenfs;
    }

    public String getGiafs() {
        return giafs;
    }

    public void setGiafs(String giafs) {
        this.giafs = giafs;
    }

    public String getMotafs() {
        return motafs;
    }

    public void setMotafs(String motafs) {
        this.motafs = motafs;
    }
}
