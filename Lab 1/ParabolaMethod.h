#pragma once

#include "MinMethod.h"
#include <cmath>

struct ParabolaMethod : MinMethod {
    double min(func_t f, double l, double r, double eps) override {
        std::ofstream out("parabola_log.txt");
        setupOut(out);
        out << "Iteration number\tleft\tright\tx1\tf(x1)\tx2\tf(x2)\tscale\n";

        unsigned int index = 0;
        double x1 = l, x2, x3 = r;
        double f1 = f(x1), f2, f3 = f(x3);
        if (f1 > f3) {
            x2 = x3 - eps;
            f2 = f(x2);
            if (f2 > f3) {
                return x3;
            }
        } else {
            x2 = x1 + eps;
            f2 = f(x2);
            if (f2 > f1) {
                return x1;
            }
        }
        double a1, a2;
        double x, fx, x_prev;

        while (true) {
            a1 = (f2 - f1) / (x2 - x1);
            a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
            x = 0.5 * (x1 + x2 - a1 / a2);
            if (index != 0 && abs(x - x_prev) < eps) {
                break;
            }
            fx = f(x);
            if (x <= x2) {
                if (fx >= f2) {
                    x1 = x, f1 = fx;
                } else {
                    x3 = x2, f3 = f2;
                    x2 = x, f2 = fx;
                }
            } else {
                if (fx >= f2) {
                    x3 = x, f3 = fx;
                } else {
                    x1 = x2, f1 = f2;
                    x2 = x, f2 = fx;
                }
            }
            x_prev = x;
            ++index;
            out << "\n";
        }


        return x;
    }
};
