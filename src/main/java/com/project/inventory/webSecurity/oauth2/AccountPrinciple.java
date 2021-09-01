package com.project.inventory.webSecurity.oauth2;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.role.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class AccountPrinciple implements OAuth2User, UserDetails {
    private String id;
    private String email;
    private String password;
    private List<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public AccountPrinciple() {
    }

    public AccountPrinciple( String id, String email, String password, List<? extends GrantedAuthority> authorities ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public AccountPrinciple( String id, String email, String password, List<? extends GrantedAuthority> authorities, Map<String, Object> attributes ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static AccountPrinciple create( Account account ) {
        List<GrantedAuthority> authorities;
        if(account.getRoles().isEmpty()){

            authorities = Collections.singletonList( new SimpleGrantedAuthority( "ROLE_USER" ) );
        }else {
            authorities = new ArrayList<>();
            for( Role role : account.getRoles()){
                authorities.add( new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()) );
            }
        }


        return new AccountPrinciple( account.getEmail(),
                account.getEmail(),
                account.getPassword(),
                authorities);
    }
    public static AccountPrinciple create(Account account, Map<String, Object> attributes) {
        AccountPrinciple accountPrinciple = AccountPrinciple.create(account);
        accountPrinciple.setAttributes(attributes);
        return accountPrinciple;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return getUsername();
    }

}
