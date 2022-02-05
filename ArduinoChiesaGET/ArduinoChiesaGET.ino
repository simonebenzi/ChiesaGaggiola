/* Controllo Relè App Chiesa Gaggiola (La Spezia) */

// Load Wi-Fi library
#include <WiFiNINA.h>

// the IP address for the shield:
IPAddress ip(192, 168, 1, 124);

// Wifi network credentials
// const char* ssid     = "FASTWEB-7N6SPT"; //OR+
//const char* pass = "12RD23JE7I"; //OR+
//const char* ssid = "TIM-18143925"; // CASA
//const char* pass = "CN2ltmwLXDvmVfWtC1ogJU95";
//const char* ssid = "Mi 10T"; // CELL
//const char* pass = "cellrouter";
const char* ssid = "GaggiolaC-LC"; // CHIESA
const char* pass = "FraGiacomo1";
int status = WL_IDLE_STATUS;

// Logic changed in logic-low
const int low = HIGH;
const int high = LOW;

// Set web server port number to 80
WiFiServer server(80);

// Variable to store the HTTP request
String header;

// Auxiliar variables to store the current output state
bool ch1State = false;
bool ch1_1State = false;
bool ch10State = false;
bool ch9State = false;
bool ch4State = false;
bool ch5State = false;
bool ch6aState = false;
bool ch6bState = false;
bool ch7State = false;
bool ch2State = false;
bool ch15State = false;
bool ch3State = false;
bool ch21State = false;
bool ch11State = false;
bool ch12aState = false;
bool ch12bState = false;

// Assign output variables to IO pins
const int ch1 = 0;
const int ch1_1 = 1;
const int ch10 = 2;
const int ch9 = 3;
const int ch4 = 4;
const int ch5 = 5;
const int ch6a = 6;
const int ch6b = 7;
const int ch7 = 8;
const int ch2 = 9;
const int ch15 = 10;
const int ch3 = 11;
const int ch21 = 12;
const int ch11 = 13;
const int ch12a = 14;
const int ch12b = 15;
const int ch6c = 16; // Lampadari navata fronte-retro alti
const int ch30 = 17; // Faretti LED quadri navata

void setup() {
  Serial.begin(9600);
//  while (!Serial) {
//    ; // wait for serial port to connect. Needed for native USB port only
//  }
  // Initialize the output variables as outputs
  pinMode(ch1, OUTPUT);
  pinMode(ch1_1, OUTPUT);
  pinMode(ch10, OUTPUT);
  pinMode(ch9, OUTPUT);
  pinMode(ch4, OUTPUT);
  pinMode(ch5, OUTPUT);
  pinMode(ch6a, OUTPUT);
  pinMode(ch6b, OUTPUT);
  pinMode(ch7, OUTPUT);
  pinMode(ch2, OUTPUT);
  pinMode(ch15, OUTPUT);
  pinMode(ch3, OUTPUT);
  pinMode(ch21, OUTPUT);
  pinMode(ch11, OUTPUT);
  pinMode(ch12a, OUTPUT);
  pinMode(ch12b, OUTPUT);

  
  // Set outputs to LOW
  digitalWrite(ch1, low);
  digitalWrite(ch1_1, low);
  digitalWrite(ch10, low);
  digitalWrite(ch9, low);
  digitalWrite(ch4, low);
  digitalWrite(ch5, low);
  digitalWrite(ch6a, low);
  digitalWrite(ch6b, low);
  digitalWrite(ch7, low);
  digitalWrite(ch2, low);
  digitalWrite(ch15, low);
  digitalWrite(ch3, low);
  digitalWrite(ch21, low);
  digitalWrite(ch11, low);
  digitalWrite(ch12a, low);
  digitalWrite(ch12b, low);

  // check for the WiFi module:
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    // don't continue
    while (true);
  }

  String fv = WiFi.firmwareVersion();
  if (fv < "1.0.0") {
    Serial.println("Please upgrade the firmware");
  }

  // Set the Arduino's static IP address
  WiFi.config(ip);

  // attempt to connect to Wifi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
  }
  server.begin();
  // you're connected now, so print out the status:
  printWifiStatus();

}

void loop() {
  WiFiClient client = server.available();   // Listen for incoming clients

  if (client) {                             // If a new client connects,
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {            // loop while the client's connected
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();

            // turns the GPIOs on and off
            if (header.indexOf("GET /ch4/on") >= 0) {
              Serial.println("CH.4 is on");
              ch4State = true;
              digitalWrite(ch4, high);
            } else if (header.indexOf("GET /ch4/off") >= 0) {
              Serial.println("CH.4 is off");
              ch4State = false;
              digitalWrite(ch4, low);
            } else if (header.indexOf("GET /ch5/on") >= 0) {
              Serial.println("CH.5 is on");
              ch5State = true;
              digitalWrite(ch5, high);
            } else if (header.indexOf("GET /ch5/off") >= 0) {
              Serial.println("CH.5 is off");
              ch5State = false;
              digitalWrite(ch5, low);
            } else if (header.indexOf("GET /ch10/on") >= 0) {
              Serial.println("CH.10 is on");
              ch10State = true;
              digitalWrite(ch10, high);
            } else if (header.indexOf("GET /ch10/off") >= 0) {
              Serial.println("CH.10 is off");
              ch10State = false;
              digitalWrite(ch10, low);
            } else if (header.indexOf("GET /ch6a/on") >= 0) {
              Serial.println("CH.6a is on");
              ch6aState = true;
              digitalWrite(ch6a, high);
            } else if (header.indexOf("GET /ch6a/off") >= 0) {
              Serial.println("CH.6a is off");
              ch6aState = false;
              digitalWrite(ch6a, low);
            } else if (header.indexOf("GET /ch6b/on") >= 0) {
              Serial.println("CH.6b is on");
              ch6bState = true;
              digitalWrite(ch6b, high);
            } else if (header.indexOf("GET /ch6b/off") >= 0) {
              Serial.println("CH.6b is off");
              ch6bState = false;
              digitalWrite(ch6b, low);
            } else if (header.indexOf("GET /ch11/on") >= 0) {
              Serial.println("CH.11 is on");
              ch11State = true;
              digitalWrite(ch11, high);
            } else if (header.indexOf("GET /ch11/off") >= 0) {
              Serial.println("CH.11 is off");
              ch11State = false;
              digitalWrite(ch11, low);
            } else if (header.indexOf("GET /ch3/on") >= 0) {
              Serial.println("CH.3 is on");
              ch3State = true;
              digitalWrite(ch3, high);
            } else if (header.indexOf("GET /ch3/off") >= 0) {
              Serial.println("CH.3 is off");
              ch3State = false;
              digitalWrite(ch3, low);
            } else if (header.indexOf("GET /ch12b/on") >= 0) {
              Serial.println("CH.12b is on");
              ch12bState = true;
              digitalWrite(ch12b, high);
            } else if (header.indexOf("GET /ch12b/off") >= 0) {
              Serial.println("CH.12b is off");
              ch12bState = false;
              digitalWrite(ch12b, low);
            } else if (header.indexOf("GET /ch7/on") >= 0) {
              Serial.println("CH.7 is on");
              ch7State = true;
              digitalWrite(ch7, high);
            } else if (header.indexOf("GET /ch7/off") >= 0) {
              Serial.println("CH.7 is off");
              ch7State = false;
              digitalWrite(ch7, low);
            } else if (header.indexOf("GET /ch9/on") >= 0) {
              Serial.println("CH.9 is on");
              ch7State = true;
              digitalWrite(ch9, high);
            } else if (header.indexOf("GET /ch9/off") >= 0) {
              Serial.println("CH.9 is off");
              ch7State = false;
              digitalWrite(ch9, low);
            } else if (header.indexOf("GET /ch1/on") >= 0) {
              Serial.println("CH.1 is on");
              ch1State = true;
              digitalWrite(ch1, high);
            } else if (header.indexOf("GET /ch1/off") >= 0) {
              Serial.println("CH.1 is off");
              ch1State = false;
              digitalWrite(ch1, low);
            } else if (header.indexOf("GET /ch2/on") >= 0) {
              Serial.println("CH.2 is on");
              ch2State = true;
              digitalWrite(ch2, high);
            } else if (header.indexOf("GET /ch2/off") >= 0) {
              Serial.println("CH.2 is off");
              ch2State = false;
              digitalWrite(ch2, low);
            } else if (header.indexOf("GET /ch15/on") >= 0) {
              Serial.println("CH.15 is on");
              ch15State = true;
              digitalWrite(ch15, high);
            } else if (header.indexOf("GET /ch15/off") >= 0) {
              Serial.println("CH.15 is off");
              ch15State = false;
              digitalWrite(ch15, low);
            } else if (header.indexOf("GET /ch21/on") >= 0) {
              Serial.println("CH.21 is on");
              ch21State = true;
              digitalWrite(ch21, high);
            } else if (header.indexOf("GET /ch21/off") >= 0) {
              Serial.println("CH.21 is off");
              ch21State = false;
              digitalWrite(ch21, low);
            } else if (header.indexOf("GET /ch12a/on") >= 0) {
              Serial.println("CH.12a is on");
              ch12aState = true;
              digitalWrite(ch12a, high);
            } else if (header.indexOf("GET /ch12a/off") >= 0) {
              Serial.println("CH.12a is off");
              ch12aState = false;
              digitalWrite(ch12a, low);
            } else if (header.indexOf("GET /ch1_1/on") >= 0) {
              Serial.println("CH.1.1 is on");
              ch1_1State = true;
              digitalWrite(ch1_1, high);
            } else if (header.indexOf("GET /ch1_1/off") >= 0) {
              Serial.println("CH.1.1 is off");
              ch1_1State = false;
              digitalWrite(ch1_1, low);
            }
            

            // Display the HTML web page
            client.println("<!DOCTYPE html><html>");
            client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            client.println("<link rel=\"icon\" href=\"data:,\">");
            // CSS to style the on/off buttons
            // Feel free to change the background-color and font-size attributes to fit your preferences
            client.println("<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}");
            client.println(".button { background-color: #4CAF50; border: none; color: white; padding: 16px 40px;");
            client.println("text-decoration: none; font-size: 30px; margin: 2px; cursor: pointer;}");
            client.println(".button2 {background-color: #555555;}</style></head>");

            // Web Page Heading
            client.println("<body><h1>ESP32 Web Server</h1>");

            // Display current state, and ON/OFF buttons for GPIO 26
            client.println("<p>GPIO 26 - State " + String(ch12bState) + "</p>");
            // If the output26State is off, it displays the ON button
            if (ch12bState == false) {
              client.println("<p><a href=\"/26/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/26/off\"><button class=\"button button2\">OFF</button></a></p>");
            }

            // Display current state, and ON/OFF buttons for GPIO 27 
            client.println("<p>GPIO 27 - State " + String(ch12bState) + "</p>");
            // If the output27State is off, it displays the ON button
            if (ch12bState == false) {
              client.println("<p><a href=\"/27/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/27/off\"><button class=\"button button2\">OFF</button></a></p>");
            }
            client.println("</body></html>");

            // The HTTP response ends with another blank line
            client.println();
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    client.stop();
    Serial.println("Client disconnected.");
    Serial.println("");
  }
}

void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your board's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
