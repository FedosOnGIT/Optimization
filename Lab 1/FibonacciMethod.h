#pragma once

#include "BaseMethod.h"

struct FibonacciMethod : BaseMethod {
    FibonacciMethod(std::function<double(double)> f)
        : BaseMethod(f)
    {}

    double min(double left, double right, double epsilon) override {
        // TODO
        return 0;
    }
};