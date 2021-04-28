#include <iostream>
#include <cmath>
#include <vector>
#include <sstream>

#include "logger.h"
#include "DichotMethod.h"
#include "GoldenRatioMethod.h"
#include "FibonacciMethod.h"
#include "ParabolaMethod.h"
#include "BrentMethod.h"

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

static double eps = 1e-13;

// Тестирует метод на определённой функции
template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_func(logger&lgg, std::string const& methodName, std::string const& func_name,
               Method&& method, func_t const& f, double l, double r) {
    double min_x = method.min(f, l, r, eps);
    lgg << methodName << " " << func_name << " argument: " << min_x << ", function value: " << f(min_x) << '\n';
}

// Функция принимает поток вывода результатов тестирования, имя метода и сам класс.
template <typename Method, bool = std::is_same_v<
        std::remove_reference_t<std::remove_cv_t<Method>>,
        MinMethod>>
void test_method(logger& lgg, std::string const& methodName, Method&& method) {
    test_func(lgg, methodName, "f", std::forward<Method>(method), main_func, 6, 9.9);
    test_func(lgg, methodName, "poly_1", std::forward<Method>(method), polynom1, -0.6, 1.5);
    test_func(lgg, methodName, "poly_2", std::forward<Method>(method), polynom2, -2.2, 2.2);
}

std::string to_string_double(double x, int precision = 2) {
    std::stringstream s;
    s.setf(std::stringstream::fixed);
    s.precision(precision);
    s << x;
    return s.str();
}

void main_task() {
    using namespace std;

    eps = 0.5;
    logger cout_log(&cout);
    logger graph_log("graph_log.csv");
    graph_log.println("log(eps)", "Dichot", "GoldenRatio",
                      "Fibonacci","Parabola", "Combined Brent");

    for (int epsilon_degree = -1; epsilon_degree > -15; --epsilon_degree) {
        graph_log.print(epsilon_degree);
        cout_log.println("log(eps)=", epsilon_degree);

        test_method(cout_log, "Dichot",
                    DichotMethod(logger("dichot_log.csv"), graph_log, eps / 2));

        test_method(cout_log, "GoldenRatio",
                    GoldenRatioMethod(logger("golden_ratio_log.csv"), graph_log));

        test_method(cout_log, "Fibonacci",
                    FibonacciMethod(logger("fibonacci_log.csv"), graph_log));

        test_method(cout_log, "Parabola",
                    ParabolaMethod(logger("parabola_log.csv"), graph_log));

        test_method(cout_log, "Combined Brent",
                    BrentMethod(logger("brent_log.csv"), graph_log));

        graph_log.println();
        cout_log.println();
        eps /= 2;
    }
}

void extra_task() {
    std::vector<double> x1 = {6.8, 7.2, 7.6, 8.0};
    std::vector<double> x2 = {8.4, 8.5, 8.6, 8.7};
    const double start_epsilon = 0.1;
    const double r = 9.9;

    logger graph_log("extra_task_graph_log.csv");
    graph_log.println("log(eps)", "x1", "x2", "Parabola");

    for (double l : x1) {
        for (double x2_point : x2) {
            eps = start_epsilon;
            for (int epsilon_degree = -1; epsilon_degree > -15; --epsilon_degree) {
                graph_log.print(epsilon_degree, "\t");
                graph_log.print(to_string_double(l), "\t", to_string_double(x2_point), "\t");
                ParabolaMethod method = ParabolaMethod(logger(nullptr), graph_log, x2_point);
                method.min(main_func, l, r, eps);
                graph_log.println();
                eps /= 10;
            }
            graph_log.println();
        }
    }
}

int main() {
    main_task();
    extra_task();
}
