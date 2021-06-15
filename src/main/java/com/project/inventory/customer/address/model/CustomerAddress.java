package com.project.inventory.customer.address.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.jsonView.View;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.permission.model.Account;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "customer_address_info")
@Transactional
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_address_id")
    @JsonView(value = {View.CustomerAddress.class})
    private int id;

    @Column(name = "first_name")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String firstName;

    @Column(name = "last_name")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String lastName;

    @Column(name = "phone_number")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private int phoneNumber;

    @Column(name = "postal_code")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private int postalCode;

    @Column(name = "region")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String region;

    @Column(name = "city")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String city;

    @Column(name = "province")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String province;

    @Column(name = "barangay")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String barangay;

    @Column(name = "street")
    @JsonView(value = {View.PlaceOrder.class, View.CustomerAddress.class})
    private String street;

    @Column(name = "is_default")
    private boolean isDefault;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "customerAddress", fetch = FetchType.LAZY)
    @JsonIgnore
    private ShoppingOrder shoppingOrder;

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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
        return getId() == that.getId() && getPhoneNumber() == that.getPhoneNumber() && getPostalCode() == that.getPostalCode() && isDefault() == that.isDefault() && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getRegion(), that.getRegion()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getProvince(), that.getProvince()) && Objects.equals(getBarangay(), that.getBarangay()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getAccount(), that.getAccount()) && Objects.equals(shoppingOrder, that.shoppingOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getPostalCode(), getRegion(), getCity(), getProvince(), getBarangay(), getStreet(), isDefault(), getAccount(), shoppingOrder);
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
                ", shoppingOrder=" + shoppingOrder +
                '}';
    }
}
