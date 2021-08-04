package com.project.inventory.webSecurity.oauth2.service;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.repository.AccountRepository;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.role.model.RoleType;
import com.project.inventory.common.persmision.role.service.RoleService;
import com.project.inventory.webSecurity.oauth2.AccountPrinciple;
import com.project.inventory.webSecurity.oauth2.AuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Logger logger = LoggerFactory.getLogger( CustomOAuth2UserService.class );
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleService roleService;


    @Override
    public OAuth2User loadUser( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser( userRequest );
        try {
            return authenticateOAuth2User( userRequest, user );
        } catch( Exception e ) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException( e.getMessage(), e.getCause() );
        }
    }

    private OAuth2User authenticateOAuth2User( OAuth2UserRequest request, OAuth2User oAuth2User ) {

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                request.getClientRegistration()
                        .getRegistrationId(),
                oAuth2User.getAttributes() );

        if( StringUtils.isEmpty( oAuth2UserInfo.getEmail() ) ) {
            throw new OAuth2AuthenticationException( "Email not found from OAuth2 provider" );
        }

        Optional<Account> getAccount = accountRepository.findByEmail( oAuth2UserInfo.getEmail() );
        Account account;
        if( getAccount.isPresent() ) {
            account = getAccount.get();
            if( !account.getAuthProvider().equals( AuthProvider.valueOf( request.getClientRegistration().getRegistrationId() ) ) ) {
                throw new OAuth2AuthenticationException( "Looks like you're signed up with " +
                        account.getAuthProvider() + " account. Please use your " + account.getAuthProvider() +
                        " account to login." );
            }
            account = updateAccount( account, oAuth2UserInfo );
        } else {
            account = registerOAuth2Account( request, oAuth2UserInfo );
        }
        return AccountPrinciple.create( account, oAuth2User.getAttributes() );
    }

    @Transactional
    private Account registerOAuth2Account( OAuth2UserRequest request, OAuth2UserInfo oAuth2UserInfo ) {
        String username = oAuth2UserInfo.getEmail().substring( 0, oAuth2UserInfo.getEmail().indexOf( "@" ) );

        Role role = roleService.getRoleByRoleName( RoleType.USER );
        Set<Role> authority = new HashSet<>();
        authority.add( role );

        Account account = new Account();
        account.setAuthProvider( AuthProvider.valueOf( request.getClientRegistration().getRegistrationId() ) );
        account.setUsername( username );
        account.setEmail( oAuth2UserInfo.getEmail() );
        account.setRoles( authority );
        return accountRepository.save( account );
    }


    private Account updateAccount( Account account, OAuth2UserInfo auth2UserInfo ) {
        String username = auth2UserInfo.getEmail().substring( 0, auth2UserInfo.getEmail().indexOf( "@" ) );
        account.setUsername( username );
        return accountRepository.save( account );
    }
}
