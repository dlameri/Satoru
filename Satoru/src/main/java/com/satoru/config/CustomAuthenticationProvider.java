package com.satoru.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.satoru.domain.UserStatus;
import com.satoru.service.UserService;

/*
 Extend AbstractUserDetailsAuthenticationProvider when you want to
 prehandle authentication, as in throwing custom exception messages,
 checking status, etc. 
 */
@Component("authenticationProvider")
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired UserService userService;
	
    @Autowired private PasswordEncoder encoder;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
	}

	@Override
	public UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        if (!StringUtils.hasText(password)) {
        	logger.warn("Email {}: no password provided", email);
            throw new BadCredentialsException("Please enter password");
        }

        com.satoru.domain.User user = userService.getByEmail(email);
        if (user == null) {
        	logger.warn("Email {} password {}: user not found", email, password);
            throw new UsernameNotFoundException("Invalid Login");
        }
        
        if (!encoder.matches(password, user.getPassword())) {
        	logger.warn("Email {} password {}: invalid password", email, password);
            throw new BadCredentialsException("Invalid Login");
        }
        
        if (!(UserStatus.STATUS_APPROVED.equals(user.getStatus()))) {
        	logger.warn("Email {}: not approved", email);
            throw new BadCredentialsException("User has not been approved");
        }
        if (!user.getEnabled()) {
        	logger.warn("Email {}: disabled", email);
            throw new BadCredentialsException("User disabled");
        }

        final List<GrantedAuthority> auths;
        if (!user.getRoles().isEmpty()) {
	    	auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRolesCSV());
        } else {
        	auths = AuthorityUtils.NO_AUTHORITIES;
        }

        return new User(email, password, user.getEnabled(), // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                auths);
	}

}
