package stickies;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@CrossOrigin
public class LoginController {

	//making the sessions map protected so the other controllers can access it as well to verify the user that is logged in
	protected static final ConcurrentMap<String, Integer> sessions = new ConcurrentHashMap<String, Integer>();

	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@ResponseBody
	@RequestMapping(path = "/login")
	public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

		String query = "select password from users where username= ?";
		List<String> passwords = jdbcTemplate.queryForList(query, String.class, loginRequest.username);

		if (passwords.size() > 0) {
			if (passwords.get(0).equals(loginRequest.password)) {
				
				UUID sessionId = UUID.randomUUID();
				sessions.put(sessionId.toString(), getUserID(loginRequest.username)); //maps the sessionID to the userID
				Cookie sessionCookie = new Cookie("sessionId", sessionId.toString());
				Cookie usernameCookie = new Cookie("userName", loginRequest.username);
				response.addCookie(sessionCookie);
				response.addCookie(usernameCookie);

				response.setStatus(HttpStatus.OK.value()); // 200
				
			} else
				response.setStatus(HttpStatus.UNAUTHORIZED.value()); // password didn't match error: 401
		} else
			response.setStatus(HttpStatus.UNAUTHORIZED.value()); // no passwords returned for username entered (user is
																	// not in our system)error: 401

	}

	private int getUserID(String username) {
		String query = "select UserID from users where Username =? ";
		List<Integer> userids = jdbcTemplate.queryForList(query, Integer.class, username);
		if (userids.size() > 0) {
			return userids.get(0);
		}
		// should never happen since user is already logged in...
		else
			return -1;
	}

	@RequestMapping(path = "/logout")
	public void logout(HttpServletResponse response) {

		Cookie sessionCookie = new Cookie("sessionId", null);
		sessionCookie.setMaxAge(0);
		response.addCookie(sessionCookie);

		Cookie userCookie = new Cookie("userName", null);
		userCookie.setMaxAge(0);
		response.addCookie(userCookie);

	}

	static class LoginRequest {
		String username;
		String password;

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String pass) {
			this.password = pass;
		}

	}

}
