package n07;

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

    // Đặt vị trí mới cho đối tượng
    public void setLocation(Location location) {
        this.x = location.x;
        this.y = location.y;
    }

    // Lấy vị trí hiện tại
    public Location getLocation() {
        return new Location(this.x, this.y);
    }

    // Di chuyển đối tượng theo x, y
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    // Di chuyển đối tượng theo hướng và khoảng cách
    public void moveDirection(int direction, int distance) {
        int moveX = distance * (int) Math.cos(Math.toRadians(direction));
        int moveY = distance * (int) Math.sin(Math.toRadians(direction));
        this.move(moveX, moveY);
    }
}
