#pragma once

#include "MinMethod.h"
#include <cmath>
#include <fstream>

struct GoldenRatioMethod : MinMethod {
    double min(func_t f, double l, double r, double eps) override {
        std::ofstream out("golden_ration_log.txt");
        out.setf(std::iostream::fixed);
        out.precision(15);

        double x1 = (r - l) * X1_FACTOR + l;
        double x2 = (r - l) * X2_FACTOR + l;
        double f1 = f(x1);
        double f2 = f(x2);
        unsigned int index = 0;

        while (r - l > eps) {
            out << "Iteration number " << index << ": left=" << l << ", right=" << r <<
                ", x1=" << x1 << ", f(x1)=" << f1 << ", x2=" << x2 << ", f(x2)=" << f2 << " scale= ";

            if (f1 < f2) {
                out << (x2 - l)/(r - l) << std::endl;
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = (r - l) * X1_FACTOR + l;
                f1 = f(x1);
            } else {
                out << (r - x1)/(r - l) << std::endl;
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = (r - l) * X2_FACTOR + l;
                f2 = f(x2);
            }
            ++index;
        }
        out << "Iteration number " << index << ": left=" << l << ", right=" << r <<
                  ", x1=" << x1 << ", f(x1)=" << f1 << ", x2=" << x2 << ", f(x2)=" << f2 << ".\n";
        return (r + l) / 2;
    }

    private:
        const double X1_FACTOR = 2 / (3 + sqrt(5));
        const double X2_FACTOR = 2 / (sqrt(5) + 1);
};
