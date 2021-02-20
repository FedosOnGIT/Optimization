#pragma once

#include <functional>

struct BaseMethod {
    BaseMethod(std::function<double(double)> f)
        : f(f)
    {}

    double operator()(double x) {
        return f(x);
    }

    virtual double min(double left, double right, double epsilon) = 0;
private:
    const std::function<double(double)> f;
};
