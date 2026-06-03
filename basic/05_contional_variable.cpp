#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <queue>
#include <chrono>

using namespace std;

mutex mtx;
condition_variable cv;
queue<int> q;

void producer()
{
  for (int i = 0; i < 100; i++)
  {
    // Simulate the time taken to produce an item.
    // this_thread::sleep_for(chrono::milliseconds(100));

    unique_lock<mutex> lock(mtx);
    q.push(i);

    cout << "Produced: " << i << endl;

    cv.notify_one(); // Notify one waiting consumer.
  }
}

void consumer()
{
  for (int i = 0; i < 100; i++)
  {
    unique_lock<mutex> lock(mtx);
    cv.wait(lock, []
            { return !q.empty(); });

    int val = q.front();
    q.pop();
    cout << "Consumed: " << val << endl;
  }
}
int main()
{
  thread t1(producer);
  thread t2(consumer);

  if (t1.joinable())
    t1.join();
  if (t2.joinable())
    t2.join();

  cout << "All threads finished." << endl;

  return 0;
}