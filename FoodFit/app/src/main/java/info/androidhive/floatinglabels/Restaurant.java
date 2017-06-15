package info.androidhive.floatinglabels;

/**
 * Created by admin on 14/11/2016.
 */
public class Restaurant {

    private String rid;
    private String rname;
    private String email;
    private String address;
    private String phone;
    private String city;
    private String zipcode;
    private  String[] food_name;
    private  String[] food_price;

    Restaurant(){
        rid = "-1";
        rname = "kaushal";
        email = "kaushal@gmail.com";
        address = "103 / 1830 krishnanagar";
        phone = "8866456322";
        city = "Ahmedabad";
        zipcode = "382345";
    }

    public String getRid() {
        return rid;
    }


    public void setRest_menu(int j, String[] food_name, String[] food_price){
        this.food_name = new String[j];
        this.food_price = new String[j];

        for(int i=0;i<j;i++) {
            this.food_name[i] = food_name[i];
            this.food_price[i] = food_price[i];
        }
    }
    public String[] getFood_name(){
        return food_name;
    }

    public String[] getFood_price(){
        return food_price;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
