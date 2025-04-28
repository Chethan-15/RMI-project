import java.io.Serializable;

public class Employee implements Serializable {
    private String empNo;
    private String empName;
    private double basicSalary;

    public Employee(String empNo, String empName, double basicSalary) {
        this.empNo = empNo;
        this.empName = empName;
        this.basicSalary = basicSalary;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
