package pl.ultrakino;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.ultrakino.model.User;

import java.security.Principal;
import java.util.Optional;

public class Utils {

	public static String jsonError(String msg) {
//		return JsonNodeFactory.instance.objectNode().put("error", msg);
		return "{" +
				"\"error\":\"" + msg + "\"" +
				"}";
	}

	public static ResponseEntity URISyntaxExceptionResponse() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError("URL parsing error"));
	}

	/**
	 * Checks whether a given authentication object's authorities contain 'ROLE_USER'
	 * @param auth Authentication object to be examined. Can be null.
	 * @return true if {@code auth} is not null and it contains a 'ROLE_USER' authority. false otherwise.
	 */
	public static boolean isUser(Authentication auth) {
		return auth != null && auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_USER"));
	}

	public static boolean isAdmin(UserDetails user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"));
	}

}
