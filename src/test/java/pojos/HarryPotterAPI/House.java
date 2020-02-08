package pojos.HarryPotterAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class House {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mascot")
    @Expose
    private String mascot;
    @SerializedName("headOfHouse")
    @Expose
    private String headOfHouse;
    @SerializedName("houseGhost")
    @Expose
    private String houseGhost;
    @SerializedName("founder")
    @Expose
    private String founder;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("values")
    @Expose
    private List<String> values = null;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;

    public House(String id, String name, String mascot, String headOfHouse, String houseGhost, String founder, Integer v, String school, List<Member> members, List<String> values, List<String> colors) {
        this.id = id;
        this.name = name;
        this.mascot = mascot;
        this.headOfHouse = headOfHouse;
        this.houseGhost = houseGhost;
        this.founder = founder;
        this.v = v;
        this.school = school;
        this.members = members;
        this.values = values;
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mascot='" + mascot + '\'' +
                ", headOfHouse='" + headOfHouse + '\'' +
                ", houseGhost='" + houseGhost + '\'' +
                ", founder='" + founder + '\'' +
                ", v=" + v +
                ", school='" + school + '\'' +
                ", members=" + members +
                ", values=" + values +
                ", colors=" + colors +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(String headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public String getHouseGhost() {
        return houseGhost;
    }

    public void setHouseGhost(String houseGhost) {
        this.houseGhost = houseGhost;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

}
