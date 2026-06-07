#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>

using namespace std;

class Foo {
public:
    Foo() = default;

    void first() {

        cout << "first" << endl;

        {
            unique_lock<mutex> lock(mtx);
            order = 1;
        }

        cv.notify_all();
    }

    void second() {
        {
            unique_lock<mutex> lock(mtx);

            cv.wait(lock, [this] { return order >= 1; });
        }

        cout << "second" << endl;

        {
            unique_lock<mutex> lock(mtx);
            order = 2;
        }

        cv.notify_all();
    }

    void third() {
        {
            unique_lock<mutex> lock(mtx);
            cv.wait(lock, [this] { return order >= 2; });
        }

        cout << "third" << endl;
    }

private:
    mutex mtx;
    condition_variable cv;
    int order = 0;
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