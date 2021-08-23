import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
}

