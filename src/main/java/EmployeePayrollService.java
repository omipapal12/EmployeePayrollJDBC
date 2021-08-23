import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class EmployeePayrollService {
    private List<EmployeePayroll> employeePayrolls;

    private EmployeePayrollServiceDB singletonEmployeePayrollServiceDB;

    public void updateSalaryByName(String name, double salary) {
        int result = singletonEmployeePayrollServiceDB.updateSalaryByName(name, salary);
        if(result == 0)
            return;

        EmployeePayroll employeePayroll = getEmployeePayrollByName(name);
        if(Objects.nonNull(employeePayroll))
            employeePayroll.setSalary(salary);
    }

    public boolean checkEmployeeDataSyncByName(String name) {
        List<EmployeePayroll> employeePayrollList = singletonEmployeePayrollServiceDB.getEmployeePayRollByName(name);
        return employeePayrollList.get(0).equals(getEmployeePayrollByName(name));
    }

    public EmployeePayrollService () {
        singletonEmployeePayrollServiceDB = EmployeePayrollServiceDB.getInstance();
    }

    public List<EmployeePayroll> readEmployeePayrollData() {
        this.employeePayrolls = singletonEmployeePayrollServiceDB.readData();
        return this.employeePayrolls;
    }

    private EmployeePayroll getEmployeePayrollByName(String name) {
        return employeePayrolls.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
    }

    public EmployeePayrollService(List<EmployeePayroll> employeePayrolls) {
        this.employeePayrolls =employeePayrolls;
    }

    public void addNewEmployee(EmployeePayroll employeePayroll) {
        singletonEmployeePayrollServiceDB.addNewEmployee(employeePayroll);
        if(employeePayroll.getId() > 0) {
            employeePayrolls.add(employeePayroll);
        }
    }


//============================================================================================================


    public enum IOService {
        FILE_IO,DB_IO,CONSOLE_IO,REST_IO
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
