package com.arafat.friends.mode;

public class Profile {
    String portrait;
    String fullPortrait;
    String fullName;
    String address;
    String city;
    String state;
    String country;
    String email;
    String cellPhone;

    public Profile(String portrait, String fullPortrait, String fullName, String address, String city, String state, String country, String email, String cellPhone) {
        this.portrait = portrait;
        this.fullPortrait = fullPortrait;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getFullPortrait() {
        return fullPortrait;
    }

    public void setFullPortrait(String fullPortrait) {
        this.fullPortrait = fullPortrait;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
