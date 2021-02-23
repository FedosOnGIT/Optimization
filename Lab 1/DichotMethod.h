#pragma once

#include "MinMethod.h"
#include <fstream>

struct DichotMethod : MinMethod {
    DichotMethod(double delta = 1e-8)
        : delta(delta)
    {}

    double getDelta() const {
        return delta;
    }

    void setDelta(double delta) {
        this->delta = delta;
    }

    double min(func_t f, double l, double r, double eps) override {
        std::ofstream out("dichot_log.txt");
        setupOut(out);
        out << "Iteration number\tleft\tright\tx1\tf(x1)\tx2\tf(x2)\tscale\n";

        unsigned int index = 0;
        delta = std::min(delta, 0.5*eps);

        while (r - l > eps) {
            double x1 = (r + l - delta) / 2;
            double x2 = (r + l + delta) / 2;
            double f1 = f(x1);
            double f2 = f(x2);
            iterationOut(out, index, l, r, x1, f1, x2, f2);
            if (f1 < f2) {
                iterationOut(out, (x2 - l) / (r - l));
                r = x2;
            } else {
                iterationOut(out, (r - x1) / (r - l));
                l = x1;
            }
            ++index;
            out << "\n";
        }
        return (l + r)/2;
    }

private:
    double delta;
};
