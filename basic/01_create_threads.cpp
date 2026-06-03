#include <iostream>
#include <thread>

using namespace std;

void hello_thread()
{
  cout << "I am a new thread, hello!" << endl;
}

int main()
{
  // Create a new thread that executes the hello_thread function
  thread t(hello_thread);

  cout << "I am the main thread, hello!" << endl;

  t.join(); // Wait for the new thread to finish before exiting the main thread

  cout << "Both threads have finished execution." << endl;

  return 0;
}