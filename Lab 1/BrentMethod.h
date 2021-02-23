#pragma once

#include "MinMethod.h"

struct BrentMethod : MinMethod {
    explicit BrentMethod(std::string const& output = "brent_log.txt")
        : MinMethod(output) {}

    double min(func_t const& f, double l, double r, double eps) override {
        // TODO
        return 0;
    }
};