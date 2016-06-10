
inline float arraySum(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum;
}


inline float arrayMin(float arr[]) {
  float min = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] < min){
      min = arr[i];
    }
  }
  return min;
}

inline float arrayMax(float arr[]) {
  float max = arr[0];
  for(int i = 1; i < ArrayLen(arr); i++) {
    if (arr[i] > max){
      max = arr[i];
    }
  }
  return max;
}
float arrayMean(float arr[]) {
  float sum = 0;
  for(int i = 0; i < ArrayLen(arr); i++) {
    sum += arr[i];
  }
  return sum/ArrayLen(arr);
}


inline void arrayInsertionSort(float &arr[]) {
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

inline float arrayMedian(float arr[]) {
   int n = ArrayLen(arr);
   if ( n == 0 ) {
     return 0;
   }
   arrayInsertionSort(arr);
   float median;
   if ( n % 2 == 0 ) {
      median = (arr[n/2] + arr[n / 2 - 1]) / 2;
   }
   else {
     median = arr[n / 2];
   }
   return median;
}

inline float mathPow(float firstValue, float secondValue) {
  float result = 1;
  for (int i = 0; i < secondValue; i++) {
    result = result * firstValue;
  }
  return result;
}

inline float arrayStandardDeviatioin(float arr[]) {
        int n = ArrayLen(arr);
        if ( n == 0 ) {
            return 0;
        }
        float variance = 0;
        float mean = arrayMean(arr);
        for ( int i = 0; i < ArrayLen(arr); i++) {
            variance += mathPow(arr[i] - mean, 2);
        }
        variance /= n;
        return sqrt(variance);
}

inline float mathMin(float firstValue, float secondValue) {
  if (firstValue < secondValue){
    return firstValue;
  }
  else{
    return secondValue;
  }
}

inline float arrayRand(float arr[]) {
  int arrayInd = ArrayLen(arr) * Random(100) / 100;
  return arr[arrayInd - 1];
}

inline float arrayMode(float arr[]){
  arrayInsertionSort(arr);
  float element = arr[0];
  float maxSeen = element;
  int count = 1;
  int modeCount = 1;

  for (int i = 1; i < ArrayLen(arr); i++){

      if (arr[i] == element){
         count++;
         if (count > modeCount)
            {
            modeCount = count;
            maxSeen = element;
        }
      }
      else {
        element = arr[i];
        count = 1;
    }
  }
  return maxSeen;
}


inline int arrayFindLast(float arr[], float item) {

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
