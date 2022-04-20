#include <WiFiNINA.h>
#include <utility/wifi_drv.h>

// String containig the sent command
String command;

// Logic changed in logic-low
const int low = LOW;
const int high = HIGH;

// Auxiliar variables to store the current output state
bool ch1State = false;
bool ch1_1State = false;
bool ch2State = false;
bool ch3State = false;
bool ch4State = false;
bool ch5State = false;
bool ch6aState = false;
bool ch6bState = false;
bool ch6cState = false;
bool ch7State = false;
bool ch9State = false;
bool ch10State = false;
bool ch11State = false;
bool ch12aState = false;
bool ch12bState = false;
bool ch15State = false;
bool ch30State = false;

// Assign output variables to IO pins
const int ch1 = 5;
const int ch1_1 = 4;
const int ch2 = 3;
const int ch3 = 2;
const int ch4 = 1;
const int ch5 = 0;
const int ch6a = 21;
const int ch6b = 20;
const int ch6c = 6;
const int ch7 = 7;
const int ch9 = 8;
const int ch10 = 9;
const int ch11 = 10;
const int ch12a = 11;
const int ch12b = 12;
const int ch15 = 13;
const int ch30 = 19;

// Variable to identify RGB channels in Arduino RGB LED
const int greenPin = 25;
const int redPin = 26;
const int bluePin = 27;

void setup() {
  Serial.begin(9600);

  // Initialize the output variables as outputs
  pinMode(ch1, OUTPUT);
  pinMode(ch1_1, OUTPUT);
  pinMode(ch2, OUTPUT);
  pinMode(ch3, OUTPUT);
  pinMode(ch4, OUTPUT);
  pinMode(ch5, OUTPUT);
  pinMode(ch6a, OUTPUT);
  pinMode(ch6b, OUTPUT);
  pinMode(ch6c, OUTPUT);
  pinMode(ch7, OUTPUT);
  pinMode(ch9, OUTPUT);
  pinMode(ch10, OUTPUT);
  pinMode(ch11, OUTPUT);
  pinMode(ch12a, OUTPUT);
  pinMode(ch12b, OUTPUT);
  pinMode(ch15, OUTPUT);
  pinMode(ch30, OUTPUT);

  // Initialize RGB pin as output
  WiFiDrv::pinMode(greenPin, OUTPUT);
  WiFiDrv::pinMode(redPin, OUTPUT);
  WiFiDrv::pinMode(bluePin, OUTPUT);

  // Set outputs to LOW
  digitalWrite(ch1, low);
  digitalWrite(ch1_1, low);
  digitalWrite(ch2, low);
  digitalWrite(ch3, low);
  digitalWrite(ch4, low);
  digitalWrite(ch5, low);
  digitalWrite(ch6a, low);
  digitalWrite(ch6b, low);
  digitalWrite(ch6c, low);
  digitalWrite(ch7, low);
  digitalWrite(ch9, low);
  digitalWrite(ch10, low);
  digitalWrite(ch11, low);
  digitalWrite(ch12a, low);
  digitalWrite(ch12b, low);
  digitalWrite(ch15, low);
  digitalWrite(ch30, low);

  // Switch on the LED as red (Serial not available)
  //  WiFiDrv::analogWrite(greenPin, 0);
  //  WiFiDrv::analogWrite(redPin, 10);
  //  WiFiDrv::analogWrite(bluePin, 0);

}

void loop() {
  // Switch on the LED as red (Serial not available)
  //  WiFiDrv::analogWrite(greenPin, 0);
  //  WiFiDrv::analogWrite(redPin, 10);
  //  WiFiDrv::analogWrite(bluePin, 0);

  while (Serial.available() > 0) {
    // Read command from serial
    command = Serial.readStringUntil('\n');
    Serial.print("Command: ");
    Serial.println(command);

    if (command.equals("ch4 ON")) {
      ch4State = true;
      digitalWrite(ch4, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch4 OFF")) {
      ch4State = false;
      digitalWrite(ch4, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch5 ON")) {
      ch5State = true;
      digitalWrite(ch5, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch5 OFF")) {
      ch5State = false;
      digitalWrite(ch5, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch10 ON")) {
      ch10State = true;
      digitalWrite(ch10, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch10 OFF")) {
      ch10State = false;
      digitalWrite(ch10, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6a ON")) {
      ch6aState = true;
      digitalWrite(ch6a, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6a OFF")) {
      ch6aState = false;
      digitalWrite(ch6a, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6b ON")) {
      ch6bState = true;
      digitalWrite(ch6b, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6b OFF")) {
      ch6bState = false;
      digitalWrite(ch6b, low);
    }
    else if (command.equals("ch11 ON")) {
      ch11State = true;
      digitalWrite(ch11, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch11 OFF")) {
      ch11State = false;
      digitalWrite(ch11, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch3 ON")) {
      ch3State = true;
      digitalWrite(ch3, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch3 OFF")) {
      ch3State = false;
      digitalWrite(ch3, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch12b ON")) {
      ch12bState = true;
      digitalWrite(ch12b, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch12b OFF")) {
      ch12bState = false;
      digitalWrite(ch12b, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch7 ON")) {
      ch7State = true;
      digitalWrite(ch7, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch7 OFF")) {
      ch7State = false;
      digitalWrite(ch7, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch9 ON")) {
      ch9State = true;
      digitalWrite(ch9, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch9 OFF")) {
      ch9State = false;
      digitalWrite(ch9, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch1 ON")) {
      ch1State = true;
      digitalWrite(ch1, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch1 OFF")) {
      ch1State = false;
      digitalWrite(ch1, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch2 ON")) {
      ch2State = true;
      digitalWrite(ch2, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch2 OFF")) {
      ch2State = false;
      digitalWrite(ch2, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch15 ON")) {
      ch15State = true;
      digitalWrite(ch15, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch15 OFF")) {
      ch15State = false;
      digitalWrite(ch15, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch12a ON")) {
      ch12aState = true;
      digitalWrite(ch12a, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch12a OFF")) {
      ch12aState = false;
      digitalWrite(ch12a, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch1_1 ON")) {
      ch1_1State = true;
      digitalWrite(ch1_1, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch1_1 OFF")) {
      ch1_1State = false;
      digitalWrite(ch1_1, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch30 ON")) {
      ch30State = true;
      digitalWrite(ch30, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch30 OFF")) {
      ch30State = false;
      digitalWrite(ch30, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6c ON")) {
      ch6cState = true;
      digitalWrite(ch6c, high);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else if (command.equals("ch6c OFF")) {
      ch6cState = false;
      digitalWrite(ch6c, low);
      // Switch on blue LED for 500 ms
      WiFiDrv::analogWrite(greenPin, 127);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 255);
      delay(500);
      WiFiDrv::analogWrite(greenPin, 0);
      WiFiDrv::analogWrite(redPin, 0);
      WiFiDrv::analogWrite(bluePin, 0);
    }
    else {
      Serial.println("Invalid command");
    }
  }
}
