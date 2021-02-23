#pragma once

#include "MinMethod.h"

struct FibonacciMethod : MinMethod {
    explicit FibonacciMethod(std::string const& output = "fibonacci_log.txt")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        // TODO
        return 0;
    }
};