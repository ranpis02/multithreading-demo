#include <iostream>
#include <thread>
#include <mutex>
#include <future>
using namespace std;

mutex mtx;

void communicate()
{
  promise<int> p;

  future<int> f = p.get_future();

  thread t1([&p]
            { p.set_value(42); });

  int res = f.get();

  cout << "Result from promise: " << res << endl;

  t1.join();
}

void work()
{
  future<int> f = async([]()
                        { return 42; });

  // Convert future to shared_future，ans future is not longer valid
  shared_future<int> sft = f.share();
  cout << sft.get() << endl;
  cout << sft.get() << endl;
  cout << sft.get() << endl;
}

int main()
{
  // communicate();
  work();

  cout << "Main thread is doing other work..." << endl;

  return 0;
}