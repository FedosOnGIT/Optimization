#include <iostream>
#include <cmath>

#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"

double f(double x) {
    return pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2);
}

double l = 6;
double r = 9.9;
double eps = 1e-13;

template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_method(std::ostream& out, Method&& method) {
    double min_x = method.min(f, l, r, eps);
    out << "argument:" << min_x << ", function value: " << f(min_x) << "\n";
}

int main() {
    using namespace std;

    unsigned int epsilon_degree;
    cout << "How many characters after the decimal point in epsilon?" << "\n";
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

    cout << "DichotMethod: ";
    test_method(cout, DichotMethod(eps/2));
    cout << "GoldenRation: ";
    test_method(cout, GoldenRatioMethod());
    // test_method(cout, FibonacciMethod());
    // test_method(cout, ParabolaMethod());
    // test_method(cout, BrentMethod());
}
