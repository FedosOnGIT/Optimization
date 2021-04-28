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
    {Brent, "№	left	               right	                min	                f(min)	               2min	               f(2min)	               prev_2min	               f(prev_2min)	 parabola?    new point               f(new point)             scale\n"}
};

void Opt::evaluate(MethodType methodType, Opt::FunctionData functionData, double eps, double delta, std::ostream& out) {
    using namespace Opt;
    logger log(&out, 15), cmn_log(nullptr);
    MinMethod* method;
    switch (methodType) {
        case Dichot: {
            method = new DichotMethod(std::move(log), cmn_log, delta);
            break;
        }
        case GoldenRatio : {
            method = new GoldenRatioMethod(std::move(log), cmn_log);
            break;
        }
        case Fibonacci: {
            method = new FibonacciMethod(std::move(log), cmn_log);
            break;
        }
        case Parabola: {
            method = new ParabolaMethod(std::move(log), cmn_log);
            break;
        }
        case Brent: {
            method = new BrentMethod(std::move(log), cmn_log);
            break;
        }
        default: {
            throw std::invalid_argument( "received wrong method");
        }
    }
    method->min(functionData.f, functionData.left, functionData.right, eps);
    delete method;
}
