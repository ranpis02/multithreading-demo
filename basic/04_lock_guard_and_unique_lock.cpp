#include <iostream>
#include <mutex>
#include <thread>

using namespace std;

mutex mtx;

int counter = 0;

/**
 * lock_guard is convenient and lightweight, which locks only when constructing, unlocks only when destructing.
 */
void add()
{
  for (int i = 0; i < 1000000; i++)
  {
    lock_guard<mutex> lock(mtx);
    ++counter;
  }
}

/**
 * 相较于 lock_guard ， unique_lock 更加灵活，可以实现下面的功能：
 * 1. manually lock and unlock the mutex
 * 2. defer locking
 */
void work()
{
  unique_lock<mutex> lock(mtx);

  // The operations that require to be protected.
  ++counter;

  lock.unlock(); // manually unlock the mutex

  cout << "counter updated: " << counter << endl;
}

void printLetters(const string &threadName)
{
  unique_lock<mutex> lock(mtx, defer_lock);

  // Manually lock the mutex
  lock.lock();

  for (char c = 'A'; c <= 'Z'; ++c)
  {
    cout << threadName << ": " << c << endl;
  }

  // Manually unlock the mutex
  lock.unlock();

  cout << threadName << ": Finished printing letters" << endl;
}

void printNumbers(const string &threadName)
{
}
int main()
{
  // thread t1(add);
  // thread t2(add);

  // t1.join();
  // t2.join();

  thread t1(printLetters, "Thread 1");
  if (t1.joinable())
  {
    t1.join();
  }

  cout << "Counter: " << counter << endl;

  return 0;
}