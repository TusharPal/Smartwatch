#include <SPI.h>
#include <Adafruit_GFX.h>
#include <Adafruit_PCD8544.h>

// pin 13 - Serial clock out (SCLK)
// pin 12 - Serial data out (DIN)
// pin 10 - Data/Command select (D/C)
// pin 9 - LCD chip select (CS)
// pin 8 - LCD reset (RST)
// pin 7 - button up
// pin 6 - button down
// pin 5 - button left
// pin 3 - button right
// pin 2 - button select

Adafruit_PCD8544 display = Adafruit_PCD8544(13, 12, 10, 9, 8);
int PIN_7=0;
int PIN_6=0;
int PIN_5=0;
int PIN_3=0;
int PIN_2=0;
String data; 
char c;

void setup()   
{
  display.begin();
  display.setRotation(2);
  display.clearDisplay();
  
  Serial.begin(9600);
  
  pinMode(7, INPUT);
  pinMode(6, INPUT);
  pinMode(5, INPUT);
  pinMode(3, INPUT);
  pinMode(2, INPUT);
}

void loop() 
{
  PIN_7=digitalRead(7);
  PIN_6=digitalRead(6);
  PIN_5=digitalRead(5);
  PIN_3=digitalRead(3);
  PIN_2=digitalRead(2);
  
  if(PIN_7==1)
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "UP");
  }
  else if(PIN_6==1)
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "DOWN");
  }
  else if(PIN_5==1)
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "LEFT");
  }
  else if(PIN_3==1)
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "RIGHT");
  }
  else if(PIN_2==1)
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "SELECT");
  }
  else
  {
    display.clearDisplay();
    setDisplayBuffer(1, 0, 0, "No button");  
  }
  
  if(Serial.available())
  {
    data="";
    while(Serial.available())
    {
      c=Serial.read();              
      data+=c; 
    }
    setDisplayBuffer(2, 0, 10, data);  
  }
  else
  {
    setDisplayBuffer(2, 0, 10, data);  
  }
  display.display();
  delay(10);
}

void setDisplayBuffer(int textSize, int x, int y, String text)
{
  display.setTextSize(textSize);
  display.setCursor(x,y);
  display.println(text);
}
  
