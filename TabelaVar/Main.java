import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Main
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        VarTable table = new VarTable();
        Interpreter.start(table);
        DataOUT.Start(table);
        while (true) {
            DataOUT.printComandSign();
            String code = scanner.nextLine();
            interpret(code);
        }
    }

    static void interpret(String code) {
        Interpreter.interpret(code);
    }
}

