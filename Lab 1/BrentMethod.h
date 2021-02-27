#pragma once

#include "MinMethod.h"

class BrentMethod : MinMethod {
public:
    explicit BrentMethod(std::string const& output = "brent_log.txt")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        // TODO
        return 0;
    }
};