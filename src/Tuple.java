import java.util.Objects;

public class Tuple {
    private String name;
    private double x;
    private double y;

    public Tuple(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Double.compare(tuple.getX(), getX()) == 0 &&
                Double.compare(tuple.getY(), getY()) == 0 &&
                name.equals(tuple.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getX(), getY());
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
