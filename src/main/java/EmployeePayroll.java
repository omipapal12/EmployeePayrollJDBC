import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayroll {
    private int id;
    private String name;
    private double salary;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    private LocalDate start;
    private char gender;

    public EmployeePayroll(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayroll(String name, char gender ,double salary, LocalDate start) {
        this(0, name, salary);
        this.start = start;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayroll that = (EmployeePayroll) o;
        return id == that.id && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary);
    }

    @Override
    public String toString() {
        return "EmployeePayroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

