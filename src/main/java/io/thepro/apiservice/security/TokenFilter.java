package io.thepro.apiservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	RestSecurityProperties restSecProps;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		if (!restSecProps.getAllowedpublicapis().contains(path)) {
			String idToken = securityUtils.getTokenFromRequest(request);
			FirebaseToken decodedToken = null;
			try {
				decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
			} catch (FirebaseAuthException e) {
				String message = e.getLocalizedMessage();
				LOGGER.error("Firebase Exception: " + message);
			}
			if (decodedToken != null) {
				User user = new User();
				user.setUid(decodedToken.getUid());
				user.setName(decodedToken.getName());
				user.setEmail(decodedToken.getEmail());
				user.setPicture(decodedToken.getPicture());
				user.setIssuer(decodedToken.getIssuer());
				user.setEmailVerified(decodedToken.isEmailVerified());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						user, decodedToken, null);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}
