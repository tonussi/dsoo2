#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

#ifndef STASSID
#define STASSID "ESP8266HACKING"
#define STAPSK  "clarification"
#endif

const char* ssid = STASSID;
const char* password = STAPSK;

ESP8266WebServer server(80);

void handleRoot() {
  server.send(200, "text/plain", "Ola vindo do ESP8266 Server - Esse Server foi feito para comunicar com um Android. (smart home)");
}

void handleNotFound() {
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i = 0; i < server.args(); i++) {
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
}

void setup(void) {
  pinMode(D2, OUTPUT);
  digitalWrite(D2, HIGH);

  pinMode(D3, INPUT);

  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.println("");

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (MDNS.begin("esp8266")) {
    Serial.println("MDNS responder started");
  }

  server.on("/", handleRoot);

  server.on("/up", []() {
    server.send(200, "application/json", "{\"up\":\"esp8266\"}");
  });

  server.on("/relay/on", []() {
    digitalWrite(D2, HIGH);
    server.send(200, "application/json", "{\"relayd2\":true}");
  });

  server.on("/relay/off", []() {
    digitalWrite(D2, LOW);
    server.send(200, "application/json", "{\"relayd2\":false}");
  });

  server.on("/pir", []() {
    int r = digitalRead(D3);
    if (r == 1) {
//        Serial.println(r, HEX);
        server.send(200, "application/json", "{\"pir\":\"moviment_detected\"}");
    } else if (r == 0) {
//        Serial.println(r, HEX);
        server.send(200, "application/json", "{\"pir\":\"moviment_ended\"}");
    }
  });

  server.onNotFound(handleNotFound);

  server.begin();
  Serial.println("HTTP server started");
}

void loop(void) {
  server.handleClient();
  MDNS.update();
}
