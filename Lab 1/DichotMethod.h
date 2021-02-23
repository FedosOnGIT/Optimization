#pragma once

#include "MinMethod.h"
#include <fstream>
#include <utility>

struct DichotMethod : MinMethod {
    explicit DichotMethod(double delta = 1e-8, std::string const& output = "dichot_log.txt")
        : MinMethod(output), delta(delta) {}

    double getDelta() const {
        return delta;
    }

    void setDelta(double delta) {
        this->delta = delta;
    }

    double min(func_t const& f, double l, double r, double eps) override {
        println("Iteration number", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "scale");

        unsigned int index = 0;
        delta = std::min(delta, 0.5 * eps);

        while (r - l > eps) {
            double x1 = (r + l - delta) / 2;
            double x2 = (r + l + delta) / 2;
            double f1 = f(x1);
            double f2 = f(x2);
            print(index, l, r, x1, f1, x2, f2);
            if (f1 < f2) {
                println((x2 - l) / (r - l));
                r = x2;
            } else {
                println((r - x1) / (r - l));
                l = x1;
            }
            ++index;
        }
        return (l + r) / 2;
    }

private:
    double delta;
};
