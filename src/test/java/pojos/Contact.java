package pojos;

import com.google.gson.annotations.SerializedName;

public class Contact {

    private int contactId;
    private String phone;
    private String emailAddress;

    @SerializedName("premanentAddress")
    private String permanentAddress;


    public Contact(String phone, String emailAddress, String permanentAddress) {
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.permanentAddress = permanentAddress;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", phone='" + phone + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                '}';
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
}
