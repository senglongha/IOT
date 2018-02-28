package com.keylesson.ServiceClasses;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.keylesson.EntityClasses.Data;
import com.keylesson.EntityClasses.Location;
import com.keylesson.EntityClasses.User;
import com.keylesson.EntityClasses.User_Registration;
import com.keylesson.DaoClasses.usersDao;
import com.keylesson.ModelClasses.boardreg;
import com.keylesson.ModelClasses.event;



@Service
public class usersServiceImpl implements usersService{

	@Autowired
	usersDao usersDao1;

	public List<Map<String,Object>> getAllRegion() {
		return usersDao1.getAllRegion();
	}
	public List<User_Registration> getUsers(){
		return usersDao1.getUsers();
	}
	public List<Map<String,Object>> getSource() {
		return usersDao1.getSource();
	}
	public  List<Map<String,Object>> getAllLocation(){
		return usersDao1.getAllLocation();
	}
	public List<Map<String, Object>> getRegion(String id){
		return usersDao1.getRegion(id);
	}
	public List<Map<String, Object>> getRoom(String id){
		return usersDao1.getRoom(id);
	}
		
	public boolean getEvent(event event){
		return usersDao1.getEvent(event);
	}
	public boolean registerBoard(boardreg boardreg){
		return usersDao1.registerBoard(boardreg);
	}
	public boolean registerRoom(boardreg boardreg){
		return usersDao1.registerRoom(boardreg);
	}
	public boolean registerRegionName(boardreg boardreg){
		return usersDao1.registerRegionName(boardreg);
	}
	public boolean registerLocation(boardreg boardreg){
		return usersDao1.registerLocation(boardreg);
	}
	public Map<String, Object> getReport(String id, String export_from, String export_until){
		return usersDao1.getReport(id, export_from, export_until);
	}
	
	public List<Map<String, Object>> getEvents(){
		return usersDao1.getEvents();
	}

	
	
}
