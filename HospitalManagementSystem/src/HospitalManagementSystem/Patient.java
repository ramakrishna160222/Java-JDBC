package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter Patient Name: ");
		String name=scanner.next();
		System.out.println("Enter Patient Age: ");
		int age=scanner.nextInt();
		System.out.println("Enter Patient Gender: ");
		String gender=scanner.next();
		
		try {
				String query="INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
				PreparedStatement ps= connection.prepareStatement(query);
				ps.setString(1,name);
				ps.setInt(2, age);
				ps.setString(3, gender);
				int affectedRows=ps.executeUpdate();
				if(affectedRows>0) {
					System.out.println("Patient added Successfully");
				}else {
					System.out.println("Failed to add Patient");
				}			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void viewPatients() {
		String query="SELECT * FROM patients";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-----------------------------------------------+");
			System.out.println("| PatientId	|	Name	|	Age	|	Gender	|");
			System.out.println("+-----------------------------------------------+");
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				int age=rs.getInt("age");
				String gender=rs.getString("gender");
				System.out.println(id+" "+name+" "+age+" "+ gender);
				System.out.println("+-----------------------------------------------+");
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean getPatientById(int id) {
		String query="SELECT * FROM patients WHERE id=?";
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
