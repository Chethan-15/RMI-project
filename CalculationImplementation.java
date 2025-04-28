import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CalculationImplementation extends UnicastRemoteObject implements SalaryCalculation {
    private static Map<String, Employee> employeeMap = new HashMap<>();
    private Connection connection;

    public CalculationImplementation() throws RemoteException {
        super();
        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double CalculateDA(double basic) throws RemoteException {
        return basic * 0.08;
    }

    @Override
    public double CalculateHRA(double basic) throws RemoteException {
        return basic * 0.1;
    }

    @Override
    public double CalculateNet(double basic) throws RemoteException {
        // Implement calculation of net salary here
        return basic + CalculateDA(basic) + CalculateHRA(basic);
    }

    @Override
public void addEmployee(String empNo, String empName, double basicSalary) throws RemoteException {
    try {
        // Calculate DA and HRA
        double da = CalculateDA(basicSalary);
        double hra = CalculateHRA(basicSalary);
        // Calculate Net Salary
        double netSalary = CalculateNet(basicSalary);

        PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (empNo, empName, basicSalary, DA, HRA, netSalary) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setString(1, empNo);
        statement.setString(2, empName);
        statement.setDouble(3, basicSalary);
        statement.setDouble(4, da); // Set DA
        statement.setDouble(5, hra); // Set HRA
        statement.setDouble(6, netSalary); // Set netSalary
        statement.executeUpdate();
        employeeMap.put(empNo, new Employee(empNo, empName, basicSalary));
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    @Override
    public void deleteEmployee(String empNo) throws RemoteException {
        // Implement deleting employee from the database
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE empNo = ?");
            statement.setString(1, empNo);
            statement.executeUpdate();
            employeeMap.remove(empNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(String empNo, String empName, double basicSalary) throws RemoteException {
        try {
            if (employeeMap.containsKey(empNo)) {
                // Calculate DA, HRA, and Net Salary
                double da = CalculateDA(basicSalary);
                double hra = CalculateHRA(basicSalary);
                double netSalary = CalculateNet(basicSalary);
    
                // Update employee details in the database
                PreparedStatement statement = connection.prepareStatement("UPDATE employees SET empName = ?, basicSalary = ?, DA = ?, HRA = ?, netSalary = ? WHERE empNo = ?");
                statement.setString(1, empName);
                statement.setDouble(2, basicSalary);
                statement.setDouble(3, da);
                statement.setDouble(4, hra);
                statement.setDouble(5, netSalary);
                statement.setString(6, empNo);
                statement.executeUpdate();
    
                // Update employee map
                employeeMap.get(empNo).setEmpName(empName);
                employeeMap.get(empNo).setBasicSalary(basicSalary);
            } else {
                throw new RemoteException("Employee not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public String displayEmployeeDetails() throws RemoteException {
        // Implement displaying employee details from the database
       StringBuilder details = new StringBuilder();
    try {
        // Fetch employee details including DA, HRA, and Net Salary from the database
        PreparedStatement statement = connection.prepareStatement("SELECT empNo, empName, basicSalary, DA, HRA, netSalary FROM employees");
        java.sql.ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String empNo = resultSet.getString("empNo");
            String empName = resultSet.getString("empName");
            double basicSalary = resultSet.getDouble("basicSalary");
            double da = resultSet.getDouble("DA");
            double hra = resultSet.getDouble("HRA");
            double net = resultSet.getDouble("netSalary");

            details.append("Emp No: ").append(empNo).append("\n")
                   .append("Emp Name: ").append(empName).append("\n")
                   .append("Basic Salary: ").append(basicSalary).append("\n")
                   .append("DA: ").append(da).append("\n")
                   .append("HRA: ").append(hra).append("\n")
                   .append("Net Salary: ").append(net).append("\n\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return details.toString();
}
}