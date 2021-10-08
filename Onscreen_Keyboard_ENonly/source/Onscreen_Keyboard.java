import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.Robot; 
import java.awt.AWTException; 
import java.awt.GraphicsDevice.*; 
import java.awt.*; 
import java.awt.geom.*; 
import javax.swing.*; 
import com.sun.jna.*; 
import com.sun.jna.Native; 
import com.sun.jna.platform.win32.User32; 
import com.sun.jna.win32.StdCallLibrary; 

import com.sun.jna.ptr.*; 
import com.sun.jna.platform.*; 
import com.sun.jna.platform.dnd.*; 
import com.sun.jna.*; 
import com.sun.jna.platform.win32.COM.tlb.imp.*; 
import com.sun.jna.platform.win32.COM.tlb.*; 
import com.sun.jna.platform.win32.COM.*; 
import com.sun.jna.platform.mac.*; 
import com.sun.jna.win32.*; 
import com.sun.jna.platform.win32.*; 
import com.sun.jna.platform.wince.*; 
import com.sun.jna.platform.unix.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Onscreen_Keyboard extends PApplet {











int switched=0; //switch counter;
int r1=107; //radius of the innermost circle
int r2=168; //radius of the middle circle
int r3=223; //radius of the outermost circle
int xOffSet=250; //the origin of circles are positioned somewhere around(250,250)
int yOffSet=250;
PImage aimg; //background for english
PImage bimg; //background for numbers
PImage cimg;
boolean number=false; //see if numbers mode is activated
boolean hangul=false;
boolean capitalize=false; //caps lock basically
boolean shifted; //use capital letter or !@#$ characters just once
User32jna u32 = User32jna.INSTANCE;
Robot rb;
int[] alphabet={ENTER,32,8,0,SHIFT,'D','L','C','Y','P','W','G','K','V','Z','Q','X','J','F','B','M','O','E','A','U','H','R','S','N','T','I'};
int[] numbers={ENTER,32,8,0,SHIFT,92,61,192,44,46,91,258,59,222,45,CONTROL,ALT,36,35,33,34,'1','2','3','4','5','6','7','8','9','0'};
int[] hanguls={ENTER,32,8,0,SHIFT,'R','Z','S','F','E','X',47,46,'C','W','T','Q','D','A','G','V','K','J','N','B','K','H','M','L','O','P'};
public interface User32jna extends User32 {
 User32jna INSTANCE = (User32jna) Native.loadLibrary("user32.dll",
   User32jna.class);
 public void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
}
public double getR(int x, int y)
{
  int newX, newY;
  newX=x-xOffSet;
  newY=yOffSet-y;
  return  sqrt(newX*newX+newY*newY);
}//the center of circle is around 250,250
//so in order to use polar coordinate we need to
//give 250px offset to x and y
public int getIndex(int x, int y)
{
  double r =  getR(x,y);
  double theta = atan2(yOffSet-y,x-xOffSet);
  if (r<=r1)
  {
    if(theta>atan2(-84,64)&&theta<=atan2(34,102)) return 1;
    else if(theta>atan2(34,102)&&theta<=atan2(107,0)) return 0;
    else if(theta>atan2(107,0)&&theta<=atan2(33,-104)) return 4;
    else if(theta>atan2(33,-104)&&theta<=atan2(0,-108)||theta>-atan2(0,-108)&&theta<=atan2(250-335,188-250)) return 3;
    else if(theta>atan2(-85,-62)&&theta<=atan2(-84,64)) return 2;
  }
  else if(r<=r2)
  {
    if (theta>atan2(92,137)&&theta<=atan2(155,55)) return 21;
    else if(theta>atan2(-4,167)&&theta<=atan2(92,137)) return 22;
    else if(theta>atan2(-102,131)&&theta<=atan2(-4,167)) return 23;
    else if(theta>atan2(-156,46)&&theta<=atan2(-102,131)) return 24;
    else if(theta>atan2(-153,-57)&&theta<=atan2(-156,46)) return 25;
    else if(theta>atan2(-94,-140)&&theta<=atan2(-153,-57)) return 26;
    else if(theta>(-atan2(0,-170))&&theta<=atan2(-94,-140)||theta>atan2(6,-171)&&theta<=atan2(0,-170)) return 27;
    else if(theta>atan2(103,-133)&&theta<=atan2(6,-171)) return 28;
    else if(theta>atan2(159,-47)&&theta<=atan2(103,-133)) return 29;
    else if(theta>atan2(155,48)&&theta<=atan2(177,-56)) return 30;
  }
  else if(r<=r3)
  {
    if (theta>atan2(156,158)&&theta<=atan2(202,85)) return 5;
    else if(theta>atan2(82,207)&&theta<=atan2(156,158)) return 6;
    else if(theta>0&&theta<=atan2(82,207)) return 7;
    else if(theta>atan2(-88,203)&&theta<=0) return 8;
    else if(theta>atan2(-153,153)&&theta<=atan2(-88,203)) return 9;
    else if(theta>atan2(-203,81)&&theta<=atan2(-153,153)) return 10;
    else if(theta>atan2(-207,0)&&theta<=atan2(-203,81)) return 11;
    else if(theta>atan2(-203,-79)&&theta<=atan2(-207,0)) return 12;
    else if(theta>atan2(-162,-154)&&theta<=atan2(-203,-79)) return 13;
    else if(theta>atan2(-89,-208)&&theta<=atan2(-162,-154)) return 14;
    else if(theta>atan2(-2,-230)&&theta<=atan2(-89,-209)) return 15;
    else if((theta>(-atan2(0,-229))&&theta<=atan2(-2,-230))||(theta>atan2(85,-214)&&theta<=atan2(0,-229))) return 16;
    else if(theta>atan2(158,-160)&&theta<=atan2(85,-211)) return 17;
    else if(theta>atan2(206,-86)&&theta<=atan2(158,-160)) return 18;
    else if(theta>atan2(218,0)&&theta<=atan2(206,-86)) return 19;
    else if(theta>atan2(202,85)&&theta<=atan2(218,0)) return 20;
  }
  return 31;
} //get the proper area index according to click position
public void setup()
{
  size(500,520);
  frame.setAlwaysOnTop(true);
  try{
  rb=new Robot();
  }
  catch(AWTException ex)
  {
    ex.printStackTrace();
  }
  frame.removeNotify();
  frame.setAlwaysOnTop(true);
   frame.setFocusableWindowState(false);
  frame.setFocusable(false);
  frame.enableInputMethods(false);
  aimg=loadImage("bg_eng.png");
  bimg=loadImage("bg_num.png");
  cimg=loadImage("bg_kor.png");
}
public void draw()
{
  if (!number&&!hangul) image(aimg, 0,0,500,500);
  else if(number) image(bimg,0,0,500,500);
  else if(hangul) image(cimg,0,0,500,500);
}
public void mousePressed()
{
  ellipse(mouseX,mouseY,20,20);
  int index=getIndex(mouseX, mouseY);
  if (index==31){}
  else if (index==3) switchKeys();
  else if (index==4)
  {
    if (mouseEvent.getClickCount()==2)
    {
      if (!capitalize)
      {
       capitalize=true;
      }
      else 
      {
        capitalize=false;
      }
    }
    else if (!shifted) shifted=true;
    else shifted=false;
  }
  else if (!shifted&&!number&&!hangul) 
  {
    rb.keyPress(alphabet[index]);
    rb.keyRelease(alphabet[index]);
  } 
  else if (shifted&&!number&&!hangul)
  {
    rb.keyPress(SHIFT);
    rb.keyPress(alphabet[index]);
    rb.keyRelease(SHIFT);
    rb.keyRelease(alphabet[index]);
    if (!capitalize) shifted=false;
  }
    else if (!shifted&&number) 
  {
    rb.keyPress(numbers[index]);
    rb.keyRelease(numbers[index]);
  } 
  else if (shifted&&number)
  {
    rb.keyPress(SHIFT);
    rb.keyPress(numbers[index]);
    rb.keyRelease(SHIFT);
    rb.keyRelease(numbers[index]);
    if (!capitalize) shifted=false;
  }
  else if(!shifted&&hangul)
  {
    if (index==11)
    {
      rb.keyPress(SHIFT);
      rb.keyPress(47);
      rb.keyRelease(47);
      rb.keyRelease(SHIFT);
    }
    else
    {
      rb.keyPress(hanguls[index]);
      rb.keyRelease(hanguls[index]);
    }
  }
  else if(shifted&&hangul)
  {
    if (index==11)
    {
      rb.keyPress(47);
      rb.keyRelease(47);
    }
    else if (index==12)
    {
      rb.keyPress(44);
      rb.keyRelease(44);
    }
    else if(index==22)
    {
      rb.keyPress('U');
      rb.keyRelease('U');
    }
    else if(index==26)
    {
      rb.keyPress('Y');
      rb.keyRelease('Y');
    }
    else if(index==11)
    {
      rb.keyPress(47);
      rb.keyRelease(47);
    }
    else{
      rb.keyPress(SHIFT);
      rb.keyPress(hanguls[index]);
      rb.keyRelease(hanguls[index]);
      rb.keyRelease(SHIFT);
    }
    if(!capitalize) shifted=false;
  }
}
public void switchKeys() //function to switch between alpha, number, and hangul
{
  switched++;
  if(switched%2==0)
  {
    if(hangul) u32.keybd_event((byte) 0x15,(byte)0xF2,0,0); //\ud55c\uae00\ubaa8\ub4dc\uc5d0\uc11c\ub294 \ub2e4\uc2dc \ud55c\uae00\ud0a4\ub97c \ub20c\ub7ec\uc8fc\uc5b4 \uc601\uc5b4\ub97c \uc4f8 \uc218 \uc788\uac8c \ud55c\ub2e4.
    image(aimg,0,0,500,500);
    number=false;
    hangul=false;
  }
  else if(switched%2==1) 
  {
    image(bimg,0,0,500,500);
    number=true;
    hangul=false;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Onscreen_Keyboard" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
