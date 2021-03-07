#pragma once

#include <cmath>
#include <cstdint>
#include <functional>
#include <vector>
#include <ostream>
#include <map>
#include <string>
#include <sstream>

#include "MinMethod.h"
#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"
#include "logger.h"

namespace Ui {
    class MainWindow;
}

namespace Opt {
    // Вариант 7
    double main_function(double x);
    // Полиномиальная функция №1
    double polynom1(double x);
    // Полиномиальная функция №2
    double polynom2(double x);

    enum FunctionType {
        F1,
        F2,
        F3
    };

    struct FunctionData {
        // Функция
        std::function<double(double)> f;
        // Левая граница графика
        double left;
        // Правая граница графика
        double right;
        // Кол-во точек, которые будет прорисовано на графике
        uint32_t points;
    };

    enum MethodType {
        Dichot,
        GoldenRatio,
        Fibonacci,
        Parabola,
        Brent
    };

    enum ErrorType {
        OK,
        Function,
        Method,
        Eps,
        Delta
    };

    extern const std::vector<FunctionData> FUNCTION_DATA_STORAGE;

    extern const std::map<int, std::string> HEAD_BY_METHOD;

    void evaluate(MethodType methodType, Opt::FunctionData functionData, double eps, double delta, std::ostream& out);
}
