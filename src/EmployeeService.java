import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeService {
    public static int empId = 10;

//  final keyword: ensures that the list always points to the same object, avoiding bugs caused by accidentally replacing the list.
    private final List<Employee> employeeDir = new ArrayList<>();

    public static EmployeeService instance;

    //Used double-checked locking for Thread safety
    public static EmployeeService getInstance() {
        if(instance == null){
            synchronized (EmployeeService.class){
                if(instance == null) {
                    instance = new EmployeeService();
                }
            }
        }
            return instance;
    }

    public void addEmployee(String name, String dept, Double salary, LocalDate jDate){
        int id = ++empId;
        if(isValidInput(name) && isValidInput(dept) && salary >= 0){
            Employee emp = new Employee(id, name, dept, salary, jDate);
            employeeDir.add(emp);
            System.out.println("Employee added successfully "+emp);
        }
        else{
            System.out.println("Invalid details found!!");
        }
    }

    public void searchById(int id){
        Optional<Employee> employee = employeeDir.stream()
                .filter(e -> e.getId() == id).findFirst();

        employee.ifPresentOrElse(
                emp -> System.out.println("Employee found: "+emp),
                () -> System.out.println("Employee not found :(")
        );
    }

    public void searchByName(String name){
        List<Employee> empList = employeeDir.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name)).toList();
        if(!empList.isEmpty()){
            System.out.println(empList);
        }
        else{
            System.out.println("Employee not found :(");
        }
    }

    public void filterByDept(String dept){
        List<Employee> empList = employeeDir.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(dept)).toList();
        if(!empList.isEmpty()){
            System.out.println(empList);
        }
        else{
            System.out.println("Department not found :(");
        }
    }

    public void filterBySalaryRange(double start, double end){
        if(start > end || start < 0 || end < 0){
            System.out.println("Invalid salary range provided. Start should be <= end and both should be positive");
            return;
        }

        Predicate<Employee> greaterThanStart = e -> e.getSalary() > start;
        Predicate<Employee> lessThanEnd = e -> e.getSalary() < end;
        List<Employee> empList = employeeDir.stream().filter(greaterThanStart.and(lessThanEnd)).toList();
        if(!empList.isEmpty()){
            System.out.println(empList);
        }
        else{
            System.out.println("Invalid range for salary :(");
        }
    }

    public void sortByName(int order){
        List<Employee> sortedList = order == 1
                ? employeeDir.stream().sorted(Comparator.comparing(Employee::getName).reversed()).toList()
                : employeeDir.stream().sorted(Comparator.comparing(Employee::getName)).toList();

        System.out.println(sortedList);
    }

    public void sortBySalary(int order){
        List<Employee> sortedList = order == 1
                ? employeeDir.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList())
                : employeeDir.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList());

        System.out.println(sortedList);
    }

    public void sortByJoinDate(int order){
        List<Employee> sortedList = order == 1
                ? employeeDir.stream().sorted(Comparator.comparing(Employee::getJoiningDate).reversed()).toList()
                : employeeDir.stream().sorted(Comparator.comparing(Employee::getJoiningDate)).toList();

        System.out.println(sortedList);
    }

    public void countByDept(){
        System.out.println(employeeDir.stream()
                .collect(Collectors.toMap(Employee::getDepartment, v-> 1, Integer::sum)));
    }

    public void deleteEmp(int id){
        if(employeeDir.removeIf(e -> e.getId() == id)){
            System.out.println("Employee deleted successfully!!");
        }
        else{
            System.out.println("Unable to delete it..");
        }
    }

    public void updateEmp(int id, String name, String dept, double salary){
        Employee employee = employeeDir.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
       if(employee != null){
           if(isValidInput(name) && isValidInput(dept) && salary >= 0){
               employee.setName(name);
               employee.setDepartment(dept);
               employee.setSalary(salary);
               System.out.println("Details updated successfully for id: "+id);
               System.out.println(employee);
           }
           else{
               System.out.println("Provide valid details to update!");
           }
       }
       else{
           System.out.println("Employee not found :(");
       }
    }

    public void printEmployees(){
        if(!employeeDir.isEmpty()){
            employeeDir.forEach(System.out::println);
        }
        else{
            System.out.println("No Employees found!!");
        }
    }

    public static boolean isValidInput(String input){
        return input.matches("[a-zA-Z\\s&-]+");
    }

    public boolean isDirectoryEmpty(){
        if (employeeDir.isEmpty()) {
            System.out.println("No employees in the directory!");
            return true;
        }
        return false;
    }
}
