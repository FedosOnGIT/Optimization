#pragma once

#include "MinMethod.h"
#include <cmath>

struct ParabolaMethod : MinMethod {
    explicit ParabolaMethod(std::string const& output = "parabola_log.txt")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        println("left=", l, "right=", r);
        println("Iteration number", "x1", "f(x1)", "x2", "f(x2)", "x3", "f(x3)", "x", "f(x)", "scale");

        unsigned int index = 0;
        double x1, x2, x3, f1, f2, f3;
        // Находим такие x1, x2, x3: x1 < x2 < x3 and f(x2) <= f(x1) and f(x2) <= f(x3)
        initPoints(f, l, r, eps, x1, x2, x3, f1, f2, f3);
        // Завершаем, если находимся в eps окрестности
        if (x3 - x1 < eps) {
            return x2;
        }
        double a1, a2, x, fx, x_prev;

        while (true) {
            // Вычисление коэффициентов параболы
            a1 = (f2 - f1) / (x2 - x1);
            a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
            // Вершина параболы
            x = 0.5 * (x1 + x2 - a1 / a2);
            if (index != 0 && std::abs(x - x_prev) < eps) {
                break;
            }
            fx = f(x);
            print(index, x1, f1, x2, f2, x3, f3, x, fx);
            double x1_prev = x1, x3_prev = x3;
            // Правило выбора
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
            println((x3 - x1) / (x3_prev - x1_prev));
            x_prev = x;
            ++index;
            if (index >= ITERATION_MAX)
                return NAN;
        }
        return x;
    }

private:
    // Ищем x1, x2, x3 используя метод золотого сечения
    // Теперь l = x1, r = x3.
    void initPoints(func_t const& f, double l, double r, double eps,
                    double& x1, double& x2, double& x3,
                    double& f1, double& f2, double& f3) {
        const double FACTOR1 = 2 / (3 + sqrt(5));
        const double FACTOR2 = 2 / (sqrt(5) + 1);
        x1 = l, x3 = r;
        f1 = f(x1);
        f3 = f(x3);
        double x2_left = (x3 - x1) * FACTOR1 + x1;
        double x2_right = (x3 - x1) * FACTOR2 + x1;
        double f2_left = f(x2_left);
        double f2_right = f(x2_right);
        double low = std::min(f2_left, f2_right);
        unsigned int index = 0;

        while (x3 - x1 < eps && !(low <= f1 && low <= f3)) {
            if (f2_left < f2_right) {
                x3 = x2_right;
                x2_right = x1;
                f2_right = f1;
                x2_left = (x3 - x1) * FACTOR1 + x1;
                f2_left = f(x2_left);
            } else {
                x1 = x2_left;
                x2_left = x2_right;
                f2_left = f2_right;
                x2_right = (x3 - x1) * FACTOR2 + x1;
                f2_right = f(x2_right);
            }
            low = std::min(f2_left, f2_right);
            ++index;
            if (index >= ITERATION_MAX)
                return;
        }
        x2 = f2_left < f2_right ? x2_left : x2_right;
        f2 = low;
    }
};
