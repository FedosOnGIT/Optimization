#include <iostream>
#include <cmath>

#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"

double f(double x) {
    return log10(x - 2) * log10(x - 2) + log10(10 - x) * log10(10 - x) - pow(x, 0.2);
}

int main() {
    // something
}
