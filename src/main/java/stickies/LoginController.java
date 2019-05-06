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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class LoginController {

	private static final ConcurrentMap<String,String> sessions = new ConcurrentHashMap<String,String>();
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;
	@RequestMapping(path="/login")
	public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
	{

			String query = "select password from users where username= ?";
			List<String> passwords = jdbcTemplate.queryForList(query, String.class, loginRequest.username);
			
			if(passwords.size()>0)
			{
				if(passwords.get(0).equals(loginRequest.password))
				{
					UUID sessionId = UUID.randomUUID();
					sessions.put(sessionId.toString(),loginRequest.username);
					response.addCookie(new Cookie("sessionId", sessionId.toString()));
					response.setStatus(HttpStatus.OK.value()); //200
				}
				else
					response.setStatus(HttpStatus.UNAUTHORIZED.value()); //password didnt match error: 401
			}
			else
				response.setStatus(HttpStatus.UNAUTHORIZED.value()); //no passwords returned for username entered (user is not in our system)error: 401
	
	}
	
	static class LoginRequest{
		String username;
		String password;
		
		public void setUsername(String username){
			this.username = username;
		}
		
		public void setPassword(String pass){
			this.password = pass;
		}
		
		
		
	}
	



}


