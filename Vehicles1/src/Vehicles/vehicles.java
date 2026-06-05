package Vehicles;

import java.sql.*;
import java.util.Scanner;

public class vehicles {

    static final String URL =
            "jdbc:mysql://localhost:3306/vehicledb";

    static final String USER = "root";
    static final String PASSWORD = "root";

    static Scanner sc = new Scanner(System.in);

    // Database Connection
    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Add Vehicle
    public static void addVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Vehicle Type : ");
            String type = sc.nextLine();

            System.out.print("Enter Rent Per Day : ");
            double rent = sc.nextDouble();
            sc.nextLine();

            String sql =
                    "INSERT INTO vehiclest(name,vehicletype,"
                    + "rentperday,available) VALUES(?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, type);
            ps.setDouble(3, rent);
            ps.setString(4, "YES");

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Vehicle Added Successfully");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // View All Vehicles
    public static void viewVehicles() {

        try(Connection con = getConnection()) {

            String sql = "SELECT * FROM vehiclest";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n---------------------------------------------");
            System.out.println("ID\tNAME\tTYPE\tRENT\tAVAILABLE");
            System.out.println("---------------------------------------------");

            while(rs.next()) {

                System.out.println(
                        rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("vehicletype") + "\t" +
                        rs.getDouble("rentperday") + "\t" +
                        rs.getString("available")
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Search Vehicle
    public static void searchVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql =
                    "SELECT * FROM vehiclest WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                System.out.println("\nVehicle Found");
                System.out.println("ID : "
                        + rs.getInt("id"));

                System.out.println("Name : "
                        + rs.getString("name"));

                System.out.println("Type : "
                        + rs.getString("vehicletype"));

                System.out.println("Rent : "
                        + rs.getDouble("rentperday"));

                System.out.println("Available : "
                        + rs.getString("available"));

            } else {
                System.out.println("Vehicle Not Found");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Update Vehicle
    public static void updateVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle ID : ");
            int id = sc.nextInt();

            System.out.print("Enter New Rent : ");
            double rent = sc.nextDouble();
            sc.nextLine();

            String sql =
                    "UPDATE vehiclest SET rentperday=? WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setDouble(1, rent);
            ps.setInt(2, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Vehicle Updated");
            else
                System.out.println("Vehicle Not Found");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Vehicle
    public static void deleteVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql =
                    "DELETE FROM vehiclest WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Vehicle Deleted");
            else
                System.out.println("Vehicle Not Found");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Rent Vehicle
    public static void rentVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            String checkSql =
                    "SELECT available FROM vehiclest WHERE id=?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setInt(1, id);

            ResultSet rs = checkPs.executeQuery();

            if(rs.next()) {

                if(rs.getString("available").equalsIgnoreCase("YES")) {

                    String sql =
                            "UPDATE vehiclest SET available='NO' WHERE id=?";

                    PreparedStatement ps =
                            con.prepareStatement(sql);

                    ps.setInt(1, id);

                    ps.executeUpdate();

                    System.out.println("Vehicle Rented Successfully");

                } else {
                    System.out.println("Vehicle Already Rented");
                }

            } else {
                System.out.println("Vehicle Not Found");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Return Vehicle
    public static void returnVehicle() {

        try(Connection con = getConnection()) {

            System.out.print("Enter Vehicle ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql =
                    "UPDATE vehiclest SET available='YES' WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Vehicle Returned Successfully");
            else
                System.out.println("Vehicle Not Found");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        while(true) {

            System.out.println("\n========== VEHICLE RENTAL SYSTEM ==========");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View Vehicles");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Update Vehicle");
            System.out.println("5. Delete Vehicle");
            System.out.println("6. Rent Vehicle");
            System.out.println("7. Return Vehicle");
            System.out.println("8. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    addVehicle();
                    break;

                case 2:
                    viewVehicles();
                    break;

                case 3:
                    searchVehicle();
                    break;

                case 4:
                    updateVehicle();
                    break;

                case 5:
                    deleteVehicle();
                    break;

                case 6:
                    rentVehicle();
                    break;

                case 7:
                    returnVehicle();
                    break;

                case 8:
                    System.out.println("Thank You");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}