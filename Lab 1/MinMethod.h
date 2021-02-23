#pragma once

#include <functional>
#include <fstream>

struct MinMethod {
    using func_t = std::function<double(double)>;

    virtual double min(func_t, double l, double r, double eps) = 0;

protected:
    void setupOut(std::ofstream& out) {
        out.setf(std::iostream::fixed);
        out.precision(15);
    }

    template <typename First, typename... Last>
    void iterationOut(std::ofstream& out, First first, Last... last) {
        out << first << "\t";
        iterationOut(out, last...);
    }

    template <typename First>
    void iterationOut(std::ofstream& out, First first) {
        out << first << "\t";
    }
};
