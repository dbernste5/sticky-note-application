package stickies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class StickyController
{
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping(path="/addStickynote")
	public void addSticky(@RequestBody BasicSticky sticky, HttpServletResponse response )
	{
		String query = "Insert into basicStickies (userid, title, body) values(?,?,?)";
		
		int count =jdbcTemplate.update(query, sticky.getUserID(), sticky.getTitle(), sticky.getBody());
		log.info("got from react" + sticky);
		log.info("title: "+ sticky.getTitle());
		log.info("body: "+ sticky.getTitle());
		log.info("userid: "+ sticky.getUserID());
		
		if(count==1)
		{
			//success
			response.setStatus(HttpStatus.OK.value());
		}
		else
		{
			//didnt work...
			response.setStatus(HttpStatus.BAD_REQUEST.value());	//400
		}
	}

}
