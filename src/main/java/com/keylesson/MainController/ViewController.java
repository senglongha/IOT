package com.keylesson.MainController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keylesson.ModelClasses.boardreg;
import com.keylesson.ModelClasses.event;
import com.keylesson.ServiceClasses.usersService;
//import com.keylesson.EntityClasses.Data;
import com.keylesson.EntityClasses.Location;
import com.keylesson.EntityClasses.User;
import com.keylesson.EntityClasses.User_Registration;

@Controller

public class ViewController {
	@Autowired
	usersService usersService1;	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView init() {
		
		return new ModelAndView("dashboard");
    }
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView goToPageDashboard1() {
		
		return new ModelAndView("dashboard");
    }
	
	@RequestMapping(value = "/dash", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> getAll() {
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			List<Map<String,Object>> list = usersService1.getAllRegion();
			
			if (list != null){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		  return map;
    }
	
	
	@RequestMapping(value = "/source", method = RequestMethod.GET)
    public ModelAndView goToPageSource() {
		return new ModelAndView("source");
    }
	
	@RequestMapping(value = "/sourceload", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> getSourceBody() {
		Map<Object, Object> map1 = new HashMap<Object, Object>();
		
			List<Map<String,Object>> list = usersService1.getSource();
			List<Map<String,Object>> regionList = usersService1.getAllRegion();
			if (list != null){
				map1.put("status","200");
				map1.put("message","Data found");
				map1.put("data", list);
				map1.put("data2", regionList);
				
			}else{
				map1.put("status","404");
				map1.put("message","Data not found");			
			}
		
		  return map1;
    }
	
	@RequestMapping(value = "/event", method = RequestMethod.GET)
    public ModelAndView goToPageEvent() {
		return new ModelAndView("event");
    }
	
	
	
	
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView goToPageExport() {
		return new ModelAndView("export");
    }
	@RequestMapping(value = "/exportloc", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> getLoc() {
			Map<Object, Object> map = new HashMap<Object, Object>();
	
			List<Map<String,Object>> list = usersService1.getAllLocation();
			
			if (list != null){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		  return map;
    }
	@RequestMapping(value = "/exportreg", method = RequestMethod.GET)
    public @ResponseBody Map<Object, Object> getReg(String id) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			List<Map<String,Object>> list = usersService1.getRegion(id);
			System.out.println(list);
			if (list != null){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		  return map;
    }
	@RequestMapping(value = "/exportroom", method = RequestMethod.GET)
    public @ResponseBody Map<Object, Object> getRoom(String id) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			List<Map<String,Object>> list = usersService1.getRoom(id);
			
			if (list != null){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		  return map;
    }
	
	@RequestMapping(value = "/exportoutput", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getReport(String id, String export_from, String export_until) {
		Map<String, Object> map = new HashMap<String, Object>();
	
		Map<String, Object> ret = usersService1.getReport(id, export_from, export_until);
			System.out.println(ret);
			if (ret.size()>0){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", ret);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		  return map;
    }
	/* Event Controller*/
	
	
	@RequestMapping(value = "/jobEvent", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> getEvent(@RequestBody event event) {
		System.out.println(event);
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			boolean list = usersService1.getEvent(event);
			
			if (list){
				map.put("status","200");
				map.put("message","Data found");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		
		  return map;
    }
	
	@RequestMapping(value = "/boardboard", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> registerBoard(boardreg boardreg) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			boolean list = usersService1.registerBoard(boardreg);
			System.out.println(list);
			if (list){
				map.put("status","200");
				map.put("message","Already added");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		
		  return map;
    }
	
	@RequestMapping(value = "/boardroom", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> registerRoom(boardreg boardreg) {
	
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			boolean list = usersService1.registerRoom(boardreg);
			
			if (list){
				map.put("status","200");
				map.put("message","Already added");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		
		  return map;
    }
	@RequestMapping(value = "/boardregion", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> registerRegionName(boardreg boardreg) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			boolean list = usersService1.registerRegionName(boardreg);
			
			if (list){
				map.put("status","200");
				map.put("message","Already added");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		
		  return map;
    }
	@RequestMapping(value = "/boardlocation", method = RequestMethod.POST)
    public @ResponseBody Map<Object, Object> registerLocation(boardreg boardreg) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
	
			boolean list = usersService1.registerLocation(boardreg);
		
			if (list){
				map.put("status","200");
				map.put("message","Already added");
				map.put("data", list);
				
			}else{
				map.put("status","404");
				map.put("message","Data not found");			
			}
		
		
		  return map;
    }
	
	
	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
		public @ResponseBody Map<String, Object> getEvents(){
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<Map<String, Object>> ret = usersService1.getEvents();
				System.out.println(ret);
				if (ret.size()>0){
					map.put("status","200");
					map.put("message","Data found");
					map.put("data", ret);
					
				}else{
					map.put("status","404");
					map.put("message","Data not found");			
				}
			
			  return map;
			
		}
		
	}