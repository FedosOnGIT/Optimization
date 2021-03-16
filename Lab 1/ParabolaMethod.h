#pragma once

#include "MinMethod.h"
#include <cmath>

class ParabolaMethod : public MinMethod {
public:
    using MinMethod::MinMethod;

    double min(func_t const& f, double l, double r, double eps) override {
        // print("left=", l, "right=", r);

        // Количество проделанных итераций
        double x1, x2, x3, f1, f2, f3;
        // Находим такие x1, x2, x3: x1 < x2 < x3 and f(x2) <= f(x1) and f(x2) <= f(x3)
        initPoints(f, l, r, 2*eps, x1, x2, x3, f1, f2, f3);
        // Печать в лог числа итераций, проделанных при инициализации начальных точек
        // методом золотого сечения. Чаще всего около нулевое.
        // println("init_iterations_count=", init_iter);
        if (iter >= ITERATION_MAX) {
            common_lgg.print(-func_calc);
            return NAN;
        }

        // Печать в лог шапки таблицы
        lgg.println("№", "x1", "x3", "f(x1)", "f(x3)", "x2", "f(x2)", "x", "f(x)", "scale");

        // Завершаем, если находимся в eps окрестности
        if (x3 - x1 < 2*eps) {
            common_lgg.print(func_calc);
            return (x1 + x3) / 2;
        }
        double a1, a2, x, fx, x_prev;
        iter = 0;

        while (true) {
            // Вычисление коэффициентов параболы
            a1 = (f2 - f1) / (x2 - x1);
            a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
            // Вершина параболы
            x = 0.5 * (x1 + x2 - a1 / a2);

            // Условие выхода - длина отрезка меньше эпсилон.
            if (iter != 0 && std::abs(x - x_prev) < eps) {
                break;
            }

            fx = f(x);
            ++func_calc;
            // Печать в лог данных текущей итерации
            lgg.print(iter, x1, x3, f1, f3, x2, f2, x, fx);
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
            // Печатаем в лог отношение отрезков
            lgg.println((x3 - x1) / (x3_prev - x1_prev));
            x_prev = x;
            ++iter;
            if (iter >= ITERATION_MAX) {
                common_lgg.print(-func_calc);
                return NAN;
            }
        }
        common_lgg.print(func_calc);
        return x;
    }

private:
    // Ищем x1, x2, x3 используя метод золотого сечения
    // Теперь l = x1, r = x3.
    // Возвращает количество проделанных итераций
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
        iter = 0;
        func_calc = 4;

        while (x3 - x1 >= eps && !(low <= f1 && low <= f3) && (iter < ITERATION_MAX)) {
            if (f2_left < f2_right) {
                x3 = x2_right;
                x2_right = x2_left;
                f2_right = f2_left;
                x2_left = (x3 - x1) * FACTOR1 + x1;
                f2_left = f(x2_left);
                low = f2_left;
            } else {
                x1 = x2_left;
                x2_left = x2_right;
                f2_left = f2_right;
                x2_right = (x3 - x1) * FACTOR2 + x1;
                f2_right = f(x2_right);
                low = f2_right;
            }
            ++iter;
            ++func_calc;
        }
        x2 = f2_left < f2_right ? x2_left : x2_right;
        f2 = low;
    }
};
