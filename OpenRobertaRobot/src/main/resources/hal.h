//Constants

#ifndef E
#define E 2.718281828459045
#endif

#ifndef GOLDEN_RATIO
#define GOLDEN_RATIO 1.61803398875
#endif


#ifndef SQRT2
#define SQRT2 1.41421356237
#endif

#ifndef SQRT1_2
#define SQRT1_2 0.707106781187
#endif

#ifndef INFINITY
#define INFINITY 0x7f800000
#endif

//sensor functions

float NumberOfRotations(int port){
  int fullCircle = 360;
  return MotorTachoCount(port)/fullCircle;
}

float MotorDistance(int port, float diameter){
  float rad = diameter/2;
  int degreePi = 180;
  return (PI*MotorTachoCount(port)/degreePi)*rad;
}

sub SetTimerValue(long& timer1){
  timer1 = CurrentTick();
}

inline long GetTimerValue(long timer1){
  return CurrentTick() - timer1;
}

sub ResetTimerValue(long& timer1){
  timer1 = CurrentTick();
}

//Bluetooth functions and constants

#ifndef BT_SLAVE_CONN
#define BT_SLAVE_CONN 0
#endif


inline bool BTCheck(int conn){
  if (!BluetoothStatus(conn)==NO_ERR){
    TextOut(5,LCD_LINE2,"Error");
    Wait(1000);
    StopAllTasks();
    return false;
  }
  else{
    return true;
  }
}

inline float BluetoothGetNumber(int inbox){
  int in;
  ReceiveRemoteNumber(inbox,true,in);
  return in;
  //TextOut(0,LCD_LINE3,"Receiving");
  //TextOut(0,LCD_LINE4,"  ");
  //NumOut(5,LCD_LINE4,in);
  //return in;
}

//sub bluetooth_send_number(int out, int connection, int inbox, int outbox){
  //TextOut(0,LCD_LINE1,"Sending");
  //TextOut(0,LCD_LINE2,"  ");
  //NumOut(5,LCD_LINE2,out);
  //SendRemoteNumber(connection,outbox,out);
//}

inline string BluetoothGetString(int inbox){
    string in;
    ReceiveRemoteString(inbox,true,in);
    //TextOut(0,LCD_LINE3,"Receiving");
    //TextOut(0,LCD_LINE4,"  ");
    //TextOut(5,LCD_LINE4,in);
    return in;
}

//sub bluetooth_send_string(string out, int connection, int outbox){
  //TextOut(0,LCD_LINE1,"Sending");
  //TextOut(0,LCD_LINE2,"  ");
  //TextOut(5,LCD_LINE2,out);
  //SendRemoteString(connection,outbox,out);
//}

inline bool BluetoothGetBoolean(int inbox){
  bool in;
  ReceiveRemoteBool(inbox,true,in);
  return in;
  //TextOut(0,LCD_LINE3,"Receiving");
  //TextOut(0,LCD_LINE4,"  ");
  //if (in == true){
  //  TextOut(5,LCD_LINE2,"true");
  //}
  //else{
  //  TextOut(5,LCD_LINE2,"false");
  //}
}

//sub bluetooth_send_boolean(bool out, int connection, int inbox, int outbox){
  //TextOut(0,LCD_LINE1,"Sending");
  //TextOut(0,LCD_LINE2,"  ");
  //if (out == true){
  //  TextOut(5,LCD_LINE2,"true");
  //}
  //else{
  //  TextOut(5,LCD_LINE2,"false");
  //}
  //SendRemoteBool(connection,outbox,out);
//}

//sensors' functions
sub IsPressedAndReleased(int button){
  SetButtonReleaseCount(button, 0);
  NumOut (0,LCD_LINE6, ButtonReleaseCount(button));
  while((ButtonReleaseCount(button) == 0)||(ButtonPressCount(button) == 0)){
    TextOut(0,LCD_LINE1, "zero");
  }
  //SetButtonState(BTN1, BTNSTATE_PRESSED_EV);
  //SetButtonState(BTNLEFT, 0x10);
}

//math Functions

inline int MathFloor(float val) {
  int temp = val;
  return temp;
}
inline int MathRound(float val){
  return MathFloor(0.5 + val);
}
inline int MathRoundUp(float val){
  return (1 + MathFloor(val));
}
inline bool MathIsWhole(float val){
  int intPart = val;
  return ((val - intPart) == 0);
}
inline float MathPow(float firstValue, float secondValue) {
  float result = 1;
  for (int i = 0; i < secondValue; i++) {
    result = result * firstValue;
  }
  return result;
}
inline float MathMin(float firstValue, float secondValue) {
  if (firstValue < secondValue){
    return firstValue;
  }
  else{
    return secondValue;
  }
}
inline float MathMax(float firstValue, float secondValue) {
  if (firstValue > secondValue){
    return firstValue;
  }
  else{
    return secondValue;
  }
}
inline bool MathPrime(float number){
    if ((number % 2 == 0) || (number == 1)) return false;
    //if not, then just check the odds
    for(int i = 3; i * i <= number; i += 2) {
        if(number % i == 0)
            return false;
    }
    return true;
}
inline float MathLn(float val) {
  if (val > 1){
    float values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 10000};
    float results[] = {0, 0.693147, 1.098612, 1.386294, 1.609438, 1.791759, 1.945910, 2.079442, 2.197225, 2.302585, 2.995732, 3.401197, 3.688879, 3.912023, 4.094345, 4.248495, 4.382027, 4.499810, 4.605170, 5.298317, 5.703782, 5.991465, 6.214608, 6.396930, 6.551080, 6.684612, 6.802395, 6.907755, 9.210340};
    int i = 1;
    while(values[i] < abs(val)){
      i++;
    }
    float result =  results[i - 1] + (abs(val) - values[i - 1]) * (results[i] - results[i - 1]) / (values[i] - values[i - 1]);
    if (val <= 100000){
      return result;
    }
    else{
      return 9.22;
    }
  }
  else if ((val > 0) && (val <= 1)){
    float summ = 0;
    for (int n = 1; n < 10; n++){
      summ += MathPow(-1, (n + 1)) * MathPow((val - 1), n)/n;
    }
    return summ;
  }
  else{
    TextOut(0, LCD_LINE1, "invalid value");
    Wait(1000);
    ClearScreen();
    return NULL;
  }
}
inline float MathLog(float val) {
  return MathLn(val)/MathLn(2.71828);
}

inline float MathFactorial(float val){
  float result = val;
  if (val == 0){
    return 1;
  }
  else{
    for (int i = 1; i < val; i++){
      result = result*(val - i);
    }
    return result;
  }
}

inline float MathSin(float val) {
  float angle = PI*val/180;
  float summ = 0;
  for (int n = 0; n < 10; n++){
    summ += MathPow(-1, n) * MathPow(angle, (2 * n + 1))/MathFactorial(2 * n + 1);
  }
  return summ;
}

inline float MathCos(float val) {
  float angle = PI*val/180;
  float summ = 0;
  for (float n = 0; n < 10; n++){
    summ += (MathPow(-1, n)/MathFactorial(2 * n)) * MathPow(angle, (2 * n));
  }
  return summ;
}

inline float MathTan(float val) {
  return MathSin(val)/MathCos(val);
}

inline float MathAsin(float val) {
  if (abs(val) > 1){
    TextOut(0, LCD_LINE1, "invalid value");
    Wait(1000);
    ClearScreen();
    return NULL;
  }
  else{
    float summ = 0;
    for (float n = 0; n < 15; n++){
      summ += MathFactorial(2 * n) * MathPow(val, (2 * n + 1)) / MathPow(4, n) / MathPow(MathFactorial(n), 2)/(2* n + 1);
    }
    return summ * 180 / PI;
  }
}


inline float MathAcos(float val) {
  if (abs(val) > 1){
    TextOut(0, LCD_LINE1, "invalid value");
    Wait(1000);
    ClearScreen();
    return NULL;
  }
  else{
    return 90 - MathAsin(val);
  }
}

inline float MathAtan(float val) {
  if (abs(val) > 1){
    float values[] = {1, sqrt(3), 2, 3, 0x7f800000};
    float results[] = {45, 60, 63.435, 71.565, 90};
    int i = 1;
    while(values[i] < abs(val)){
      i++;
    }
    float result =  results[i - 1] + (abs(val) - values[i - 1]) * (results[i] - results[i - 1]) / (values[i] - values[i - 1]);
    if (val > 0){
      return result;
    }
    else{
      return -result;
    }
  }
  else{
    float summ = 0;
    for (float n = 1; n < 15; n++){
      summ += MathPow(-1, (n - 1)) * MathPow(val, (2 * n - 1)) / (2 * n - 1);
    }
    return summ * 180 / PI;
  }
}

inline int RandomIntegerInRange(int val1, int val2){
  return abs(val1 - val2) * Random(100) / 100 + MathMin(val1, val2);
}

inline float RandomFloat(){
  return Random(100) / 100;
}

inline float Constrain(float val, float min, float max){
	return MathMin(MathMax(val, min), max);
}

//numerical array functions

inline float ArrSum(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum;
}
inline float ArrMin(float arr[]) {
  float min = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] < min){
      min = arr[i];
    }
  }
  return min;
}
inline float ArrMax(float arr[]) {
  float max = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] > max){
      max = arr[i];
    }
  }
  return max;
}
float ArrMean(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum/ArrayLen(arr);
}
inline void ArrInsertionSort(float &arr[]) {
  for (int i=1; i < ArrayLen(arr); i++) {
      int index = arr[i];
      int j = i;
      while (j > 0 && arr[j-1] > index){
           arr[j] = arr[j-1];
           j--;
      }
      arr[j] = index;
  }
}
inline float ArrMedian(float arr[]) {
   int n = ArrayLen(arr);
   if ( n == 0 ) {
     return 0;
   }
   ArrInsertionSort(arr);
   float median;
   if ( n % 2 == 0 ) {
      median = (arr[n/2] + arr[n / 2 - 1]) / 2;
   }
   else {
     median = arr[n / 2];
   }
   return median;
}
inline float ArrStandardDeviatioin(float arr[]) {
        int n = ArrayLen(arr);
        if ( n == 0 ) {
            return 0;
        }
        float variance = 0;
        float mean = ArrMean(arr);
        for ( int i = 0; i < ArrayLen(arr); i++) {
            variance += MathPow(arr[i] - mean, 2);
        }
        variance /= n;
        return sqrt(variance);
}
inline float ArrRand(float arr[]) {
  int arrayInd = ArrayLen(arr) * Random(100) / 100;
  return arr[arrayInd - 1];
}
inline float ArrMode(float arr[]){
  ArrInsertionSort(arr);
  float element = arr[0];
  float max_seen = element;
  int count = 1;
  int mode_count = 1;
  for (int i = 1; i < ArrayLen(arr); i++){
      if (arr[i] == element){
         count++;
         if (count > mode_count)
            {
            mode_count = count;
            max_seen = element;
        }
      }
      else {
        element = arr[i];
        count = 1;
    }
  }
  return max_seen;
}

// functions for unknown type arrays

inline int ArrFindFirstNum(float arr[], float item) {
  int i = 0;
  if (arr[0] == item){
    return i;
  }
  else{
    do{
      i++;
    } while((arr[i] != item) && (i != ArrayLen(arr)));
    return i;
  }
}
inline int ArrFindLastNum(float arr[], float item) {
  int i = 0;
  if (arr[ArrayLen(arr) - 1] == item){
    return ArrayLen(arr) - 1 - i;
  }
  else{
    do{
      i++;
    } while((arr[ArrayLen(arr) - 1 - i] != item)&&(i != 0));
      return ArrayLen(arr) - 1 - i;
  }
}

inline int ArrFindFirstStr(string arr[], string item) {
  int i = 0;
  if (arr[0] == item){
    return i;
  }
  else{
    do{
      i++;
    } while((arr[i] != item) && (i != ArrayLen(arr)));
    return i;
  }
}
inline int ArrFindLastStr(string arr[], string item) {
  int i = 0;
  if (arr[ArrayLen(arr) - 1] == item){
    return ArrayLen(arr) - 1 - i;
  }
  else{
    do{
      i++;
    } while((arr[ArrayLen(arr) - 1 - i] != item)&&(i != 0));
      return ArrayLen(arr) - 1 - i;
  }
}

inline int ArrFindFirstBool(bool arr[], bool item) {
  int i = 0;
  if (arr[0] == item){
    return i;
  }
  else{
    do{
      i++;
    } while((arr[i] != item) && (i != ArrayLen(arr)));
    return i;
  }
}
inline int ArrFindLastBool(bool arr[], bool item) {
  int i = 0;
  if (arr[ArrayLen(arr) - 1] == item){
    return ArrayLen(arr) - 1 - i;
  }
  else{
    do{
      i++;
    } while((arr[ArrayLen(arr) - 1 - i] != item)&&(i != 0));
      return ArrayLen(arr) - 1 - i;
  }
}

//drive functions
inline float OnReg(int ports, float speed,int regmode) {
  if (speed > 0){
    OnRevReg(OUT_B, speed,regmode);
  }
  else{
    OnFwdReg(OUT_B, speed,regmode) ;
  }
}
sub TurnLeft(float s, float t){
   OnFwd(OUT_A, s);
   OnRev(OUT_B, s);
   Wait(t);
 }
 sub TurnRight(float s, float t){
   OnFwd(OUT_A, s);
   OnRev(OUT_B, s);
   Wait(t);
 }

