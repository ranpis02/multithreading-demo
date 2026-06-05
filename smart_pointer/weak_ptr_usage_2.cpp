#include <iostream>
#include <memory>

using namespace std;

struct Node
{
  int val;

  Node() : val(0) {}
  Node(int m_val) : val(m_val) {}

  ~Node()
  {
    cout << "Node destroyed: " << val << endl;
  }
};

int main()
{
  weak_ptr<Node> wp;

  {
    auto sp = make_shared<Node>(10);
    wp = sp;

    cout << "use_count: " << wp.use_count() << endl;
    cout << "expired: " << wp.expired() << endl;

    // Upgrade weak_ptr to shared_ptr
    if (auto spt = wp.lock())
    {
      cout << "locked value: " << sp->val << endl;                 // 10
      cout << "use_count after lock: " << spt.use_count() << endl; // 2
    }
  }

  // cout << "expired after block: " << wp.expired() << endl;
  return 0;
}