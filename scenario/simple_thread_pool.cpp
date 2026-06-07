#include <iostream>
#include <vector>
#include <thread>
#include <mutex>
#include <queue>
#include <functional>
#include <condition_variable>

using namespace std;

class SimpleThreadPool {
private:
    vector<thread> workers;

    queue<function<void()>> tasks;

    mutex mtx;
    condition_variable cv;
    bool stop;

public:
    SimpleThreadPool(int threadCount) : stop(false) {
        for (int i = 0; i < threadCount; i++) {
            workers.emplace_back([this] {
                while (true) {
                    function<void()> task;

                    {
                        unique_lock<mutex> lock(mtx);

                        cv.wait(lock, [this] { return stop || !tasks.empty(); });

                        if (stop && tasks.empty()) {
                            return;
                        }

                        task = tasks.front();
                        tasks.pop();
                    }

                    task();
                }
            });
        }
    }

    ~SimpleThreadPool() {
        {
            unique_lock<mutex> lock(mtx);
            stop = true;
        }

        cv.notify_all();

        for (auto &worker : workers) {
            worker.join();
        }
    }

    void submit(function<void()> task) {
        {
            unique_lock<mutex> lock(mtx);
            tasks.push(task);
        }

        cv.notify_one();
    }
};

int main() {
    SimpleThreadPool pool(4);

    mutex coutMtx;
    for (int i = 0; i < 10; i++) {
        pool.submit([i, &coutMtx]() {
            lock_guard<mutex> lock(coutMtx);

            cout << "Task ID: " << i << ", Task executed by thread: " << this_thread::get_id()
                 << endl;
        });
    }

    return 0;
}
