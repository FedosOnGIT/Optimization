#include <bits/stdc++.h>
using namespace std;

double f(double argument) {
    return log10(argument - 2) * log10(argument - 2) + log10(10 - argument) * log10(10 - argument) - pow(argument, 0.2);
}

double minimal(double start, double finish, double epsilon, double delta) {
    assert(epsilon > delta);
    if (finish - start < epsilon) {
        double x = (finish + start) / 2;
        return x;
    }
    double x1 = (finish + start - delta) / 2;
    double x2 = (finish + start + delta) / 2;
    double f1 = f(x1);
    double f2 = f(x2);
    if (f1 < f2) {
        return minimal(start, x2, epsilon, delta);
    } else {
        return minimal(x1, finish, epsilon, delta);
    }
}

int main() {
    double start = 6, finish = 9.9;
    int epsilon_degree, delta_degree;
    cout << "How many characters after the decimal point in epsilon?" << "\n";
    cin >> epsilon_degree;
    cout << "How many characters after the decimal point in delta?" << "\n";
    cin >> delta_degree;
    while (epsilon_degree >= delta_degree) {
        cout << "Wrong! Epsilon must be more than delta!" << "\n";
        cout << "How many characters after the decimal point in epsilon?" << "\n";
        cin >> epsilon_degree;
        cout << "How many characters after the decimal point in delta?" << "\n";
        cin >> delta_degree;
    }
    double epsilon = pow(10, -epsilon_degree);
    double delta = pow(10, -delta_degree);
    double answer = minimal(start, finish, epsilon, delta);
    cout << "Answer is x = " << answer << ", y = " << f(answer) << ".\n";
}
