public class Hall {
    private String filmName;
    private String hallName;
    private int price;
    private int row;
    private int column;

    public Hall(String filmName, String hallName, int price, int row, int column) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.price = price;
        this.row = row;
        this.column = column;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return hallName;
    }
}
