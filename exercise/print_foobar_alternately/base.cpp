#include <iostream>
#include <mutex>
#include <condition_variable>
#include <thread>

using namespace std;

class FooBar {
private:
    int n;
    mutex mtx;
    bool fooBtn = true;
    condition_variable cv;

public:
    FooBar(int m_n) : n(m_n) {}

    void foo() {
        for (int i = 0; i < n; i++) {
            unique_lock<mutex> lock(mtx);
            cv.wait(lock, [this] { return fooBtn; });

            cout << "foo";

            fooBtn = false;
            cv.notify_one();
        }
    }

    void bar() {
        for (int i = 0; i < n; i++) {
            unique_lock<mutex> lock(mtx);
            cv.wait(lock, [this] { return !fooBtn; });

            cout << "bar";

            fooBtn = true;

            cv.notify_one();
        }
    }
};

int main() {
    int n = 5;

    FooBar foobar(5);

    thread t1(&FooBar::foo, &foobar);

    thread t2(&FooBar::bar, &foobar);

    t1.join();
    t2.join();

    return 0;
}