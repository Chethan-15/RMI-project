import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SalaryCalculation extends Remote {
    double CalculateDA(double basic) throws RemoteException;
    double CalculateHRA(double basic) throws RemoteException;
    double CalculateNet(double basic) throws RemoteException;
    void addEmployee(String empNo, String empName, double basicSalary) throws RemoteException;
    void deleteEmployee(String empNo) throws RemoteException;
    void updateEmployee(String empNo, String empName, double basicSalary) throws RemoteException;
    String displayEmployeeDetails() throws RemoteException;
}
