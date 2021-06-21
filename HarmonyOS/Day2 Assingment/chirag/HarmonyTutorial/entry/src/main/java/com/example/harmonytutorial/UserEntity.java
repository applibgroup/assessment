
package com.example.harmonytutorial;
import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity extends OrmObject {
    @PrimaryKey
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String mobile;
    private boolean Male = false;
    private boolean Female = false;
    private boolean Other = false;

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return  lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setMale(boolean male){
        this.Male = male;
    }
    public boolean isMale(){
        return this.Male;
    }

    public void setFemale(boolean female){
        this.Female = female;
    }
    public boolean isFemale(){
        return this.Female;
    }

    public void setOther(boolean other){
        this.Other = other;
    }
    public boolean isOther(){
        return this.Other;
    }
}
