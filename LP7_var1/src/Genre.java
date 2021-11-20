import javafx.beans.property.SimpleStringProperty;

 public class Genre {
        private SimpleStringProperty genre;
        Genre(String genre, String id) {
            this.genre = new SimpleStringProperty(genre);
        }
        SimpleStringProperty getFieldBySQL(String sqlName) {
            SimpleStringProperty res;
            switch (sqlName) {
                case "Æàíð": res = this.genre; break;
                default: res = null;
            }
            return res;
        }
        public String getGenre() { return genre.get(); }

    }
