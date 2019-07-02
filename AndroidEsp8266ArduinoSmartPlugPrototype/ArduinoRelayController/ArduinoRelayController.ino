#define GREEN 3
#define BLUE 5
#define RED 6
#define PIR 8
#define rec_relay 2
#define to_relay 4
#define to_pir 10

int val = 0;
int pirState = LOW;
int redV = 200;
int greenV = 0;
int blueV = 200;


void setup() {
  Serial.begin(9600);

  pinMode(PIR, INPUT);

  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  pinMode(RED, OUTPUT);

  pinMode(rec_relay, INPUT);
  pinMode(to_relay, OUTPUT);
  pinMode(to_pir, OUTPUT);
}

void checkRelay() {
  if (digitalRead(rec_relay) == LOW) {
    digitalWrite(to_relay, LOW);
  }

  if (digitalRead(rec_relay) == HIGH) {
    digitalWrite(to_relay, HIGH);
  }
}

void checkPir() {
  val = digitalRead(PIR);

  if (val == HIGH) {
    digitalWrite(to_pir, HIGH);
    redV = 255;
    greenV = 0;
    blueV = 0;
    if (pirState == LOW) {
      Serial.println("Motion detected!");
      pirState = HIGH;
    }
  } else {
    digitalWrite(to_pir, LOW);
    redV = 0;
    greenV = 0;
    blueV = 255;
    if (pirState == HIGH) {
      Serial.println("Motion ended!");
      pirState = LOW;
    }
  }
}

void loop() {
  checkRelay();
  checkPir();

  analogWrite(RED, redV);
  analogWrite(BLUE, greenV);
  analogWrite(GREEN, blueV);

}
