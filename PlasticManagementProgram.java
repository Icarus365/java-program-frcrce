package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;
import java.util.Optional;

public class PlasticManagementProgram extends Application {

    private int loginAttempts = 0;  // Track the number of failed login attempts

    // Variables for Plastic Management
    String[] plast_types = {"Transparent", "Translucent", "Black"};
    float[] w = new float[3];
    float weight;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plastic Management System");
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage primaryStage) {
        Label welcomeLabel = new Label("WELCOME TO THE PLASTIC MANAGEMENT SYSTEM");
        welcomeLabel.setStyle("-fx-font-size: 10px; -fx-padding: 10; -fx-font-weight: bold;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> showSignUpScreen(primaryStage));

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> showLoginScreen(primaryStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        VBox layout = new VBox(20, welcomeLabel, signUpButton, loginButton, exitButton);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSignUpScreen(Stage primaryStage) {
        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setStyle("-fx-font-size: 10px; -fx-padding: 10; -fx-font-weight: bold;");

        TextField NameField = new TextField();
        NameField.setPromptText("Enter Username");

        TextField PasswordField = new PasswordField();
        PasswordField.setPromptText("Enter Password");

        TextField securityAnswerField = new TextField();
        securityAnswerField.setPromptText("Enter Security Answer");

        TextField cityField = new TextField();
        cityField.setPromptText("Enter City");

        TextField areaField = new TextField();
        areaField.setPromptText("Enter Area");

        TextField streetField = new TextField();
        streetField.setPromptText("Enter Street");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Residential Address");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter Phone Number");

        TextField pincodeField = new TextField();
        pincodeField.setPromptText("Enter Pincode");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String username = NameField.getText();
            String password = PasswordField.getText();
            String securityAnswer = securityAnswerField.getText();
            String city = cityField.getText();
            String area = areaField.getText();
            String street = streetField.getText();
            String address = addressField.getText();
            String phoneNumber = phoneField.getText();
            String pincode = pincodeField.getText();

            if (!isValidPhoneNumber(phoneNumber)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid phone number! Must be 10 digits.");
                return;
            }
            if (!isValidPincode(pincode)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid pincode! Must be 6 digits.");
                return;
            }

            try {
                saveUserData(username, password, securityAnswer, city, area, street, address, phoneNumber, pincode);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Sign-up successful!");
                showMainMenu(primaryStage);
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving data.");
            }
        });

        VBox layout = new VBox(20, signUpLabel, NameField, PasswordField, securityAnswerField, cityField, areaField,
                streetField, addressField, phoneField, pincodeField, submitButton);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");

        primaryStage.getScene().setRoot(layout);
    }

    private void showLoginScreen(Stage primaryStage) {
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 10px; -fx-padding: 10; -fx-font-weight: bold;");

        TextField NameField = new TextField();
        NameField.setPromptText("Enter Username");

        PasswordField PasswordField = new PasswordField();
        PasswordField.setPromptText("Enter Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String Name = NameField.getText();
            String Password = PasswordField.getText();

            if (validateLogin(Name, Password)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
                loginAttempts = 0; // Reset the counter on successful login
                showPlasticSection(primaryStage); // Show Plastic Section
            } else {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    showExitButton(primaryStage); // Call the `showExitButton` method
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password. Attempt " + loginAttempts + " of 3.");
                }
            }
        });

        VBox layout = new VBox(20, loginLabel, NameField, PasswordField, loginButton);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");

        primaryStage.getScene().setRoot(layout);
    }

    // Move this method to the class level
    private void showExitButton(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Attempts Exceeded");
        alert.setHeaderText(null);
        alert.setContentText("You have exceeded the maximum login attempts. Would you like to exit?");

        ButtonType exitButton = new ButtonType("Exit");
        ButtonType retryButton = new ButtonType("Retry");

        alert.getButtonTypes().setAll(exitButton, retryButton);

        // Handle user choice
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == exitButton) {
            System.exit(0); // Exit the application
        } else {
            loginAttempts = 0; // Reset login attempts if retrying
            showLoginScreen(primaryStage); // Redirect to login screen
        }
    }


    private boolean validateLogin(String Name, String Password) {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            String currentName = null;
            String currentPassword;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Check for blank lines
                if (line.isEmpty()) {
                    continue;
                }

                // Parse user details
                if (line.startsWith("Name:")) {
                    currentName = line.substring(5).trim();
                } else if (line.startsWith("Password:")) {
                    currentPassword = line.substring(9).trim();

                    // Validate if both Name and Password match
                    if (currentName != null && currentName.equals(Name) && currentPassword.equals(Password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while reading data.");
        }

        return false;
    }

    private void saveUserData(String Name, String Password, String securityAnswer, String city, String area,
                              String street, String address, String phoneNumber, String pincode) throws IOException {
        try (FileWriter fileWriter = new FileWriter("users.txt", true);
             PrintWriter writer = new PrintWriter(fileWriter)) {

            writer.println("Name:" + Name);
            writer.println("Password:" + Password);
            writer.println("Security Answer:" + securityAnswer);  // Save security answer
            writer.println("City:" + city);
            writer.println("Area:" + area);
            writer.println("Street:" + street);
            writer.println("Residential Address:" + address);
            writer.println("Phone Number:" + phoneNumber);
            writer.println("Pincode:" + pincode);
            writer.println("____________________________");
        }
    }

    private void showPlasticSection(Stage primaryStage) {
        Label headerLabel = new Label("Welcome to Plastic Management Section");
        headerLabel.setStyle("-fx-font-size: 10px; -fx-padding: 10; -fx-font-weight: bold;");

        ChoiceBox<String> plasticTypeBox = new ChoiceBox<>();
        plasticTypeBox.getItems().addAll(plast_types);
        plasticTypeBox.setValue(plast_types[0]);

        TextField weightField = new TextField();
        weightField.setPromptText("Enter weight in grams");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String type = plasticTypeBox.getValue();
            String weightText = weightField.getText();

            if (!weightText.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid number for weight.");
                return;
            }

            weight = Float.parseFloat(weightText);

            int typeIndex = switch (type) {
                case "Transparent" -> 0;
                case "Translucent" -> 1;
                case "Black" -> 2;
                default -> 0;
            };

            w[typeIndex] = weight;
            showAlert(Alert.AlertType.INFORMATION, "Success", "Plastic weight recorded successfully!");

            // Ask user if they want to add more data
            Button continueButton = new Button("Add More");
            continueButton.setOnAction(_ -> showPlasticSection(primaryStage));

            Button endButton = new Button("End");
            endButton.setOnAction(_ -> displayWeight(primaryStage));

            VBox layout = new VBox(20, headerLabel, plasticTypeBox, weightField, submitButton, continueButton, endButton);
            layout.setStyle("-fx-padding: 30; -fx-alignment: center;");

            primaryStage.getScene().setRoot(layout);
        });

        VBox layout = new VBox(20, headerLabel, plasticTypeBox, weightField, submitButton);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");
        primaryStage.getScene().setRoot(layout);
    }

    private void displayWeight(Stage primaryStage) {

        String output = "---------------------Welcome to output Section\n\n" +
                "Remember: PLEASE DON'T USE PLASTIC or CONSIDER reducing plastic use and recycling. This helps protect our oceans and environment!\n\n" +
                "Final Weight\n" +
                "Transparent: " + w[0] + "\n" +
                "Translucent: " + w[1] + "\n" +
                "Black: " + w[2] + "\n\n" +

                // Measures to reduce plastic use
                "Measures to Reduce Plastic Use and Encourage Recycling:\n\n" +

                // For Transparent plastic
                "Transparent Plastic:\n" +
                "- Recycle transparent plastics where possible.\n" +
                "- Avoid single-use transparent plastics like plastic wrap and food containers.\n" +
                "- Use glass or metal containers as alternatives.\n" +
                "- Educate others about the harmful impact of transparent plastics on marine life.\n\n" +

                // For Translucent plastic
                "Translucent Plastic:\n" +
                "- Reuse translucent plastic containers to reduce waste.\n" +
                "- Support initiatives to recycle or repurpose translucent plastics.\n" +
                "- Choose biodegradable options when available.\n" +
                "- Reduce consumption of translucent plastic items like bags and bottles.\n\n" +

                // For Black plastic
                "Black Plastic:\n" +
                "- Avoid using black plastic as it is harder to recycle.\n" +
                "- Opt for other colors or materials when possible.\n" +
                "- Encourage retailers to avoid black plastic packaging.\n" +
                "- Be mindful of disposal to prevent it from ending up in oceans.\n\n" +

                // Final message
                "-------------------------Thank you for your valuable inputs and for using our program!";

        // Create an Alert Box
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plastic Management - Final Report");
        alert.setHeaderText(null);
        alert.setContentText(output);

        // Show the alert box with the content
        alert.showAndWait();

        // Optionally, you can return to the main menu after displaying the alert
        showMainMenu(primaryStage);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidPincode(String pincode) {
        return pincode.matches("\\d{6}");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
