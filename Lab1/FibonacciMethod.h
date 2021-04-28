#pragma once

#include "MinMethod.h"

class FibonacciMethod : public MinMethod {
public:
    using MinMethod::MinMethod;

    double min(func_t const& f, double l, double r, double eps) override {
        create(l, r, eps);
        if (iter >= ITERATION_MAX) {
            common_lgg.print(0);
            return NAN;
        }

        double x1 = fibonacci1 / fibonacci3 * (r - l) + l;
        double x2 = fibonacci2 / fibonacci3 * (r - l) + l;
        double f1 = f(x1), f2 = f(x2);
        unsigned int func_calc = 2;

        // Печать в лог шапки таблицы
        lgg.println("№", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "fib1", "fib2", "scale");

        for (int i = 0; i < iter; i++) {
            // Печать в лог данных конкретной итерации
            lgg.print(i, l, r, x1, f1, x2, f2, fibonacci1, fibonacci2);
            if (f1 < f2) {
                // Печатаем в лог отношение отрезков
                lgg.println((x2 - l)/(r - l));
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = fibonacci1 / fibonacci3 * (r - l) + l;
                f1 = f(x1);
            } else {
                // Печатаем в лог отношение отрезков
                lgg.println((r - x1)/(r - l));
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = fibonacci2 / fibonacci3 * (r - l) + l;
                f2 = f(x2);
            }
            ++func_calc;
            fibonacci3 = fibonacci2;
            fibonacci2 = fibonacci1;
            fibonacci1 = fibonacci3 - fibonacci2;
        }
        common_lgg.print(func_calc);
        return (l + r) / 2;
    }

private:
    // Соседние числа фибоначчи. Используем double,
    // чтобы случайно не получить переполнение
    double fibonacci1 = 1, fibonacci2 = 1, fibonacci3 = 2;

    void create(double l, double r, double eps) {
        fibonacci1 = 1, fibonacci2 = 1, fibonacci3 = 2;

        for (iter = 1; (r - l) / fibonacci3 >= eps && (iter < ITERATION_MAX); ++iter) {
            fibonacci1 = fibonacci2;
            fibonacci2 = fibonacci3;
            fibonacci3 = fibonacci1 + fibonacci2;
        }
    }
};