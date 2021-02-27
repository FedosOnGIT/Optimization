#pragma once

#include <functional>
#include <fstream>
#include <filesystem>

using func_t = std::function<double(double)>;

// Интерфейс для всех методов минимизации.
// Содержит общие методы для логирования.
struct MinMethod {
    const unsigned int ITERATION_MAX = 250;
    const std::string LOGS_PATH = "./logs/";

    // Конструктор принимает имя файла для логирования
    explicit MinMethod(std::string const& file)
    {
        using namespace std::filesystem;
        create_directories(LOGS_PATH);
        out.open(LOGS_PATH + file);
        out.setf(std::iostream::fixed);
        out.precision(15);
    }

    virtual double min(func_t const& f, double l, double r, double eps) = 0;

private:
    // Эта функция печатает в лог слова, разделённые табуляцией.
    // После последнего слова ничего напечатано не будет.
    void spec_print() {}

    template <typename Current>
    void spec_print(Current current) {
        out << current;
    }

    template <typename Current, typename... Rest>
    void spec_print(Current current, Rest... rest) {
        spec_print(current);
        out << '\t';
        spec_print(rest...);
    }

protected:
    // Печатает в лог, добавляя в конец табуляцию.
    template <typename... Args>
    void print(Args... args) {
        spec_print(args...);
        out << '\t';
    }

    // Печатает в лог, добавляя в конец перевод строки.
    template <typename... Args>
    void println(Args... args) {
        spec_print(args...);
        out << '\n';
    }

    std::ofstream out;
};
