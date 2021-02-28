#pragma once

#include "MinMethod.h"

class FibonacciMethod : MinMethod {
public:
    explicit FibonacciMethod(std::string const& output = "fibonacci_log.csv")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        int n = create(l, r, eps);
        double x1 = fibonacci1 / fibonacci3 * (r - l) + l;
        double x2 = fibonacci2 / fibonacci3 * (r - l) + l;
        double f1 = f(x1), f2 = f(x2);

        // Печать в лог шапки таблицы
        println("№", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "fib1", "fib2", "scale");

        for (int i = 0; i < n; i++) {
            // Печать в лог данных конкретной итерации
            print(i, l, r, x1, f1, x2, f2, fibonacci1, fibonacci2);
            if (f1 < f2) {
                // Печатаем в лог отношение отрезков
                println((x2 - l)/(r - l));
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = fibonacci1 / fibonacci3 * (r - l) + l;
                f1 = f(x1);
            } else {
                // Печатаем в лог отношение отрезков
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
    // Соседние числа фибоначчи. Используем double,
    // чтобы случайно не получить переполнение
    double fibonacci1 = 1, fibonacci2 = 1, fibonacci3 = 2;

    int create(double l, double r, double eps) {
        fibonacci1 = 1, fibonacci2 = 1, fibonacci3 = 2;
        // номер fibonacci1
        int number = 1;
        while ((r - l) / fibonacci3 >= eps) {
            fibonacci1 = fibonacci2;
            fibonacci2 = fibonacci3;
            fibonacci3 = fibonacci1 + fibonacci2;
            number++;
        }
        return number;
    }
};