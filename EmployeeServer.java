import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmployeeServer {
    private static Connection connection;

    public static void main(String[] args)throws MalformedURLException{
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "");

            // Create RMI registry
            LocateRegistry.createRegistry(2009);

            // Bind CalculationImplementation object
            CalculationImplementation stub = new CalculationImplementation();
            Naming.rebind("rmi://localhost:2009/employee", stub);

            System.out.println("Server ready");
        } catch (ClassNotFoundException | SQLException | RemoteException e) {
            System.out.println("hello world");
            e.printStackTrace();
        }
    }
}
