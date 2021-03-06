#pragma once

#include <memory>
#include <fstream>

class logger {
    std::ostream *out;
    bool is_owner;

    // Эта функция печатает в лог слова, разделённые табуляцией.
    // После последнего слова ничего напечатано не будет.
    void spec_print() {}

    template<typename Current>
    void spec_print(Current current) {
        *out << current;
    }

    template<typename Current, typename... Rest>
    void spec_print(Current current, Rest... rest) {
        spec_print(current);
        *out << '\t';
        spec_print(rest...);
    }

    void out_init();

public:
    explicit logger(std::ostream *out);
    explicit logger(std::string const &file);
    logger(logger&) = delete;
    logger(logger&&) noexcept;
    ~logger();

    // Печатает в лог, добавляя в конец табуляцию.
    template<typename... Args>
    logger &print(Args... args) {
        spec_print(args...);
        *out << '\t';
        return *this;
    }

    // Печатает в лог, добавляя в конец перевод строки.
    template<typename... Args>
    logger &println(Args... args) {
        spec_print(args...);
        *out << '\n';
        return *this;
    }

    template<typename T>
    friend logger &operator<<(logger &lgg, T x) {
        *lgg.out << x;
        return lgg;
    }
};


