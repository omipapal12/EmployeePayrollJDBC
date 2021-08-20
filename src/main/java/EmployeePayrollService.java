import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class EmployeePayrollService {
    List<EmployeePayroll> employeePayrolls;

    public List<EmployeePayroll> readEmployeePayrollData() {
        return new EmployeePayrollServiceDB().readData();
    }

    public enum IOService {
        FILE_IO,DB_IO,CONSOLE_IO,REST_IO
    }

    public EmployeePayrollService () {

    }

    public EmployeePayrollService(List<EmployeePayroll> employeePayrolls) {
        this.employeePayrolls =employeePayrolls;
    }

    public static void main(String[] args) {

        List<EmployeePayroll> employeePayrolls = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrolls);
        employeePayrollService.readEmployeePayroll();
        employeePayrollService.writeEmployeePayroll();
    }

    private void writeEmployeePayroll() {
        System.out.println("\n Writing Employee Payroll Data \n" + employeePayrolls);
    }

    private void readEmployeePayroll() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee id : ");
        int empId = scanner.nextInt();
        System.out.println("Enter Employe Name :");
        String empName = scanner.next();
        System.out.println("Enter the Salary : ");
        double salary = scanner.nextDouble();

        employeePayrolls.add(new EmployeePayroll(empId, empName, salary));

    }

}
