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


    public void addNewEmployee(EmployeePayroll employeePayroll) {
        String insertQuery = "insert into employee_payroll (name, gender, salary, start) values ('%s', '%s', %s, '%s')";
        String formattedinsertQuery = String.format(insertQuery, employeePayroll.getName(),
                String.valueOf(employeePayroll.getGender()), employeePayroll.getSalary(), Date.valueOf(employeePayroll.getStart()));

        String payrollInsertQuery = "insert into payroll_details (employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) values (%s, %s, %s, %s, %s, %s)";

        Connection connection = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
            try(Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate(formattedinsertQuery, statement.RETURN_GENERATED_KEYS);
                if(count == 1) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if(resultSet.next()) {
                        int empId = resultSet.getInt(1);
                        employeePayroll.setId(empId);
                    }

                }
            }catch (SQLException ex) {
                connection.rollback();
            }

            double deductions = employeePayroll.getSalary() * 0.2;
            double taxablePay = employeePayroll.getSalary() - deductions;
            double tax = taxablePay* 0.1;
            double netPay = employeePayroll.getSalary() - tax;
            String payrollFormattedinsertQuery = String.format(payrollInsertQuery, employeePayroll.getId(), employeePayroll.getSalary(),
                    deductions, taxablePay, tax, netPay);

            try(Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate(payrollFormattedinsertQuery);
                if(count == 1) {
                }
            }catch (SQLException ex) {
                connection.rollback();
            }

            connection.commit();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
