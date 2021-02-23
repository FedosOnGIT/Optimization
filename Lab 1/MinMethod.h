#pragma once

#include <functional>
#include <fstream>
#include <optional>

struct MinMethod {
    using func_t = std::function<double(double)>;

    MinMethod(std::string file = "min_method.txt")
        : out(file)
    {
        out.setf(std::iostream::fixed);
        out.precision(15);
    }

    virtual double min(func_t, double l, double r, double eps) = 0;

protected:
    template <typename Current, typename... Rest>
    void print(Current current, Rest... rest) {
        print(current);
        print(rest...);
    }

    template <typename Current>
    void print(Current current) {
        out << current << "\t";
    }

    template <typename... Rest>
    void println(Rest... rest) {
        print(rest...);
        out << "\n";
    }

    std::ofstream out;
};
