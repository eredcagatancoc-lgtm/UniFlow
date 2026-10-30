/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniFlow;

/**
 *
 * @author ACER
 */
public class UserSession {
    private static int userId;
    private static String username;
    private static String role;

    // Old version still works (defaults to student)
    public static void setUser(int id, String user) {
        setUser(id, user, "student");
    }

    // New version with role
    public static void setUser(int id, String user, String userRole) {
        userId = id;
        username = user;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    public static void clearSession() {
        userId = 0;
        username = null;
        role = null;
    }
    
    public static javax.swing.JFrame newHomePage() {
        return isAdmin() ? new HomePageAdmin() : new HomePage();
    }
    
    public static javax.swing.JFrame newStudentsPage() {
    return isAdmin() ? new StudentAdmin() : new Students();
    }
}
