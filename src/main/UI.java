package main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.BasicStroke;



public class UI {
    
    GamePanel gp;
    Font arial_40;
    Font lucida_80;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    double frameTime = (double)1/60;
    double timeLeft = 120.00;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("0.00");
    Graphics2D g2;
    public String currentDialog;
    public boolean timerOn = false;
    public boolean winGame = false;

    //constructor:
    public UI (GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        lucida_80 = new Font("Lucida Console", Font.BOLD,80);
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);


         //PLAY STATE
        if(gp.gameState == gp.playState){
            playTime += frameTime;
            if(timerOn){
                drawTimer_60();
            }

            if(timeLeft < 0 || (playTime > 120 && winGame == false)){
                gp.gameState = gp.endGameState;

            }
            
            //MESSAGE:
            if(messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        //DIALOG STATE
        if(gp.gameState == gp.dialogState){
            drawDialogScreen();
        }
        //END-GAME STATE
        if(gp.gameState == gp.endGameState){
            drawEndGameScreen();
        }
        //MENU STATE
        if(gp.gameState == gp.menuState){
            drawMenuScreen();
        }
        

    }
    public void drawMenuScreen() {
        String gameTitle = "Entangled";
        String line1 = "Press A to PLAY";
        String line2 = "Press D to EXIT";

        int y = gp.screenHeight/2;

        g2.setFont(lucida_80);
        g2.drawString(gameTitle, getMiddle(gameTitle), (y/4)+gp.tileSize);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60));
        g2.drawString(line1, 50, y += gp.tileSize*2);
        g2.drawString(line2, 50, y += gp.tileSize*2);

        


    }

    public void drawEndGameScreen() {
        String text;
        if(winGame == true){
            text = "You won!";
        }
        else {
            text = "You lose!";
        }
        
        String ins = "Press ENTER to Menu!";

        int y = gp.screenHeight/2;

        g2.setFont(lucida_80);
        g2.drawString(text, getMiddle(text), y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));
        g2.drawString(ins, getMiddle(ins), y*3/2);

    }
    public int getMiddle(String text) {
        int x;

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - length/2;
        return x;
    }



    public void drawPauseScreen(){
        String text = "PAUSED";
        int x;

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - length/2;

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawTimer_60(){
        //TIME:

        if(timeLeft > 0)    {
            playTime = timeLeft;
            timeLeft -= frameTime;
            g2.drawString("Time left: "+ dFormat.format(timeLeft),50,50);
        }
        
    }

    public void  drawDialogScreen() {
        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize *4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialog.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }
        
    }

public void drawSubWindow(int x, int y, int width, int height){

    Color c = new Color(0,0,0,200);
    g2.setColor(c);
    g2.fillRoundRect(x, y, width, height, 35, 35);

    c = new Color(255,255,255,200);
    g2.setColor(c);
    g2.setStroke(new BasicStroke(5));
    g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
}
    
}
