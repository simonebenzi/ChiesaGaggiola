/* Controllo Relè App Chiesa Gaggiola (La Spezia) */

// Load Wi-Fi library
#include <WiFiNINA.h>

// the IP address for the shield:
IPAddress ip(192, 168, 1, 124);

// Wifi network credentials
// const char* ssid     = "FASTWEB-7N6SPT"; //OR+
//const char* pass = "12RD23JE7I"; //OR+
//const char* ssid = "TIM-18143925"; // CAPIRE PERCHE NON VA!!
//const char* pass = "CN2ltmwLXDvmVfWtC1ogJU95";
const char* ssid = "Mi 10T"; // CELL
const char* pass = "cellrouter";
int status = WL_IDLE_STATUS;

// Logic changed in logic-low
const int low = LOW;
const int high = HIGH;

// Set web server port number to 80
WiFiServer server(80);

// Variable to store the HTTP request
String header;

// Auxiliar variables to store the current output state
String ch4State = "off";
String ch5State = "off";
String ch10State = "off";
String ch6aState = "off";
String ch6bState = "off";
String ch11State = "off";
String ch3State = "off";
String ch12bState = "off";
String ch7State = "off";
String ch9State = "off";

// Assign output variables to IO pins
const int ch3 = 2;
const int ch7 = 5;
const int ch4 = 11;
const int ch5 = 6;
const int ch9 = 7;
const int ch10 = 12;
const int ch6a = 8;
const int ch6b = 3;
const int ch11 = 9;
const int ch12b = 4;

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  // Initialize the output variables as outputs
  pinMode(ch7, OUTPUT);
  pinMode(ch5, OUTPUT);
  pinMode(ch9, OUTPUT);
  pinMode(ch11, OUTPUT);
  pinMode(ch6a, OUTPUT);
  pinMode(ch6b, OUTPUT);
  pinMode(ch3, OUTPUT);
  pinMode(ch12b, OUTPUT);
  // Set outputs to LOW
  digitalWrite(ch7, low);
  digitalWrite(ch5, low);
  digitalWrite(ch9, low);
  digitalWrite(ch11, low);
  digitalWrite(ch6a, low);
  digitalWrite(ch6b, low);
  digitalWrite(ch3, low);
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
              ch4State = "on";
              digitalWrite(ch4, high);
            } else if (header.indexOf("GET /ch4/off") >= 0) {
              Serial.println("CH.4 is off");
              ch4State = "off";
              digitalWrite(ch4, low);
            } else if (header.indexOf("GET /ch5/on") >= 0) {
              Serial.println("CH.5 is on");
              ch5State = "on";
              digitalWrite(ch5, high);
            } else if (header.indexOf("GET /ch5/off") >= 0) {
              Serial.println("CH.5 is off");
              ch5State = "off";
              digitalWrite(ch5, low);
            } else if (header.indexOf("GET /ch10/on") >= 0) {
              Serial.println("CH.10 is on");
              ch10State = "on";
              digitalWrite(ch10, high);
            } else if (header.indexOf("GET /ch10/off") >= 0) {
              Serial.println("CH.10 is off");
              ch10State = "off";
              digitalWrite(ch10, low);
            } else if (header.indexOf("GET /ch6a/on") >= 0) {
              Serial.println("CH.6a is on");
              ch6aState = "on";
              digitalWrite(ch6a, high);
            } else if (header.indexOf("GET /ch6a/off") >= 0) {
              Serial.println("CH.6a is off");
              ch6aState = "off";
              digitalWrite(ch6a, low);
            } else if (header.indexOf("GET /ch6b/on") >= 0) {
              Serial.println("CH.6b is on");
              ch6bState = "on";
              digitalWrite(ch6b, high);
            } else if (header.indexOf("GET /ch6b/off") >= 0) {
              Serial.println("CH.6b is off");
              ch6bState = "off";
              digitalWrite(ch6b, low);
            } else if (header.indexOf("GET /ch11/on") >= 0) {
              Serial.println("CH.11 is on");
              ch11State = "on";
              digitalWrite(ch11, high);
            } else if (header.indexOf("GET /ch11/off") >= 0) {
              Serial.println("CH.11 is off");
              ch11State = "off";
              digitalWrite(ch11, low);
            } else if (header.indexOf("GET /ch3/on") >= 0) {
              Serial.println("CH.3 is on");
              ch3State = "on";
              digitalWrite(ch3, high);
            } else if (header.indexOf("GET /ch3/off") >= 0) {
              Serial.println("CH.3 is off");
              ch3State = "off";
              digitalWrite(ch3, low);
            } else if (header.indexOf("GET /ch12b/on") >= 0) {
              Serial.println("CH.12b is on");
              ch12bState = "on";
              digitalWrite(ch12b, high);
            } else if (header.indexOf("GET /ch12b/off") >= 0) {
              Serial.println("CH.12b is off");
              ch12bState = "off";
              digitalWrite(ch12b, low);
            } else if (header.indexOf("GET /ch7/on") >= 0) {
              Serial.println("CH.7 is on");
              ch7State = "on";
              digitalWrite(ch7, high);
            } else if (header.indexOf("GET /ch7/off") >= 0) {
              Serial.println("CH.7 is off");
              ch7State = "off";
              digitalWrite(ch7, low);
            } else if (header.indexOf("GET /ch9/on") >= 0) {
              Serial.println("CH.9 is on");
              ch7State = "on";
              digitalWrite(ch9, high);
            } else if (header.indexOf("GET /ch9/off") >= 0) {
              Serial.println("CH.9 is off");
              ch7State = "off";
              digitalWrite(ch9, low);
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
            client.println("<p>GPIO 26 - State " + ch12bState + "</p>");
            // If the output26State is off, it displays the ON button
            if (ch12bState == "off") {
              client.println("<p><a href=\"/26/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/26/off\"><button class=\"button button2\">OFF</button></a></p>");
            }

            // Display current state, and ON/OFF buttons for GPIO 27
            client.println("<p>GPIO 27 - State " + ch12bState + "</p>");
            // If the output27State is off, it displays the ON button
            if (ch12bState == "off") {
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
