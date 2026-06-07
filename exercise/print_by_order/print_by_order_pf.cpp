#include <iostream>
#include <future>
#include <thread>

using namespace std;

class Foo {
private:
    promise<void> p1;
    promise<void> p2;

    future<void> f1 = p1.get_future();
    future<void> f2 = p2.get_future();

public:
    Foo() = default;

    void first() {
        cout << "first" << endl;

        p1.set_value();
    }

    void second() {
        f1.wait();

        cout << "second" << endl;

        p2.set_value();
    }

    void third() {
        f2.wait();

        cout << "third" << endl;
    }
};

int main() {
    Foo foo;

    thread t1([&] { foo.first(); });

    thread t2([&] { foo.second(); });

    thread t3([&] { foo.third(); });

    t1.join();
    t2.join();
    t3.join();

    return 0;
}