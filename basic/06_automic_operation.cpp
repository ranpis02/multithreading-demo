#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <chrono>
#include <atomic>

using namespace std;

mutex mtx;
condition_variable cv;

/*
Another initialization for  automic variable:
```
atomic<int> counter;
counter.store(0);
```
*/

atomic<int> counter(0);

void incrementCounter()
{
  for (int i = 0; i < 100000; ++i)
  {
    counter++;
  }
}

int main()
{
  thread t1(incrementCounter);
  thread t2(incrementCounter);

  t1.join();
  t2.join();

  // Some methods about atomic varibale

  // 1. store(): store a value to the varibale
  counter.store(100);
  // 2. load(): load the value from the variable
  counter.load();
  // 3. add() / sub(): add or sub a value to the variable
  counter.fetch_add(10);
  counter.fetch_sub(5);

  // 4. Compare and Swap
  int expected = 0;
  counter.compare_exchange_strong(expected, 200); // If counter == expected, then counter = 200
  cout << "Final counter value: " << counter.load() << endl;

  return 0;
}