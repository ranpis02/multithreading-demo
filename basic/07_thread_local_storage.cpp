#include <iostream>
#include <thread>
#include <mutex>

using namespace std;

thread_local int counter = 0;

mutex mtx;

/**
 * Thread-local storage(TLS) vs. global variable vs. local variable:
 * 1. thread local and local variable is thread-safe, while global variable is not.
 * 2. thread local variable is shared among the same thread, while local variable is only visible to the function
 *
 */
void worker(int id)
{
  counter += id;

  unique_lock<mutex> lock(mtx);
  cout << "Thread " << id << ": counter = " << counter << endl;
}

int main()
{
  thread t1(worker, 1);
  thread t2(worker, 2);

  t1.join();
  t2.join();

  cout << "Main thread: counter = " << counter << endl;
  return 0;
}