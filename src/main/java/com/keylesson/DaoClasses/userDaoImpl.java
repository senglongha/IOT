package com.keylesson.DaoClasses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.keylesson.EntityClasses.Board;
import com.keylesson.EntityClasses.Daily_Usage;
import com.keylesson.EntityClasses.Data;
import com.keylesson.EntityClasses.Location;
import com.keylesson.EntityClasses.Region;
import com.keylesson.EntityClasses.Room;
import com.keylesson.EntityClasses.User;
import com.keylesson.EntityClasses.User_Registration;
import com.keylesson.HibernateUtil.HibernateUtil;
import com.keylesson.ModelClasses.boardreg;
import com.keylesson.ModelClasses.event;


import com.keylesson.ServiceClasses.usersService;

@Repository
public class userDaoImpl implements usersDao {

	public List<User_Registration> getUsers() {
		List<User_Registration> locations = new ArrayList<User_Registration>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			locations = session.createQuery("FROM User_Registration").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return locations;
		} finally {
			session.flush();
			session.close();
		}
		return locations;
	}

	public List<Map<String, Object>> getAllRegion() {
		List<Map<String, Object>> regions = new ArrayList<Map<String, Object>>();
		
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			Query qury = session
					.createQuery("select D.room.region.name, sum(D.power)/1000, sum(D.water) from Daily_Usage D Group by(D.room.region.ID)");
			List<Object[]> q = qury.list();

			for (Object b[] : q) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("region_name", b[0]);
				map.put("power", b[1]);
				map.put("water", b[2]);
				regions.add(map);
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return regions;
	}

	public List<Map<String, Object>> getSource() {
		List<Map<String, Object>> locations = new ArrayList<Map<String, Object>>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();

			Query qury = session
					.createQuery("Select D.room.region.ID, D.room.region.name , D.room.ID, Sum(D.power), Sum(D.water), D.dmy from Daily_Usage D Group by(D.room.ID)");
			List<Object[]> q = qury.list();

			for (Object b[] : q) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("region_name", b[0]);
			}

			for (Object b[] : q) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("region_id", b[0]);
				map.put("region_name", b[1]);
				map.put("room", b[2]);
				map.put("power", b[3]);
				map.put("water", b[4]);
				map.put("date", b[5]);
				locations.add(map);
			}
			
		}

		catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return locations;
	}

	public List<Map<String, Object>> getAllLocation() {
		List<Location> location = new ArrayList<Location>();
		List<Map<String, Object>> locations = new ArrayList<Map<String, Object>>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			String queryString = ("from Location");
			Query query = session.createQuery(queryString);
			location = query.list();
			for (int i = 0; i < location.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", location.get(i).getName());
				map.put("id", location.get(i).getID());
				locations.add(map);
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return locations;
	}

	public List<Map<String, Object>> getRegion(String id) {
		List<Region> region = new ArrayList<Region>();
		List<Map<String, Object>> regions = new ArrayList<Map<String, Object>>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Region where location.ID = " + id;
			Query query = session.createQuery(queryString);

			region = query.list();
			for (int i = 0; i < region.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", region.get(i).getID());
				map.put("name", region.get(i).getName());
				regions.add(map);
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return regions;

	}

	public List<Map<String, Object>> getRoom(String id) {
		List<Room> room = new ArrayList<Room>();
		List<Map<String, Object>> rooms = new ArrayList<Map<String, Object>>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Room where region.ID = " + id;
			Query query = session.createQuery(queryString);

			room = query.list();
			for (int i = 0; i < room.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", room.get(i).getID());
				map.put("name", room.get(i).getName());
				rooms.add(map);
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return rooms;

	}

	public Map<String, Object> getReport(String id, String export_from,
			String export_until) {
		List<Daily_Usage> data = new ArrayList<Daily_Usage>();
		List<Room> room = new ArrayList<Room>();
		Transaction trns1 = null;
		Map<String, Object> rooms = new HashMap<String, Object>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Daily_Usage where room.ID=:id and dmy>=:export_from AND dmy<=:export_until";
			String queryString2 = " from Room where ID=" + id;
			Query query = session.createQuery(queryString);
			Query query2 = session.createQuery(queryString2);
			query.setString("id", id);
			query.setString("export_from", export_from.concat(" 00:00"));
			query.setString("export_until", export_until.concat(" 23:59"));
			room = query2.list();
			data = query.list();
			int total_power = 0;
			int total_water = 0;
			for (Daily_Usage d : data) {
				total_power += d.getPower();
				total_water += d.getWater();
			}
			rooms.put("name", room.get(0).getName());
			rooms.put("id", id);
			rooms.put("power", total_power);
			rooms.put("water", total_water);
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return rooms;

	}

	public boolean getEvent(event event) {
		Transaction trns = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Data data = new Data();
			String hql = "from Data where room_ID=" + event.getRoom();
			Query query = session.createQuery(hql);
			System.out.println("get event");

			if (query.list().size() > 0) {
				data = (Data) query.list().get(0);
				
				data.setOver_usage_power(event.getPower());
				data.setOver_usage_water(event.getWater());
				data.setEmail(event.getEmail());
				data.setMessage(event.getMessage());
				session.update(data);

			} else {
				Room room = new Room();
				room = (Room) session.load(Room.class, event.getRoom());
				data.setRoom(room);
				data.setOver_usage_power(event.getPower());
				data.setOver_usage_water(event.getWater());
				data.setEmail(event.getEmail());
				data.setMessage(event.getMessage());

				session.save(data);
			}
			trns.commit();
			return true;
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return false;
	}
	

	public List<Map<String, Object>> getEvents() {
		ArrayList<Data> data = new ArrayList<Data>();
		Transaction trns1 = null;
		List<Map<String, Object>> events = new ArrayList<Map<String, Object>>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Data where over_usage_water != 'NULL' and over_usage_power != 'NULL'";
			Query query = session.createQuery(queryString);
			data = (ArrayList) query.list();
			for (int i = 0; i < data.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("power", data.get(i).getPower());
				map.put("water", data.get(i).getWater());
				map.put("id", data.get(i).getID());
				map.put("roomName", data.get(i).getRoom().getName());
				map.put("over_usage_power", data.get(i).getOver_usage_power());
				map.put("over_usage_water", data.get(i).getOver_usage_water());
				map.put("email", data.get(i).getEmail());
				events.add(map);
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return events;

	}

	public int getAllPowerDailyUsageByRoomId(int id) {
		ArrayList<Daily_Usage> data = new ArrayList<Daily_Usage>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		int sum = 0;
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Daily_Usage where room_ID =:id";
			Query query = session.createQuery(queryString);
			query.setString("id", Integer.toString(id));
			data = (ArrayList) query.list();
			for (int i = 0; i < data.size(); i++) {
				sum += data.get(i).getPower();
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return sum;

	}

	public int getAllWaterDailyUsageByRoomId(int id) {
		ArrayList<Daily_Usage> data = new ArrayList<Daily_Usage>();
		Transaction trns1 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		int sum = 0;
		try {
			trns1 = session.beginTransaction();
			String queryString = "from Daily_Usage where room_ID =:id";
			Query query = session.createQuery(queryString);
			query.setString("id", Integer.toString(id));
			data = (ArrayList) query.list();
			for (int i = 0; i < data.size(); i++) {
				sum += data.get(i).getWater();
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();
		}
		return sum;

	}

	

		public boolean registerBoard(boardreg boardreg){
		Transaction trns = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Board board = new Board();
			Room room = new Room();
			room.setID(boardreg.getRoom_ID());
			board.setRoom(room);
			board.setMAC(boardreg.getMac());
			System.out.println(boardreg.getRoom_ID()+"   "+boardreg.getMac());
			session.save(board);
			trns.commit();
			return true;
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();

		} finally {
			session.flush();
			session.close();

		}
		return false;}

		

public boolean registerRoom(boardreg boardreg){
	Transaction trns = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		trns = session.beginTransaction();
		Region region = new Region();
		Room room = new Room();
		region.setID(boardreg.getRegion_ID());
		room.setRegion(region);
		room.setID(boardreg.getRoom_ID());
		room.setName(boardreg.getRoom_Name());
		
		session.save(room);
		trns.commit();
		return true;
	} catch (RuntimeException e) {
		if (trns != null) {
			trns.rollback();
		}
		e.printStackTrace();

	} finally {
		session.flush();
		session.close();

	}
	return false;}

public boolean registerRegionName(boardreg boardreg){
	Transaction trns = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		trns = session.beginTransaction();
		Region region = new Region();
		Location location = new Location();
		location.setID(boardreg.getLocation_ID());
		region.setID(boardreg.getRegion_ID());
		region.setLocation(location);
		region.setName(boardreg.getRegion_Name());
		
		session.save(region);
		trns.commit();
		return true;
	} catch (RuntimeException e) {
		if (trns != null) {
			trns.rollback();
		}
		e.printStackTrace();

	} finally {
		session.flush();
		session.close();

	}
	return false;}
	
public boolean registerLocation(boardreg boardreg){
	Transaction trns = null;

	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		trns = session.beginTransaction();
		
		Location location = new Location();
		location.setID(boardreg.getLocation_ID());
		location.setName(boardreg.getLocation_Name());
		
		session.save(location);
		trns.commit();
		return true;
	} catch (RuntimeException e) {
		if (trns != null) {
			trns.rollback();
		}
		e.printStackTrace();

	} finally {
		session.flush();
		session.close();

	}
	return false;}

	}


