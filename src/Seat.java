public class Seat {
    private String filmName;
    private String hallName;
    private int row;
    private int column;
    private String ownerName = "null";
    private int boughtPrice = 0;

    public Seat(String filmName, String hallName, int row, int column, String ownerName, int boughtPrice) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.row = row;
        this.column = column;
        this.ownerName = ownerName;
        this.boughtPrice = boughtPrice;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(int boughtPrice) {
        this.boughtPrice = boughtPrice;
    }
}
