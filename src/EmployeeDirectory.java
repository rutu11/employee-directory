import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class EmployeeDirectory {
    public static void main(String[] args) {

        EmployeeService service = EmployeeService.getInstance();
        int choice;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\n******Welcome to the Employee Directory!******");
            System.out.println("Select one from the below options (numbers only)");
            System.out.println("1. Add employee");
            System.out.println("2. View all employees");
            System.out.println("3. Search an employee by ID");
            System.out.println("4. Search an employee by Name");
            System.out.println("5. Update Employee");
            System.out.println("6. Filter by Department");
            System.out.println("7. Filter by Salary Range");
            System.out.println("8. Sort by Name");
            System.out.println("9. Sort by Salary");
            System.out.println("10. Sort by Joining Date");
            System.out.println("11. Count employees by Department");
            System.out.println("12. Delete Employee");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    LocalDate jDate = LocalDate.now(); //For simplicity
                    service.addEmployee(name, dept, salary, jDate);
                    break;

                case 2:
                    System.out.println("----- Employee Directory -----");
                    service.printEmployees();
                    break;

                case 3:
                    System.out.print("Enter the Id to be searched: ");
                    int id = sc.nextInt();
                    service.searchById(id);
                    break;

                case 4:
                    System.out.print("Enter the Name to be searched: ");
                    String sName = sc.nextLine();
                    service.searchByName(sName);
                    break;

                case 5:
                    System.out.print("Enter the id for details are to be updated: ");
                    int uId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter updated Emp name: ");
                    String uName = sc.nextLine();
                    System.out.print("Enter updated Department: ");
                    String uDept = sc.nextLine();
                    System.out.print("Enter updated Salary: ");
                    double uSalary = sc.nextDouble();
                    service.updateEmp(uId, uName, uDept, uSalary);
                    break;

                case 6:
                    System.out.print("Enter the department to be filtered: ");
                    String sDept = sc.nextLine();
                    service.filterByDept(sDept);
                    break;

                case 7:
                    System.out.print("Enter starting salary range: ");
                    double sRange = sc.nextDouble();
                    System.out.print("Enter ending salary range: ");
                    double eRange = sc.nextDouble();
                    service.filterBySalaryRange(sRange, eRange);
                    break;

                case 8:
                    System.out.print("Type '0' for Ascending '1' for Descending: ");
                    int sortNameOrder = sc.nextInt();
                    System.out.println("Sorted by Name");
                    service.sortByName(sortNameOrder);
                    break;

                case 9:
                    System.out.print("Type '0' for Ascending '1' for Descending: ");
                    int sortSalaryOrder = sc.nextInt();
                    System.out.println("Sorted by Salary");
                    service.sortBySalary(sortSalaryOrder);
                    break;

                case 10:
                    System.out.print("Type '0' for Ascending '1' for Descending: ");
                    int sortDateOrder = sc.nextInt();
                    System.out.println("Sorted by Joining Date");
                    service.sortByJoinDate(sortDateOrder);
                    break;

                case 11:
                    System.out.println("Employees per department");
                    service.countByDept();
                    break;

                case 12:
                    System.out.print("Enter Employee Id: ");
                    int deleteEmp = sc.nextInt();
                    service.deleteEmp(deleteEmp);
                    break;

                default:
                    System.out.print("Invalid input, enter a valid choice: ");
                    if(sc.hasNextLine()){
                        choice = sc.nextInt();
                    }
                    else{
                        sc.nextLine();
                        choice = -1;
                    }
                    break;
            }
        } while (choice != 13);

        System.out.println("Directory closed...");
    }
}