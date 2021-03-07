#include "Defs.h"

double Opt::main_function(double x)  {
    return pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2);
}

double Opt::polynom1(double x)  {
    return 2 * pow(x, 3) - 3 * pow(x, 2);
}

double Opt::polynom2(double x) {
    return 3 * pow(x, 4) - 4 * pow(x, 3) - 12 * pow(x, 2);
}

const std::vector<Opt::FunctionData> Opt::FUNCTION_DATA_STORAGE = {
    {main_function, 6.0, 9.9, 100000},
    {polynom1, -0.6, 1.5, 100000},
    {polynom2, -2.2, 2.2, 100000}
};

const std::map<int, std::string> Opt::HEAD_BY_METHOD = {
    {Dichot, "№	left	               right	               x1	                f(x1)	               x2	               f(x2)	               scale            \n"},
    {GoldenRatio, "№	left	               right	               x1	                f(x1)	               x2	               f(x2)	               scale            \n"},
    {Fibonacci, "№	left	               right	               x1	                f(x1)	               x2	               f(x2)	                fib1	                               fib2	                               scale           \n"},
    {Parabola, "№	x1	                x3	              f(x1)	               f(x3)	               x2	                f(x2)	               x_min	                f(x_min)	               scale             \n"},
    {Brent, "№	left	               right	               min	               f(min)	               2min	               f(2min)	               prev_2min	               f(prev_2min)	parabola_min	f(parabola_min)	scale             \n"}
};

MinMethod* Opt::getMethod(MethodType methodType, double delta, std::ostream& logger) {
    using namespace Opt;
    switch (methodType) {
        case Dichot: {
            return new DichotMethod(logger, delta);
        }
        case GoldenRatio : {
            return new GoldenRatioMethod(logger);
        }
        case Fibonacci: {
            return new FibonacciMethod(logger);
        }
        case Parabola: {
            return new ParabolaMethod(logger);
        }
        case Brent: {
            return new BrentMethod(logger);
        }
    }
    throw std::invalid_argument( "received wrong method");
}
