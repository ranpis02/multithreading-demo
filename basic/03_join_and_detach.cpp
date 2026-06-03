#include <iostream>
#include <thread>

using namespace std;

void worker()
{
  cout << "Worker start" << endl;
  this_thread::sleep_for(chrono::seconds(2));
  cout << "Worker end" << endl;
}

int main()
{
  thread t(worker);

  if (t.joinable())
  {
    t.join();
  }

  cout << "Main thread continues..." << endl;
  return 0;
}