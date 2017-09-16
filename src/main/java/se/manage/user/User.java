package se.manage.user;



import javax.persistence.*;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

@Entity
public class User {
    @Id
//    private String account = "";
//    private String password = "";
//    private String fullName = "";



    private String account = "";
    private String password = "";
    private String registerDate = "";
    private String name = "";
    private String sex = "";
    private String identity = "";
    private String address = "";
    private String profession = "";
    private String education = "";
    private String company = "";
    private String phone = "";
    private String agent = "";

    private String legalNumber = "";
    private String operationNumber = "";
    private String executorName = "";
    private String executorIdentity = "";
    private String executorPhone = "";
    private String executorAddress = "";

    private String frozenFlag = "false";
    private String corperationFlag = "false";

    private String token = "";


    public User() {
    }

    public User(String account , String password, String registerDate, String name, String sex,
                String identity, String address, String profession, String education,
                String company, String phone, String agent, String legalNumber, String operationNumber,
                String executorName, String executorIdentity, String executorPhone, String executorAddress, String corperationFLag, String token
    ) {
        this.account = account;
        this.password = password;
        this.registerDate = registerDate;
        this.name=name;
        this.sex=sex;
        this.identity=identity;
        this.address=address;
        this.profession=profession;
        this.education=education;
        this.company=company;
        this.phone=phone;
        this.agent=agent;
        this.legalNumber=legalNumber;
        this.operationNumber=operationNumber;
        this.executorName=executorName;
        this.executorIdentity=executorIdentity;
        this.executorPhone=executorPhone;
        this.executorAddress=executorAddress;
        this.corperationFlag=corperationFLag;
        this.token=token;
    }

    public void setAccount (String account) {this.account= account;}
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setIdentity(String identity) {
        this.identity =identity ;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public void setEducation(String education) {
        this.education =education ;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAgent(String agent) {
        this.agent = agent;
    }
    public void setLegalNumber(String legalNumber) {
        this.legalNumber =legalNumber ;
    }
    public void setOperationNumber(String operationNumber) {
        this.operationNumber =operationNumber ;
    }
    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }
    public void setExecutorIdentity(String executorIdentity) {
        this.executorIdentity =executorIdentity ;
    }
    public void setExecutorPhone(String executorPhone) {
        this.executorPhone =executorPhone ;
    }
    public void setExecutorAddress(String executorAddress) {
        this.executorAddress = executorAddress;
    }
    public void setCorperationFlag(String corperationFLag) {this.corperationFlag = corperationFLag;}
    public void setToken(String token){this.token=token;}

    public String getAccount() {
        return this.account;
    }
    public String getPassword() {
        return this.password;
    }
    public String getRegisterDate() {
        return this.registerDate;
    }
    public String getSex() {
        return this.sex;
    }
    public String getIdentity() {
        return this.identity;
    }
    public String getAddress() {
        return this.address;
    }
    public String getProfession() {
        return this.profession;
    }
    public String getEducation() {
        return this.education;
    }
    public String getCompany() {
        return this.company;
    }
    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getAgent() {
        return this.agent;
    }
    public String getLegalNumber() {
        return this.legalNumber;
    }
    public String getOperationNumber() {
        return this.operationNumber;
    }
    public String getExecutorName() {
        return this.executorName;
    }
    public String getExecutorIdentity() {
        return this.executorIdentity;
    }
    public String getExecutorPhone() {
        return this.executorPhone;
    }
    public String getExecutorAddress() {
        return this.executorAddress;
    }
    public String getCorperationFlag() {return this.corperationFlag;}
    public String getToken() {return this.token;}

    public void freeze(){this.frozenFlag="true";}
    public void unFreeze(){this.frozenFlag="false";}
    public String getFrozenFlag(){return frozenFlag;}
    public void setCor(){this.corperationFlag="true";}



/*
    public String toString() {
        return String.format("{account:%s, password:%s, fullName:%s}", this.account, this.password, this.fullName);
    }
*/


}