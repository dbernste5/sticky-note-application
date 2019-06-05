package stickies;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class StickyController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(path = "/stickynote")
	public void addSticky(@RequestBody BasicSticky sticky, HttpServletResponse response, @CookieValue("sessionId") String sessionId) {
		int userID = LoginController.sessions.get(sessionId);
		
		String query = "Insert into basicStickies (userid, title, body) values(?,?,?)";
		int count = jdbcTemplate.update(query, userID, sticky.getTitle(), sticky.getBody());

		if (count == 1) {
			// success
			response.setStatus(HttpStatus.OK.value());
		} else {
			// didnt work...
			response.setStatus(HttpStatus.BAD_REQUEST.value()); // 400
		}
	}

	
	@RequestMapping(path = "/deleteStickies", method = RequestMethod.POST)
	public void deleteStickies(@RequestBody List<String> stickies, HttpServletResponse response) {
		int count=-1;
		for(String sticky : stickies) {
				String query = "delete from basicStickies where StickyId=?";
				count=jdbcTemplate.update(query, sticky);
		}
		
		if(count==-1) {
			//stickies is null
			response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
		}
		else {
			response.setStatus(HttpStatus.OK.value()); // 200
		}
	}
	
	
	@RequestMapping(path= "/editStickies", method = RequestMethod.POST)
	public void editStickies(@RequestBody List<StickyInfo> newInfo, HttpServletResponse response) {
		for(StickyInfo s : newInfo) {
			String query = "update basicStickies set body = ? where StickyId = ?";
			jdbcTemplate.update(query, s.text, s.id);
		}
		
		response.setStatus(HttpStatus.OK.value());
	}
	
	
	@ResponseBody
	@RequestMapping(path = "/userStickies")
	public List<Map<String, Object>> getAllStickies(@CookieValue("sessionId") String sessionId, HttpServletResponse response) {
		int userID = LoginController.sessions.get(sessionId);
		String query = "SELECT Title, Body, StickyId from basicStickies where UserID=? order by StickyId";
		List<Map<String, Object>> stickies = jdbcTemplate.queryForList(query, userID);

		if (stickies.size() > 0) {
			response.setStatus(HttpStatus.OK.value()); // 200
			return stickies;
		} else {
			response.setStatus(HttpStatus.NO_CONTENT.value()); // 204
			return null;
		}
	}

	
	
	static class StickyInfo {
		String id;
		String text;

		public void setId(String id) {
			this.id = id;
		}

		public void setText(String text) {
			this.text = text;
		}

	}
}
