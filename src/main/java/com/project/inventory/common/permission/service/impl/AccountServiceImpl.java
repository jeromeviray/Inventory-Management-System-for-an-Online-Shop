package com.project.inventory.common.permission.service.impl;

import com.project.inventory.common.permission.verificationCode.model.ResetPassword;
import com.project.inventory.common.permission.verificationCode.service.VerificationCodeService;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.model.AccountDto;
import com.project.inventory.common.permission.model.ChangePassword;
import com.project.inventory.common.permission.repository.AccountRepository;
import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.common.permission.role.model.RoleType;
import com.project.inventory.common.permission.role.service.RoleService;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.service.CommonService;
import com.project.inventory.common.user.model.RequestUserAccount;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.common.user.service.UserService;
import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.mail.service.MailService;
import com.project.inventory.store.website.service.StoreInformationService;
import com.project.inventory.webSecurity.oauth2.AuthProvider;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.HashSet;
import java.util.Set;

@Service( value = "accountServiceImpl" )
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger( AccountServiceImpl.class );
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private StoreInformationService storeInformationService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CommonService commonService;

    @Override
    public Account getAccountById( int accountId ) throws NotFoundException {
        return accountRepository.findById( accountId );
    }


    @Override
    public void saveUserAccount( UserAccount userAccount ) {
        if ( mailService.validateEmail( userAccount.getEmail() ) ) {
            try {
                //save user role
                Role role = roleService.getRoleByRoleName( RoleType.CUSTOMER );
                Set<Role> authority = new HashSet<>();
                authority.add( role );
                // save account
                Account account = new Account();
                account.setUsername( userAccount.getUsername() );
                account.setAuthProvider( AuthProvider.local );
                account.setEmail( userAccount.getEmail() );
                account.setPassword( passwordEncoder.encode( userAccount.getPassword() ) );
                account.setStoreInformation( storeInformationService.getStoreInformation() );
                account.setRoles( authority );
                account.setNotBanned( false );
                Account savedAccount = accountRepository.save( account );

                //save user information account
                User user = new User();
                user.setFirstName( userAccount.getFirstName() );
                user.setLastName( userAccount.getLastName() );
//                user.setPhoneNumber( userAccount.getPhoneNumber() );

                userService.saveUserInformation( savedAccount, user );

                //send verification code after saving the account
                verificationCodeService.sendVerificationCode( account );
            } catch ( Exception e ) {
                throw new InvalidException( e.getMessage() );
            }
        } else {
            throw new InvalidException( "Your Email is Invalid Format." );
        }
    }

    @Override
    public Account saveEmployeeAccount( String username, String password, String email, RoleType roleType ) {
        if ( mailService.validateEmail( email ) ) {
            Account account = new Account();
            account.setAuthProvider( AuthProvider.local );
            account.setUsername( username );
            account.setPassword( passwordEncoder.encode( password ) );
            account.setEmail( email );
            account.setStoreInformation( storeInformationService.getStoreInformation() );

            Role getRole = roleService.getRoleByRoleName( roleType );
            Set<Role> authority = new HashSet<>();
            authority.add( getRole );

            account.setRoles( authority );

            return accountRepository.save( account );
        } else {
            throw new InvalidException( "Your Email is Invalid Format." );
        }
    }

    @Override
    public Account getAccountByUsername( String username ) {
        return accountRepository.findByUsername( username );
    }

    @Override
    public void changePassword( ChangePassword changePassword ) {
        Account account = getAccountById( changePassword.getId() );
        if ( !comparePassword( account, changePassword.getCurrentPassword() ) ) {
            throw new InvalidException( "Mismatch Current Password. Please Try Again!" );
        } else if ( !changePassword.getPassword().equals( changePassword.getConfirmPassword() ) ) {
            throw new InvalidException( "Not match New Password. Please Try Again!" );
        }
        account.setPassword( passwordEncoder.encode( changePassword.getPassword() ) );
        accountRepository.save( account );
    }

    @Override
    public String changeUsername( int id, String username ) {
        Account account = getAccountById( id );
        account.setUsername( username );

        Account updatedUsernameAccount = accountRepository.save( account );

        return updatedUsernameAccount.getUsername();
    }

    @Override
    public AccountDto convertEntityToDto( Account account ) {
        return mapper.map( account, AccountDto.class );
    }

    @Override
    public Account convertDtoToEntity( AccountDto accountDto ) {
        return mapper.map( accountDto, Account.class );

    }

    public Boolean isNotBanned( Account account ) throws AccountLockedException {
        if ( !account.isNotBanned() ) {
            throw new AccountLockedException();
        }
        return true;
    }

    public Boolean isNotDeleted( Account account ) throws AccountLockedException {
        if ( !account.isNotDeleted() ) {
            throw new AccountLockedException();
        }
        return true;
    }

    public Boolean isNotBannedAndDeleted( Account account ) throws AccountLockedException {
        if ( !account.isNotBanned() && !account.isNotDeleted() ) {
            throw new AccountLockedException();
        }
        return true;
    }

    @Override
    public void deleteAccount( int id ) {
        logger.info( "Delete Account with Id: {}", id );
        Account account = getAccountById( id );
        account.setNotDeleted( false );
        accountRepository.save( account );
    }

    public void banAccount( int id ) {
        Account savedAccount = getAccountById( id );
        savedAccount.setNotBanned( false );
        accountRepository.save( savedAccount );
    }

    @Override
    public Account updateAccount( int id, RequestUserAccount userAccount ) {
        Account account = accountRepository.findById( id );
        account.setUsername( userAccount.getUsername() );
        account.setEmail( userAccount.getEmail() );

        return accountRepository.save( account );
    }

    protected boolean comparePassword( Account account, String currentPassword ) {
        return passwordEncoder.matches( currentPassword, account.getPassword() );
    }

    @Override
    public Account getAccountByEmail( String email ) {
        return accountRepository.findByEmail( email ).orElseThrow( () -> new NotFoundException( "Email not Found." ) );
    }

    @Override
    public void resetPassword( ResetPassword resetPassword ) {
        if ( !resetPassword.getPassword().equals( resetPassword.getConfirmPassword() ) )
            throw new InvalidException( "Not match New Password. Please Try Again!" );
        verificationCodeService.deleteToken( resetPassword.getToken() );
        Account account = getAccountById( resetPassword.getAccountId() );
        account.setPassword( passwordEncoder.encode( resetPassword.getPassword() ) );
        accountRepository.save( account );
    }

    @Override
    public void verifyAccount( int accountId ) {
        Account account = getAccountById( accountId );
        account.setNotBanned( true );
        accountRepository.save( account );
    }
}
