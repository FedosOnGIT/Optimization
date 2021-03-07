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
    void spec_print(Current const& current) {
        *out << current;
    }

    template<typename Current, typename... Rest>
    void spec_print(Current const& current, Rest const&... rest) {
        spec_print(current);
        *out << '\t';
        spec_print(rest...);
    }

    void out_init(unsigned int = 15);

public:
    explicit logger(std::ostream *out, unsigned int = 8);
    explicit logger(std::string const &file);
    logger(logger&) = delete;
    logger(logger&&) noexcept;
    ~logger();

    logger& operator=(logger&) = delete;
    logger& operator=(logger&&) noexcept;

    // Печатает в лог, добавляя в конец табуляцию.
    template<typename... Args>
    logger &print(Args const&... args) {
        if (out != nullptr) {
            spec_print(args...);
            *out << '\t';
        }
        return *this;
    }

    // Печатает в лог, добавляя в конец перевод строки.
    template<typename... Args>
    logger &println(Args const&... args) {
        if (out != nullptr) {
            spec_print(args...);
            *out << '\n';
        }
        return *this;
    }

    void close();

    template<typename T>
    friend logger &operator<<(logger &lgg, T const &x) {
        if (lgg.out != nullptr) {
            *lgg.out << x;
        }
        return lgg;
    }
};


