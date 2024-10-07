import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class firstone extends Application {
	static ArrayList<User> ua;
	static User cuser;
	static int role;
	static boolean loggedin;
	static int csc;// 0 = login, 1 = role select
	static ArrayList<String> otps;
	static HashMap<String, Integer> otpow;
	// static HashMap<User, LocalDate>;
	
	
	public User find(String un, String pw) {
		for (int i = 0; i < ua.size(); ++i) {
			if (ua.get(i).uname.equals(un) && ua.get(i).pw.equals(pw)) {
				return ua.get(i);
			}
		}
		return ua.get(0);
	}
	
	public boolean chk(String un, String pw) {
		for (int i = 0; i < ua.size(); i++) {
			if (ua.get(i).uname.equals(un) && ua.get(i).pw.equals(pw)) {
				return true;
			}
		}
		return false;
	}
    @Override
    public void start(Stage primaryStage) {
    	
    	// -------- LOGIN ------------
    	
        // Create the grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10)); // Padding around the grid
        grid.setVgap(10); // Vertical gap between components
        grid.setHgap(10); // Horizontal gap between components

        // Create username label and text field
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // Create password label and password field
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Label otp = new Label("One-Time Invite Code:");
        GridPane.setConstraints(otp, 0, 2);
        PasswordField otpi = new PasswordField();
        GridPane.setConstraints(otpi, 1, 2);
        
        // Create the login button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        Button otplogin = new Button("One-Time Invite Login");
        GridPane.setConstraints(otplogin, 1, 4);



        // Add all components to the grid
        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, otp, otpi, loginButton, otplogin);

        // Create the scene and set the stage
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Window");

        
        
        
        // ------- ROLE SELECT -----------
        Button adminButton = new Button("Admin");
        Button instructorButton = new Button("Instructor");
        Button userButton = new Button("User");

        // Layout for the buttons (VBox with spacing between buttons)
        VBox layout = new VBox(15);
        layout.getChildren().addAll(adminButton, instructorButton, userButton);

        // Create a scene with the layout
        Scene rolesel = new Scene(layout, 300, 200);
        
        
        // ----- USER HOME ----------
        
        Button ulogoutButton = new Button("Logout");

        // Layout (StackPane) to center the button
        StackPane uhlay = new StackPane();
        uhlay.getChildren().add(ulogoutButton);

        // Create a Scene with the layout
        Scene uhome = new Scene(uhlay, 300, 200);

        // Set the scene to the stage
        primaryStage.setTitle("User Home");
        //primaryStage.setScene(home);
        //primaryStage.show();

        // ----- INSTRUCTOR HOME ----------
        
        Button ilogoutButton = new Button("Logout");

        // Layout (StackPane) to center the button
        StackPane ihlay = new StackPane();
        ihlay.getChildren().add(ilogoutButton);

        // Create a Scene with the layout
        Scene ihome = new Scene(ihlay, 300, 200);

        // Set the scene to the stage
        primaryStage.setTitle("Instructor Home");
        //primaryStage.setScene(home);
        //primaryStage.show();

        // ------- ADMIN HOME -------------
        
        TextField accountToDeleteField = new TextField();
        accountToDeleteField.setPromptText("Account to be deleted");

        TextField oneTimePasswordField = new TextField();
        oneTimePasswordField.setPromptText("One-Time Invite Code to be added");
        
        CheckBox otpAdminRole = new CheckBox("Give One-Time Invite Code Admin Role");
        CheckBox otpInstructorRole = new CheckBox("Give One-Time Invite Code Instructor Role");
        CheckBox otpUserRole = new CheckBox("Give One-Time Invite Code User Role");

        TextField userToEditRolesField = new TextField();
        userToEditRolesField.setPromptText("User to edit roles for");

        // Create Buttons with specified labels
        Button deleteAccountButton = new Button("Delete Account");
        Button addOneTimePasswordButton = new Button("Add One-Time Invite Code");
        addOneTimePasswordButton.setOnAction(event -> {
        	String notp = oneTimePasswordField.getText();
        	if (!otps.contains(notp)) {
        		otps.add(notp);
        	}
        	int rolepow = 0;
        	if (otpAdminRole.isSelected()) rolepow++;
        	if (otpInstructorRole.isSelected()) rolepow += 2;
        	if (otpUserRole.isSelected()) rolepow += 4;
        	otpow.put(notp, rolepow);
        });
        
        Button addInstructorRoleButton = new Button("Add Instructor Role");
        addInstructorRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).instr = true;
        	}
        });
        
        
        
        Button addAdminRoleButton = new Button("Add Admin Role");
        addAdminRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).admin = true;
        	}
        });
        Button addUserRoleButton = new Button("Add User Role");
        addAdminRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).user = true;
        	}
        });  
      
        Button removeInstructorRoleButton = new Button("Remove Instructor Role");
        removeInstructorRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).instr = false;
        	}
        });       
        Button removeAdminRoleButton = new Button("Remove Admin Role");
        removeAdminRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).admin = false;
        	}
        });
        Button removeUserRoleButton = new Button("Remove User Role");
        removeUserRoleButton.setOnAction(event -> {
        	String eun = userToEditRolesField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(eun)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx > -1) {
        		ua.get(idx).user = false;
        	}
        });
        
        //Button listUsersButton = new Button("List Users");
        
        Button alogoutButton  = new Button("Logout");
        alogoutButton.setOnAction(event -> {
        	primaryStage.setTitle("Login");
            primaryStage.setScene(scene); // goto login
            primaryStage.show();
        });
        
        ListView<String> unl = new ListView<>();
        for (int i = 0; i < ua.size(); ++i) {
        	unl.getItems().add(ua.get(i).uname);
        }
        
        TextField resetUserField = new TextField();
        resetUserField.setPromptText("User to be Reset");
        TextField otrf = new TextField();
        otrf.setPromptText("One-Time password for Reset User");
        
        DatePicker otrd = new DatePicker();
        Label otrdl = new Label("Expiry Date: ");
        otrd.setOnAction(event -> {
        	LocalDate seldate = otrd.getValue();
        	otrdl.setText("Expiry Date: " + seldate);
        });
        
        Button resetUserButton = new Button("Reset User");
        resetUserButton.setOnAction(event -> {
        	String usn = resetUserField.getText();
        	int idx = -1;
        	for (int i = 0; i < ua.size(); ++i) {
        		if ( ua.get(i).uname.equals(usn)) {
        			idx = i;
        			break;
        		}
        	}
        	if (idx != -1) {
        		ua.get(idx).pw = otrf.getText();
        		ua.get(idx).onetimepw = true;
        		ua.get(idx).expdate = otrd.getValue();
        		ua.get(idx).fname = ""; ua.get(idx).pname = ""; ua.get(idx).mname = ""; ua.get(idx).lname = "";
        	}
        });
        
        // Layout (VBox) with spacing between elements
        VBox alay = new VBox(10);
        alay.getChildren().addAll(
                new Label("Account to be deleted:"), accountToDeleteField, deleteAccountButton,
                new Label("One-time invite to be added:"), oneTimePasswordField,
                otpAdminRole, otpInstructorRole, otpUserRole,
                addOneTimePasswordButton,
                new Label("User to edit roles for:"), userToEditRolesField,
                new Label("User List"), unl,
                addInstructorRoleButton, addAdminRoleButton, addUserRoleButton,
                removeInstructorRoleButton, removeAdminRoleButton, removeUserRoleButton,
                resetUserField, otrf,
                otrdl, otrd,
                resetUserButton,
                //listUsersButton,
                alogoutButton
        );



        // Create the scene 'ahome' with the layout
        Scene ahome = new Scene(alay, 800, 800);

        // Set the scene to the stage
        //primaryStage.setTitle("Admin Home");
        //primaryStage.setScene(ahome);
        //primaryStage.show();
        
        deleteAccountButton.setOnAction(event -> {
        	for (int i = 0; i < ua.size(); ++i) {
        		if (ua.get(i).uname.equals(accountToDeleteField.getText())) {
        			ua.remove(i);
        			unl.getItems().remove( accountToDeleteField.getText() );
        			primaryStage.show();
        			//System.out.println("Removed user");
        			break;
        		}
        	}
        });
   

        
        // ------ UNAME/PW SETUP ---------
        TextField suname = new TextField();
        suname.setPromptText("Username");
        PasswordField spw = new PasswordField();
        spw.setPromptText("Password");
        PasswordField spwc = new PasswordField();
        spwc.setPromptText("Confirm Password");
        Button cracc = new Button("Create Account");

        // Layout (VBox) with spacing between fields
        VBox crlay = new VBox(10);
        crlay.getChildren().addAll(
                new Label("Username:"), suname,
                new Label("Password:"), spw,
                new Label("Confirm Password:"), spwc,
                cracc
        );
        Scene cracs = new Scene(crlay, 400, 450);
        
        // --------- USER RE-SETUP ----------------
        PasswordField rpw = new PasswordField();
        spw.setPromptText("Password");
        PasswordField rpwc = new PasswordField();
        spwc.setPromptText("Confirm Password");
        Button rracc = new Button("Reset Account");

        // Layout (VBox) with spacing between fields
        VBox rrlay = new VBox(10);
        rrlay.getChildren().addAll(
                new Label("Password:"), rpw,
                new Label("Confirm Password:"), rpwc,
                rracc
        );
        Scene rracs = new Scene(rrlay, 400, 450);
        
        
        
        // ------ USER SETUP ---------
        TextField cunameF = new TextField(); // Username field
        cunameF.setPromptText("Username");

        PasswordField cpwF = new PasswordField(); // Password field
        cpwF.setPromptText("Password");

        PasswordField confirmPwF = new PasswordField(); // Confirm Password field
        confirmPwF.setPromptText("Confirm Password");

        TextField emailField = new TextField(); // Email field
        emailField.setPromptText("Email");

        TextField firstNameField = new TextField(); // First Name field
        firstNameField.setPromptText("First Name");

        TextField middleNameField = new TextField(); // Middle Name field
        middleNameField.setPromptText("Middle Name");

        TextField lastNameField = new TextField(); // Last Name field
        lastNameField.setPromptText("Last Name");

        TextField preferredNameField = new TextField(); // Preferred Name field
        preferredNameField.setPromptText("Preferred Name");

        // Create a Button to finalize the account
        Button finalizeButton = new Button("Finalize Account");

        // Layout (VBox) with spacing between fields
        VBox uslay = new VBox(10);
        uslay.getChildren().addAll(
                //new Label("Username:"), cunameF,
                //new Label("Password:"), cpwF,
                //new Label("Confirm Password:"), confirmPwF,
                new Label("Email:"), emailField,
                new Label("First Name:"), firstNameField,
                new Label("Middle Name:"), middleNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Preferred Name:"), preferredNameField,
                finalizeButton
        );

        // Create a Scene with the layout
        Scene uset = new Scene(uslay, 400, 450);

        // Set the scene to the stage
        primaryStage.setTitle("User Setup");

        // Button action (e.g., collect data or perform validation)
        
        
        
        // Transitions
        
        loginButton.setOnAction(event -> { // Login to thing
        	String uname = usernameInput.getText();
        	String pw = passwordInput.getText();
        	// loggedin = chk(uname, pw);
        	if (chk(uname, pw)) { 
        		cuser = find(uname, pw);
        		System.out.println(cuser.uname);
        		if (!cuser.firsttime) {
        			if (!cuser.onetimepw) {
	        			primaryStage.setScene(rolesel);
		        		primaryStage.setTitle("Role Select");
		        		primaryStage.show();
        			} else if (cuser.expdate.isAfter( LocalDate.now() )){
        				primaryStage.setScene(rracs);
        				primaryStage.setTitle("Reset Account");
        			}
        		} else {
        			primaryStage.setScene(uset);
        			primaryStage.setTitle("User Setup");
        			primaryStage.show();
        		}
        	}
        	//System.out.println(uname + " " + pw);
        	//System.out.println(ua.get(0).uname + ua.get(0).pw);
        });
        
        otplogin.setOnAction(event ->  {
        	String otpw = otpi.getText();
        	if (otps.contains(otpw)) {
        		otps.remove(otpw);
        		User u = new User();
        		u.uname = "";
        		u.pw = otpw;
        		u.firsttime = true;
        		u.instr = false;
        		u.admin = false;
        		u.user = false;
        		if (otpow.get(otpw) % 2 == 1) {
        			u.admin = true;
        		} 
        		if ((otpow.get(otpw) >> 1) % 2  == 1) {
        			u.instr = true;
        		}
        		if ((otpow.get(otpw) >> 2) % 2 == 1) {
        			u.user = true;
        		}
        		otpow.remove(otpw);
        		ua.add(u);
        		
        		primaryStage.setScene(cracs);
        		primaryStage.setTitle("Account Creation");
        		primaryStage.show();
        	} else {
        		// System.out.println(otpi.getText());
        	}
        });
        
        cracc.setOnAction(event -> {
        	String pw1 = spw.getText();
        	boolean repeat = false;
        	if (ua.size() != 0) {
        		for (int i = 0; i < ua.size(); i++) {
        			//System.out.println(i + " " + ua.size());
        			if (ua.get(i).uname.equals(suname.getText())) {
        				repeat = true;
        			}
        		}
        	}
        	if (repeat) {
        		System.out.println("Username is already taken");
        	}
        	if (!repeat && pw1.equals(spwc.getText())) {
        		if (ua.size() == 0) { 
        			User u = new User();
        			u.admin = true;
        			ua.add(u);
        		} 
        		ua.get(ua.size() - 1).uname = suname.getText();
        		unl.getItems().add(suname.getText());
        		ua.get(ua.size() - 1).pw = pw1;
        		ua.get(ua.size() - 1).password = new Password(pw1);
        		primaryStage.setScene(scene);
        		primaryStage.setTitle("Login");
        		primaryStage.show();
        	}
        });
        
        rracc.setOnAction(event -> {
        	String pw1 = rpw.getText();
        	if ( pw1.equals(rpwc.getText()) ) {
        		cuser.pw = pw1;
        		cuser.password = new Password(pw1);
        		cuser.onetimepw = false;
        		cuser.firsttime = true;
        		primaryStage.setScene(scene);
        		primaryStage.setTitle("Login");
        		primaryStage.show();
        	}
        });
        
        finalizeButton.setOnAction(event -> {
            // Collect data from the fields
            //String username = cunameF.getText();
            //String password = cpwF.getText();
            //String confirmPassword = confirmPwF.getText();
            String email = emailField.getText();
            String firstName = firstNameField.getText();
            String middleName = middleNameField.getText();
            String lastName = lastNameField.getText();
            String preferredName = preferredNameField.getText();


        	//cuser.uname = username;
        	// cuser.pw = password;
        	cuser.fname = firstName;
        	cuser.lname = lastName;
        	cuser.mname = middleName;
        	cuser.pname = preferredName;
        	if (preferredName.equals("")) {
        		cuser.pname = firstName;
        	}
        	cuser.firsttime = false;
        	
        	primaryStage.setScene(rolesel);
        	primaryStage.setTitle("Role Selection");
        	primaryStage.show();
        });
        
        ulogoutButton.setOnAction(event -> {
            //System.out.println("You have logged out.");
        	primaryStage.setTitle("Login");
            primaryStage.setScene(scene); // goto login
            primaryStage.show();
        });

        ilogoutButton.setOnAction(event -> {
            //System.out.println("You have logged out.");
        	primaryStage.setTitle("Login");
            primaryStage.setScene(scene); // goto login
            primaryStage.show();
        });
        
        adminButton.setOnAction(event -> {
        	if (cuser.admin) {
        		primaryStage.setScene(ahome);
        		primaryStage.setTitle("Admin Home");
        		primaryStage.show();
        	}
        });
        
        instructorButton.setOnAction(event -> {
        	if (cuser.instr) {
        		primaryStage.setScene(ihome);
        		primaryStage.setTitle("Instructor Home");
        		primaryStage.show();
        	}
        });
        
        userButton.setOnAction(event -> {
        	if (cuser.user) {
	        	primaryStage.setScene(uhome);
	        	primaryStage.setTitle("User Home");
	        	primaryStage.show();
        	}
        });
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
    	role = 1;
    	ua = new ArrayList<User>();
    	csc = 0;
    	loggedin = false;
    	otps = new ArrayList<String>();
    	otps.add("otp");
    	otpow = new HashMap<String, Integer>();
    	otpow.put("otp", 1);
        launch(args);
    }

}