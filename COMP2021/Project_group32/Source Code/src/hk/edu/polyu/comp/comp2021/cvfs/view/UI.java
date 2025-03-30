package hk.edu.polyu.comp.comp2021.cvfs.view;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;
import java.util.Scanner;

public class UI {
    private CVFSController controller;

    public UI() {
        controller = new CVFSController();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            System.out.print(">> ");
            command = scanner.nextLine();
            if (command.equals("quit")) {
                System.out.println("Exiting...");
                scanner.close();
                System.exit(0);
            }
            controller.handleCommand(command);
        }
    }
}