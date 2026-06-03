#include <iostream>
#include <thread>

using namespace std;

/**
 * Caution: When passing arguments to a thread function, the arguments are copies by default. If you want to pass by reference, you need to use std::ref to wrap the argument.
 */
void hello_thread(string name, int times)
{
  for (int i = 1; i <= times; i++)
  {
    cout << "Hello from " << name << "! This is message " << i << endl;
  }
}

void hello_thread_ref(string &name, int times)
{
  for (int i = 1; i <= times; i++)
  {
    cout << "Hello from " << name << "! This is message " << i << endl;
  }
}

int main()
{
  // Create a new thread and pass arguments to the thread function
  thread t1(hello_thread, "Alice", 3);

  thread t2(hello_thread_ref, ref("Bob"), 3);

  cout << "I am the main thread, hello!" << endl;

  t1.join(); // Wait for the new thread to finish before exiting the main thread

  cout << "Both threads have finished execution." << endl;

  return 0;
}