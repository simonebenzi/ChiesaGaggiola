/*********
  Rui Santos
  Complete project details at https://randomnerdtutorials.com
*********/

// Load Wi-Fi library
#include <WiFiNINA.h>

// the IP address for the shield:
IPAddress ip(192, 168, 0, 177);

// Wifi network credentials
const char* ssid     = "TIM-18143925";
const char* pass = "CN2ltmwLXDvmVfWtC1ogJU95";

int status = WL_IDLE_STATUS;

// Set web server port number to 80
WiFiServer server(80);

// Variable to store the HTTP request
String header;

// Auxiliar variables to store the current output state
String pin5State = "off";
String pin6State = "off";

// Assign output variables to GPIO pins
const int ch4 = 5;
const int ch5 = 6;
const int ch9 = 7;
const int ch12a = 8;

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  // Initialize the output variables as outputs
  pinMode(ch4, OUTPUT);
  pinMode(ch5, OUTPUT);
  pinMode(ch9, OUTPUT);
  pinMode(ch12a, OUTPUT);
  // Set outputs to LOW
  digitalWrite(ch4, LOW);
  digitalWrite(ch5, LOW);
  digitalWrite(ch9, LOW);
  digitalWrite(ch12a, LOW);

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
              pin5State = "on";
              digitalWrite(ch4, HIGH);
            } else if (header.indexOf("GET /ch4/off") >= 0) {
              Serial.println("CH.4 is off");
              pin5State = "off";
              digitalWrite(ch4, LOW);
            } else if (header.indexOf("GET /ch5/on") >= 0) {
              Serial.println("CH.5 is on");
              pin6State = "on";
              digitalWrite(ch5, HIGH);
            } else if (header.indexOf("GET /ch5/off") >= 0) {
              Serial.println("CH.5 is off");
              pin6State = "off";
              digitalWrite(ch5, LOW);
            } else if (header.indexOf("GET /ch9/on") >= 0) {
              Serial.println("CH.9 is on");
              pin6State = "on";
              digitalWrite(ch9, HIGH);
            } else if (header.indexOf("GET /ch9/off") >= 0) {
              Serial.println("CH.9 is off");
              pin6State = "off";
              digitalWrite(ch9, LOW);
            } else if (header.indexOf("GET /ch12a/on") >= 0) {
              Serial.println("CH.12a is on");
              pin6State = "on";
              digitalWrite(ch12a, HIGH);
            } else if (header.indexOf("GET /ch12a/off") >= 0) {
              Serial.println("CH.12a is off");
              pin6State = "off";
              digitalWrite(ch12a, LOW);
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
            client.println("<p>GPIO 26 - State " + pin5State + "</p>");
            // If the output26State is off, it displays the ON button
            if (pin5State == "off") {
              client.println("<p><a href=\"/26/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/26/off\"><button class=\"button button2\">OFF</button></a></p>");
            }

            // Display current state, and ON/OFF buttons for GPIO 27
            client.println("<p>GPIO 27 - State " + pin6State + "</p>");
            // If the output27State is off, it displays the ON button
            if (pin6State == "off") {
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
