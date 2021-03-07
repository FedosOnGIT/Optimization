#pragma once

#include "MinMethod.h"
#include <fstream>
#include <utility>

class DichotMethod : MinMethod {
public:
    // Конструктор принимает дельту,
    // а так же имя файла для логирования, которое можно опустить.
    explicit DichotMethod(logger lgg, logger& c_lgg, double delta = 1e-8)
        : MinMethod(std::move(lgg), c_lgg), delta(delta) {}

    double getDelta() const {
        return delta;
    }

    void setDelta(double delta) {
        this->delta = delta;
    }

    double min(func_t const& f, double l, double r, double eps) override {
        // Печать в лог шапки таблицы
        lgg.println("№", "left", "right", "x1", "f(x1)", "x2", "f(x2)", "scale");

        // Количество проделанных итераций
        iter = 0;
        func_calc = 0;
        // Проверка на то, что дельта меньше эпсилон
        delta = std::min(delta, 0.5 * eps);

        // Условие выхода - длина отрезка меньше двойного эпсилон.
        while (r - l > 2*eps) {
            double x1 = (r + l - delta) / 2;
            double x2 = (r + l + delta) / 2;
            double f1 = f(x1);
            double f2 = f(x2);
            func_calc += 2;
            // Печать в лог данных конкретной итерации
            lgg.print(iter, l, r, x1, f1, x2, f2);
            if (f1 < f2) {
                // Печатаем в лог отношение отрезков
                lgg.println((x2 - l) / (r - l));
                r = x2;
            } else {
                // Печатаем в лог отношение отрезков
                lgg.println((r - x1) / (r - l));
                l = x1;
            }
            ++iter;
            // Если метод не сошёлся к минимуму, за адекватное
            // количество шагов, то выгодим
            if (iter >= ITERATION_MAX) {
                common_lgg.print(-1);
                return NAN;
            }
        }
        common_lgg.print(func_calc);
        return (l + r) / 2;
    }

private:
    // Величина, задающая отступ от середины отрезка
    double delta;
};
