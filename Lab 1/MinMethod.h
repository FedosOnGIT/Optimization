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
        out << "Iteration number\tleft\tright\tx1\tf(x1)\tx2\tf(x2)\tscale\n";
    }

    void iterationIndexOut(std::ofstream& out,
                           unsigned int index,
                           double l, double r,
                           double x1, double f1,
                           double x2, double f2) {
        out << index << "\t" << l << "\t" << r << "\t" << x1 << "\t" << f1 <<
            "\t" << x2 << "\t" << f2 << "\t";
    }

    void singleOut(std::ofstream& out, double value) {
        out << value << "\n";
    }
};
