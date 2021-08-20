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
}
    
