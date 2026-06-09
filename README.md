# multithreading-demo

A collection of examples and practice code for learning multithreaded programming. The repository currently focuses on C++ multithreading, with a small set of Java thread and syntax exercises as well.

## Project Layout

```text
basic/                 C++ multithreading basics
advanced_character/    Advanced C++ concurrency utilities
scenario/              Common C++ concurrency scenarios
exercise/              C++ synchronization practice problems
smart_pointer/         C++ smart pointer examples
test/                  Small C++ syntax or feature tests
Java/                  Java thread basics and syntax exercises
```

## C++ Multithreading Examples

The examples in `basic/` are organized by topic and are suitable for reading and running in order:

```text
01_create_threads.cpp              Creating threads
02_thread_arguments.cpp            Passing arguments to threads
03_join_and_detach.cpp             join and detach
04_lock_guard_and_unique_lock.cpp  lock_guard and unique_lock
05_contional_variable.cpp          Condition variables
06_automic_operation.cpp           Atomic operations
07_thread_local_storage.cpp        Thread-local storage
08_promise_and_future.cpp          promise and future
```

The `scenario/` directory contains examples that are closer to real-world concurrent programming patterns, such as:

```text
blocking_queue.cpp
producer_comsumer.cpp
simple_thread_pool.cpp
thread_pool.cpp
```

The `exercise/` directory contains synchronization practice problems, such as ordered printing and alternating output.

## Build and Run C++ Examples

Most C++ examples are standalone `.cpp` files and can be compiled individually. For example, in PowerShell:

```powershell
g++ -std=c++17 -Wall -Wextra basic\01_create_threads.cpp -o out\basic_01.exe
.\out\basic_01.exe
```

Run the thread pool example:

```powershell
g++ -std=c++17 -Wall -Wextra scenario\thread_pool.cpp -o out\thread_pool.exe
.\out\thread_pool.exe
```

If the `out/` directory does not exist yet, create it first:

```powershell
mkdir out
```

## Java Exercises

Java code lives under the `Java/` directory:

```text
Java/Basic/       Java thread basics
Java/SyntaxTest/  Java syntax tests
```

The Java files use package names such as `Java.Basic`. Compile and run them from the repository root:

```powershell
javac -d out Java\Basic\MyThread.java
java -cp out Java.Basic.MyThread
```

## Environment

- C++: a compiler with C++17 support, such as GCC, Clang, or MSVC.
- Java: JDK 17 or newer is recommended.

Check your local environment with:

```powershell
g++ --version
java -version
javac -version
```

## Notes

This repository is meant for learning and experimentation. Each example is kept as independent as possible so it can be read, modified, compiled, and run on its own. When adding new examples, place them in the directory that best matches the topic so the README stays aligned with the actual project structure.
