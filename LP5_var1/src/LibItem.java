import java.io.Serializable;

public class LibItem implements Serializable {
    private int year;
    private int number;
    private String model;
    private String proc;

    public LibItem(int year, int number, String model, String proc) {
        this.year = year;
        this.number = number;
        this.model = model;
        this.proc = proc;
    }

    public String getModel() {
        return model;
    }

    public int getNumber() {
        return number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProc() {
        return proc;
    }

    public void setProc(String proc) {
        this.proc = proc;
    }
}
