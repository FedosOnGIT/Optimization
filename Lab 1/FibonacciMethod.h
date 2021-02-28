#pragma once

#include "MinMethod.h"

class FibonacciMethod : MinMethod {
public:
    explicit FibonacciMethod(std::string const& output = "fibonacci_log.csv")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        int n = create(l, r, eps);
        double fibonacci3 = fibonacci1 + fibonacci2;
        double x1 = fibonacci1 / fibonacci3 * (r - l) + l;
        double x2 = fibonacci2 / fibonacci3 * (r - l) + l;
        double f1 = f(x1), f2 = f(x2);

        println("№", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "scale");

        for (int i = 0; i < n; i++) {
            print(i, l, r, x1, f1, x2, f2);
            if (f1 < f2) {
                println((x2 - l)/(r - l));
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = fibonacci1 / fibonacci3 * (r - l) + l;
                f1 = f(x1);
            } else {
                println((r - x1)/(r - l));
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = fibonacci2 / fibonacci3 * (r - l) + l;
                f2 = f(x2);
            }
            fibonacci3 = fibonacci2;
            fibonacci2 = fibonacci1;
            fibonacci1 = fibonacci3 - fibonacci2;
        }
        return (l + r) / 2;
    }

private:
    double fibonacci1 = 1, fibonacci2 = 1;

    int create(double l, double r, double eps) {
        fibonacci1 = 1, fibonacci2 = 1;
        int number = 0;
        while (true) {
            double fibonacci3 = fibonacci1 + fibonacci2;
            fibonacci1 = fibonacci2;
            fibonacci2 = fibonacci3;
            number++;
            if ((r - l) / fibonacci3 < eps) {
                break;
            }
        }
        return number;
    }
};