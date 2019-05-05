package stickies;

import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

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
public class SaveUserToDBController
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	Environment env;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@RequestMapping(path="/user", method =RequestMethod.POST)
	public void getUser(@RequestBody User newUser)
	{
		
		try
		{
			if(saveUser(newUser))
			{
				log.info("User saved to DB successfully");
			}
			else {
				log.info("Unsuccessful...");
			}
		}
		catch(SQLException e)
		{
			log.info(e.getMessage());
		}
		
	}
	private boolean saveUser(User user) throws SQLException
	{
		String query = "Insert into users (username, password, firstname, lastname, email, phone) values(?,?,?,?,?,?)";
		log.info(""+jdbcTemplate);
		int count =jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNum());
		
		//check that result set affected rows- was successful
		
		return count==1;
	}
	
}
