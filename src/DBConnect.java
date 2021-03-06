
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

	
	public class DBConnect {

		public Integer Connect(String nev, String pass) {
			// TODO Auto-generated method stub
			try {
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int szintNehezseg = -1;
			try{
				String host = "jdbc:sqlserver://localhost:1433;databaseName=csoportos;integratedSecurity=true;";
				
				Connection con = DriverManager.getConnection(host);
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT * FROM Felhasznalo WHERE Nev = '"+nev+"';");
				String keresett = "";
				if (rs.first()){
					keresett = rs.getString("Nev");
				}
				if (!keresett.equals("")){
					String passw = rs.getString("Jelszo");
					String szint = rs.getString("Szint");
					if (pass.equals(passw)){
						switch(szint){
							case "beginner" : szintNehezseg = 10;break;
							case "advanced" : szintNehezseg = 22; break;
							case "professional" : szintNehezseg = 50; break;
						}
					}
				}
				
				con.close();
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}
			return szintNehezseg;
		}
		public Integer regisztralas(ArrayList<String> reg){
			String nev = reg.get(0);
			String mail = reg.get(1);
			String pass = reg.get(2);
			String pass2 = reg.get(3);
			Integer szintNehezseg = 5;
			
			try {
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				String host = "jdbc:sqlserver://localhost:1433;databaseName=csoportos;integratedSecurity=true;";
				
				Connection con = DriverManager.getConnection(host);
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT * FROM Felhasznalo WHERE Nev = '"+nev+"';");
				String keresett = "";
				if (rs.first()){
				keresett = rs.getString("Nev");
				}
				if(keresett != "" ){
					szintNehezseg=-1;
					return szintNehezseg;
				}
					if (!pass.equals(pass2)){
						System.out.println(pass+"."+pass2+".");
						szintNehezseg=-2;
						return szintNehezseg;
					}
					else{
						Statement stmt_2 = con.createStatement();
						stmt_2.executeQuery("INSERT INTO Felhasznalo VALUES('"+nev+"', '"+ pass+"', 'beginner');");
						Statement stmt_3 = con.createStatement();
						stmt_3.executeQuery("INSERT INTO Mail VALUES('"+mail+"');");
						Statement stmt_4 = con.createStatement();
						stmt_4.executeQuery("INSERT INTO Maile VALUES('"+nev+"', '"+mail+"');");
						szintNehezseg = 10;
					}
				
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}
			return szintNehezseg;
		}
	}
