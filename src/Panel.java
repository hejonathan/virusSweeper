import java.awt.*;
import java.awt.image.*;
import java.awt.Image;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import static java.lang.System.*;

public class Panel extends JPanel{
    public final int minx = 90;
    public int minMenux = getWidth()/3 - 70;
    public final int miny = 65;
    public int minMenuy = getHeight()/5 + 270;
    public final int maxx = 710;
    public final int maxy = 685;
    public boolean menu = true, board = false, option = false;
    public Control control;
    public Image mine, flag, mask, block, virusbustR, virusbustL;
    public int pointerX = 10, pointerY = 10;
    public int menuPointerY = 0;
    public boolean facingLeft = false;
    public Panel() {
        try {
            mine = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("mine.gif"));
            flag = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("flag.gif"));
            mask = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("vac.gif"));
            block = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("Block.gif"));
            virusbustR = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("VirusBusterR.gif"));
            virusbustL = Toolkit.getDefaultToolkit().createImage(Panel.class.getResource("VirusBusterL.gif"));
            mine = mine.getScaledInstance(29, 29, Image.SCALE_DEFAULT);
            flag = flag.getScaledInstance(29, 29, Image.SCALE_DEFAULT);
            mask = mask.getScaledInstance(29, 29, Image.SCALE_DEFAULT);
            virusbustR = virusbustR.getScaledInstance(29, 29, Image.SCALE_DEFAULT);
            virusbustL = virusbustL.getScaledInstance(29, 29, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            out.println("Image error Adam's a stupid idiot. I agree -Will");
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(150, 150, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        if(menu){
            /*g.setColor(Color.darkGray);
            g.fillRect(getWidth()/3, getHeight()/5, getWidth()/3, 3*getHeight()/5);

            g.setColor(new Color(120, 120, 120));
            g.fillRect(getWidth()/3 + getWidth()/10, 2*getHeight()/5, getWidth()/3 * 8/10, getHeight()/10);
            g.fillRect(getWidth()/3 + getWidth()/10, 2*getHeight()/5 + getHeight()/10, getWidth()/3* 8/10, getHeight()/10);
            g.fillRect(getWidth()/3 + getWidth()/10, 2*getHeight()/5 + 2*getHeight()/10, getWidth()/3 * 8/10, getHeight()/10);
            g.fillRect(getWidth()/3 + getWidth()/10, 2*getHeight()/5 + 3*getHeight()/10, getWidth()/3 * 8/10, getHeight()/10);
*/
            g.setColor(Color.WHITE);
            g.setFont(new Font("New Courier", Font.BOLD, 64));
            g.drawString("Minesweeper", getWidth()/3 - 25, getHeight()/5 + 30);

            g.setFont(new Font("New Courier", Font.BOLD, 48));
            minMenux = getWidth()/3 - 20;
            g.drawString("Easy", minMenux, minMenuy);
            g.drawString("Medium", minMenux, minMenuy + 100);
            g.drawString("Hard", minMenux, minMenuy + 200);
            g.drawString("Options", minMenux, minMenuy + 300);

            drawMenuPointer(g);
        }
        else if(board){
            if(!control.endGame())
                redrawAll(g);
            else if (control.isFinished()) {
                drawGridLines(g);
                drawNumsAll(g);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Comic Sans", Font.BOLD, 32));
                g.drawString("YOU WIN!", getWidth()/2-100, 40);
            }
            else{
                drawGridLines(g);
                drawNumsAll(g);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Comic Sans", Font.BOLD, 32));
                g.drawString("GAMEOVER", getWidth()/2-100, 40);
            }
        }
        else if(option){

        }
    }
    private void drawMask(Graphics g){
        for(int i=0;i<control.numMask;i++)
            g.drawImage(mask,minx+i*40,miny-35,this);
    }
    private void drawMine(Graphics g){
        boolean[][] grid = control.mine;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j]){
                    g.drawImage(mine, minx + 31 * i+1, miny + 31 * j+1, this);
                }
            }
        }
    }
    private void drawFlags(Graphics g){
        boolean[][] grid = control.flagged;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j]){
                    g.drawImage(flag, minx + 31 * i+1, miny + 31 * j+1, this);
                }
            }
        }
    }
    private void drawGridLines(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i=minx; i<=maxx; i+=31) {
            g.drawLine(i, miny, i, maxy);
        }
        for (int i=miny; i<=maxy; i+=31) {
            g.drawLine(minx, i, maxx, i);
        }
    }
    private int[] mapToPixels(int cx, int cy) {
        return new int[]{31*cx+90, 31*cy+65};
    }
    public boolean revealPointer() {
        if(!control.flagged[pointerX][pointerY] && board)
            return control.reveal(pointerX, pointerY);
        else return false;
    }
    public void drawNumMinesLeft(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans", Font.BOLD, 12));
        g.drawImage(mine,minx+120,miny-35,this);
        g.drawString("unrevealed: "+(control.numMines-control.numFlagged-control.minesRevealed),minx+150, miny-15);
    }
    public void drawNumsAll(Graphics g) {
        for (int i=0; i<20; i++) {
            for (int j = 0; j < 20; j++) {
                if (control.mine[i][j]) {
                    g.drawImage(mine, mapToPixels(i, j)[0]+1, mapToPixels(i, j)[1]+1 , 29, 29, this);
                }
                else if (control.count[i][j]!=0) {
                    g.setFont(new Font("Calibri", 1, 20));
                    switch (control.count[i][j]) {
                        case 1:
                            g.setColor(Color.BLUE);
                            break;
                        case 2:
                            g.setColor(new Color(0x33,0x83,0x33));
                            break;
                        case 3:
                            g.setColor(Color.RED);
                            break;
                        case 4:
                            g.setColor(Color.MAGENTA);
                            break;
                        case 5:
                            g.setColor(new Color(128, 0, 0));
                            break;
                        case 6:
                            g.setColor(new Color(60, 60, 60));
                            break;
                        case 7:
                            g.setColor(new Color(40, 20, 40));
                            break;
                        case 8:
                            g.setColor(new Color(0, 0, 0));
                            break;
                    }
                    g.drawString(String.valueOf(control.count[i][j]), mapToPixels(i, j)[0] + 10, mapToPixels(i, j)[1] + 23);
                }
            }
        }
    }
    public void drawNums(Graphics g){
        for (int i=0; i<20; i++) {
            for (int j = 0; j < 20; j++) {
                if (!control.flagged[i][j] && control.revealed[i][j]) {
                    if (control.mine[i][j]) {
                        g.drawImage(mine, mapToPixels(i, j)[0]+1, mapToPixels(i, j)[1]+1 , 29, 29, this);
                    }
                    else if (control.count[i][j]!=0) {
                        g.setFont(new Font("Calibri", 1, 20));
                        switch(control.count[i][j]){
                            case 1:
                                g.setColor(Color.BLUE);
                                break;
                            case 2:
                                g.setColor(new Color(0x33,0x83,0x33));
                                break;
                            case 3:
                                g.setColor(Color.RED);
                                break;
                            case 4:
                                g.setColor(Color.MAGENTA);
                                break;
                            case 5:
                                g.setColor(new Color(128,0,0));
                                break;
                            case 6:
                                g.setColor(new Color(60,60,60));
                                break;
                            case 7:
                                g.setColor(new Color(40,20,40));
                                break;
                            case 8:
                                g.setColor(new Color(0,0,0));
                                break;
                        }
                        g.drawString(String.valueOf(control.count[i][j]), mapToPixels(i, j)[0] + 10, mapToPixels(i, j)[1] + 23);
                    }
                }else{
                    g.drawImage(block, mapToPixels(i, j)[0]+1, mapToPixels(i, j)[1]+1 , 29, 29, this);
                }
            }
        }
    }
    public void redrawAll(Graphics g) {
        g.setColor(new Color(200,200,200));
        g.fillRect(0, 0, getWidth(), getHeight());
        drawMask(g);

        drawGridLines(g);
        //drawBlocks(g);
        drawNums(g);
        drawFlags(g);
        drawNumMinesLeft(g);
        drawPointer(g);
    }
    public void drawPointer(Graphics g){
        g.drawImage(facingLeft? virusbustL:virusbustR, minx + pointerX * 31 + 1, miny + pointerY * 31 + 1, 29, 29, this);
        //g.drawRoundRect(minx + pointerX * 31+1, miny + pointerY * 31+1, 29, 29, 8, 5);
    }
    public void drawMenuPointer(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRoundRect(minMenux - 10, minMenuy-40 +100 * menuPointerY, 700, 50, 25, 25);
    }
    public void flagPointer(){
        /*if (control.revealed[pointerX][pointerY])
            return;
        control.flagged[pointerX][pointerY] = !control.flagged[pointerX][pointerY];*/
        control.flag(pointerX,pointerY);
    }
    public void select(){
        if(menuPointerY == 0 || menuPointerY == 1 || menuPointerY == 2) {
            control = new Control(menuPointerY);
            menu = false;
            board = true;
        }
        else{
            menu = false;
            board = false;
            option = true;
        }
    }
    public void moveMenuPointer(String dir){
        if(!menu) return;
        switch (dir) {
            case "U":
                if(menuPointerY > 0)
                    menuPointerY -= 1;
                break;
            case "D":
                if(menuPointerY < 3)
                    menuPointerY += 1;
                break;
        }
    }
    public void movePointer(String dir) {
        if(!board) return;
        switch (dir) {
            case "U":
                if(pointerY > 0)
                    pointerY -= 1;
                break;
            case "D":
                if(pointerY < 19)
                    pointerY += 1;
                break;
            case "L":
                if(pointerX > 0)
                    pointerX -= 1;
                facingLeft = true;
                break;
            case "R":
                if(pointerX < 19)
                    pointerX += 1;
                facingLeft = false;
                break;
        }
    }
}
