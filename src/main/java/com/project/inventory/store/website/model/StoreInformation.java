package com.project.inventory.store.website.model;

import javax.persistence.*;

@Entity
@Table( name = "store_information" )
public class StoreInformation {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "store_name" )
    private String storeName;

    @Column( name = "domain_name" )
    private String domainName;

    @Column( name = "logo" )
    private String logo;

    @Column( name = "location" )
    private String location;

    @Column( name = "description" )
    private String description;

    @Column(name = "contact_number", columnDefinition = "VARCHAR(15)")
    private String contactNumber;

    @Column(name = "email", columnDefinition = "VARCHAR(60)")
    private String email;

    public StoreInformation() {
    }

    public StoreInformation( String storeName, String domainName, String logo, String location, String description, String contactNumber, String email ) {
        this.storeName = storeName;
        this.domainName = domainName;
        this.logo = logo;
        this.location = location;
        this.description = description;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName( String storeName ) {
        this.storeName = storeName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName( String domainName ) {
        this.domainName = domainName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo( String logo ) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber( String contactNumber ) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
