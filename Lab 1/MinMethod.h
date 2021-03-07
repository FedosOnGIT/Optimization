#pragma once

#include "logger.h"
#include <functional>
#include <fstream>
#include <filesystem>
#include <ostream>

using func_t = std::function<double(double)>;

// Интерфейс для всех методов минимизации.
// Содержит общие методы для логирования.
class MinMethod {
public:
    const unsigned int ITERATION_MAX = 250;

    // Инициализируем поток, куда будем логировать итерации
    explicit MinMethod(logger lgg, logger& c_lgg)
        : lgg(std::move(lgg)), common_lgg(c_lgg)
    {}

    // Возвращает точку минимума у функции f на отрезке [l, r],
    // с погрешностью не более eps
    virtual double min(func_t const& f, double l, double r, double eps) = 0;

protected:
    logger lgg;
    logger& common_lgg;

    unsigned int iter = 0;
    int func_calc = 0;
};
