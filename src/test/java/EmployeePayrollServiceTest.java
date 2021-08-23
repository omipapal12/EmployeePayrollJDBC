import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    void givenDataInDB_WhenRetrieved_ShouldMatchTheCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayroll> employeePayrolls = employeePayrollService.readEmployeePayrollData();
        Assertions.assertEquals(3,  employeePayrolls.size());
    }

    @Test
    void givenNewSalary_WhenUpdated_ShouldSyncInDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData();
        employeePayrollService.updateSalaryByName("Terisa", 3000000.00);
        boolean result = employeePayrollService.checkEmployeeDataSyncByName("Terisa");
        Assertions.assertTrue(result);
    }
    @Test
    void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData();
        EmployeePayroll employeePayroll = new EmployeePayroll("Mark",'M', 3000000.00, LocalDate.now());
        employeePayrollService.addNewEmployee(employeePayroll);
        boolean result = employeePayrollService.checkEmployeeDataSyncByName("Mark");
        Assertions.assertTrue(result);
    }
}

