import java.io.*;
import java.util.Scanner;

class Login_User extends Address {
    String name;
    String customer_id;
    private String password;
    private String securityAns;

    Scanner sc = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);

    int flag; // for successful login

    File f1 = new File("trial1.txt");
    FileWriter f = new FileWriter(f1, true);
    PrintWriter pw = new PrintWriter(f);
    BufferedReader br = new BufferedReader(new FileReader(f1));

    //RandomAccessFile rand = new RandomAccessFile("trial1.txt", "rw");

    Login_User() throws IOException {
        ;
    }

    int input() throws IOException {
        int ch;
        do {
            System.out.print("\n1.Login \n2.Sign-Up \n3.Go Back to Main Menu  \nEnter your choice: ");
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    f1 = new File("trial1.txt");
                    f = new FileWriter(f1, true);
                    pw = new PrintWriter(f);
                    br = new BufferedReader(new FileReader(f1));

                    existingUser();
                    if (flag == 1) {
                        flag = 0;
                        return 1;// plastic section
                    }

                    break;

                case 2:

                    f1 = new File("trial1.txt");
                    f = new FileWriter(f1, true);
                    pw = new PrintWriter(f);
                    br = new BufferedReader(new FileReader(f1));

                    newUser();
                    break;

                case 3:
                    return -1; // main menu

                default:
                    System.out.println("\nEnter valid choice: ");

            }

            System.out.println("\nPress \"1\" for login/sign up page?");
            System.out.println("Press \"2\" for going to Plastic Section");
            System.out.println("Press \"0\" for exiting");
            System.out.print("Enter Your choice: ");
            ch = sc.nextInt();
            if (ch == 0)
                System.exit(0);
        } while (ch == 1);

        pw.flush();
        pw.close();
        // rand.close();
        br.close();
        return ch;
    }

    void existingUser() throws IOException {
        int ch, p = 0;
        String temp, temp2 = "", temp3 = "";
        sc.nextLine();

        System.out.print("\nEnter your name: ");
        name = sc.nextLine();
        sc2.nextLine();

        String line = br.readLine();
        while (line != null && p == 0) {

            if ("Name: ".equals(line.substring(0, 6))) {
                if (name.equalsIgnoreCase(line.substring(6))) {
                    temp2 = br.readLine();
                    p = 1;

                }
            }
            // to read the next line
            line = br.readLine();
            temp3 = line;
        }
        if (p == 0) {
            System.out.println("\nInvalid Name! ");

            input();
        } else {
            // temp2-> password
            temp2 = temp2.substring(10);
            //temp3->security code
            temp3 = temp3.substring(17);
        }

        sc.nextLine();

        System.out.print("\nDo you remember your password? \npress \"1\" for yes, else any other number ");
        System.out.print("\nEnter Your choice: ");
        ch = sc.nextInt();

        if (ch == 1) {
            sc.nextLine();
            System.out.print("\n------You Have 3 attempts!----\n");
            for (int i = 0; i <= 2; i++) {

                System.out.print("\nEnter your Password:  ");
                temp = sc.nextLine();
                sc.nextLine();

                // sc.nextLine();

                if (temp.trim().equals(temp2.trim())) {
                    flag = 1;
                    System.out.println("\n-----------------------Login Successful------------------------\n");
                    return;
                    // break;
                } else {
                    System.out.println("\nYour Password doesn't match try again! \n");
                    if (i == 2) {
                        System.out.println("\n--------You exceeded your attempts!-------\n");
                        System.exit(0);
                        
                    }
                }

            }

        }

        else {
            System.out.print("\n----------You Have 3 attempts to enter correct Security Password!---------\n");

            for (int i = 0; i <= 2; i++) {
                sc.nextLine();
                System.out.println("\nEnter a Security answer to the question");
                System.out.println("\nQues- What is the name  of your first school? ");

                temp = sc.nextLine(); // entered security password
                sc.nextLine();

                if (temp.equals(temp3)) {
                    System.out.println("Your Password is: " + temp2);
                    break;

                }

                else {
                    sc.nextLine();
                    System.out.println("\n---------Your Security Answer doesn't match try again!------- \n");
                    if (i == 2) {
                        System.out.println("\n------------You exceeded your attempts!-----------------\n");
                        System.exit(0);
                    }
                }
                sc.nextLine();
            }
        }
        pw.flush();
        f.flush();

    }

    // same name
    void newUser() throws IOException {
        int p = 0;
        do {
            sc.nextLine();
            System.out.print("\nEnter your Username:  ");
            name = sc.nextLine().trim();
            br = new BufferedReader(new FileReader("trial1.txt"));
            String line = br.readLine(); // checking duplicate
            while (line != null) {
                if (("Name: " + name).equalsIgnoreCase(line)) {
                    p = 1;
                    System.out.println("Username already exists");
                    break;
                }

                line = br.readLine();
            }
            if (p == 0) {
                pw.println("Name: " + name);
                // p=0;
                break;

            }
        } while (p == 1);

        System.out.print("Enter your Password:  ");
        password = sc.nextLine().trim();
        pw.println("Password: " + password);
        sc2.nextLine();

        System.out.print("\n\nEnter your Security answer (in case you forget your password) \n\nQues- What is the name  of your first school? : ");
        securityAns = sc.nextLine();
        pw.println("Security Answer: " + securityAns);
        sc2.nextLine();

        getAddress(pw);
        pw.println("____________________________");
        pw.flush();
    }

}
