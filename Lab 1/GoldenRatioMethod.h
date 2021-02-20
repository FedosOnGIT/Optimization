#pragma once

#include "BaseMethod.h"
#include <cmath>

struct GoldenRatioMethod : BaseMethod {
    GoldenRatioMethod(std::function<double(double)> f)
        : BaseMethod(f)
    {}

    double min(double left, double right, double epsilon) override {
        // TODO
        return 0;
    }
 private:
    const double X1_FACTOR = 2 / (3 + sqrt(5));
    const double X2_FACTOR = 2 / (sqrt(5) + 1);
};
