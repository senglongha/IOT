package com.keylesson.DaoClasses;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keylesson.EntityClasses.Data;
import com.keylesson.EntityClasses.Location;
import com.keylesson.EntityClasses.User;
import com.keylesson.EntityClasses.User_Registration;
import com.keylesson.ModelClasses.boardreg;
import com.keylesson.ModelClasses.event;



public interface usersDao {
	public List<Map<String,Object>> getAllRegion();
	public List<User_Registration> getUsers();
	public List<Map<String, Object>> getSource();
	public List<Map<String,Object>> getAllLocation();
	public List<Map<String, Object>> getRegion(String id);
	public List<Map<String, Object>> getRoom(String id);
	public Map<String, Object> getReport(String id, String export_from, String export_until);
	public boolean getEvent(event event);
	public List<Map<String, Object>> getEvents();
	public boolean registerBoard(boardreg boardreg);
	public boolean registerRoom(boardreg boardreg);
	public boolean registerRegionName(boardreg boardreg);
	public boolean registerLocation(boardreg boardreg);
}
