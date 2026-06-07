#include <iostream>
#include <mutex>
#include <queue>
#include <thread>
#include <condition_variable>
#include <functional>
#include <vector>
#include <future>
#include <stdexcept>
#include <type_traits>

using namespace std;

class ThreadPool {
private:
    queue<function<void()>> tasks;
    vector<thread> workers;
    condition_variable cv;

    bool stop;
    mutex mtx;

public:
    ThreadPool(size_t threadCount) : stop(false) {
        for (size_t i = 0; i < threadCount; ++i) {
            workers.emplace_back([this] {
                while (true) {
                    function<void()> task;

                    {
                        unique_lock<mutex> lock(mtx);

                        cv.wait(lock, [this] { return stop || !tasks.empty(); });

                        if (stop && tasks.empty())
                            return;

                        task = move(tasks.front());

                        tasks.pop();
                    }

                    // Excute the task
                    task();
                }
            });
        }
    }

    template <typename F, typename... Args>
    auto submit(F &&f, Args &&...args) -> future<typename invoke_result<F, Args...>::type> {
        using return_type = typename invoke_result<F, Args...>::type;

        auto task =
            make_shared<packaged_task<return_type()>>(bind(forward<F>(f), forward<Args>(args)...));

        future<return_type> result = task->get_future();

        {
            unique_lock<mutex> lock(mtx);

            if (stop)
                throw runtime_error("Submit on stopped ThreadPool");

            tasks.emplace([task]() { (*task)(); });
        }

        cv.notify_one();

        return result;
    }

    ~ThreadPool() {
        {
            unique_lock<mutex> lock(mtx);
            stop = true;
        }

        cv.notify_all();

        for (thread &worker : workers) {
            if (worker.joinable()) {
                worker.join();
            }
        }
    }
};

int main() {
    // Create a thread pool with 4 threads
    ThreadPool pool(4);

    auto f1 = pool.submit([]() {
        cout << "task 1 is running" << endl;
        return 1;
    });

    auto f2 = pool.submit(
        [](int a, int b) {
            cout << "task 2 is running" << endl;
            return a + b;
        },
        10, 20);

    cout << "f1 result = " << f1.get() << endl;
    cout << "f2 result = " << f2.get() << endl;

    return 0;
}
