#pragma once

#include <functional>

struct MinMethod {
    using func_t = std::function<double(double)>;

    virtual double min(func_t, double l, double r, double eps) = 0;
};
