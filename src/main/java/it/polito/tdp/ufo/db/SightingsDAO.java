package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Adiacenza;
import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Anno> getAnni() {
		final String sql = "SELECT YEAR(s.datetime) AS anno, COUNT(s.id) AS peso " + 
				"FROM sighting AS s " + 
				"GROUP BY YEAR(s.datetime) " + 
				"ORDER BY YEAR(s.datetime) ASC";
		List<Anno> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Anno a = new Anno(res.getInt("anno"),res.getInt("peso"));
				lista.add(a);
			}
			conn.close();
			return lista;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}

	public List<Adiacenza> getAdiacenze(int anno) {
		final String sql = "SELECT s1.state AS s1,s2.state AS s2 " + 
				"FROM sighting AS s1, sighting AS s2 " + 
				"WHERE YEAR(s1.datetime)=? " + 
				"AND YEAR(s2.datetime)=? " + 
				"AND s1.state<>s2.state " + 
				"AND s2.datetime>s1.datetime";
		List<Adiacenza> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Adiacenza a = new Adiacenza(res.getString("s1"),res.getString("s2"));
				lista.add(a);
			}
			conn.close();
			return lista;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

}
