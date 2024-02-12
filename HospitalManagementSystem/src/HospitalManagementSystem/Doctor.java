package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection=connection;
	}
	
	public void viewDoctors() {
		String query="SELECT * FROM doctors";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+-----------------------------------------------+");
			System.out.println("| DoctorId	|	Name	|	Specialization	|");
			System.out.println("+-----------------------------------------------+");
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String specialization=rs.getString("specialization");
				System.out.println(id+" " +name+ "  "+ specialization);
				System.out.println("+-----------------------------------------------+");
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean getDoctorById(int id) {
		String query="SELECT * FROM doctors WHERE id=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}
}
