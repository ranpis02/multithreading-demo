#include <iostream>
#include <memory>

using namespace std;

/**
 * smart pointer: unique_ptr
 *
 * smart pointer is a class template that provides automatic memory management.
 *
 * The characteristics of unique_ptr are:
 * 1. unique_ptr is owned by a single object. It cannot be copied, but it can be moved.
 */
void foo()
{
  // Create a unique_ptr to an integer with value 10
  unique_ptr<int> p = make_unique<int>(10);

  cout << "Value: " << *p << endl;

  // Move the unique_ptr to another uniqe_ptr
  unique_ptr<int> p2 = move(p);

  // Copying a unique_ptr is not allowed
  // unique_ptr<int> p2 = p2;
}

/**
 * shared_ptr retains shared ownership of an object through a pointer. Serveral shared_ptr objects may own the same object.
 * The innner structure of shared_ptr likes this:
 * shared_ptr
 *  |
 *  +---- ptr
 *  |
 *  +---- control block
 *            |
 *             +--- ref_count
 *
 */
void bar()
{
  shared_ptr<int> p = make_shared<int>(10);

  shared_ptr<int> p2 = p;

  cout << "p use_count: " << p.use_count() << endl;   // p use_count: 2
  cout << "p2 use_count: " << p2.use_count() << endl; // p2 use_count: 2

  shared_ptr<int> p3 = p2;

  cout << "p3 use_count: " << p3.use_count() << endl; // p3 use_count: 3
}

int main()
{
  // foo();

  bar();
  return 0;
}