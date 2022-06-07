import java.sql.*;
import java.util.regex.Pattern;
import java.util.Scanner; 
class Fetch{
	
	public  void fetch()  {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			System.out.print("hii");
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			ResultSet rs=stmt.executeQuery("select * from emp");  
			
			while(rs.next()) {
				int empid=rs.getInt("empid");
				String empname=rs.getString("empname");
				double empsalary=rs.getDouble("empsalary");
				System.out.println(empid +": "+empname + ", "+empsalary);
			}
			
			con.close();
			}
		catch(Exception e) {
			System.out.println("Not connected");
		}
		
	}
}


class Insert{
	public void insert() {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter Name: ");
			String empname=sc.next();
			System.out.print("Enter Salary: ");
			Double empsalary=sc.nextDouble();
			String sql="INSERT INTO emp (empname,empsalary) VALUES (?,?)";
			PreparedStatement stmt=con.prepareStatement(sql); 
			stmt.setString(1,empname);
			stmt.setDouble(2,empsalary);
			int rows=stmt.executeUpdate();  
			if(rows >0) {
				System.out.println("A new User has been Succesfully inserted");
			}
		
			con.close();
			}
		catch(Exception e) {
			System.out.println("Not connected");
		}
	}
	
}
class Delete{
	public void delete() {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter ID to Delete: ");
			int dempid=sc.nextInt();
			String sql="DELETE FROM emp WHERE empid=?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, dempid);
			int rows=stmt.executeUpdate();
			if(rows>0) {
				System.out.println("Selected User Deleted Succesfully");
			}
		}
	
		catch(Exception e) {
			System.out.println("Error Occured");
		}
	
}
}

class SearchID {
	public void searchid()  {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter Employee id to Search: ");
			int empid=sc.nextInt();
			
			String sql="SELECT empname,empsalary  FROM emp WHERE empid=?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, empid);
			ResultSet rs=stmt.executeQuery();					
			while(rs.next()) {
				String empname=rs.getString("empname");
				Double empsalary=rs.getDouble("empsalary");
				System.out.println(empid +": "+empname + ", "+empsalary);
			}
}
		catch(Exception e) {
			System.out.print(e);
		}
	}
}
class SearchName {
	public void searchname()  {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter Employee Name to Search: ");
			String iempname=sc.next();
			System.out.flush();
			String sql="SELECT *  FROM emp WHERE empname LIKE ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, iempname+"%");
			ResultSet rs=stmt.executeQuery();					
			while(rs.next()) {
				int empid=rs.getInt("empid");
				String empname=rs.getString("empname");
				Double empsalary=rs.getDouble("empsalary");
				System.out.println(empid +": "+ empname + ", "+empsalary);
			}	
}
		catch(Exception e) {
			System.out.println("Error");
		}
	}
}
class Update{
	
	public void update()  {
		try{ 
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection( 
			"jdbc:mysql://localhost:3306/employeejavajdbc","root","Siva@123"); 
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter Employee id to update: ");
			int empid=sc.nextInt();
			
			String sql="SELECT empname,empsalary  FROM emp WHERE empid=?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, empid);
			ResultSet rs=stmt.executeQuery();						
			while(rs.next()) {
				String empname=rs.getString("empname");
				Double empsalary=rs.getDouble("empsalary");
				System.out.println(empid +": "+empname + ", "+empsalary);
			}
			System.out.println("Update 1.Name 2.Salary 3.exit ");
			int choice=sc.nextInt();
			while(choice < 3) {
			switch(choice) {
			case 1: 
				Scanner sc1=new Scanner(System.in);
				System.out.print("Enter New Name: ");
				String uempname=sc1.next();
				String sql1="UPDATE emp SET empname=? WHERE empid=?";
				PreparedStatement stmt1=con.prepareStatement(sql1);
				stmt1.setString(1, uempname);
				stmt1.setInt(2, empid);
				//System.out.println(uempname);
				//System.out.println(empid);
				//System.out.println(con);
				int rows= stmt1.executeUpdate();				
				if(rows>0) {
					System.out.println("The user information has been Updated Succesfully");
				}	
				else {
					System.out.println("Failed to update");
				}
				break;
			case 2:
				Scanner sc2=new Scanner(System.in);
				System.out.print("Enter New Salary: ");
				Double usalary=sc2.nextDouble();
				String sql2="UPDATE emp SET empsalary=? WHERE empid=?";
				PreparedStatement stmt2=con.prepareStatement(sql2);
				stmt2.setDouble(1,usalary);
				stmt2.setInt(2, empid);
				int rows1=stmt2.executeUpdate();
				if(rows1 >0) {
					System.out.println("The User information has been Updated Succesfully");
				}
				else {
					System.out.println("Failed to update");
				}
				break;
			case 3:
				break;
			default :
				System.out.println("Invalid Choice");
			}
			System.out.println("");
			System.out.println("Update 1.Name 2.Salary 3.exit");
			 choice=sc.nextInt();
			}
			con.close();
			
	}
		catch(Exception e) {
			System.out.println("Not connected");
	}
}
}
public class EmployeeApp {
		public static void main(String[] args) {
		Fetch fetch=new Fetch();
		Insert insert=new Insert();
		Update update=new Update();
		Delete delete=new Delete();
		SearchID searchid=new SearchID();
		SearchName searchname = new SearchName();
		System.out.println("Enter the option:");
		System.out.println("1.Fetch Records");
		System.out.println("2.Insert Record");
		System.out.println("3.Delete Record");
		System.out.println("4.Update Record");
		System.out.println("5.Search Record with ID");
		System.out.println("6.Search Record with Employee Name");
		System.out.println("7.Exit");
		System.out.flush();
		
		Scanner sc=new Scanner(System.in);
		int inp=sc.nextInt();
		System.out.flush();
		
		while(inp < 7) {
		switch(inp) {
		case 1:
			fetch.fetch();
			break;
		
		case 2:
			insert.insert();
			break;
		case 3:
			delete.delete();
			break;
		case 4:
			update.update();
			break;
		case 5:
			searchid.searchid();
			break;
		case 6:
			searchname.searchname();
			break;
			
		case 7:
			System.out.println("Good Bye");
			break;
		
		default :
			System.out.println("Invalid Choice");
			break;
		}
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Enter the option:");
		System.out.println("1.Fetch Records");
		System.out.println("2.Insert Record");
		System.out.println("3.Delete Record");
		System.out.println("4.Update Record");
		System.out.println("5.Search Record with ID");
		System.out.println("6.Search Record with Employee Name");
		System.out.println("7.Exit");
		System.out.println("");
		inp=sc.nextInt();
		}
		System.out.println("Good Bye");
	}
		
}

