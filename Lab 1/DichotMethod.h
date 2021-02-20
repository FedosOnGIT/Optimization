#pragma once

#include "BaseMethod.h"

struct DichotMethod : BaseMethod {
    DichotMethod(std::function<double(double)> f, double delta)
        : BaseMethod(f), delta(delta)
    {}

    double getDelta() const {
        return delta;
    }

    void setDelta(double delta) {
        this->delta = delta;
    }

    double min(double left, double right, double epsilon) override {
        // TODO
        return left;
    }
private:
    double delta;
};
