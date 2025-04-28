import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;

public class EmployeeClient {
    private JFrame frame;
    private SalaryCalculation stub;

    public EmployeeClient(SalaryCalculation stub) {
        this.stub = stub;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Employee Management System");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton addButton = new JButton("Add Employee");
        addButton.setBounds(30, 30, 150, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNo = JOptionPane.showInputDialog(frame, "Enter EMP no:");
                String empName = JOptionPane.showInputDialog(frame, "Enter Emp Name:");
                String basicSalaryStr = JOptionPane.showInputDialog(frame, "Enter Basic salary:");
                double basicSalary = Double.parseDouble(basicSalaryStr);
                try {
                    stub.addEmployee(empNo, empName, basicSalary);
                    JOptionPane.showMessageDialog(frame, "Employee added successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error adding employee: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(addButton);

        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.setBounds(30, 80, 150, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNo = JOptionPane.showInputDialog(frame, "Enter EMP no to delete:");
                try {
                    stub.deleteEmployee(empNo);
                    JOptionPane.showMessageDialog(frame, "Employee deleted successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error deleting employee: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(deleteButton);

        JButton updateButton = new JButton("Update Employee");
        updateButton.setBounds(30, 130, 150, 30);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNo = JOptionPane.showInputDialog(frame, "Enter EMP no to update:");
                String empName = JOptionPane.showInputDialog(frame, "Enter new Emp Name:");
                String basicSalaryStr = JOptionPane.showInputDialog(frame, "Enter new Basic salary:");
                double basicSalary = Double.parseDouble(basicSalaryStr);
                try {
                    stub.updateEmployee(empNo, empName, basicSalary);
                    JOptionPane.showMessageDialog(frame, "Employee updated successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error updating employee: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(updateButton);

        JButton displayButton = new JButton("Display Employee Details");
        displayButton.setBounds(30, 180, 200, 30);
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeDetails = stub.displayEmployeeDetails();
                    JOptionPane.showMessageDialog(frame, "Employee Details:\n" + employeeDetails);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error displaying employee details: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(displayButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(30, 230, 100, 30);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(exitButton);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            SalaryCalculation stub = (SalaryCalculation) Naming.lookup("rmi://localhost:2009/employee");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new EmployeeClient(stub).show();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
