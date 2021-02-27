#pragma once

#include "MinMethod.h"
#include <cmath>
#include <fstream>
#include <utility>

struct GoldenRatioMethod : MinMethod {
    // Конструктор принимает  имя файла для логирования, которое можно опустить.
    explicit GoldenRatioMethod(std::string const& output = "golden_ratio_log.txt")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        // Печать в лог шапки таблицы
        println("Iteration number", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "scale");

        double x1 = (r - l) * X1_FACTOR + l;
        double x2 = (r - l) * X2_FACTOR + l;
        double f1 = f(x1);
        double f2 = f(x2);
        // Количество проделанных итераций
        unsigned int index = 0;

        // Условие выхода - длина отрезка меньше двойного эпсилон.
        while (r - l > 2*eps) {
            print(index, l, r, x1, f1, x2, f2);
            if (f1 < f2) {
                println((x2 - l) / (r - l));
                r = x2;
                x2 = x1;
                f2 = f1;
                x1 = (r - l) * X1_FACTOR + l;
                f1 = f(x1);
            } else {
                println((r - x1) / (r - l));
                l = x1;
                x1 = x2;
                f1 = f2;
                x2 = (r - l) * X2_FACTOR + l;
                f2 = f(x2);
            }
            ++index;
            if (index >= ITERATION_MAX)
                return NAN;
        }
        println(index, l, r, x1, f1, x2, f2);
        return (r + l) / 2;
    }

private:
    // Множители, для получения левой и правой точек соответственно
    const double X1_FACTOR = 2 / (3 + sqrt(5));
    const double X2_FACTOR = 2 / (sqrt(5) + 1);
};
