package n07;

public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location() {
        this.x = 0;
        this.y = 0;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Location location) {
        this.x = location.x;
        this.y = location.y;
    }

    public Location getLocation() {
        return new Location(this.x, this.y);
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void moveDirection(int direction, int distance) {
        int moveX = distance * (int) Math.cos(Math.toRadians(direction));
        int moveY = distance * (int) Math.sin(Math.toRadians(direction));
        this.move(moveX, moveY);
    }
}
