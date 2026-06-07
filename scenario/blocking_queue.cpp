#include <iostream>
#include <mutex>
#include <condition_variable>
#include <queue>
#include <thread>
#include <chrono>

using namespace std;

template <typename T>
class BlockingQueue {
private:
    queue<T> queue;
    mutex mtx;
    condition_variable cv;
    bool closed = false;

public:
    BlockingQueue() = default;

    void push(T value) {
        {
            unique_lock<mutex> lock(mtx);
            if (closed)
                throw runtime_error("push to closed blockingqueue");

            queue.push(move(value));
        }

        cv.notify_one();
    }

    bool pop(T &value) {
        unique_lock<mutex> lock(mtx);

        cv.wait(lock, [this] { return closed || !queue.empty(); });

        if (queue.empty()) {
            return false;
        }

        value = move(queue.front());
        queue.pop();

        return true;
    }

    void close() {
        {
            unique_lock<mutex> lock(mtx);
            closed = true;
        }

        cv.notify_all();
    }
};

int main() {
    BlockingQueue<int> bq;
    mutex cout_mtx;

    thread consumer([&] {
        int x;
        while (bq.pop(x)) {
            {
                lock_guard<mutex> lock(cout_mtx);
                cout << "Consumed: " << x << endl;
            }

            this_thread::sleep_for(chrono::milliseconds(100));
        }
    });

    this_thread::sleep_for(chrono::milliseconds(500));

    for (int i = 0; i < 10; i++) {
        {
            lock_guard<mutex> lock(cout_mtx);
            cout << "Produced: " << i << endl;
        }

        bq.push(i);
        this_thread::sleep_for(chrono::milliseconds(50));
    }

    bq.close();
    consumer.join();

    return 0;
}