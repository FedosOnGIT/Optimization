#pragma once

#include <functional>
#include <fstream>
#include <optional>

struct MinMethod {
    const unsigned int ITERATION_MAX = 100;
    using func_t = std::function<double(double)>;

    explicit MinMethod(std::string const& file)
        : out(file)
    {
        out.setf(std::iostream::fixed);
        out.precision(15);
    }

    virtual double min(func_t const& f, double l, double r, double eps) = 0;

private:
    void spec_print() {}

    //без этой специализации в конце будет '\t'
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
    template <typename... Args>
    void print(Args... args) {
        spec_print(args...);
        out << '\t';
    }

    template <typename... Args>
    void println(Args... args) {
        spec_print(args...);
        out << '\n';
    }

    std::ofstream out;
};
