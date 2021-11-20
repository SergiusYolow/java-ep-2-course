import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private SimpleStringProperty title;
    private SimpleStringProperty numUDK;
    private SimpleStringProperty publicationYear;
    private SimpleStringProperty condition;
    private SimpleStringProperty author;
    private SimpleStringProperty genre;
    Book(String title, String numUDK, String publicationYear, String condition, String author, String genre) throws Exception {
        if (title == null || title.isEmpty() || numUDK == null || numUDK.isEmpty() || publicationYear == null || publicationYear.isEmpty() || condition == null || condition.isEmpty() || genre == null || genre.isEmpty() || author == null || author.isEmpty()) throw new Exception("������ �����");
        try
        {
            Integer.parseInt(publicationYear);
            if (Integer.parseInt(publicationYear) > (new GregorianCalendar()).get(Calendar.YEAR))
                throw new Exception("�������� ���!");
        }
        catch (NumberFormatException e)
        {
            throw new Exception("���� ��� ������ ���� �������������!");
        }
        this.title = new SimpleStringProperty(title);
        this.numUDK = new SimpleStringProperty(numUDK);
        this.publicationYear = new SimpleStringProperty(publicationYear);
        this.condition = new SimpleStringProperty(condition);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
    }
    SimpleStringProperty getFieldBySQL(String sqlName) {
        SimpleStringProperty res;
        switch (sqlName) {
            case "��������": res = this.title; break;
            case "����� �����": res = this.numUDK; break;
            case "��� �������": res = this.publicationYear; break;
            case "���������": res = this.condition; break;
            case "�����": res = this.author; break;
            case "����": res = this.genre; break;
            default: res = null;
        }
        return res;
    }
    public String getTitle() {
        return title.get();
    }

    public String getNumUDK() {
        return numUDK.get();
    }

    public String getPublicationYear() {
        return publicationYear.get();
    }

    public String getCondition() {
        return condition.get();
    }

    public String getGenre() { return genre.get(); }

    public String getAuthor() {
        return author.get();
    }
}
