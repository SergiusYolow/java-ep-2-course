import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.Enumeration;

public class Author implements Serializable {
    private int id;
    private String name;
    private String birthdate;

    public Author(int id, String name,String birthdate) {
        this.name = name;
        this.id=id;
        this.birthdate=birthdate;
    }
    public Author(String name,String birthdate) {
        this.name = name;
        this.birthdate=birthdate;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getId() {
        return id;
    }

}
