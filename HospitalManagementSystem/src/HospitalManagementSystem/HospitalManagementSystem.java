package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3305/hospital";
	private static final String username="root";
	private static final String password="Siva@123";
	
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		Scanner scanner=new Scanner(System.in);
				try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(connection,scanner);
			Doctor doctor=new Doctor(connection);
			while(true) {
				System.out.println("Hospital Management System");
				System.out.println("1 . Add Patient");
				System.out.println("2 . view patients");
				System.out.println("3 . view Doctors");
				System.out.println("4 . Book Appointment");
				System.out.println("5 . exit");
				System.out.println("Enter your choice: ");
				int choice =scanner.nextInt();
				switch(choice) {
				case 1:
					//Add patient
					patient.addPatient();
					break;
				case 2:
					// view patients
					patient.viewPatients();
					break;
				case 3:
					// view Doctors
					doctor.viewDoctors();
					break;
				case 4:
					// Book Appointment
					bookAppointment(patient, doctor, connection, scanner);
					break;
				case 5:
					return ;
				default: 
					System.out.println("Enter valid choice");
					break;
					
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
		System.out.println("Enter Patient ID: ");
		int patientId=scanner.nextInt();
		System.out.println("ENter Doctor ID: ");
		int doctorId=scanner.nextInt();
		System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
		String appointmentDate=scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailability(doctorId,appointmentDate,connection)) {
				String query="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
				try {
					PreparedStatement ps=connection.prepareStatement(query);
					ps.setInt(1, patientId);
					ps.setInt(2, doctorId);
					ps.setString(3, appointmentDate);
					int rowsaffected=ps.executeUpdate();
					if(rowsaffected >0) {
						System.out.println("Appointment Booked");
					}else {
						System.out.println("Failed to Book Appointment");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("Doctor not available on this date");
			}
			
		}else {
			System.out.println("Either Doctor or Patient are not availble");
		}
	}
	
	
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection) {
		String query="SELECT COUNT(*) FROM appointments where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, doctorId);
			ps.setString(2, appointmentDate);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int count=rs.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
					
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
