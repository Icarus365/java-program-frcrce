import java.io.*;
import java.util.Scanner;

class Address {
    Scanner sc = new Scanner(System.in);
    String city, area, street, residentialAddress, phoneNumber;
    int pincode;

    File f1 = new File("trial1.txt");
    FileWriter f = new FileWriter(f1, true);
    PrintWriter pw = new PrintWriter(f);

    Address() throws IOException {
    }

    void getAddress(PrintWriter pw1) throws IOException {
        pw = pw1;

        System.out.println("\n***************Enter Your Address****************\n");

        System.out.print("\nEnter City name: ");
        city = sc.nextLine();

        System.out.print("\nEnter Area name: ");
        area = sc.nextLine();

        System.out.print("\nEnter Street name: ");
        street = sc.nextLine();

        System.out.print("\nEnter Residential Address: ");
        residentialAddress = sc.nextLine();

        do {
			System.out.print("\nEnter 10-digit Phone Number: ");
			phoneNumber = sc.nextLine();
	
			if (phoneNumber.length() != 10) {
				System.out.println("Invalid phone number. Please enter a 10-digit number.");
			}
		} while (phoneNumber.length() != 10);
		
        System.out.print("\nEnter Pincode: ");
        pincode = sc.nextInt();

        pw.println("City: " + city);
        pw.println("Area: " + area);
        pw.println("Street: " + street);
        pw.println("Residential Address: " + residentialAddress);
        pw.println("Phone Number: " + phoneNumber);
        pw.println("Pincode: " + pincode);

        pw.flush();
    }

    void displayAddress() {
        System.out.println("Address is:\n" + "\n"
                + "City: " + city + "\n"
                + "Area: " + area + "\n"
                + "Street: " + street + "\n"
                + "Residential Address: " + residentialAddress + "\n"
                + "Phone Number: " + phoneNumber + "\n"
                + "Pincode: " + pincode);
    }
}