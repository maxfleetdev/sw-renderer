package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private char currentKeyPressed = '\0';  // Variable to store the current key pressed
    private boolean keyPressed = false;     // Variable to check if a key is pressed

    // Implementing KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        currentKeyPressed = e.getKeyChar();  // Capture the key pressed
        keyPressed = true;                   // Set key pressed flag to true
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKeyPressed = '\0';  // Reset the current key
        keyPressed = false;        // Reset key pressed flag
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This can be left empty or used if you need to handle key typed events
    }

    // Method to get the current key pressed
    public char getCurrentKeyPressed() {
        return currentKeyPressed;
    }

    // Method to check if a key is currently pressed
    public boolean isKeyPressed() {
        return keyPressed;
    }
}