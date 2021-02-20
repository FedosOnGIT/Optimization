#pragma once

#include "BaseMethod.h"

struct ParabolaMethod : BaseMethod {
    ParabolaMethod(std::function<double(double)> f)
        : BaseMethod(f)
    {}

    double min(double left, double right, double epsilon) override {
        // TODO
        return 0;
    }
};
