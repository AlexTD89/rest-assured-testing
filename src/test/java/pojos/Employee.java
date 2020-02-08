package pojos;

import com.google.gson.annotations.SerializedName;

public class Employee {

    /**
     * when the names of the variables doesn't match the once from json file
     * we use this annotation to match them with the local once
     */
    @SerializedName("employee_id")
    private String employeeid;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phoneNumber;
    public Employee(String employeeid, String firstName, String lastName, String email, String phoneNumber) {
        this.employeeid = employeeid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "employeeid='" + employeeid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
    public String getemployeeid() {
        return employeeid;
    }
    public void setemployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
    public String getfirstName() {
        return firstName;
    }
    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getlastName() {
        return lastName;
    }
    public void setlastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getphoneNumber() {
        return phoneNumber;
    }
    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
