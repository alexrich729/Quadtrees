/**
 * Tuple representing data being stored in Quadtree. Can easily modify to hold whatever data one wants, as long as x and
 * y are kept, as well as search in Quadtree class is modified.
 *
 * @author alexrich
 */
public class Tuple {
    private String name;        // name of data
    private double x;           // x loc of data
    private double y;           // y loc of data

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

    public String getName() {
        return name;
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
    public String toString() {
        return "Tuple{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
