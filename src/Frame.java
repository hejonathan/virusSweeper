import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.System.*;

public class Frame extends JFrame implements MouseListener, KeyListener{
    public static int WIDTH = 800;
    public static int HEIGHT = 750;
    public Panel p = new Panel();
    public Frame(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        add(p);
        setVisible(true);
        addMouseListener(this);
        addKeyListener(this);
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        if(p.menu) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                p.moveMenuPointer("U");
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                p.moveMenuPointer("D");
            }
            p.repaint();
        }
        else if(p.board) {
            if (!p.control.endGame()) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    p.movePointer("U");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    p.movePointer("D");
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.movePointer("L");
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.movePointer("R");
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    p.flagPointer();
                }
                p.repaint();
            }
        }
    }
    public void keyReleased(KeyEvent e){
        if(p.board) {
            if (p.control != null) {
                if (!p.control.endGame()) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        p.revealPointer();
                    }
                    p.repaint();
                }
            }
        }
        else if(p.menu){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                p.select();
            }
        }
    }
}
