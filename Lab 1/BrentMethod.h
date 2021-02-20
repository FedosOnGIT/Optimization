#pragma once

#include "BaseMethod.h"

struct BrentMethod : BaseMethod {
    BrentMethod(std::function<double(double)> f)
        : BaseMethod(f)
    {}

    double min(double left, double right, double epsilon) override {
        // TODO
        return 0;
    }
};