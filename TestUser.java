//Plastic Management System

import java.io.IOException;
import java.util.Scanner;

class TestUser {
    static void Welcome() {
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" \t\t\t\t\t\t\tWELCOME TO PLASTIC MANAGEMETN SYSTEM ");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" \t\t\t\t\t\t\tWe believe in protecting the OCEAN and Nature");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------\n\n");

    }

    public static void main(String[] args) throws IOException {
        Welcome();
        Scanner sc = new Scanner(System.in);
        int val = 0;
        do {
            System.out.println("1.User \n Choose the login system to proceed in ");
            int choice = sc.nextInt();
            if (choice == 1) {
                Plastic_Gen user1 = new Plastic_Gen();

                val = user1.input();//Method of Login_User i.e. parent of Plastic_Gen
                
                if (val != -1) {
                    user1.plasticType(); //Method of plastic_Gen
                    user1.displayWeight();//Method of plastic_Gen
                }
            }

            else {
                System.out.println("Invlaid Input");
                val = -1;
            } 
            
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------------------------\n\n");

        } while (val == -1); 

    }
}