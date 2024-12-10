package n25;

public class vector2D {
    public int x;
    public int y;

    // Khởi tạo vector tại (0, 0)
    public vector2D() {
        this.x = 0;
        this.y = 0;
    }

    // Khởi tạo vector tại (x, y)
    public vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Đặt lại giá trị vector
    public void setVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Đặt lại giá trị vector
    public void setVector(vector2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    // Vector đảo 
    public vector2D negate() {
        return new vector2D(-x, -y);
    }
}
