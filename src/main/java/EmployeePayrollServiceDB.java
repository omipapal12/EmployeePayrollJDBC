import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EmployeePayrollServiceDB {
    private PreparedStatement employeePayrollStmt;

    private static EmployeePayrollServiceDB employeePayrollServiceDB = null;

    private EmployeePayrollServiceDB() {
    }

    public static EmployeePayrollServiceDB getInstance() {
        if(Objects.isNull(employeePayrollServiceDB)) {
            employeePayrollServiceDB = new EmployeePayrollServiceDB();
        }
        return employeePayrollServiceDB;
    }

    public List<EmployeePayroll> readData() {
        List<EmployeePayroll> employeePayrolls = null;
        String selectQuery = "SELECT * from employee_payroll";

        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            employeePayrolls = getEmployeePayrollsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeePayrolls;
    }

    public int updateSalaryByName(String name, double salary) {
        String updateQuery = String.format("update employee_payroll set salary = %.2f where name ='%s';", salary, name);
        try(Connection connection = this.getConnection()) {
            Statement updateStmt = connection.createStatement();
            return updateStmt.executeUpdate(updateQuery);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayroll> getEmployeePayRollByName(String name) {
        if(Objects.isNull(employeePayrollStmt))
            this.prepareStatementForEmployeePayroll();

        List<EmployeePayroll> employeePayrollList = null;
        try {
            employeePayrollStmt.setString(1, name);
            ResultSet resultSet = employeePayrollStmt.executeQuery();
            employeePayrollList = this.getEmployeePayrollsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employeePayrollList;
    }

    private List<EmployeePayroll> getEmployeePayrollsFromResultSet(ResultSet resultSet) {
        List<EmployeePayroll> employeePayrolls = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                employeePayrolls.add(new EmployeePayroll(id, name, salary));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employeePayrolls;
    }

    private void prepareStatementForEmployeePayroll() {
        String selectQuery = "SELECT * from employee_payroll where name = ?";
        try {
            Connection connection = this.getConnection();
            employeePayrollStmt = connection.prepareStatement(selectQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String username = "root";
        String password = "Omkar@12345";

        System.out.println("Connecting to database : " + jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Connection to the Database Successful : " + connection);
        return connection;
    }

}
