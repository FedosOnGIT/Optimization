#include <iostream>
#include <cmath>
using namespace std;

const double LEFT = (3 - sqrt(5)) / 2;
const double RIGHT = (sqrt(5) - 1) / 2;

double f(double argument) {
    return log10(argument - 2) * log10(argument - 2) + log10(10 - argument) * log10(10 - argument) - pow(argument, 0.2);
}

double minimal(double start, double finish, double epsilon) {
    double x1 = (finish - start) * LEFT + start;
    double x2 = (finish - start) * RIGHT + start;
    double f1 = f(x1);
    double f2 = f(x2);
    int index = 1;
    while (finish - start > epsilon) {
        cout << "Iteration number " << index << ": start=" << start << ", finish=" << finish << ", x1=" << x1 << ", f(x1)=" << f1 << ", x2=" << x2 << ", f(x2)=" << f2 << ".\n";
        index++;
        if (f1 < f2) {
            finish = x2;
            x2 = x1;
            f2 = f1;
            x1 = (finish - start) * LEFT + start;
            f1 = f(x1);
        } else {
            start = x1;
            x1 = x2;
            f1 = f2;
            x2 = (finish - start) * RIGHT + start;
            f2 = f(x2);
        }
    }
    cout << "Iteration number " << index << ": start=" << start << ", finish=" << finish << ", x1=" << x1 << ", f(x1)=" << f1 << ", x2=" << x2 << ", f(x2)=" << f2 << ".\n";
    return (finish + start) / 2;
}

int main() {
    double start = -6, finish = 9.9;
    int epsilon_symbols;
    cout << "How many characters after the decimal point in epsilon?" << "\n";
    cin >> epsilon_symbols;
    std::cout.setf(std::iostream::fixed);
    std::cout.precision(epsilon_symbols);
    double epsilon = pow(10, -epsilon_symbols);
    double answer = minimal(start, finish, epsilon);
    cout << "The answer is x = " << answer << ", y = " << f(answer) << ".\n";
}