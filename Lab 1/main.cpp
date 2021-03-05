#include <iostream>
#include <cmath>

#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"

#include "logger.h"

// Вариант 7
double main_func(double x) {
    return pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2);
}

// Полиномиальная функция №1
double polynom1(double x) {
    return 2*x*x*x - 3*x*x;
}

// Полиномиальная функция №2
double polynom2(double x) {
    return 3 * pow(x, 4) - 4 * x * x * x - 12 * x * x;
}

double eps = 1e-13;

// Тестирует метод на определённой функции
template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_func(logger& out, std::string const& methodName, std::string const& func_name,
               Method&& method, func_t const& f, double l, double r) {
    double min_x = method.min(f, l, r, eps);
    out << methodName << " " << func_name << " argument: " << min_x << ", function value: " << f(min_x) << '\n';
}

// Функция принимает поток вывода результатов тестирования, имя метода и сам класс.
template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_method(logger& out, std::string const& methodName, Method&& method) {
    test_func(out, methodName, "f", std::forward<Method>(method), main_func, 6, 9.9);
    test_func(out, methodName, "poly_1", std::forward<Method>(method), polynom1, -0.6, 1.5);
    test_func(out, methodName, "poly_2", std::forward<Method>(method), polynom2, -2.2, 2.2);
}


int main() {
    using namespace std;

    unsigned int epsilon_degree = 6;

    /*
    cout << "How many characters after the decimal point in epsilon?\n";
    while(true) {
        cin >> epsilon_degree;
        if (epsilon_degree > 14) {
            cout << "Too much. Entry less number.\n";
        } else {
            break;
        }
    }
    */

    eps = pow(10, -static_cast<double>(epsilon_degree))/2;
    cout.setf(iostream::fixed);
    cout.precision(epsilon_degree + 2);

    logger log_to_cout(&cout);
    log_to_cout.print(5);
    //log_to_cout.println(5);

    /*test_method(log_to_cout, "Dichot",
                DichotMethod(logger("dichot_log.csv"), eps/2));

    test_method(log_to_cout, "GoldenRatio",
                GoldenRatioMethod(logger("golden_ratio_log.csv")));

    test_method(log_to_cout, "Fibonacci",
                FibonacciMethod(logger("fibonacci_log.csv")));

    test_method(log_to_cout, "Parabola",
                ParabolaMethod(logger("parabola_log.csv")));

    test_method(log_to_cout, "Combined Brent",
                BrentMethod(logger("brent_log.csv")));*/
}
