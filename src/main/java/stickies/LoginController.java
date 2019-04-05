package stickies;

import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;
	@RequestMapping(path="/login", method =RequestMethod.POST)
	public int login(@RequestBody LoginRequest loginRequest)
	{

			String query = "select password from users where username= ?";
			List<String> passwords = jdbcTemplate.queryForList(query, String.class, loginRequest.username);
			
			if(passwords.size()>0)
			{
				if(passwords.get(0).equals(loginRequest.password))
					return 0; //was successful
				else
					return 2; //password didnt match
			}
			else {
				//username didnt exist
				return 1; //code means that username was not found in the database
			}
		

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


