package com.project.inventory.common.user.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.role.model.RoleType;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.user.model.RequestUserAccount;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.common.user.model.UserDto;
import com.project.inventory.common.user.repository.UserRepository;
import com.project.inventory.common.user.service.UserService;
import com.project.inventory.exception.notFound.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service( value = "userInformationServiceImpl" )
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger( UserServiceImpl.class );
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createUserAccount( UserAccount userAccount ) throws ParseException {
        logger.info( "{}", userAccount.getBirthday() );
        SimpleDateFormat pattern = new SimpleDateFormat( "YYYY-MM-dd" );
        Account account = accountService.saveEmployeeAccount( userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getEmail(), getRoleType( userAccount.getRole() ) );
        User user = new User();
        user.setFirstName( userAccount.getFirstName() );
        user.setLastName( userAccount.getLastName() );
//        user.setPhoneNumber( userAccount.getPhoneNumber() );
        user.setBirthday( pattern.format( pattern.parse( userAccount.getBirthday() ) ) );
        saveUserInformation( account, user );
    }

    @Override
    public void saveUserInformation( Account savedAccount, User user ) {
        Account account = accountService.getAccountById( savedAccount.getId() );
        user.setAccount( account );
        userRepository.save( user );
    }

    @Override
    public void saveUser( User user ) {
        userRepository.save( user );
    }

    @Override
    public void updateUserInformation( int id, RequestUserAccount userAccount ) throws ParseException {
        SimpleDateFormat pattern = new SimpleDateFormat( "YYYY-MM-dd" );

        User savedUser = getUserInformationById( id );
        savedUser.setFirstName( userAccount.getFirstName() );
        savedUser.setLastName( userAccount.getLastName() );
//        savedUser.setPhoneNumber( userAccount.getPhoneNumber() );
        savedUser.setBirthday( pattern.format( pattern.parse( userAccount.getBirthday() ) ) );
        userRepository.save( savedUser );
    }

    @Override
    public void deleteUserAccount( int id ) {
        accountService.deleteAccount( id );
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> users = new ArrayList<>();
        for( User user : userRepository.findAll() ) {
            users.add( convertEntityToDto( user ) );
        }
        return users;
    }

    @Override
    public Page<UserDto> getUsersByRole( String query, String role, Pageable pageable ) {
        Page<User> users = userRepository.findAll( query, role, pageable );
        List<UserDto> userPageRecords = new ArrayList<>();
        for( User user : users.getContent() ) {
            UserDto userDto = convertEntityToDto( user );
            userPageRecords.add( userDto );
        }
        return new PageImpl<>( userPageRecords, pageable, users.getTotalElements() );
    }

    @Override
    public User getUserInformationById( int id ) {
        return userRepository.findById( id )
                .orElseThrow(() -> new NotFoundException( "User not found." ));
    }

    @Override
    public User getUserInformationByAccountId( int accountId ) {
        return userRepository.findByAccountId( accountId )
                .orElseThrow( () -> new NotFoundException( "User not found." ) );
    }

    @Override
    public UserDto convertEntityToDto( User user ) {
        return modelMapper.map( user, UserDto.class );
    }

    @Override
    public User convertDtoTOEntity( UserDto userDto ) {
        return modelMapper.map( userDto, User.class );
    }

    private RoleType getRoleType( String role ) {

        if( role.equals( RoleType.SUPER_ADMIN.name() ) ) {
            return RoleType.SUPER_ADMIN;
        } else if( role.equals( RoleType.ADMIN.name() ) ) {
            return RoleType.ADMIN;
        } else {
            return null;
        }
    }
}
