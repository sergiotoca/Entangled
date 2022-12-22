package entity;


import main.GamePanel;
import main.KeyHandler;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    
    GamePanel gp;
    public KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
    
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;
        
        //instantiating solid area:
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 5;
        worldY = gp.tileSize * 5;
        speed = 4;
        direction = "down";
    }
    //get image method:
    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/man_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/man_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/man_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/man_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/man_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/man_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/man_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/man_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //update method:
    public void update() {

        //update player position whenever the key handler objects catch any key pressed
    	
    	if( keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
	        //x increases to the right and y increases downwards
	        if(keyH.upPressed == true){
	        direction = "up";	        
	        }
	        else if(keyH.downPressed == true){
	        direction = "down";	        
	        }
	        else if(keyH.leftPressed == true){
	        direction = "left";
	        }
	        else if(keyH.rightPressed == true){
	        direction = "right";
	        }
	        
	        //implementing tile collision checker
	        collisionOn = false;
	        gp.cChecker.checkTile(this);
	        
	        //implementing object collision checker
	        int objIndex = gp.cChecker.checkObject(this, true);
	        pickUpObject(objIndex);

			//implementing event checker:
			gp.eHandler.checkEvent();
	        
	        //If collision is false, player can move
	        if(collisionOn == false) {
	        	
	        	switch(direction) {
	        	case "up":
	        		worldY -= speed;
	        		break;
	        	case "down":
	        		worldY += speed;
	    			break;
	    		case "left":
	    			worldX -= speed;
	    			break;
	    		case "right":
	    			worldX += speed;
	    			break;
	        	}
	        	
	        }
	        
	        //make the character walk animation
	        spriteCounter++;
	        if(spriteCounter > 12) {
	        	if(spriteNum == 1) {
	        		spriteNum = 2;
	        	}
	        	else if(spriteNum == 2) {
	        		spriteNum = 1;
	        	}
	        	spriteCounter = 0;
	        }
    	}
    }
    public void pickUpObject(int i) {
    	
    	if(i != 999) {
    		
    		String objectName = gp.obj[i].name;
    		
    		switch(objectName) {
    		case "Key":
    			hasKey++;
    			gp.obj[i] = null;
    			System.out.println("Key: "+ hasKey);
    			break;
    		case "Door":
    			if(hasKey > 0) {
    				gp.obj[i] = null;
    				hasKey--;
					
    			}
    			System.out.println("Key: "+ hasKey);
    			break;
    		case "Boots":
    			speed += 2;
    			gp.obj[i] = null;
    		}
    	}
    }
    //Drawing method:
    public void draw(Graphics2D g2){

    BufferedImage image = null;

    switch(direction) {
        case "up":
        	if(spriteNum == 1) {
        		image = up1;
        	}
        	if(spriteNum == 2) {
        		image = up2;
        	}
        break;
        case "down":
        	if(spriteNum == 1) {
        		image = down1;
        	}
        	if(spriteNum == 2) {
        		image = down2;
        	}
        break;
        case "left":
        	if(spriteNum == 1) {
        		image = left1;
        	}
        	if(spriteNum == 2) {
        		image = left2;
        	}
        break;
        case "right":
        	if(spriteNum == 1) {
        		image = right1;
        	}
        	if(spriteNum == 2) {
        		image = right2;
        	}
        break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}

