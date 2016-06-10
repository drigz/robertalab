//math Functions

inline int math_floor(float val) {
  int temp = val;
  return temp;
}
inline bool is_whole(float val){
  int intPart = val;
  return ((val - intPart) == 0);
}
inline float math_pow(float first_value, float second_value) {
  float result = 1;
  for (int i = 0; i < second_value; i++) {
    result = result * first_value;
  }
  return result;
}
inline float math_min(float first_value, float second_value) {
  if (first_value < second_value){
    return first_value;
  }
  else{
    return second_value;
  }
}
inline float math_max(float first_value, float second_value) {
  if (first_value > second_value){
    return first_value;
  }
  else{
    return second_value;
  }
}
inline bool math_prime(float number){
  for ( int i = 2; i <= sqrt(number); i++ ) {
    if ((number % i) == 0 ) {
      return false;
    }
    else{
      return true;
    }
  }
}
inline float math_ln(float val) {
  if (val > 0){
    float summ = 0;
    for (int n = 1; n < 1000; n++){
      summ += math_pow(-1, (n + 1)) * math_pow((val - 1), n)/n;
      //NumOut(0, LCD_LINE2, summ);
      //Wait(1000);
      //ClearScreen();
    }
    return summ;
  }
  else{
    return NULL;
  }
}
inline float math_log(float val) {
  return math_ln(val)/math_ln(2.71828);
}

inline float math_factorial(float val){
  float result = val;
  for (int i = 1; i < val; i++){
    result = result*(val - i);
  }
  return result;
}

inline float math_sin(float val) {
  float summ = 0;
  float angle = val%360;
  float results[] = {0, (-1 + sqrt(3))/2/sqrt(2), 1/2, 1/sqrt(2), sqrt(3)/2, (1 + sqrt(3))/2/sqrt(2), 1};
  float angles[] = {0, 15, 30, 45, 60, 75, 90};
  
  if (angle < 1){
    return 0;
  }
  else{
    int i = 1;
    while (angle%angles[i] == 0){
      i++;
    }
    if(angle < 90){
      return (angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else if((angle < 180)&&(angle >= 90)){
      return (90 - angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else if((angle >= 180)&&(angle < 270)){
      return -(angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else{
     return -(90 - angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
  }

}

inline float math_cos(float val) {
  float summ = 0;
  float angle = val%360;
  float results[] = {0, (1 + sqrt(3))/2/sqrt(2), sqrt(3)/2, 1/sqrt(2), 1/2, (-1 + sqrt(3))/2/sqrt(2), 0};
  float angles[] = {0, 15, 30, 45, 60, 75, 90};

  if (angle < 1){
    return 1;
  }
  else{
    int i = 1;
    while (angle%angles[i] == 0){
      i++;
    }
    if(angle < 90){
      return -(angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else if((angle < 180)&&(angle >= 90)){
      return (90 - angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else if((angle >= 180)&&(angle < 270)){
      return (angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
    else{
     return -(90 - angle%90)*(results[i] - results[i-1])/(angles[i] - angles[i-1]);
    }
  }

}

//numerical array functions

inline float array_sum(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum;
}
inline float array_min(float arr[]) {
  float min = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] < min){
      min = arr[i];
    }
  }
  return min;
}
inline float array_max(float arr[]) {
  float max = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] > max){
      max = arr[i];
    }
  }
  return max;
}
float array_mean(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum/ArrayLen(arr);
}
inline void array_insertion_sort(float &arr[]) {
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
inline float array_median(float arr[]) {
   int n = ArrayLen(arr);
   if ( n == 0 ) {
     return 0;
   }
   array_insertion_sort(arr);
   float median;
   if ( n % 2 == 0 ) {
      median = (arr[n/2] + arr[n / 2 - 1]) / 2;
   }
   else {
     median = arr[n / 2];
   }
   return median;
}
inline float array_standard_deviatioin(float arr[]) {
        int n = ArrayLen(arr);
        if ( n == 0 ) {
            return 0;
        }
        float variance = 0;
        float mean = array_mean(arr);
        for ( int i = 0; i < ArrayLen(arr); i++) {
            variance += math_pow(arr[i] - mean, 2);
        }
        variance /= n;
        return sqrt(variance);
}
inline float array_rand(float arr[]) {
  int arrayInd = ArrayLen(arr) * Random(100) / 100;
  return arr[arrayInd - 1];
}
inline float array_mode(float arr[]){
  array_insertion_sort(arr);
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

inline int array_find_first_num(float arr[], float item) {
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
inline int array_find_last_num(float arr[], float item) {
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

inline int array_find_first_str(string arr[], string item) {
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
inline int array_find_last_str(string arr[], string item) {
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

inline int array_find_first_bool(bool arr[], bool item) {
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
inline int array_find_last_bool(bool arr[], bool item) {
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
