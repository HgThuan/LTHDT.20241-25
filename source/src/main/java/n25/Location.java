package n25;

public class Location {    
    public int x;
    public int y;

    // Khởi tạo vị trí tại (x, y)
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Khởi tạo vị trí tại (0, 0)
    public Location() {
        this.x = 0;
        this.y = 0;
    }

    // Đặt vị trí mới cho đối tượng
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Đặt lại vị trí đối tượng
    public void move(Location location) {
        this.x = location.x;
        this.y = location.y;
    }

    // Di chuyển đối tượng theo vector
    public void move(vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    // Trừ 2 vị trí
    public static vector2D subtract(Location start, Location end) {
        return new vector2D(end.x - start.x, end.y - start.y);
    }
}
