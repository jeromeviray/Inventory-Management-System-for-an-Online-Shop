package com.project.inventory.customer.address.model;

import com.project.inventory.common.permission.model.Account;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "customer_addresses")
@Transactional
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "postalCode", nullable = false, columnDefinition = "int(7)")
    private int postalCode;

    @Column(name = "region", nullable = false, length = 50)
    private String region;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Column(name = "barangay", nullable = false, length = 100)
    private String barangay;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "isDefault", columnDefinition = "TINYINT(1) default 0", nullable = false)
    private boolean isDefault;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAddress)) return false;
        CustomerAddress that = (CustomerAddress) o;
        return getId() == that.getId() && getPhoneNumber() == that.getPhoneNumber() && getPostalCode() == that.getPostalCode() && isDefault() == that.isDefault() && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getRegion(), that.getRegion()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getProvince(), that.getProvince()) && Objects.equals(getBarangay(), that.getBarangay()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getAccount(), that.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getPostalCode(), getRegion(), getCity(), getProvince(), getBarangay(), getStreet(), isDefault(), getAccount());
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", postalCode=" + postalCode +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", barangay='" + barangay + '\'' +
                ", street='" + street + '\'' +
                ", isDefault=" + isDefault +
                ", account=" + account +
                '}';
    }
}
