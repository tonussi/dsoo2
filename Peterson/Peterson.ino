#include <Thread.h>
#include <LiquidCrystal.h>

#define FALSE 0
#define TRUE 1
#define N 2
int volatile turn;
int interested[N];

const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;

LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

const int sensorPin = A0;
const int ledPin = 7;
volatile int sensorValue = 0;
volatile bool activeLed = true;

Thread p0 = Thread();
Thread p1 = Thread();

int volatile runtime = 0;

void enterRegion(int process) {
  int other = 1 - process;
  interested[process] = TRUE;
  turn = process;
  while (turn == process && interested[other] == TRUE);
}

void leaveRegion(int process) {
  interested[process] = FALSE;
}

void * work0() {
  lcd.begin(16, 2);

  if (activeLed) {
    lcd.print("LED (Active)");
  } else {
    lcd.print("LED (NotActive)");
  }

  lcd.setCursor(0, 1);

  enterRegion(0);
  runtime = millis() / 1000;

  if (activeLed)
    digitalWrite(ledPin, HIGH);
  else
    digitalWrite(ledPin, LOW);

  leaveRegion(0);

  lcd.print(runtime);
  lcd.print(" sec");
}

void * work1() {
  lcd.begin(16, 2);
  lcd.print("LDR (Scanning)");
  lcd.setCursor(0, 1);

  enterRegion(1);
  runtime = millis() / 1000;

  sensorValue = analogRead(sensorPin);
  if (sensorValue < 100)
    activeLed = false;
  else
    activeLed = true;

  leaveRegion(1);

  lcd.print(runtime);
  lcd.print(" sec");
}

void setup() {
  Serial.begin(9600);
  randomSeed(analogRead(0));

  p0.onRun(work0);
  p0.setInterval(random(1000, 2000));

  p1.onRun(work1);
  p1.setInterval(random(1000, 2000));

  pinMode(7, OUTPUT);
}

void loop() {
  if (p0.shouldRun())
    p0.run();
  else if (p1.shouldRun())
    p1.run();
}
