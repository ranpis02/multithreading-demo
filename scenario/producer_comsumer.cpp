#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <queue>
#include <chrono>

using namespace std;

mutex mtx;
condition_variable not_empty;
condition_variable not_full;
constexpr int MAX_BUFFER_SIZE = 5;

queue<int> buffer;

void producer(int item_id)
{
  for (int i = 0; i < 10; i++)
  {
    unique_lock<mutex> lock(mtx);

    not_full.wait(lock, []
                  { return buffer.size() < MAX_BUFFER_SIZE; });

    buffer.push(item_id);
    cout << "Producer no: " << i << ", item id: " << item_id << endl;

    lock.unlock();

    // Notify one waiting consumer that an item has been produced.
    not_empty.notify_one();

    // Simulate the time taken to produce an item.
    this_thread::sleep_for(chrono::milliseconds(200));
  }
}

void consumer(int item_id)
{
  for (int i = 0; i < 10; i++)
  {
    unique_lock<mutex> lock(mtx);

    not_empty.wait(lock, []
                   { return !buffer.empty(); });

    cout << "Consumer no: " << i << ", item id: " << buffer.front() << endl;
    buffer.pop();

    lock.unlock();

    // Notify one waiting producer that has empty space in the buffer.
    not_full.notify_one();

    this_thread::sleep_for(chrono::milliseconds(300));
  }
}
int main()
{
  thread prod1(producer, 1);
  thread prod2(producer, 2);

  thread cons1(consumer, 1);
  thread cons2(consumer, 2);

  prod1.join();
  prod2.join();
  cons1.join();
  cons2.join();

  cout << "All producers and consumers have finished." << endl;

  return 0;
}