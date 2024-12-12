package n25;

public class Vector_2D {
    public int x;
    public int y;

    // Khởi tạo vector tại (0, 0)
    public Vector_2D() {
        this.x = 0;
        this.y = 0;
    }

    // Khởi tạo vector tại (x, y)
    public Vector_2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Đặt lại giá trị vector
    public void setVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Đặt lại giá trị vector
    public void setVector(Vector_2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    // Vector đảo 
    public Vector_2D negate() {
        return new Vector_2D(-x, -y);
    }
}
