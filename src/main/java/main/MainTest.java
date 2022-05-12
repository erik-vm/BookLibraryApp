package main;

import presistence.RepositoryAdmin;
import presistence.RepositoryBook;
import presistence.RepositoryMember;

import java.sql.SQLException;
import java.util.Scanner;

public class MainTest {
    static Scanner scanner = new Scanner(System.in);
    static RepositoryAdmin repositoryAdmin = new RepositoryAdmin();
    static RepositoryBook repositoryBook = new RepositoryBook();
    static RepositoryMember repositoryMember = new RepositoryMember();

    public static void main(String[] args) throws SQLException {

        boolean quit = false;
        while (!quit) {
            System.out.println("-------------------------------------------------------------------------------------------------------" +
                    "\nSelect role: \n\t1 - Admin \n\t2 - Member \n\t3 - Quest \n\n\t9 - Quit" +
                    "\n-------------------------------------------------------------------------------------------------------");
            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case 1:
                    System.out.println("Enter your ID: ");

                    if (repositoryAdmin.login(scanner.nextInt())) {
                        adminMenu();
                    }
                    break;
                case 2:
                    repositoryMember.showAllMembers();
                    System.out.println("Enter your ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    if (repositoryMember.doesMemberExist(memberId)){
                    memberMenu(memberId);
                    }
                    break;
                case 3:
                    questMenu();
                    break;
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    static void adminMenu() throws SQLException {
        System.out.println("-------------------------------------------------------------------------------------------------------" +
                "\n0 - Show menu\n1 - Show all admins \n2 - Add admin \n3 - remove admin" +
                "\n4 - Show all books \n5 - Add book to library \n6 - Remove book from library \n7 - Modify book " +
                "\n\n9 - Quit" +
                "\n-------------------------------------------------------------------------------------------------------");
        boolean quit = false;
        while (!quit) {

            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case 0:
                    System.out.println("-------------------------------------------------------------------------------------------------------" +
                            "\n0 - Show menu\n1 - Show all admins \n2 - Add admin \n3 - Remove admin" +
                            "\n4 - Show all books \n5 - Add book to library \n6 - Remove book from library \n7 - Modify book " +
                            "\n\n9 - Quit" +
                            "\n-------------------------------------------------------------------------------------------------------");
                    break;
                case 1:
                    repositoryAdmin.showAllAdmins();
                    break;
                case 2:
                    repositoryAdmin.createAdmin();
                    break;
                case 3:
                    repositoryAdmin.deleteAdmin();
                    break;
                case 4:
                    repositoryBook.showAllBooks();
                    break;
                case 5:
                    repositoryBook.addBook();
                    break;
                case 6:
                    repositoryBook.removeBook();
                    break;
                case 7:
                    repositoryBook.modifyBook();
                    break;
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }

    static void memberMenu(int memberId) throws SQLException {
        System.out.println("-------------------------------------------------------------------------------------------------------" +
                "\n0 - Show menu\n1 - Show all books \n2 - Show books I have \n3 - Loan a book \n4 - Return a book \n5 - Delete your account. " +
                "\n\n9 - Quit" +
                "\n-------------------------------------------------------------------------------------------------------");
        boolean quit = false;
        while (!quit) {

            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case 0:
                    System.out.println("-------------------------------------------------------------------------------------------------------" +
                            "\n0 - Show menu\n1 - Show all books \n\t2 - Show books I have \n3 - Loan a book \n4 - Return a book \n5 - Delete your account. " +
                            "\n\n9 - Quit" +
                            "\n-------------------------------------------------------------------------------------------------------");
                    break;
                case 1:
                    repositoryBook.showAllBooks();
                    break;
                case 2:
                    repositoryMember.memberCurrentBooksPrint(memberId);
                    break;
                case 3:
                    repositoryMember.loanBook(memberId);
                    break;
                case 4:
                    repositoryMember.returnBook(memberId);
                    break;
                case 5:
                    repositoryMember.deleteMember(memberId);
                    quit = true;
                    break;
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }

    static void questMenu() throws SQLException {
        System.out.println("-------------------------------------------------------------------------------------------------------" +
                "\n0 - Show menu\n1 - Show all books \n2 - Create membership " +
                "\n\n9 - Quit" +
                "\n-------------------------------------------------------------------------------------------------------");
        boolean quit = false;
        while (!quit) {

            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case 0:
                    System.out.println("-------------------------------------------------------------------------------------------------------" +
                            "\n0 - Show menu\n1 - Show all books \n2 - Create membership " +
                            "\n\n9 - Quit" +
                            "\n-------------------------------------------------------------------------------------------------------");
                    break;
                case 1:
                    repositoryBook.showAllBooks();
                    break;
                case 2:
                    repositoryMember.createMember();
                    break;
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }
}
