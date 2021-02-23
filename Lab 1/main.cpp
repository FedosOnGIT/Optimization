#include <iostream>
#include <cmath>

#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"

double main_func(double x) {
    return pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2);
}

double polynom1(double x) {
    return 3*pow(x, 4) - 4*x*x*x - 12*x*x;
}

double polynom2(double x) {
    return 2*x*x*x - 3*x*x;
}

double eps = 1e-13;

template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_func(std::ostream& out, std::string const& methodName, std::string const& func_name,
               Method&& method, func_t f, double l, double r) {
    double min_x = method.min(f, l, r, eps);
    out << methodName << " " << func_name << " argument: " << min_x << ", function value: " << f(min_x) << '\n';
}

template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_method(std::ostream& out, std::string const& methodName, Method&& method) {
    test_func(out, methodName, "f", std::forward<Method>(method), main_func, 6, 9.9);
    test_func(out, methodName, "poly_1", std::forward<Method>(method), polynom1, -3, 3);
    test_func(out, methodName, "poly_2", std::forward<Method>(method), polynom2, -2, 2);
}

int main() {
    using namespace std;

    unsigned int epsilon_degree;
    cout << "How many characters after the decimal point in epsilon?\n";
    while(true) {
        cin >> epsilon_degree;
        if (epsilon_degree > 14) {
            cout << "Too much. Entry less number.\n";
        } else {
            break;
        }
    }

    eps = pow(10, -static_cast<double>(epsilon_degree))/2;
    cout.setf(iostream::fixed);
    cout.precision(epsilon_degree + 2);

    test_method(cout, "Dichot", DichotMethod(eps/2));
    test_method(cout, "GoldenRatio", GoldenRatioMethod());
    // test_method(cout, "Fibonacci", FibonacciMethod());
    test_method(cout, "Parabola", ParabolaMethod());
    // test_method(cout, "Combined Brent" BrentMethod());
}
